package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft
import factory.WeaponFactory

import misc.typing.WeaponType.SHOTGUN

import application.{CornJob, Command}
import aircraft.HeroAircraft

class CancelBullet(h: HeroAircraft) extends Command {
    def execute() = {
        h.decBulletNum()
    }
}

class BulletItem(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int
) extends AbstractItem {

    def effect(o: Game, h: HeroAircraft): Unit = {
        // o.getHero().setWeapon(WeaponFactory.spawn(SHOTGUN))
        h.setShootNum(h.getShootNum() + 1)
        h.incBulletNum()
        application.CornJob(new CancelBullet(h), 2000)
        vanish()
    }
}
