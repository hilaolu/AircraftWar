package aircraft.weapon

import bullet._
import scala.collection.mutable.ListBuffer

import basic.AbstractFlyingObject
import factory.BulletFactory

class Sniper extends AbstractWeapon {
    var fatigue: Int = 0
    var ammo: Int = 0
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
            if (ammo > 0) {
                ammo -= 1
            } else {
                // reload ammo & break
                fatigue = shoot_fatigue
                ammo = shootNum
            }
        }

        val hero = room.getHero()
        val (hero_x, hero_y) = (hero.getLocationX, hero.getLocationY)

        val dx = hero_x - x
        val dy = hero_y - y
        val dis = math.sqrt(dx * dx + dy * dy).toInt

        val speedX: Int = speed * dx / dis

        val speedY: Int = speed * dy / dis

        val newBullet = bullet_factory.spawn()
        newBullet.setVX(speedX)
        newBullet.setVY(speedY)
        newBullet.setX(x)
        newBullet.setY(y)
        res.addOne(newBullet)

        res

    }
}
