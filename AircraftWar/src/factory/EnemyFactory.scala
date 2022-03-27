package factory

import aircraft._
import application.Main
import application.ImageManager

object EnemyFactory {
    def spawn(t: String): AbstractAircraft = {
        if (t == "e") {
            new EliteEnemy(
              (
                Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE
                    .getWidth())
              ).toInt * 1,
              (Math
                  .random() * Main.WINDOW_HEIGHT * 0.2).toInt * 1,
              0,
              3,
              20
            )
        } else if (t == "t") {
            new TrivialEnemy(
              (
                Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE
                    .getWidth())
              ).toInt * 1,
              (Math
                  .random() * Main.WINDOW_HEIGHT * 0.2).toInt * 1,
              0,
              5,
              10
            )
        } else if (t == "b") {
            new BossEnemy(
              (
                Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE
                    .getWidth())
              ).toInt * 1,
              (Math
                  .random() * Main.WINDOW_HEIGHT * 0.2).toInt * 1,
              0,
              5,
              10
            )
        } else {
            this.spawn("t")
        }
    }
}
