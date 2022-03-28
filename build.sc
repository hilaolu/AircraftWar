import mill._, scalalib._
import mill.scalalib.bsp.ScalaMetalsSupport

object AircraftWar extends ScalaModule with ScalaMetalsSupport {
    def scalaVersion = "2.13.8"
    def semanticDbVersion = "4.4.32"

    override def ivyDeps = Agg(
      ivy"org.apache.commons:commons-lang3:3.12.+"
    )

}
