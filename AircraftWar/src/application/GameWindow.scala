package application

import java.awt._
import javax.swing._

import application.Game

object GameWindow {

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

    frame.add(Game)
    frame.setVisible(true)
    ScoreBoard.init()
    Game.action()
}
