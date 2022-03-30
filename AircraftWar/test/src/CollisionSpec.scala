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

class HeroCollisionSpec extends AnyFlatSpec with should.Matchers {

    "HeroAircraft" should "Collision" in {
        val hero = HeroAircraft
        val enemy =
            EnemyFactory.spawn(
              TRIVIAL,
              (hero.getLocationX(), hero.getLocationY())
            )
        hero.crash(enemy) should be(true)
    }

    "HeroAircraft" should "not Collision" in {
        val hero = HeroAircraft
        val enemy =
            EnemyFactory.spawn(
              TRIVIAL,
              (hero.getLocationX() + 100, hero.getLocationY() + 100)
            )
        hero.crash(enemy) should be(false)
    }
}

class EnemyCollisionSpec extends AnyFlatSpec with should.Matchers {

    "Enemy" should "Collision" in {
        val hero = HeroAircraft
        val enemy =
            EnemyFactory.spawn(
              TRIVIAL,
              (hero.getLocationX(), hero.getLocationY())
            )
        enemy.crash(hero) should be(true)
    }

    "Enemy" should "not Collision" in {
        val hero = HeroAircraft
        val enemy =
            EnemyFactory.spawn(
              ELITE,
              (hero.getLocationX() + 100, hero.getLocationY() + 100)
            )
        enemy.crash(hero) should be(false)
    }
}

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

class BulletCollisionSpec extends AnyFlatSpec with should.Matchers {

    "HeroBullet" should "Collision" in {
        val bullet_factory = new BulletFactory(AircraftType.HERO)
        val bullet = bullet_factory.spawn()
        val enemy =
            EnemyFactory.spawn(
              TRIVIAL,
              (bullet.getLocationX(), bullet.getLocationY())
            )
        enemy.crash(bullet) should be(true)
    }

    "HeroBullet" should "not Collision" in {
        val bullet_factory = new BulletFactory(AircraftType.HERO)
        val bullet = bullet_factory.spawn()
        val enemy =
            EnemyFactory.spawn(
              TRIVIAL,
              (bullet.getLocationX() + 100, bullet.getLocationY() + 100)
            )
        enemy.crash(bullet) should be(false)
    }
}
