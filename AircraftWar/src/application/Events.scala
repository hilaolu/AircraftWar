package application

import scala.util.Random

//todo event queue
object Events {
    def EliteEvent(): Boolean = {
        RandomInt() % 20 == 1
    }

    def RandomInt(): Int = {
        Random.between(0, 1000)
    }
}
