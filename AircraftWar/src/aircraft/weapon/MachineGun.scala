package aircraft.weapon

import bullet._
import scala.collection.mutable.ListBuffer

class MachineGun {
    var fatigue: Int = 0

    def apply[T <: AbstractBullet](
        bullet_factory: () => T,
        x: Int,
        y: Int,
        caller_direction: Int,
        speed: Int = 5,
        shootNum: Int = 1,
        power: Int = 1,
        shoot_fatigue: Int = 0
    ): ListBuffer[AbstractBullet] = {

        var res: ListBuffer[AbstractBullet] = new ListBuffer()

        if (this.fatigue > 0) {
            this.fatigue -= 1
            return res
        } else {
            this.fatigue = shoot_fatigue
        }

        var speedX: Int = 0

        var speedY = speed

        for (i <- 0 until shootNum) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            var newBullet = bullet_factory()
            newBullet.setVX(speedX)
            newBullet.setVY(speedY * caller_direction)
            newBullet.setX(x + (i * 2 - shootNum + 1) * 10)
            newBullet.setY(y)
            res.addOne(newBullet)
        }

        res

    }
}
