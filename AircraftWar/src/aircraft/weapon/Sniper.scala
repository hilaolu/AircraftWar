package aircraft.weapon

import bullet._
import scala.collection.mutable.ListBuffer

import basic.AbstractFlyingObject

class Sniper {
    var fatigue: Int = 0
    var ammo: Int = 0
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

        if (fatigue > 0) {
            fatigue -= 1
            return res
        } else {
            if (ammo > 0) {
                ammo -= 1
            } else {
                // reload ammo & break
                fatigue = shoot_fatigue
                ammo = shootNum
            }
        }

        val (hero_x, hero_y) = application.Main.game.getHeroLocation()

        val dx = hero_x - x
        val dy = hero_y - y
        val dis = math.sqrt(dx * dx + dy * dy).toInt

        var speedX: Int = speed * dx / dis

        var speedY: Int = speed * dy / dis

        // 子弹发射位置相对飞机位置向前偏移
        // 多个子弹横向分散
        var newBullet = bullet_factory()
        newBullet.setVX(speedX)
        newBullet.setVY(speedY)
        newBullet.setX(x)
        newBullet.setY(y)
        res.addOne(newBullet)

        res

    }
}
