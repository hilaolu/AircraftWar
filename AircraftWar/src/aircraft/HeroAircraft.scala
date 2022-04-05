package aircraft

import scala.collection.mutable.ListBuffer

import bullet.AbstractBullet
import bullet.HeroBullet

import weapon._
import basic.AbstractFlyingObject
import application.ImageManager
import application.Main
import factory.BulletFactory

object HeroAircraft extends AbstractAircraft {
    var locationX: Int = Main.WINDOW_WIDTH / 2
    var locationY: Int =
        Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight()
    var speedX: Int = 0
    var speedY: Int = 0
    var hp: Int = 100
    override val maxHp: Int = hp

    private var shootNum: Int = 1

    private var power: Int = 3

    private val direction: Int = -1

    override def forward() {
        // null function
    }

    val weapon = new MachineGun
    val bullet_factory = BulletFactory(misc.typing.AircraftType.HERO, 1)
    override def shoot() = {
        weapon(
          bullet_factory,
          getLocationX(),
          getLocationY(),
          direction,
          shootNum = 2,
          power = power
        )
    }

    override def getWidth(): Int = ImageManager.HERO_IMAGE.getWidth()

    override def getHeight(): Int = ImageManager.HERO_IMAGE.getHeight()

    // override def crash(o: AbstractFlyingObject): Boolean = false
}
