package bullet

import basic.AbstractFlyingObject
import application.Main
import aircraft.AbstractAircraft

/** 子弹类。 也可以考虑不同类型的子弹
  *
  * @author
  *   hitsz
  */
abstract class AbstractBullet(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int
) extends AbstractFlyingObject(_locationX, _locationY, _speedX, _speedY) {

    var power = 10

    def this(
        locationX: Int,
        locationY: Int,
        speedX: Int,
        speedY: Int,
        power: Int
    ) = {
        this(locationX, locationY, speedX, speedY)
        this.power = power
    }

    def this(
        power: Int
    ) = {
        this(0, 0, 0, 0)
        this.power = power
    }

    def setX(x: Int) = {
        locationX = x
    }

    def setY(y: Int) = {
        locationY = y
    }

    def setVX(vx: Int) = {
        speedX = vx
    }

    def setVY(vy: Int) = {
        speedY = vy
    }

    def effect[T <: AbstractAircraft](o: T)

    override def forward() = {
        super.forward()

        if (isOutOfCanvas) {
            vanish()
        }
    }

    def getPower(): Int = {
        power
    }
}
