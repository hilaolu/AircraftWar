package test
import org.scalatest._
import flatspec._
import matchers._

class GameSpec extends AnyFlatSpec with should.Matchers {
    "Game" should "run" in {
        application.Main.main(new Array[String](0))
    }

}
