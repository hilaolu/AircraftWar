package aircraft

import scala.collection.mutable.ListBuffer

import bullet.AbstractBullet
import bullet.HeroBullet

import misc.typing.WeaponType.MACHINEGUN
import misc.typing.WeaponType.SHOTGUN
import basic.AbstractFlyingObject
import application.ImageManager
import application.Main
import factory.BulletFactory
import factory.WeaponFactory
import aircraft.weapon.ShotGun
import application.Agent

class HeroAircraft(agent: Agent) extends AbstractAircraft {
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

    override def getLocationX() = {
        agent.x
    }

    override def getLocationY() = {
        agent.y
    }
    override def forward() = {
        // null function
    }

    val bullet_factory = BulletFactory(misc.typing.AircraftType.HERO, 1)
    override def shoot() = {
        weapon(
          bullet_factory,
          getLocationX(),
          getLocationY(),
          direction,
          shootNum = shootNum,
          power = power
        )
    }

    def getShootNum(): Int = shootNum

    def setShootNum(num: Int) = {
        this.shootNum = num
    }

    var bullet_num = 0

    def incBulletNum() = {
        bullet_num += 1;
        weapon = WeaponFactory.spawn(SHOTGUN)
    }

    def decBulletNum() = {
        bullet_num -= 1;
        if (bullet_num == 0) {
            weapon = WeaponFactory.spawn(MACHINEGUN)
        }
    }

    override def getWidth(): Int = ImageManager.HERO_IMAGE.getWidth()

    override def getHeight(): Int = ImageManager.HERO_IMAGE.getHeight()

    // override def crash(o: AbstractFlyingObject): Boolean = false
}
