package factory

import item._

object ItemFactory {
    def spawn(
        t: String,
        x: Int,
        y: Int,
        vx: Int = 0,
        vy: Int = 5
    ): AbstractItem = {
        if (t == "bomb") {
            new BombItem(
              x,
              y,
              vx,
              vy
            )
        } else if (t == "bullet") {
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
