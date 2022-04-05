package aircraft.weapon

import factory.BulletFactory
import bullet.AbstractBullet

import scala.collection.mutable.ListBuffer

trait AbstractWeapon {
    def apply[T <: AbstractBullet](
        bullet_factory: BulletFactory,
        x: Int,
        y: Int,
        caller_direction: Int,
        speed: Int = 5,
        shootNum: Int = 1,
        power: Int = 1,
        shoot_fatigue: Int = 0
    ): ListBuffer[AbstractBullet]
}
