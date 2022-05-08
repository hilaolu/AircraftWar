package bullet

import basic.AbstractFlyingObject
import application.Main
import aircraft.AbstractAircraft
import item.BombSubscriber

trait Effect {
    def effect[T <: AbstractAircraft](o: T)
}

abstract class AbstractBullet
    extends AbstractFlyingObject
    with Effect
    with BombSubscriber {

    item.BombPublisher.subscribe(this)

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
        item.BombPublisher.unsubscribe(this)
    }

    override def update(): Unit = {
        vanish()
    }

}
