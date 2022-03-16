package bullet

import basic.AbstractFlyingObject
import application.Main
import aircraft.AbstractAircraft

trait Effect {
    def effect[T <: AbstractAircraft](o: T)
}

abstract class AbstractBullet(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int
) extends AbstractFlyingObject(_locationX, _locationY, _speedX, _speedY)
    with Effect {

    var power = 10

    def this(
        _locationX: Int,
        _locationY: Int,
        _speedX: Int,
        _speedY: Int,
        _power: Int
    ) = {
        this(_locationX, _locationY, _speedX, _speedY)
        power = _power
    }

    def this(
        _power: Int
    ) = {
        this(0, 0, 0, 0)
        power = _power
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
