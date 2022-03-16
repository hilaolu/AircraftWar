package bullet

import aircraft.AbstractAircraft

class EnemyBullet(
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
        o.decreaseHp(power)
        vanish()
    }
}
