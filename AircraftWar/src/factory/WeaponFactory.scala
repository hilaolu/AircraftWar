package factory

import aircraft.weapon.MachineGun
import aircraft.weapon.Sniper
import aircraft.weapon.AbstractWeapon

import misc.typing.WeaponType
import misc.typing.WeaponType._
import aircraft.weapon.ShotGun

object WeaponFactory {
    def spawn(
        t: WeaponType
    ): AbstractWeapon = {
        t match {
            case WeaponType.SNIPER  => new Sniper()
            case WeaponType.SHOTGUN => new ShotGun()
            case _                  => new MachineGun()
        }
    }
}
