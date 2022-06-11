package application

import scala.util.Random

import application.Game

import factory.EnemyFactory

//todo event queue
class Events(var context: Game) extends RoomElement {

    setRoom(context)

    def poll() = {
        BossEvent(room)
    }

    def EliteEvent(): Boolean = {
        // RandomInt() % 5 == 1
        Random.nextDouble() < room.setting.eliteFreq()
    }

    def RandomInt(): Int = {
        Random.between(0, 1000)
    }

    val BOSSTHRESHOLD = 20
    var next_boss_checkpoint = BOSSTHRESHOLD

    def BossEvent(g: Game) = {
        if (g.setting.hasBoss && g.getScore() >= next_boss_checkpoint) {
            // spawn boss
            if (!g.hasBoss()) {
                g.spawnBoss()
            }
            next_boss_checkpoint += BOSSTHRESHOLD
        }
    }

}
