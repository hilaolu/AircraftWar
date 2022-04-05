package factory

import aircraft._
import application.Main
import application.ImageManager
import misc.typing.EnemyType._
import misc.typing.EnemyType

object EnemyFactory {
    def spawn(t: EnemyType, location: (Int, Int)): AbstractAircraft = {
        t match {
            case ELITE =>
                new EliteEnemy(
                  location._1,
                  location._2,
                  0,
                  3,
                  20
                )
            case TRIVIAL =>
                new TrivialEnemy(
                  location._1,
                  location._2,
                  0,
                  5,
                  10
                )
            case BOSS =>
                new BossEnemy(
                  location._1,
                  location._2,
                  0,
                  5,
                  10
                )
            case _ =>
                this.spawn(TRIVIAL, location)
        }
    }

    def spawn(t: EnemyType): AbstractAircraft = {
        val random_location = (
          (
            Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE
                .getWidth())
          ).toInt * 1,
          (Math.random() * Main.WINDOW_HEIGHT * 0.2).toInt * 1
        )
        this.spawn(t, random_location)
    }
}
