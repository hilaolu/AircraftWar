package basic

import java.awt.image.BufferedImage

import aircraft.AbstractAircraft
import application.ImageManager
import application.Main

/** 可飞行对象的父类
  *
  * @author
  *   hitsz
  */
abstract class AbstractFlyingObject(
    protected var locationX: Int,
    protected var locationY: Int,
    protected var speedX: Int,
    protected var speedY: Int
) {

    /** 图片, null 表示未设置
      */
    protected var image: BufferedImage = null

    /** x 轴长度，根据图片尺寸获得 -1 表示未设置
      */
    protected var width = -1

    /** y 轴长度，根据图片尺寸获得 -1 表示未设置
      */
    protected var height = -1

    /** 有效（生存）标记， 通常标记为 false的对象会再下次刷新时清除
      */
    protected var isValid = true

    def AbstractFlyingObject() = {}

    /** 可飞行对象根据速度移动 若飞行对象触碰到横向边界，横向速度反向
      */
    def forward() = {
        locationX += speedX
        locationY += speedY
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            // 横向超出边界后反向
            speedX = -speedX
        }
    }

    /** 碰撞检测，当对方坐标进入我方范围，判定我方击中<br> 对方与我方覆盖区域有交叉即判定撞击。 <br> 非飞机对象区域： 横向，[x -
      * width/2, x + width/2] 纵向，[y - height/2, y + height/2] <br> 飞机对象区域： 横向，[x
      * \- width/2, x + width/2] 纵向，[y - height/4, y + height/4]
      *
      * @param flyingObject
      *   撞击对方
      * @return
      *   true: 我方被击中 false 我方未被击中
      */
    def crash(flyingObject: AbstractFlyingObject): Boolean = {
        // 缩放因子，用于控制 y轴方向区域范围
        val factor: Int = if (this.isInstanceOf[AbstractAircraft]) 2 else 1
        val fFactor: Int =
            if (flyingObject.isInstanceOf[AbstractAircraft]) 2 else 1

        val x: Int = flyingObject.getLocationX()
        val y: Int = flyingObject.getLocationY()
        val fWidth: Int = flyingObject.getWidth()
        val fHeight: Int = flyingObject.getHeight()

        return x + (fWidth + this
            .getWidth()) / 2 > locationX && x - (fWidth + this
            .getWidth()) / 2 < locationX && y + (fHeight / fFactor + this
            .getHeight() / factor) / 2 > locationY && y - (fHeight / fFactor + this
            .getHeight() / factor) / 2 < locationY
    }

    def getLocationX(): Int = {
        return locationX
    }

    def getLocationY(): Int = {
        return locationY
    }

    def setLocation(locationX: Double, locationY: Double) {
        this.locationX = locationX.toInt
        this.locationY = locationY.toInt
    }

    def getSpeedY(): Int = {
        return speedY
    }

    def getImage(): BufferedImage = {
        if (image == null) {
            image = ImageManager.get(this)
        }
        return image
    }

    def getWidth(): Int = {
        if (width == -1) {
            // 若未设置，则查询图片宽度并设置
            width = ImageManager.get(this).getWidth()
        }
        return width
    }

    def getHeight(): Int = {
        if (height == -1) {
            // 若未设置，则查询图片高度并设置
            height = ImageManager.get(this).getHeight()
        }
        return height
    }

    def notValid(): Boolean = {
        return !this.isValid
    }

    def isOutOfCanvas: Boolean = {

        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            true
        } else if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT) {
            // 向下飞行出界
            true
        } else if (locationY <= 0) {
            // 向上飞行出界
            true
        }

        false

    }

    /** 标记消失， isValid = false. notValid() => true.
      */
    def vanish() = {
        isValid = false
    }

}
