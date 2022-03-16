package aircraft

import scala.collection.mutable.ListBuffer

import bullet.AbstractBullet
import bullet.HeroBullet

import weapon._

class HeroAircraft(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int,
    var hp: Int
) extends AbstractAircraft {

    private var shootNum: Int = 1

    private var power: Int = 3

    private val direction: Int = -1

    override def forward() {
        // null function
    }

    val weapon = new MachineGun
    override def shoot() = {
        weapon(
          () => new HeroBullet(1),
          getLocationX(),
          getLocationY(),
          direction,
          shootNum = 2,
          power = power
        )
    }
}
