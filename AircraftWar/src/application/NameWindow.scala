package application

import scala.swing._
import scala.swing.event._

import scala.collection.mutable.ArrayBuffer
import java.util.jar.Attributes.Name

object NameWindow {

    def apply() = {
        //
        import Dialog._
        val inp = showInput(
          message = "Name",
          title = "Name",
          initial = ""
        )

        val name = inp.getOrElse("anonymous")

        ScoreBoard.insert(
          List(Game.getScore().toString(), name, Game.difficulty.toString())
        )

        RankingWindow.open()
    }

}
