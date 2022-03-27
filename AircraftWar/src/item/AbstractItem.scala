package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft

trait Effect {
    def effect(o: Game.type)
}

abstract class AbstractItem extends AbstractFlyingObject with Effect {

    override def forward() = {
        super.forward()

        if (isOutOfCanvas) {
            vanish()
        }
    }

}
