package bullet

import basic.AbstractFlyingObject
import application.Main

/** 子弹类。 也可以考虑不同类型的子弹
  *
  * @author
  *   hitsz
  */
abstract class AbstractBullet(
    locationX: Int,
    locationY: Int,
    speedX: Int,
    speedY: Int
) extends AbstractFlyingObject(locationX, locationY, speedX, speedY) {

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
