package application

import aircraft.HeroAircraft

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

/** 英雄机控制类 监听鼠标，控制英雄机的移动
  *
  * @author
  *   hitsz
  */
object HeroController {
    // var game: Game
    // var heroAircraft: HeroAircraft
    // var mouseAdapter: MouseAdapter
    def apply(game: Game, heroAircraft: HeroAircraft) = {
        // this.game = game;
        // this.heroAircraft = heroAircraft;

        var mouseAdapter = new MouseAdapter() {

            override def mouseDragged(e: MouseEvent) = {
                super.mouseDragged(e);
                val x = e.getX()
                val y = e.getY()
                if (x < 0 || x > 512 || y < 0 || y > 768) {
                    // 防止超出边界
                } else {
                    heroAircraft.setLocation(x, y)
                }
            }
        }

        game.addMouseListener(mouseAdapter);
        game.addMouseMotionListener(mouseAdapter);
    }

}
