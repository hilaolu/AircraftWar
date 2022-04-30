package application

import scala.swing._
import scala.swing.event._
import java.awt

object MenuWindow extends MainFrame {
    title = "Difficulty Select"
    contents = new GridPanel(2, 2) {
        hGap = 3
        vGap = 3
        contents += new Button {
            text = "Easy"
            reactions += { case ButtonClicked(_) =>
                gameStart()
            }
        }

        contents += new Button {
            text = "Moderate"
            reactions += { case ButtonClicked(_) =>
                Game.difficulty = 1
                gameStart()
            }
        }

        contents += new Button {
            text = "Hard"
            reactions += { case ButtonClicked(_) =>
                Game.difficulty = 2
                gameStart()
            }
        }

        // contents += new ComboBox(List(1, 2, 3, 4))
        val sound = new CheckBox("Sound") {
            reactions += { case ButtonClicked(x) =>
                MusicController.enable = x.selected
            }
        }

        contents += sound

        // contents += new Button {
        //     text = "Ranking"
        //     reactions += { case ButtonClicked(_) =>
        //         ranking()
        //     }
        // }
    }

    def gameStart() = {
        MenuWindow.close()
        Main.frame.setVisible(true)
        Game.action()
    }

    def ranking() = {
        MenuWindow.close()
        RankingWindow.visible = true
    }

    size = new Dimension(300, 300)
}
