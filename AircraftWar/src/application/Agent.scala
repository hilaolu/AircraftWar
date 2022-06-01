package application

import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream

class Agent(
) {

    var x: Int = 0
    var y: Int = 0

    var pollStream: Option[BufferedReader] = None
    var pushStream: Option[PrintStream] = None

    def getLocation() = {
        (x, y)
    }

    def poll() = {
        pollStream match {
            case Some(s) => {
                if (s.ready()) {
                    val loc =
                        s.readLine().split(",").map(_.toInt)
                    x = loc(0)
                    y = loc(1)
                }
            }
            case None => ()
        }
    }

    def push() = {
        pushStream match {
            case Some(p) => {
                p.println(Game.genStatusStr())
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

}
