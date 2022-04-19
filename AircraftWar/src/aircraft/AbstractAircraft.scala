package aircraft

import scala.collection.mutable.ListBuffer

import basic.AbstractFlyingObject
import bullet.AbstractBullet
import aircraft.weapon.AbstractWeapon

import misc.typing.WeaponType.MACHINEGUN

import factory.WeaponFactory

abstract class AbstractAircraft extends AbstractFlyingObject {

    var hp: Int
    val maxHp: Int = hp

    var weapon: AbstractWeapon = WeaponFactory.spawn(MACHINEGUN)

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
