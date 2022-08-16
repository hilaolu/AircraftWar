package application

import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream

import application.RoomElement
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor
import org.apache.commons.lang3.concurrent.BasicThreadFactory
import java.util.concurrent.TimeUnit
import ujson.Obj

object Agent {
    final val executorService: ScheduledExecutorService =
        new ScheduledThreadPoolExecutor(
          1,
          new BasicThreadFactory.Builder()
              .namingPattern("game-action-%d")
              .daemon(true)
              .build()
        )
}

class Agent extends RoomElement {

    var x: Int = 0
    var y: Int = 0

    val timeInterval = 40

    var pollStream: Option[BufferedReader] = None
    var pushStream: Option[PrintStream] = None

    var room_id = 114514

    def getLocation() = {
        (x, y)
    }

    def poll() = {
        pollStream match {
            case Some(s) => {
                if (s.ready()) {
                    val loc = s.readLine().split(",").map(_.toInt)
                    if (loc.length == 1) {
                        if (loc(0) == -1) {
                            Lobby.start(room_id)
                        } else {
                            room_id = loc(0)
                            val result = Lobby.join(room_id, this)
                            push(result)
                        }
                    } else {
                        x = loc(0)
                        y = loc(1)
                    }
                }
            }
            case None => {
                pid.cancel(true)
            }
        }
    }

    def push(content: String) = {
        pushStream match {
            case Some(p) => {
                p.println(content)
                p.flush()
            }
            case None => ()
        }
    }

    def setConnection(s: Socket) = {
        pollStream = Some(
          new BufferedReader(
            new InputStreamReader(s.getInputStream())
          )
        )

        pushStream = Some(
          new PrintStream(
            s.getOutputStream()
          )
        )

    }

    class task extends Runnable {
        override def run() = {
            poll()
        }
    }

    var pid = Agent.executorService.scheduleWithFixedDelay(
      new task,
      timeInterval,
      timeInterval,
      TimeUnit.MILLISECONDS
    )

}