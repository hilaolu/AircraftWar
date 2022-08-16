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
import application.Game
import application.Agent
import scala.collection.mutable.ListBuffer

class ItemCollisionSpec extends AnyFlatSpec with should.Matchers {

    "Item" should "Collision" in {
        val agent = new Agent()
        val hero = new HeroAircraft(agent)
        val item =
            ItemFactory.spawn(
              ItemType.BOMB,
              hero.getLocationX(),
              hero.getLocationY()
            )
        item.crash(hero) should be(true)
    }

    "Item" should "not Collision" in {
        val agent = new Agent()
        val hero = new HeroAircraft(agent)
        val item =
            ItemFactory.spawn(
              ItemType.BOMB,
              hero.getLocationX() + 100,
              hero.getLocationY() + 100
            )
        item.crash(hero) should be(false)
    }
}

class ItemEffectSpec
    extends AnyFlatSpec
    with should.Matchers
    with PrivateMethodTester {
    "Blood" should "increase hp then vanish" in {
        // val game = new Game()
        // val hero = game.getHero()
        // hero.decreaseHp(114514)
        // val blood = ItemFactory.spawn(
        //   ItemType.BLOOD,
        //   hero.getLocationX(),
        //   hero.getLocationY()
        // )
        // blood.effect(game)
        // blood.isValid() should be(false)
        // hero.getHp() should be(30) // may change?
    }

    "Bullet" should "increase shootNum then vanish" in {
        val agents = ListBuffer(new Agent(), new Agent())
        val game = new Game(agents)
        val hero = game.heroAircrafts(1)
        val hero0 = game.heroAircrafts(1)
        hero.setLocation(100, 100)
        val item =
            ItemFactory.spawn(
              ItemType.BOMB,
              hero.getLocationX(),
              hero.getLocationY()
            )
        item.crash(hero) should be(true)
        val crashCheckAction =
            PrivateMethod[Game]('crashCheckAction)

        game invokePrivate crashCheckAction()

        assert(hero.getShootNum() == 2)
        assert(hero0.getShootNum() == 1)
        assert(!item.isValid())

    }
}

class RandomItemSpec extends AnyFlatSpec with should.Matchers {
    "Blood" should "increase hp then vanish" in {
        val blood = ItemFactory.spawn(
          ItemType.RANDOM,
          0,
          0
        )
    }
}
