package application

import application.Game;
import javax.swing._;
import java.awt._;

/** 程序入口
  * @author
  *   hitsz
  */
object Main extends App {

  val WINDOW_WIDTH = 512
  val WINDOW_HEIGHT = 768

  // 获得屏幕的分辨率，初始化 Frame
  var screenSize: Dimension = Toolkit.getDefaultToolkit().getScreenSize()
  var frame: JFrame = new JFrame("Aircraft War")
  frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT)
  frame.setResizable(false)
  // 设置窗口的大小和位置,居中放置
  frame.setBounds(
    ((screenSize.getWidth() - WINDOW_WIDTH) / 2).toInt,
    0,
    WINDOW_WIDTH,
    WINDOW_HEIGHT
  )
//   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)

  var game = new Game()
  frame.add(game)
  frame.setVisible(true)
  game.action()
}
