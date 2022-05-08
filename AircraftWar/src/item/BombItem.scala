package item

import basic.AbstractFlyingObject
import application.Game
import aircraft.AbstractAircraft
import application.MusicController
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.HashMap

trait BombSubscriber {
    def update()
}

object BombPublisher {

    var subscribers: HashMap[AbstractFlyingObject with BombSubscriber, Unit] =
        new HashMap()

    // var subscribers: ListBuffer[AbstractFlyingObject with BombSubscriber] =
    //     new ListBuffer()

    def notifySubscriber() {
        for (s <- subscribers.keys.toList) {
            // for (s <- subscribers) {
            s.update()
        }
    }

    def subscribe(o: AbstractFlyingObject with BombSubscriber) {
        subscribers.put(o, ())
        // subscribers.append(o)
    }

    def unsubscribe(o: AbstractFlyingObject with BombSubscriber) {
        subscribers.remove(o)
    }
}

class BombItem(
    var locationX: Int,
    var locationY: Int,
    var speedX: Int,
    var speedY: Int
) extends AbstractItem {

    def effect(o: Game.type): Unit = {
        MusicController.bomb()
        println("[Bomb Item Applied]")
        BombPublisher.notifySubscriber()
        vanish()
    }
}
