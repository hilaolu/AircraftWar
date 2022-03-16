package aircraft

import scala.collection.mutable.ListBuffer

import basic.AbstractFlyingObject
import bullet.AbstractBullet

abstract class AbstractAircraft(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int,
    _hp: Int
) extends AbstractFlyingObject(_locationX, _locationY, _speedX, _speedY) {

    val maxHp: Int = _hp
    var hp: Int = _hp

    def decreaseHp(decrease: Int) = {
        hp -= decrease
        if (hp <= 0) {
            hp = 0
            vanish()
        }
    }

    def increaseHp(increase: Int) = {
        hp += increase
        hp = if (hp < maxHp) hp else maxHp
    }

    def getHp(): Int = hp

    def shoot(): ListBuffer[AbstractBullet]

}
