package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft
import application.MusicController

class BombItem(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int
) extends AbstractItem {

    def effect(o: Game.type): Unit = {
        MusicController.bomb()
        println("[Bomb Item Applied]")
    }
}
