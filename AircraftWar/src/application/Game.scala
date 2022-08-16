package application

import java.awt._
import java.awt.image.BufferedImage
import java.util._
import java.util.concurrent._
import javax.swing._

import scala.collection.mutable.ListBuffer

import aircraft._
import item._
import bullet._
import basic.AbstractFlyingObject
import bullet.AbstractBullet
import org.apache.commons.lang3.concurrent.BasicThreadFactory

import misc.typing.EnemyType._

import factory._
import aircraft.weapon.MachineGun
import aircraft.weapon.Sniper
import aircraft.weapon.ShotGun
import java.util.jar.Attributes.Name
import application.difficulty.Moderate
import application.difficulty.Difficulty
import application.difficulty.Mild
import application.difficulty.Severe
import java.net.ServerSocket
import java.io.ObjectOutputStream
import java.io.PrintStream
import java.io.DataOutputStream
import java.io.BufferedReader
import java.io.InputStreamReader

trait RoomElement {
    var room: application.Game = null

    def setRoom(room: application.Game) = {
        this.room = room
    }
}

class Game(agents: ListBuffer[Agent]) {

    val events = new Events(this)

    implicit val context = this

    events.setRoom(this)

    var difficulty = 0

    private var backGroundTop = 0

    private val timeInterval = 40

    // private val heroAircraft = HeroAircraft
    var heroAircrafts: ListBuffer[HeroAircraft] =
        agents.map(agent => new HeroAircraft(agent))

    var enemyAircrafts: ListBuffer[AbstractAircraft] =
        new ListBuffer()
    var heroBullets: ListBuffer[AbstractBullet] =
        new ListBuffer()
    var enemyBullets: ListBuffer[AbstractBullet] =
        new ListBuffer()

    private var items: ListBuffer[AbstractItem] =
        new ListBuffer()

    private val enemyMaxNumber = 5

    private var gameOverFlag = false
    private var score = 0
    private var time = 0

    private var cycleDuration = 600
    private var cycleTime = 0

    private val enemyFactory = EnemyFactory

    var setting: Difficulty = new Moderate

    def setDifficulty(d: Difficulty) = {
        setting = d
    }

    private final var executorService: ScheduledExecutorService =
        new ScheduledThreadPoolExecutor(
          1,
          new BasicThreadFactory.Builder()
              .namingPattern("game-action-%d")
              .daemon(true)
              .build()
        )

    def addItem[T <: AbstractItem](item: T) = {
        items.addOne(item)
    }

    def spawnBoss() = {
        val boss = EnemyFactory.spawn(BOSS, setting.bossBlood())
        boss.setRoom(this)
        enemyAircrafts.addOne(
          boss
        )
    }

    def getFlyingObjectListLocations[T <: AbstractFlyingObject](
        l: ListBuffer[T]
    ) = {
        l.map(a => a.getLocationStr())
    }

    def genStatusStr(): String = {

        val result = ujson.Obj(
          "hero_bullets" ->
              getFlyingObjectListLocations(heroBullets),
          "heroes" -> getFlyingObjectListLocations(
            heroAircrafts.filter(_.isValid())
          ),
          "trivial_enemy" -> getFlyingObjectListLocations(
            enemyAircrafts.filter(_.isInstanceOf[TrivialEnemy])
          ),
          "elite_enemy" -> getFlyingObjectListLocations(
            enemyAircrafts.filter(_.isInstanceOf[EliteEnemy])
          ),
          "boss_enemy" -> getFlyingObjectListLocations(
            enemyAircrafts.filter(_.isInstanceOf[BossEnemy])
          ),
          "enemy_bullets" ->
              getFlyingObjectListLocations(enemyBullets)
        )

        ujson.write(result)
    }

