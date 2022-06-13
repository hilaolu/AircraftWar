package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft
import aircraft.HeroAircraft

trait Effect {
    def effect(o: Game, h: HeroAircraft)
}

abstract class AbstractItem extends AbstractFlyingObject with Effect {

    override def forward() = {
        super.forward()

        if (isOutOfCanvas) {
            vanish()
        }
    }

}
