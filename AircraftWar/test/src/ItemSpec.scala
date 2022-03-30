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

class ItemEffectSpec extends AnyFlatSpec with should.Matchers {
    "Blood" should "increase hp then vanish" in {
        val game = Game
        val hero = game.getHero()
        hero.decreaseHp(114514)
        val blood = ItemFactory.spawn(
          ItemType.BLOOD,
          hero.getLocationX(),
          hero.getLocationY()
        )
        blood.effect(game)
        blood.isValid() should be(false)
        hero.getHp() should be(30) // may change?
    }
}
