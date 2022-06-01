// package test
// import org.scalatest._
// import flatspec._
// import matchers._

// import aircraft.HeroAircraft
// import factory.EnemyFactory
// import factory.ItemFactory
// import factory.BulletFactory

// import misc.typing.EnemyType._
// import misc.typing._

// class HeroCollisionSpec extends AnyFlatSpec with should.Matchers {

//     "HeroAircraft" should "Collision" in {
//         val hero = HeroAircraft
//         val enemy =
//             EnemyFactory.spawn(
//               TRIVIAL,
//               (hero.getLocationX(), hero.getLocationY())
//             )
//         hero.crash(enemy) should be(true)
//     }

//     "HeroAircraft" should "not Collision" in {
//         val hero = HeroAircraft
//         val enemy =
//             EnemyFactory.spawn(
//               TRIVIAL,
//               (hero.getLocationX() + 100, hero.getLocationY() + 100)
//             )
//         hero.crash(enemy) should be(false)
//     }

//     "HeroAircraft" should "not vaild" in {
//         val hero = HeroAircraft
//         hero.decreaseHp(114514)
//         hero.isValid should be(false)
//     }
// }

// class HeroForwardSpec extends AnyFlatSpec with should.Matchers {
//     "HeroAircraft" should "not forward" in {
//         val hero = HeroAircraft
//         val getHeroLocation = () => (hero.getLocationX(), hero.getLocationY())
//         val old_location = getHeroLocation()
//         hero.forward()
//         getHeroLocation() should be(old_location)
//     }
// }
