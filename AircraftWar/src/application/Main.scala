package application

import java.awt._
import javax.swing._

import application.Game
import scala.collection.mutable.ListBuffer
import java.net.ServerSocket

object Main extends App {

    val WINDOW_WIDTH = 512
    val WINDOW_HEIGHT = 768
    var debug = false

    if (args.length != 0 && args(0) == "--debug") {
        println("[DEBUG ON]")
        debug = true
    }

    println("[Game Start]")

    val server_socket = new ServerSocket(11451)
    while (true) {
        val client_socket = server_socket.accept()
        val agent = new Agent()
        agent.setConnection(client_socket)
    }

    Thread.sleep(1919810114)

}
