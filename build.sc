import mill._, scalalib._
import mill.scalalib.bsp.ScalaMetalsSupport

object AircraftWar extends ScalaModule with ScalaMetalsSupport {
    def scalaVersion = "2.13.8"
    def semanticDbVersion = "4.4.32"

    override def ivyDeps = Agg(
      ivy"org.apache.commons:commons-lang3:3.12.+"
    )

    object test extends Tests with TestModule.ScalaTest {
        def ivyDeps = Agg(
          ivy"org.scalactic::scalactic:3.2.+",
          ivy"org.scalatest::scalatest:3.2.+"
        )
    }
}
