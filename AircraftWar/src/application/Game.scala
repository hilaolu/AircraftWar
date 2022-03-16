package application

import java.awt._
import java.awt.image.BufferedImage
import java.util._
import java.util.concurrent._
import javax.swing._

import scala.collection.mutable.ListBuffer

import aircraft.BossAircraft
import aircraft.EliteAircraft
import aircraft.EnemyAircraft
import aircraft.HeroAircraft
import aircraft.AbstractAircraft

import item._
import basic.AbstractFlyingObject
import bullet.AbstractBullet
import org.apache.commons.lang3.concurrent.BasicThreadFactory

/** 游戏主面板，游戏启动
  *
  * @author
  *   hitsz
  */
class Game extends JPanel {

    private var backGroundTop = 0

    private val timeInterval = 40

    private final var heroAircraft: HeroAircraft = new HeroAircraft(
      Main.WINDOW_WIDTH / 2,
      Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
      0,
      0,
      100
    )

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

    def action() = {

        object task extends Runnable {
            def run() = {

                time += timeInterval

                if (timeCountAndNewCycleJudge()) {
                    if (enemyAircrafts.length < enemyMaxNumber) {
                        enemyAircrafts.addOne(
                          new EnemyAircraft(
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
                        )
                    }

                    if (Events.EliteEvent()) {
                        enemyAircrafts.addOne(
                          new EliteAircraft(
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
                        )
                    }
                    shootAction()
                }

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
                }

            }
        }

        executorService.scheduleWithFixedDelay(
          task,
          timeInterval,
          timeInterval,
          TimeUnit.MILLISECONDS
        )

    }

    def getHero(): HeroAircraft = {
        heroAircraft
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
                          enemyAircraft
                              .crash(heroAircraft) || heroAircraft.crash(
                            enemyAircraft
                          )
                        ) {
                            enemyAircraft.vanish()
                            heroAircraft.decreaseHp(Integer.MAX_VALUE)
                        }
                    }
                }
            }
        }

        for (item <- items) {
            if (item.isValid) {
                if (heroAircraft.crash(item)) {
                    item.effect(this)
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
          ImageManager.BACKGROUND_IMAGE,
          0,
          this.backGroundTop - Main.WINDOW_HEIGHT,
          null
        )
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null)
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

}
