package aircraft;

import bullet.AbstractBullet;
import basic.AbstractFlyingObject;

import java.util.List;
import scala.jdk.CollectionConverters._

import scala.collection.mutable.ListBuffer

/** 所有种类飞机的抽象父类： 敌机（BOSS, ELITE, MOB），英雄飞机
  *
  * @author
  *   hitsz
  */
abstract class AbstractAircraft(
    locationX: Int,
    locationY: Int,
    speedX: Int,
    speedY: Int
) extends AbstractFlyingObject(locationX, locationY, speedX, speedY) {

    /** 生命值
      */
    var maxHp: Int = 0
    var hp: Int = 0

    def this(
        locationX: Int,
        locationY: Int,
        speedX: Int,
        speedY: Int,
        hp: Int
    ) {
        this(locationX, locationY, speedX, speedY)
        this.hp = hp
        this.maxHp = hp
    }

    def decreaseHp(decrease: Int) = {
        hp -= decrease
        if (hp <= 0) {
            hp = 0
            vanish()
        }
    }

    def getHp(): Int = hp

    /** 飞机射击方法，可射击对象必须实现
      *
      * @return
      *   可射击对象需实现，返回子弹 非可射击对象空实现，返回null
      */
    def shoot(): ListBuffer[AbstractBullet]

}
