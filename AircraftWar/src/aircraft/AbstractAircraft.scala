package aircraft

import scala.collection.mutable.ListBuffer

import basic.AbstractFlyingObject
import bullet.AbstractBullet

/** 所有种类飞机的抽象父类： 敌机（BOSS, ELITE, MOB），英雄飞机
  *
  * @author
  *   hitsz
  */
abstract class AbstractAircraft(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int,
    _hp: Int
) extends AbstractFlyingObject(_locationX, _locationY, _speedX, _speedY) {

    /** 生命值
      */
    var maxHp: Int = _hp
    var hp: Int = _hp

    def decreaseHp(decrease: Int) = {
        hp -= decrease
        if (hp <= 0) {
            hp = 0
            vanish()
        }
    }

    def increaseHp(increase: Int) = {
        hp += increase
        hp = if (hp < maxHp) hp else maxHp
    }

    def getHp(): Int = hp

    /** 飞机射击方法，可射击对象必须实现
      *
      * @return
      *   可射击对象需实现，返回子弹 非可射击对象空实现，返回null
      */
    def shoot(): ListBuffer[AbstractBullet]

}
