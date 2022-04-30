package application

object MusicController {

    val dir = "AircraftWar/resources/sounds/"

    val bgm = new SoundPlayer(dir + "bgm.wav", true)

    val boss_bgm = new SoundPlayer(dir + "bgm_boss.wav", true)

    var enable = false

    def playBGM() = {
        if (enable)
            bgm.play()
    }

    def stopBGM() = {
        if (enable)
            bgm.stop()
    }

    def playBossBGM() = {
        if (enable)
            boss_bgm.play()
    }

    def stopBossBGM() = {
        if (enable)
            boss_bgm.stop()
    }

    def bomb() = {
        if (enable) {
            val sound = new SoundPlayer(dir + "bomb_explosion.wav")
            sound.play()
        }
    }

    def gameOver() = {
        if (enable) {
            val sound = new SoundPlayer(dir + "game_over.wav")
            sound.play()
        }
    }

    def supply() = {
        if (enable) {
            val sound = new SoundPlayer(dir + "get_supply.wav")
            sound.play()
        }
    }

    def hit() = {
        if (enable) {
            val sound = new SoundPlayer(dir + "bullet_hit.wav")
            sound.play()
        }
    }

    def shot() = {
        if (enable) {
            val sound = new SoundPlayer(dir + "bullet.wav")
            sound.play()
        }
    }

}
