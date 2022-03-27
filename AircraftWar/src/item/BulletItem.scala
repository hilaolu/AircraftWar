package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft

class BulletItem(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int
) extends AbstractItem {

    def effect(o: Game.type): Unit = {
        println("[Bullet Item Applied]")
    }
}
