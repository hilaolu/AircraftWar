package bullet

import basic.AbstractFlyingObject
import application.Main
import aircraft.AbstractAircraft

trait Effect {
    def effect[T <: AbstractAircraft](o: T)
}

abstract class AbstractBullet extends AbstractFlyingObject with Effect {

    var power: Int

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
        locationX += speedX
        locationY += speedY
        if (isOutOfCanvas) {
            vanish()
        }
    }

    def getPower(): Int = {
        power
    }

    override def vanish(): Unit = {
        super.vanish()
    }

    def update(): Unit = {
        vanish()
    }

}
