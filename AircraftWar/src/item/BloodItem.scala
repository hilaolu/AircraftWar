package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft

class BloodItem(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int
) extends AbstractItem(_locationX, _locationY, _speedX, _speedY) {

    def effect(o: Game): Unit = {
        o.getHero.increaseHp(30)
        vanish()
    }

}
