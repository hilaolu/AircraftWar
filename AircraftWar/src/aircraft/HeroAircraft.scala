package aircraft

import scala.collection.mutable.ListBuffer

import bullet.AbstractBullet
import bullet.HeroBullet

/** 英雄飞机，游戏玩家操控
  *
  * @author
  *   hitsz
  */
class HeroAircraft(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int,
    _hp: Int
) extends AbstractAircraft(_locationX, _locationY, _speedX, _speedY, _hp) {

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

    override def shoot() = {
        weapon.MachineGun(
          () => new HeroBullet(1),
          getLocationX(),
          getLocationY(),
          direction,
          shootNum = 2
        )
    }
}
