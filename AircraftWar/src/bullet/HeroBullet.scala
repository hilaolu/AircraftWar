package bullet

/** @Author
  *   hitsz
  */
class HeroBullet(
    _locationX: Int,
    _locationY: Int,
    _speedX: Int,
    _speedY: Int,
    _power: Int
) extends AbstractBullet(_locationX, _locationY, _speedX, _speedY, _power) {
    def this(power: Int) = {
        this(0, 0, 0, 0, power)
    }
}
