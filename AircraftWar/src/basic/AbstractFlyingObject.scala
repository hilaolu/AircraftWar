package basic

import java.awt.image.BufferedImage

import aircraft.AbstractAircraft
import application.ImageManager
import application.Main

abstract class AbstractFlyingObject {

    protected var locationX: Int
    protected var locationY: Int
    protected var speedX: Int
    protected var speedY: Int
    protected var image: BufferedImage = null

    protected var width = -1

    protected var height = -1

    protected var valid = true

    def forward() = {
        locationX += speedX
        locationY += speedY
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            speedX = -speedX
        }
    }

    def crash(flyingObject: AbstractFlyingObject): Boolean = {
        val factor: Int = if (this.isInstanceOf[AbstractAircraft]) 2 else 1
        val fFactor: Int =
            if (flyingObject.isInstanceOf[AbstractAircraft]) 2 else 1

        val x: Int = flyingObject.getLocationX()
        val y: Int = flyingObject.getLocationY()
        val fWidth: Int = flyingObject.getWidth()
        val fHeight: Int = flyingObject.getHeight()

        x + (fWidth + this
            .getWidth()) / 2 > locationX && x - (fWidth + this
            .getWidth()) / 2 < locationX && y + (fHeight / fFactor + this
            .getHeight() / factor) / 2 > locationY && y - (fHeight / fFactor + this
            .getHeight() / factor) / 2 < locationY
    }

    def getLocationX(): Int = {
        locationX
    }

    def getLocationY(): Int = {
        locationY
    }

    def getLocationStr(): String = {
        locationX.toString() + "," + locationY.toString()
    }

    def setLocation(_locationX: Double, _locationY: Double) {
        locationX = _locationX.toInt
        locationY = _locationY.toInt
    }

    def getSpeedY(): Int = {
        speedY
    }

    def getImage(): BufferedImage = {
        if (image == null) {
            image = ImageManager.get(this)
        }
        image
    }

    def getWidth(): Int = {
        if (width == -1) {
            width = ImageManager.get(this).getWidth()
        }
        width
    }

    def getHeight(): Int = {
        if (height == -1) {
            height = ImageManager.get(this).getHeight()
        }
        height
    }

    def notValid(): Boolean = {
        !this.valid
    }

    def isValid(): Boolean = {
        this.valid
    }

    def isOutOfCanvas: Boolean = {

        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            true
        } else if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT) {
            true
        } else if (locationY <= 0) {
            true
        } else {
            false
        }

    }

    def vanish() = {
        valid = false
    }

}
