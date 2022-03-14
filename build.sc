import mill._, scalalib._

object AircraftWar extends ScalaModule {
    def scalaVersion = "2.13.8"

    override def ivyDeps = Agg(
      ivy"org.apache.commons:commons-lang3:3.1.+"
    )

}
