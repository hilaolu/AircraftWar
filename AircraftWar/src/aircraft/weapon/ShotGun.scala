package aircraft.weapon

import bullet._
import scala.collection.mutable.ListBuffer
import factory.BulletFactory

class ShotGun extends AbstractWeapon {
    var fatigue: Int = 0

    def apply[T <: AbstractBullet](
        bullet_factory: BulletFactory,
        x: Int,
        y: Int,
        caller_direction: Int,
        speed: Int = 5,
        shootNum: Int = 1,
        power: Int = 1,
        shoot_fatigue: Int = 0
    ): ListBuffer[AbstractBullet] = {

        val res: ListBuffer[AbstractBullet] = new ListBuffer()

        if (fatigue > 0) {
            fatigue -= 1
            return res
        } else {
            fatigue = shoot_fatigue
        }

        val speedX: Int = 1

        val speedY = speed

        for (i <- 0 until shootNum) {
            val newBullet = bullet_factory.spawn()
            newBullet.setVY(speedY * caller_direction)
            newBullet.setX(x + (i * 2 - shootNum + 1) * 10)
            newBullet.setY(y)
            newBullet.setVX(i * 2 - shootNum + 1)
            res.addOne(newBullet)
        }

        res

    }
}
