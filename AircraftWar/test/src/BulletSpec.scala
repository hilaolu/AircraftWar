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

// class BulletCollisionSpec extends AnyFlatSpec with should.Matchers {

//     "HeroBullet" should "Collision" in {
//         val bullet_factory = new BulletFactory(AircraftType.HERO)
//         val bullet = bullet_factory.spawn()
//         val enemy =
//             EnemyFactory.spawn(
//               TRIVIAL,
//               (bullet.getLocationX(), bullet.getLocationY())
//             )
//         enemy.crash(bullet) should be(true)
//     }

//     "HeroBullet" should "not Collision" in {
//         val bullet_factory = new BulletFactory(AircraftType.HERO)
//         val bullet = bullet_factory.spawn()
//         val enemy =
//             EnemyFactory.spawn(
//               TRIVIAL,
//               (bullet.getLocationX() + 100, bullet.getLocationY() + 100)
//             )
//         enemy.crash(bullet) should be(false)
//     }
// }

// class BulletForwardSpec extends AnyFlatSpec with should.Matchers {
//     "HeroBullet" should "forward" in {
//         val bullet_factory = new BulletFactory(AircraftType.HERO)
//         val bullet = bullet_factory.spawn()
//         bullet.setVX(1)
//         bullet.setVY(2)
//         val getBulletLocation =
//             () => (bullet.getLocationX(), bullet.getLocationY())
//         val old_location = getBulletLocation()
//         bullet.forward()
//         getBulletLocation()._1 should be(old_location._1 + 1)
//         getBulletLocation()._2 should be(old_location._2 + 2)
//     }
// }
