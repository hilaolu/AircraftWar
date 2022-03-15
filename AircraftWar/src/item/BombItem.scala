package item

import basic.AbstractFlyingObject
import application.Main

class BombItem(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int
) extends AbstractItem(_locationX, _locationY, _speedX, _speedY) {

    def effect() = {
        println("[Bomb Item Applied]")
    }
}
