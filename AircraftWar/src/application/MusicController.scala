package application

object MusicController {

    val dir = "AircraftWar/resources/sounds/"
    val bgm = new SoundPlayer(dir + "get_supply.wav")

    def BGM() = {
        bgm.play()
    }

    def stopBGM() = {}

    def bossBGM() = {}

    def supply() = {
        val sound = new SoundPlayer(dir + "get_supply.wav")
        sound.play()
    }

}
