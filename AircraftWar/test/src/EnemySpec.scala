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

// class EnemyCollisionSpec extends AnyFlatSpec with should.Matchers {

//     "Enemy" should "Collision" in {
//         val hero = HeroAircraft
//         val enemy =
//             EnemyFactory.spawn(
//               TRIVIAL,
//               (hero.getLocationX(), hero.getLocationY())
//             )
//         enemy.crash(hero) should be(true)
//     }

//     "Enemy" should "not Collision" in {
//         val hero = HeroAircraft
//         val enemy =
//             EnemyFactory.spawn(
//               ELITE,
//               (hero.getLocationX() + 100, hero.getLocationY() + 100)
//             )
//         enemy.crash(hero) should be(false)
//     }

//     "EnemyAircraft" should "not vaild" in {
//         val enemy = EnemyFactory.spawn(TRIVIAL)
//         enemy.decreaseHp(114514)
//         enemy.isValid should be(false)
//     }
// }
