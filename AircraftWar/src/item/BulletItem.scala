package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft
import factory.WeaponFactory

import misc.typing.WeaponType.SHOTGUN

import application.{CornJob, Command}
import application.MusicController

class CancelBullet extends Command {
    def execute() = {
        val hero = application.Game.getHero()
        hero.decBulletNum()

    }
}

class BulletItem(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int
) extends AbstractItem {

    def effect(o: Game.type): Unit = {
        // o.getHero().setWeapon(WeaponFactory.spawn(SHOTGUN))
        MusicController.supply()
        o.getHero().setShootNum(o.getHero().getShootNum() + 1)
        o.getHero().incBulletNum()
        application.CornJob(new CancelBullet, 2000)
        vanish()
    }
}
