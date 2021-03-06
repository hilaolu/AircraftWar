package aircraft

import application.Game
import application.Main

import bullet.AbstractBullet
import bullet.EnemyBullet
import weapon._

import scala.collection.mutable.ListBuffer
import factory.ItemFactory

import misc.typing.ItemType.RANDOM

class EliteEnemy(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int,
    var hp: Int
) extends EnemyAircraft {

    private val bullet_power = 4

    private val direction: Int = 1

    val score = 20

    override def shoot() = {
        weapon(
          bullet_factory,
          getLocationX(),
          getLocationY(),
          direction,
          7,
          shootNum = 6,
          shoot_fatigue = 30
        )
    }

    override def vanish(): Unit = {
        super.vanish()

        val item = ItemFactory.spawn(
          RANDOM,
          getLocationX(),
          getLocationY()
        )
        application.Game.addItem(item)
    }
}
