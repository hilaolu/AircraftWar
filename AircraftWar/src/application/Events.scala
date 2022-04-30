package application

import scala.util.Random

import application.Game

//todo event queue
object Events {

    def poll(g: Game.type) = {
        BossEvent(g)
    }

    def EliteEvent(): Boolean = {
        RandomInt() % 5 == 1
    }

    def RandomInt(): Int = {
        Random.between(0, 1000)
    }

    val BOSSTHRESHOLD = 20
    var next_boss_checkpoint = BOSSTHRESHOLD

    def BossEvent(g: Game.type) = {
        if (g.getScore() >= next_boss_checkpoint) {
            // spawn boss
            if (!g.hasBoss()) {
                MusicController.stopBGM()
                MusicController.playBossBGM()
                g.spawnEnemy(misc.typing.EnemyType.BOSS)
            }
            next_boss_checkpoint += BOSSTHRESHOLD
        }
    }

}
