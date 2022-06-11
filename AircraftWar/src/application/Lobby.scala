package application

import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer

object Lobby {
    val lobby = new HashMap[Int, ListBuffer[Agent]]()

    def join(id: Int, agent: Agent) {
        lobby.get(id) match {
            case Some(list) => {
                list.append(agent)
                val game = new application.Game(list)
                game.action()
                lobby.remove(id)
            }
            case None => {
                lobby(id) = ListBuffer(agent)
            }

        }
    }
}
