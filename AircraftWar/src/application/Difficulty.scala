package application.difficulty

import application.ImageManager

import java.awt.image.BufferedImage

abstract class Difficulty {

    def bg(): BufferedImage

    def maxEnemy(): Int

    def eliteFreq(): Double

    def eliteBlood(): Int

    def hasBoss(): Boolean

    def bossShootNum(): Int

    def bossBlood(): Int

}

class Mild extends Difficulty {

    def bg() = ImageManager.MILD_IMAGE
    def maxEnemy(): Int = 5

    def eliteFreq(): Double = 0.2

    def eliteBlood(): Int = 10

    def hasBoss(): Boolean = false

    def bossShootNum(): Int = 5

    def bossBlood(): Int = 30
}

class Moderate extends Difficulty {

    def bg() = ImageManager.MODERATE_IMAGE
    def maxEnemy(): Int = 5

    def eliteFreq(): Double = 0.2

    def eliteBlood(): Int = 10

    def hasBoss(): Boolean = true

    def bossShootNum(): Int = 5

    var boss_blood = 30
    def bossBlood(): Int = {
        boss_blood += 5
        boss_blood
    }
}

class Severe extends Difficulty {

    def bg() = ImageManager.SEVERE_IMAGE
    def maxEnemy(): Int = 6

    def eliteFreq(): Double = 0.3

    def eliteBlood(): Int = 15

    def hasBoss(): Boolean = true

    def bossShootNum(): Int = 7

    var boss_blood = 30
    def bossBlood(): Int = {
        boss_blood += 5
        boss_blood
    }
}
