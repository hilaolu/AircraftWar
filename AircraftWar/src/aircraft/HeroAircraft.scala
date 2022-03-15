package aircraft

import java.util.LinkedList

import scala.collection.mutable.ListBuffer

import bullet.AbstractBullet
import bullet.HeroBullet

/** 英雄飞机，游戏玩家操控
  *
  * @author
  *   hitsz
  */
class HeroAircraft(
    locationX: Int,
    locationY: Int,
    speedX: Int,
    speedY: Int,
    hp: Int
) extends AbstractAircraft(locationX, locationY, speedX, speedY, hp) {

    /** 攻击方式 */

    /** 子弹一次发射数量
      */
    private var shootNum: Int = 1

    /** 子弹伤害
      */
    private var power: Int = 30

    /** 子弹射击方向 (向上发射：1，向下发射：-1)
      */
    private val direction: Int = -1

    override def forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    override def shoot(): ListBuffer[AbstractBullet] = {
        var res: ListBuffer[AbstractBullet] = new ListBuffer()
        var x: Int = this.getLocationX()
        var y: Int = this.getLocationY() + direction * 2
        var speedX: Int = 0
        var speedY: Int = this.getSpeedY() + direction * 5
        for (i <- 0 until shootNum) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            var abstractBullet = new HeroBullet(
              x + (i * 2 - shootNum + 1) * 10,
              y,
              speedX,
              speedY,
              power
            )
            res.addOne(abstractBullet)
        }
        res
    }

}
