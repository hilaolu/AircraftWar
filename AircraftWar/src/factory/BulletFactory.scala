package factory

import bullet.AbstractBullet
import bullet.HeroBullet
import bullet.EnemyBullet

object BulletFactory {
    def spawn(t: String, power: Int = 1): AbstractBullet = {
        if (t == "hero") {
            new HeroBullet(power)
        } else {
            new EnemyBullet(power)
        }
    }
}

class BulletFactory(t: String, var power: Int = 1) {

    def spawn(): AbstractBullet = {
        if (t == "hero") {
            new HeroBullet(power)
        } else {
            new EnemyBullet(power)
        }
    }
}
