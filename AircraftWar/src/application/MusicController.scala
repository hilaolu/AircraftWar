package application

object MusicController {

    val dir = "AircraftWar/resources/sounds/"

    val bgm = new SoundPlayer(dir + "bgm.wav", true)

    val boss_bgm = new SoundPlayer(dir + "bgm_boss.wav", true)

    def playBGM() = {
        bgm.play()
    }

    def stopBGM() = {
        bgm.stop()
    }

    def playBossBGM() = {
        boss_bgm.play()
    }

    def stopBossBGM() = {
        boss_bgm.stop()
    }

    def bomb() = {
        val sound = new SoundPlayer(dir + "bomb_explosion.wav")
        sound.play()
    }

    def gameOver() = {
        val sound = new SoundPlayer(dir + "game_over.wav")
        sound.play()
    }

    def supply() = {
        val sound = new SoundPlayer(dir + "get_supply.wav")
        sound.play()
    }

    def hit() = {
        val sound = new SoundPlayer(dir + "bullet_hit.wav")
        sound.play()
    }

    def shot() = {
        val sound = new SoundPlayer(dir + "bullet.wav")
        sound.play()
    }

}
