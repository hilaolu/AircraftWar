package application

import scala.swing._
import scala.swing.event._

import scala.collection.mutable.ArrayBuffer

object RankingWindow extends MainFrame {

    var model =
        ScoreBoard
            .getAll()
            .zipWithIndex
            .map { case (l, index) =>
                l.appended(index.toString())
            }
            .filter(l => (l(2) == Game.difficulty.toString()))
            .sortWith(_(0).toInt > _(0).toInt)
            .map(l => l.map(s => s.asInstanceOf[Any]).toArray)
            .toArray

    lazy val ui: BoxPanel = new BoxPanel(Orientation.Vertical) {
        var table: Table = new Table(
          model,
          Array(
            "Score",
            "Name"
            //    "Mode", "Index"
          )
        ) {
            preferredViewportSize = new Dimension(700, 300)
        }
        // 1.6:table.fillsViewportHeight = true
        listenTo(table.selection)

        val sp = new ScrollPane(table)

        contents += sp
        // contents += new Label("Selection Mode")

        import Dialog.{showConfirmation, Result}

        contents += new Button {
            text = "Delete Select Query"
            reactions += { case ButtonClicked(_) =>
                showConfirmation(
                  message = "Would you like to delete select queries?"
                  //   "An Inane Question"
                ) match {
                    case Result.Yes => {
                        for (
                          index <- table.selection.rows.toList
                              .map(x => model(x)(3).toString().toInt)
                              .sortWith(_ > _)
                        ) {
                            ScoreBoard.delete(index)
                        }

                        model = ScoreBoard
                            .getAll()
                            .zipWithIndex
                            .map { case (l, index) =>
                                l.appended(index.toString())
                            }
                            .filter(l => (l(2) == Game.difficulty.toString()))
                            .sortWith(_(0).toInt > _(0).toInt)
                            .map(l => l.map(s => s.asInstanceOf[Any]).toArray)
                            .toArray

                        table = new Table(
                          model,
                          Array(
                            "Score",
                            "Name"
                            // "Mode",
                            // "Index"
                          )
                        ) {
                            preferredViewportSize = new Dimension(500, 70)
                        }
                        sp.contents = table
                    }
                    case Result.No => ()
                    case _         => ()
                }
            }
        }

        table.selection.elementMode = Table.ElementMode.Row
    }

    title = "Ranking" + (Game.difficulty match {
        case 0 => "(Easy)"
        case 1 => "(Moderate)"
        case _ => "(Hard)"
    })
    contents = ui

}
