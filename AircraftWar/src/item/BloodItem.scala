package item

import basic.AbstractFlyingObject
import application.Main
import aircraft.AbstractAircraft

/** 子弹类。 也可以考虑不同类型的子弹
  *
  * @author
  *   hitsz
  */
class BloodItem(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int
) extends AbstractItem(_locationX, _locationY, _speedX, _speedY) {

    def effect[T <: AbstractAircraft](o: T): Unit = {
        o.increaseHp(30)
        vanish()
    }

}
