package bullet

import basic.AbstractFlyingObject

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

    // 判定 x 轴出界
    if (locationX <= 0 || locationX >= 512) {
      vanish()
    }

    // 判定 y 轴出界
    if (speedY > 0 && locationY >= 768) {
      // 向下飞行出界
      vanish()
    } else if (locationY <= 0) {
      // 向上飞行出界
      vanish()
    }
  }

  def getPower(): Int = {
    power
  }
}
