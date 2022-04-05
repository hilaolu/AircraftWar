package factory

import item._

import misc.typing.ItemType._
import misc.typing.ItemType
import scala.annotation.switch

object ItemFactory {
    def spawn(
        t: ItemType,
        x: Int,
        y: Int,
        vx: Int = 0,
        vy: Int = 5
    ): AbstractItem = {
        t match {
            case BOMB =>
                new BombItem(
                  x,
                  y,
                  vx,
                  vy
                )
            case BULLET =>
                new BulletItem(
                  x,
                  y,
                  vx,
                  vy
                )
            case _ =>
                new BloodItem(
                  x,
                  y,
                  vx,
                  vy
                )
        }
    }
}