    def action() = {

        for (agent <- agents) {
            agent.setRoom(this)
        }

        class task extends Runnable {
            override def run() = {
                try {
                    time += timeInterval

                    if (timeCountAndNewCycleJudge()) {
                        if (enemyAircrafts.length < setting.maxEnemy()) {
                            enemyAircrafts.addOne(
                              enemyFactory.spawn(
                                TRIVIAL
                              )
                            )
                        }

                        if (events.EliteEvent()) {
                            enemyAircrafts.addOne(
                              enemyFactory.spawn(
                                ELITE,
                                blood = setting.eliteBlood()
                              )
                            )
                        }

                        shootAction()
                    }

                    events.poll()

                    bulletsMoveAction()

                    aircraftsMoveAction()

                    itemsMoveAction()

                    crashCheckAction()

                    postProcessAction()

                    for (agent <- agents) {
                        agent.push(genStatusStr())
                    }

                    if (
                      !(heroAircrafts
                          .map(_.isValid())
                          .reduce(_ || _)
                          || Main.debug)
                    ) {
                        executorService.shutdown()
                        gameOverFlag = true
                        System.out.println("Game Over!")
                    }

                } catch {
                    case t: Throwable => t.printStackTrace();
                }
            }
        }

        val pid = executorService.scheduleWithFixedDelay(
          new task,
          timeInterval,
          timeInterval,
          TimeUnit.MILLISECONDS
        )

    }

    def getHero() = {
        // return a random hero
        val index = scala.util.Random.nextInt(heroAircrafts.length)
        heroAircrafts(index)
    }

    def getScore() = {
        score
    }

    def hasBoss(): Boolean = {
        if (enemyAircrafts.isEmpty) {
            false
        } else {
            enemyAircrafts.map(a => a.isInstanceOf[BossEnemy]).reduce(_ | _)
        }
    }

    def addScore(adder: Int) = {
        score += adder
    }

    private def timeCountAndNewCycleJudge(): Boolean = {
        cycleTime += timeInterval
        if (
          cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime
        ) {
            cycleTime %= cycleDuration
            true
        } else {
            false
        }
    }

    private def shootAction() = {
        for (enemyAircraft <- enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot())
        }

        heroBullets.addAll(heroAircrafts.map(_.shoot()).flatten)
    }

    private def bulletsMoveAction() = {
        for (bullet <- heroBullets) {
            bullet.forward()
        }
        for (bullet <- enemyBullets) {
            bullet.forward()
        }
    }

    private def itemsMoveAction() = {
        for (item <- items) {
            item.forward()
        }
    }

    private def aircraftsMoveAction() = {
        for (enemyAircraft <- enemyAircrafts) {
            enemyAircraft.forward()
        }
    }

    private def test(): Int = {
        1
    }

    private def crashCheckAction() = {

        for (heroAircraft <- heroAircrafts) {
            for (item <- items.clone()) {
                if (item.isValid) {
                    if (item.crash(heroAircraft)) {
                        item.effect(this, heroAircraft)
                    }
                }
            }

            for (bullet <- enemyBullets) {
                if (bullet.isValid) {
                    if (bullet.crash(heroAircraft)) {
                        bullet.effect(heroAircraft)
                    }
                }
            }

            for (enemyAircraft <- enemyAircrafts) {
                if (!enemyAircraft.notValid()) {
                    if (
                      enemyAircraft.crash(heroAircraft)
                      || heroAircraft.crash(enemyAircraft)
                    ) {
                        enemyAircraft.vanish()
                        heroAircraft.decreaseHp(Integer.MAX_VALUE)
                    }
                }
            }
        }

        for (bullet <- heroBullets) {
            if (!bullet.notValid()) {
                for (enemyAircraft <- enemyAircrafts) {
                    if (!enemyAircraft.notValid()) {
                        if (enemyAircraft.crash(bullet)) {
                            bullet.effect(enemyAircraft)
                        }
                    }
                }
            }
        }

    }

    private def postProcessAction() = {
        enemyBullets = enemyBullets.filter(_.isValid)
        heroBullets = heroBullets.filter(_.isValid)
        enemyAircrafts = enemyAircrafts.filter(_.isValid)
        items = items.filter(_.isValid)
    }

    private def paintScoreAndLife(g: Graphics) = {
        val x = 10
        val y = 25
        g.setColor(new Color(16711680))
        g.setFont(new Font("SansSerif", Font.BOLD, 22))
        g.drawString("SCORE:" + this.score, x, y)
        for (heroAircraft <- heroAircrafts) {
            g.drawString("LIFE:" + heroAircraft.getHp(), x, y + 20)
        }
    }

}
