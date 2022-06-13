package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.HashMap
import aircraft.BossEnemy
import aircraft.HeroAircraft

class BombItem(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int
) extends AbstractItem {

    def effect(o: Game, h: HeroAircraft): Unit = {
        println("[Bomb Item Applied]")
        o.enemyAircrafts.filter(!_.isInstanceOf[BossEnemy]).map(_.vanish())
        o.enemyBullets.map(_.vanish())
        vanish()
    }
}
