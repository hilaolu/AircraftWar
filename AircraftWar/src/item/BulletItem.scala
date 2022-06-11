package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft
import factory.WeaponFactory

import misc.typing.WeaponType.SHOTGUN

import application.{CornJob, Command}

class CancelBullet(context: application.Game) extends Command {
    def execute() = {
        val hero = context.getHero()
        hero.decBulletNum()

    }
}

class BulletItem(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int
) extends AbstractItem {

    def effect(o: Game): Unit = {
        // o.getHero().setWeapon(WeaponFactory.spawn(SHOTGUN))
        o.getHero().setShootNum(o.getHero().getShootNum() + 1)
        o.getHero().incBulletNum()
        application.CornJob(new CancelBullet(o), 2000)
        vanish()
    }
}
