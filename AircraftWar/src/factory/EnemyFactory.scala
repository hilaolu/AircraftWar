package factory

import aircraft._
import application.Main
import application.ImageManager

object EnemyFactory {
    def spawn(t: String, location: (Int, Int)): AbstractAircraft = {
        if (t == "e") {
            new EliteEnemy(
              location._1,
              location._2,
              0,
              3,
              20
            )
        } else if (t == "t") {
            new TrivialEnemy(
              location._1,
              location._2,
              0,
              5,
              10
            )
        } else if (t == "b") {
            new BossEnemy(
              location._1,
              location._2,
              0,
              5,
              10
            )
        } else {
            this.spawn("t", location)
        }
    }

    def spawn(t: String): AbstractAircraft = {
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
