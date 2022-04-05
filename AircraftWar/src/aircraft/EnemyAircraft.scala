package aircraft

import application.Game
import application.Main
import bullet.AbstractBullet
import bullet.EnemyBullet

import weapon._

import scala.collection.mutable.ListBuffer
import factory.BulletFactory

abstract class EnemyAircraft extends AbstractAircraft {

    private val bullet_power = 1

    private val direction: Int = 1

    val score: Int

    override def forward() = {
        super.forward()
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish()
        }
    }

    val weapon = new MachineGun

    val bullet_factory = BulletFactory(misc.typing.AircraftType.ENEMY, 1)
    def shoot() = {
        weapon(
          bullet_factory,
          getLocationX(),
          getLocationY(),
          direction,
          speed = 11,
          shootNum = 1,
          shoot_fatigue = 3
        )
    }

    override def vanish(): Unit = {
        if (getHp() == 0) {
            application.Game.addScore(score)
        }
        super.vanish()
    }
}
