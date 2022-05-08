package aircraft

import application.Game
import application.Main
import bullet.AbstractBullet
import bullet.EnemyBullet

import weapon._

import scala.collection.mutable.ListBuffer
import factory.BulletFactory
import factory.WeaponFactory

import misc.typing.WeaponType.MACHINEGUN
import item.BombSubscriber

abstract class EnemyAircraft extends AbstractAircraft with BombSubscriber {

    private val bullet_power = 4

    private val direction: Int = 1

    val score: Int

    item.BombPublisher.subscribe(this)

    override def forward() = {
        super.forward()
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish()
        }
    }

    val bullet_factory =
        BulletFactory(misc.typing.AircraftType.ENEMY, bullet_power)
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
        item.BombPublisher.unsubscribe(this)
        super.vanish()
        application.Game.addScore(score)
    }

    override def update(): Unit = {
        vanish()
    }

}
