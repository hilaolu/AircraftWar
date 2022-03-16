package application

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

import aircraft.HeroAircraft

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
                    // out of canvas, do nothing
                } else {
                    heroAircraft.setLocation(x, y)
                }
            }
        }

        game.addMouseListener(mouseAdapter)
        game.addMouseMotionListener(mouseAdapter)
    }

}
