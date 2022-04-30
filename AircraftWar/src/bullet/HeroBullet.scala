package bullet

import aircraft.AbstractAircraft
import application.MusicController

class HeroBullet(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int,
    var power: Int
) extends AbstractBullet {
    def this(power: Int) = {
        this(0, 0, 0, 0, power)
    }

    override def effect[T <: AbstractAircraft](o: T): Unit = {
        MusicController.hit()
        o.decreaseHp(power)
        vanish()
    }
}
