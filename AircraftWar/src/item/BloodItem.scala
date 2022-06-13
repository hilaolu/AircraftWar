package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft
import aircraft.HeroAircraft

class BloodItem(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int
) extends AbstractItem {

    def effect(o: Game, h: HeroAircraft): Unit = {
        h.increaseHp(30)
        vanish()
    }

}
