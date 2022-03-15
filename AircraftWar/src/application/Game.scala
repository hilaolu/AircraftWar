package application;

import aircraft._;
import bullet.AbstractBullet;
import basic.AbstractFlyingObject;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing._;
import java.awt._;
import java.awt.image.BufferedImage;
import java.util._;
// import java.util.List;
import java.util.concurrent._;

import scala.jdk.CollectionConverters._

import scala.collection.mutable.ListBuffer

/** 游戏主面板，游戏启动
  *
  * @author
  *   hitsz
  */
class Game extends JPanel {

    private var backGroundTop = 0;

    /** 时间间隔(ms)，控制刷新频率
      */
    private val timeInterval = 40;

    private final var heroAircraft: HeroAircraft = new HeroAircraft(
      512 / 2,
      768 - ImageManager.HERO_IMAGE.getHeight(),
      0,
      0,
      100
    );

    private var enemyAircrafts: ListBuffer[AbstractAircraft] =
        new ListBuffer()
    private var heroBullets: ListBuffer[AbstractBullet] =
        new ListBuffer()
    private var enemyBullets: ListBuffer[AbstractBullet] =
        new ListBuffer()

    private val enemyMaxNumber = 5;

    private var gameOverFlag = false;
    private var score = 0;
    private var time = 0;

    /** 周期（ms) 指示子弹的发射、敌机的产生频率
      */
    private var cycleDuration = 600;
    private var cycleTime = 0;

    // 启动英雄机鼠标监听
    HeroController.apply(this, heroAircraft);

    /** Scheduled 线程池，用于定时任务调度 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
      * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
      */
    private final var executorService: ScheduledExecutorService =
        new ScheduledThreadPoolExecutor(
          1,
          new BasicThreadFactory.Builder()
              .namingPattern("game-action-%d")
              .daemon(true)
              .build()
        );

    /** 游戏启动入口，执行游戏逻辑
      */
    def action() = {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        object task extends Runnable {
            def run() = {

                time += timeInterval

                // 周期性执行（控制频率）
                if (timeCountAndNewCycleJudge()) {
                    // System.out.println(time);
                    // 新敌机产生
                    if (enemyAircrafts.size < enemyMaxNumber) {
                        enemyAircrafts.addOne(
                          new EnemyAircraft(
                            (
                              Math.random() * (512 - ImageManager.MOB_ENEMY_IMAGE
                                  .getWidth())
                            ).toInt * 1,
                            (Math.random() * 768 * 0.2).toInt * 1,
                            0,
                            10,
                            30
                          )
                        )
                    }
                    // 飞机射出子弹
                    shootAction()
                }

                // 子弹移动
                bulletsMoveAction()

                // 飞机移动
                aircraftsMoveAction()

                // 撞击检测
                crashCheckAction()

                // 后处理
                postProcessAction()

                // 每个时刻重绘界面
                repaint()

                // 游戏结束检查
                if (heroAircraft.getHp() <= 0) {
                    // 游戏结束
                    executorService.shutdown();
                    gameOverFlag = true;
                    System.out.println("Game Over!");
                }

            }
        }

        /** 以固定延迟时间进行执行 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
          */
        executorService.scheduleWithFixedDelay(
          task,
          timeInterval,
          timeInterval,
          TimeUnit.MILLISECONDS
        );

    }

    // ***********************
    // Action 各部分
    // ***********************

    private def timeCountAndNewCycleJudge(): Boolean = {
        cycleTime += timeInterval;
        if (
          cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime
        ) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private def shootAction() = {
        // TODO 敌机射击
        for (enemyAircraft <- enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }

        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private def bulletsMoveAction() = {
        for (bullet <- heroBullets) {
            bullet.forward();
        }
        for (bullet <- enemyBullets) {
            bullet.forward();
        }
    }

    private def aircraftsMoveAction() = {
        for (enemyAircraft <- enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /** 碰撞检测： 1. 敌机攻击英雄 2. 英雄攻击/撞击敌机 3. 英雄获得补给
      */
    private def crashCheckAction() = {
        // TODO 敌机子弹攻击英雄

        // 英雄子弹攻击敌机
        for (bullet <- heroBullets) {
            if (!bullet.notValid()) {
                for (enemyAircraft <- enemyAircrafts) {
                    if (!enemyAircraft.notValid()) {
                        // 已被其他子弹击毁的敌机，不再检测
                        // 避免多个子弹重复击毁同一敌机的判定
                        if (enemyAircraft.crash(bullet)) {
                            // 敌机撞击到英雄机子弹
                            // 敌机损失一定生命值
                            enemyAircraft.decreaseHp(bullet.getPower());
                            bullet.vanish();
                            if (enemyAircraft.notValid()) {
                                // TODO 获得分数，产生道具补给
                                score += 10;
                            }
                        }
                        // 英雄机 与 敌机 相撞，均损毁
                        if (
                          enemyAircraft
                              .crash(heroAircraft) || heroAircraft.crash(
                            enemyAircraft
                          )
                        ) {
                            enemyAircraft.vanish();
                            heroAircraft.decreaseHp(Integer.MAX_VALUE);
                        }
                    }
                }
            }
        }

        // Todo: 我方获得道具，道具生效

    }

    /** 后处理： 1. 删除无效的子弹 2. 删除无效的敌机 3. 检查英雄机生存 <p> 无效的原因可能是撞击或者飞出边界
      */
    private def postProcessAction() = {
        enemyBullets = enemyBullets.filter(!_.notValid())
        heroBullets = heroBullets.filter(!_.notValid())
        enemyAircrafts = enemyAircrafts.filter(!_.notValid())
    }

    // ***********************
    // Paint 各部分
    // ***********************

    /** 重写paint方法 通过重复调用paint方法，实现游戏动画
      *
      * @param g
      */
    override def paint(g: Graphics) = {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(
          ImageManager.BACKGROUND_IMAGE,
          0,
          this.backGroundTop - 768,
          null
        );
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == 768) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(
          ImageManager.HERO_IMAGE,
          heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
          heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2,
          null
        );

        // 绘制得分和生命值
        paintScoreAndLife(g);

    }

    private def paintImageWithPositionRevised[T <: AbstractFlyingObject](
        g: Graphics,
        objects: ListBuffer[T]
    ): Unit = {
        if (objects.size == 0) {
            return
        }

        for (o <- objects) {
            val image: BufferedImage = o.getImage();
            // assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(
              image,
              o.getLocationX() - image.getWidth() / 2,
              o.getLocationY() - image.getHeight() / 2,
              null
            );
        }
    }

    private def paintScoreAndLife(g: Graphics) = {
        val x = 10;
        val y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y + 20);
    }

}
