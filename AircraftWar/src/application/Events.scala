package application

import scala.util.Random

import application.Game

import application.Game.setting
import factory.EnemyFactory

//todo event queue
object Events {

    def poll(g: Game.type) = {
        BossEvent(g)
    }

    def EliteEvent(): Boolean = {
        // RandomInt() % 5 == 1
        Random.nextDouble() < application.Game.setting.eliteFreq()
    }

    def RandomInt(): Int = {
        Random.between(0, 1000)
    }

    val BOSSTHRESHOLD = 20
    var next_boss_checkpoint = BOSSTHRESHOLD

    def BossEvent(g: Game.type) = {
        if (setting.hasBoss && g.getScore() >= next_boss_checkpoint) {
            // spawn boss
            if (!g.hasBoss()) {
                MusicController.stopBGM()
                MusicController.playBossBGM()
                g.spawnBoss()
            }
            next_boss_checkpoint += BOSSTHRESHOLD
        }
    }

}
