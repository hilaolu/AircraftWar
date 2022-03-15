package application

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

import aircraft.HeroAircraft

/** 英雄机控制类 监听鼠标，控制英雄机的移动
  *
  * @author
  *   hitsz
  */
object HeroController {

    def apply(game: Game, heroAircraft: HeroAircraft) = {

        var mouseAdapter = new MouseAdapter() {

            override def mouseDragged(e: MouseEvent) = {
                super.mouseDragged(e)
                val x = e.getX()
                val y = e.getY()
                if (
                  x < 0 || x > Main.WINDOW_WIDTH || y < 0 || y > Main.WINDOW_HEIGHT
                ) {
                    // 防止超出边界
                } else {
                    heroAircraft.setLocation(x, y)
                }
            }
        }

        game.addMouseListener(mouseAdapter)
        game.addMouseMotionListener(mouseAdapter)
    }

}
