package item

import basic.AbstractFlyingObject
import application.Main
import aircraft.AbstractAircraft

abstract class AbstractItem(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int
) extends AbstractFlyingObject(_locationX, _locationY, _speedX, _speedY) {

    override def forward() = {
        super.forward()

        if (isOutOfCanvas) {
            vanish()
        }
    }

    def effect[T <: AbstractAircraft](o: T)

}
