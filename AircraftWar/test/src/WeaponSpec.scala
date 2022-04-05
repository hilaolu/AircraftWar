package test
import org.scalatest._
import flatspec._
import matchers._

import aircraft.HeroAircraft
import factory.BulletFactory

import misc.typing.EnemyType._
import misc.typing._
import application.Game
import factory.WeaponFactory

import misc.typing.WeaponType.MACHINEGUN

class WeaponSpec extends AnyFlatSpec with should.Matchers {
    "Weapon" should "shoot" in {
        val weapon = WeaponFactory.spawn(MACHINEGUN)
        val bullet_factory = BulletFactory(AircraftType.HERO)
        for (i <- 1 until 6) {
            weapon
                .apply(
                  bullet_factory,
                  0,
                  0,
                  1,
                  shootNum = i
                )
                .length should be(i)
        }
    }
}
