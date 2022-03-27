package aircraft

import application.Game
import application.Main

import bullet.AbstractBullet
import bullet.EnemyBullet
import weapon._

import item.BloodItem

import scala.collection.mutable.ListBuffer

class EliteEnemy(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int,
    var hp: Int
) extends EnemyAircraft {

    private val bullet_power = 2

    private val direction: Int = 1

    val score = 20

    override def shoot() = {
        weapon(
          () => new EnemyBullet(1),
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

        val item = new BloodItem(
          getLocationX(),
          getLocationY(),
          0,
          5
        )

        application.Game.addItem(item)
    }
}
