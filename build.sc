import mill._, scalalib._
import mill.scalalib.bsp.ScalaMetalsSupport

object AircraftWar extends ScalaModule with ScalaMetalsSupport {
    def scalaVersion = "2.13.8"
    def semanticDbVersion = "4.4.32"

    override def ivyDeps = Agg(
      ivy"org.apache.commons:commons-lang3:3.12.+",
      ivy"com.github.tototoshi::scala-csv:1.3.10",
      ivy"edu.berkeley.cs::treadle:1.5.+",
      ivy"org.scala-lang.modules::scala-swing:3.0.+",
      ivy"org.scala-lang:scala-library:2.13.8",
      ivy"com.lihaoyi::upickle:2.0.+"
    )

    object test extends Tests with TestModule.ScalaTest {
        def ivyDeps = Agg(
          ivy"org.scalactic::scalactic:3.2.+",
          ivy"org.scalatest::scalatest:3.2.+"
        )
    }
}
