package aircraft

import application.Game
import application.Main
import bullet.AbstractBullet
import bullet.EnemyBullet

import scala.collection.mutable.ListBuffer

/** 普通敌机 不可射击
  *
  * @author
  *   hitsz
  */
class EliteAircraft(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int,
    _hp: Int
) extends EnemyAircraft(_locationX, _locationY, _speedX, _speedY, _hp) {

    private var bullet_power = 2

    private val direction: Int = 1

    val machine_gun = new weapon.MachineGun

    override def shoot() = {
        machine_gun(
          () => new EnemyBullet(1),
          getLocationX(),
          getLocationY(),
          direction,
          7,
          shootNum = 6,
          shoot_fatigue = 30
        )
    }
}
