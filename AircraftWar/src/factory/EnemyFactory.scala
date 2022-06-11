package factory

import aircraft._
import application.Main
import application.ImageManager
import misc.typing.EnemyType._
import misc.typing.EnemyType
import application.Game

object EnemyFactory {
    def spawn(
        t: EnemyType,
        location: (Int, Int),
        blood: Int,
        shootNum: Int
    )(implicit context: Game): AbstractAircraft = {
        var result = t match {
            case ELITE =>
                new EliteEnemy(
                  location._1,
                  location._2,
                  0,
                  3,
                  blood
                )
            case TRIVIAL =>
                new TrivialEnemy(
                  location._1,
                  location._2,
                  0,
                  5,
                  blood
                )
            case BOSS =>
                new BossEnemy(
                  location._1,
                  location._2,
                  2,
                  0,
                  blood,
                  shootNum
                )
            case _ =>
                this.spawn(TRIVIAL, location, blood, shootNum)
        }
        result.setRoom(context)
        result
    }

    def spawn(
        t: EnemyType,
        blood: Int = 5,
        shootNum: Int = 2
    )(implicit context: Game): AbstractAircraft = {
        val random_location = (
          (
            Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE
                .getWidth())
          ).toInt * 1,
          (Math.random() * Main.WINDOW_HEIGHT * 0.2).toInt * 1
        )
        this.spawn(t, random_location, blood, shootNum)
    }
}
