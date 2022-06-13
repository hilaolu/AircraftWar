package application

import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer

object Lobby {
    val lobby = new HashMap[Int, ListBuffer[Agent]]()

    def join(id: Int, agent: Agent): String = {
        lobby.get(id) match {
            case Some(list) => {
                list.append(agent)
                "guest"
            }
            case None => {
                lobby(id) = ListBuffer(agent)
                "host"
            }
        }
    }

    def start(id: Int) {
        lobby.get(id) match {
            case Some(list) => {
                val game = new application.Game(list)
                game.action()
                lobby.remove(id)
            }
            case None => {}

        }
    }
}
