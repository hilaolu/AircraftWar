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

object Game extends JPanel {

    var difficulty = 0

    private var backGroundTop = 0

    private val timeInterval = 40

    private val heroAircraft = HeroAircraft

    private var enemyAircrafts: ListBuffer[AbstractAircraft] =
        new ListBuffer()
    private var heroBullets: ListBuffer[AbstractBullet] =
        new ListBuffer()
    private var enemyBullets: ListBuffer[AbstractBullet] =
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

    HeroController.apply(this, heroAircraft)

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
        enemyAircrafts.addOne(
          EnemyFactory.spawn(BOSS, setting.bossBlood())
        )
    }

    def action() = {

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

                        if (Events.EliteEvent()) {
                            enemyAircrafts.addOne(
                              enemyFactory.spawn(
                                ELITE,
                                blood = setting.eliteBlood()
                              )
                            )
                        }
                        shootAction()
                    }

                    Events.poll(Game)

                    bulletsMoveAction()

                    aircraftsMoveAction()

                    itemsMoveAction()

                    crashCheckAction()

                    postProcessAction()

                    repaint()

                    if (heroAircraft.getHp() <= 0 && !Main.debug) {
                        executorService.shutdown()
                        gameOverFlag = true
                        System.out.println("Game Over!")
                        MusicController.stopBGM()
                        MusicController.gameOver()
                        Main.frame.setVisible(false)
                        NameWindow()
                    }

                } catch {
                    case t: Throwable => t.printStackTrace();
                }
            }
        }

        // val bgm = new MusicThread("bgm.wav")

        MusicController.playBGM()

        val pid = executorService.scheduleWithFixedDelay(
          new task,
          timeInterval,
          timeInterval,
          TimeUnit.MILLISECONDS
        )
        // val pid = new Thread(new task).start

        // executorService.scheduleWithFixedDelay(
        //   bgm,
        //   timeInterval,
        //   timeInterval,
        //   TimeUnit.MILLISECONDS
        // )

    }

    def getHero() = {
        heroAircraft
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

        heroBullets.addAll(heroAircraft.shoot())
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

    private def crashCheckAction() = {

        for (item <- items.clone()) {
            if (item.isValid) {
                if (heroAircraft.crash(item)) {
                    item.effect(this)
                }
            }
        }

        for (bullet <- enemyBullets) {
            if (bullet.isValid) {
                if (heroAircraft.crash(bullet)) {
                    bullet.effect(heroAircraft)
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
        }

    }

    private def postProcessAction() = {
        enemyBullets = enemyBullets.filter(_.isValid)
        heroBullets = heroBullets.filter(_.isValid)
        enemyAircrafts = enemyAircrafts.filter(_.isValid)
        items = items.filter(_.isValid)
    }

    override def paint(g: Graphics) = {
        super.paint(g)

        g.drawImage(
          setting.bg(),
          0,
          this.backGroundTop - Main.WINDOW_HEIGHT,
          null
        )
        g.drawImage(setting.bg(), 0, this.backGroundTop, null)
        this.backGroundTop += 1
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0
        }

        paintImageWithPositionRevised(g, enemyBullets)
        paintImageWithPositionRevised(g, heroBullets)

        paintImageWithPositionRevised(g, enemyAircrafts)
        paintImageWithPositionRevised(g, items)

        g.drawImage(
          ImageManager.HERO_IMAGE,
          heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
          heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2,
          null
        )

        paintScoreAndLife(g)

    }

    private def paintImageWithPositionRevised[T <: AbstractFlyingObject](
        g: Graphics,
        objects: ListBuffer[T]
    ): Unit = {
        if (objects.size == 0) {
            return
        }

        for (o <- objects) {
            val image: BufferedImage = o.getImage()
            // assert image != null : objects.getClass().getName() + " has no image! "
            g.drawImage(
              image,
              o.getLocationX() - image.getWidth() / 2,
              o.getLocationY() - image.getHeight() / 2,
              null
            )
        }
    }

    private def paintScoreAndLife(g: Graphics) = {
        val x = 10
        val y = 25
        g.setColor(new Color(16711680))
        g.setFont(new Font("SansSerif", Font.BOLD, 22))
        g.drawString("SCORE:" + this.score, x, y)
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y + 20)
    }

    val d = new dummy

    class dummy {
        val s0: Option[BossEnemy] = None
        val s1: Option[EliteEnemy] = None
        val s2: Option[TrivialEnemy] = None
        val s3: Option[HeroBullet] = None
        val s4: Option[EnemyBullet] = None
        val s5: Option[BloodItem] = None
        val s6: Option[BombItem] = None
        val s7: Option[BulletItem] = None
        val s8: Option[MachineGun] = None
        val s9: Option[Sniper] = None
        val s10: Option[ShotGun] = None
        val s11: Option[DAO] = None
        val scoreboard = ScoreBoard
        val driver = CSVDriver
    }

}
