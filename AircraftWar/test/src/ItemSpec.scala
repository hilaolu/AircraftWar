package test
import org.scalatest._
import flatspec._
import matchers._

import aircraft.HeroAircraft
import factory.EnemyFactory
import factory.ItemFactory
import factory.BulletFactory

import misc.typing.EnemyType._
import misc.typing._

class ItemCollisionSpec extends AnyFlatSpec with should.Matchers {

    "Item" should "Collision" in {
        val hero = HeroAircraft
        val item =
            ItemFactory.spawn(
              ItemType.BOMB,
              hero.getLocationX(),
              hero.getLocationY()
            )
        item.crash(hero) should be(true)
    }

    "Item" should "not Collision" in {
        val hero = HeroAircraft
        val item =
            ItemFactory.spawn(
              ItemType.BOMB,
              hero.getLocationX() + 100,
              hero.getLocationY() + 100
            )
        item.crash(hero) should be(false)
    }
}
