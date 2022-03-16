package application

import java.awt._
import javax.swing._

import application.Game

object Main extends App {

    val WINDOW_WIDTH = 512
    val WINDOW_HEIGHT = 768
    var debug = false

    var screenSize: Dimension = Toolkit.getDefaultToolkit().getScreenSize()
    var frame: JFrame = new JFrame("Aircraft War")
    frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT)
    frame.setResizable(false)
    frame.setBounds(
      ((screenSize.getWidth() - WINDOW_WIDTH) / 2).toInt,
      0,
      WINDOW_WIDTH,
      WINDOW_HEIGHT
    )
    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)

    if (args.length != 0 && args(0) == "--debug") {
        println("[DEBUG ON]")
        debug = true
    }

    var game = new Game()
    frame.add(game)
    frame.setVisible(true)
    game.action()
}
