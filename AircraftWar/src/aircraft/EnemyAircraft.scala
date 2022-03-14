package aircraft

import application.Game
import application.Main
import bullet.AbstractBullet

import java.util.LinkedList
import java.util.List

import scala.jdk.CollectionConverters._
import bullet.EnemyBullet

/** 普通敌机 不可射击
  *
  * @author
  *   hitsz
  */
class EnemyAircraft(
    locationX: Int,
    locationY: Int,
    speedX: Int,
    speedY: Int,
    hp: Int
) extends AbstractAircraft(locationX, locationY, speedX, speedY, hp) {

    private var bullet_power = 1

    private val direction: Int = -1

    override def forward() = {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish()
        }
    }

    def shoot: List[AbstractBullet] = {
        var res: List[AbstractBullet] = new LinkedList()
        var abstractBullet = new EnemyBullet(
          locationX,
          locationY + 2,
          0,
          speedY + direction * 5,
          bullet_power
        )
        res.add(abstractBullet)
        res
    }

}
