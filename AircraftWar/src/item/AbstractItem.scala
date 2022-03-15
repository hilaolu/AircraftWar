package item

import basic.AbstractFlyingObject
import application.Main

/** 子弹类。 也可以考虑不同类型的子弹
  *
  * @author
  *   hitsz
  */
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

    def effect()

}
