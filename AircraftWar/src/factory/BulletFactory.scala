package factory

import bullet.AbstractBullet
import bullet.HeroBullet
import bullet.EnemyBullet

import misc.typing.AircraftType._
import misc.typing._

object BulletFactory {
    def spawn(t: AircraftType, power: Int = 1): AbstractBullet = {
        if (t == HERO) {
            new HeroBullet(power)
        } else {
            new EnemyBullet(power)
        }
    }

    def apply(t: AircraftType, power: Int = 1): BulletFactory = {
        new BulletFactory(t, power)
    }
}

class BulletFactory(t: AircraftType, var power: Int = 1) {

    def spawn(): AbstractBullet = {
        BulletFactory.spawn(t, power)
    }
}
