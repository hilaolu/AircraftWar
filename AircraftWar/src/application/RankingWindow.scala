package application

import scala.swing._
import scala.swing.event._

import scala.collection.mutable.ArrayBuffer

object RankingWindow extends MainFrame {

    val model =
        ScoreBoard
            .getAll()
            .map(l => l.map(s => s.asInstanceOf[Any]).toArray)
            .toArray

    lazy val ui: BoxPanel = new BoxPanel(Orientation.Vertical) {
        var table: Table = new Table(
          model,
          Array("Score", "Name")
        ) {
            preferredViewportSize = new Dimension(700, 300)
        }
        // 1.6:table.fillsViewportHeight = true
        listenTo(table.selection)

        val sp = new ScrollPane(table)

        contents += sp
        // contents += new Label("Selection Mode")

        contents += new Button {
            text = "Delete Select Query"
            reactions += { case ButtonClicked(_) =>
                for (c <- table.selection.rows)
                    ScoreBoard.delete(c)

                val model = ScoreBoard
                    .getAll()
                    .map(l => l.map(s => s.asInstanceOf[Any]).toArray)
                    .toArray

                table = new Table(
                  model,
                  Array(
                    "Score",
                    "Name"
                  )
                ) {
                    preferredViewportSize = new Dimension(500, 70)
                }
                sp.contents = table
            }
        }

        table.selection.elementMode = Table.ElementMode.Row
    }

    title = "Table Selection"
    contents = ui

}
