package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft

class BloodItem(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int
) extends AbstractItem {

    def effect(o: Game): Unit = {
        o.getHero.increaseHp(30)
        vanish()
    }

}
