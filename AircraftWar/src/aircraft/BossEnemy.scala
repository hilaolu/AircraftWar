package aircraft

import application.Game
import application.Main

import bullet.AbstractBullet
import bullet.EnemyBullet
import weapon._

import item.BloodItem

import scala.collection.mutable.ListBuffer

import factory.BulletFactory
import factory.WeaponFactory

class BossEnemy(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int,
    var hp: Int
) extends EnemyAircraft {

    private val bullet_power = 2

    private val direction: Int = 1

    val score = 30

    weapon = WeaponFactory.spawn(misc.typing.WeaponType.SNIPER)

    override def shoot() = {
        weapon(
          bullet_factory,
          getLocationX(),
          getLocationY(),
          direction,
          7,
          shootNum = 6,
          shoot_fatigue = 5
        )
    }

}
