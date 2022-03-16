package aircraft

import application.Game
import application.Main
import bullet.AbstractBullet
import bullet.EnemyBullet

import weapon._

import scala.collection.mutable.ListBuffer

class TrivialEnemy(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int,
    var hp: Int
) extends EnemyAircraft {

    val score = 10

}
