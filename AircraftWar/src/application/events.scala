package application

import scala.math.random

//todo event queue
object Events {
    def EliteEvent(): Boolean = {
        RandomInt() % 100 == 1
    }

    def RandomInt(): Int = {
        random().toInt
    }
}
