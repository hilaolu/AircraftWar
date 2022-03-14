package aircraft

import application.Game
import application.Main
import bullet.AbstractBullet

import java.util.LinkedList
import java.util.List

import scala.jdk.CollectionConverters._

/** 普通敌机 不可射击
  *
  * @author
  *   hitsz
  */
class MobEnemy(
    locationX: Int,
    locationY: Int,
    speedX: Int,
    speedY: Int,
    hp: Int
) extends AbstractAircraft(locationX, locationY, speedX, speedY, hp) {

    override def forward() = {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish()
        }
    }

    def shoot: List[AbstractBullet] = {
        new LinkedList()
    }

}
