package factory

import item._

import misc.typing.ItemType._
import misc.typing.ItemType

object ItemFactory {
    def spawn(
        t: ItemType,
        x: Int,
        y: Int,
        vx: Int = 0,
        vy: Int = 5
    ): AbstractItem = {
        if (t == BOMB) {
            new BombItem(
              x,
              y,
              vx,
              vy
            )
        } else if (t == BULLET) {
            new BulletItem(
              x,
              y,
              vx,
              vy
            )
        } else {
            new BloodItem(
              x,
              y,
              vx,
              vy
            )
        }
    }
}
