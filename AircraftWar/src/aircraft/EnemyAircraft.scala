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
class EnemyAircraft(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int,
    _hp: Int
) extends AbstractAircraft(_locationX, _locationY, _speedX, _speedY, _hp) {

    private var bullet_power = 1

    private val direction: Int = -1

    override def forward() = {
        super.forward()
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish()
        }
    }

    def shoot(): ListBuffer[AbstractBullet] = {
        var res: ListBuffer[AbstractBullet] = new ListBuffer()
        var abstractBullet = new EnemyBullet(
          locationX,
          locationY + 2,
          0,
          speedY + direction * 5,
          bullet_power
        )
        res.addOne(abstractBullet)
        res
    }

}
