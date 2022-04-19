package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft
import factory.WeaponFactory

import misc.typing.WeaponType.SHOTGUN

class BulletItem(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int
) extends AbstractItem {

    def effect(o: Game.type): Unit = {
        o.getHero().setWeapon(WeaponFactory.spawn(SHOTGUN))
        o.getHero().setShootNum(o.getHero().getShootNum() + 1)
        vanish()
    }
}
