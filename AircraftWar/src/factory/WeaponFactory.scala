package factory

import aircraft.weapon.MachineGun
import aircraft.weapon.Sniper
import aircraft.weapon.AbstractWeapon

import misc.typing.WeaponType
import misc.typing.WeaponType._

object WeaponFactory {
    def spawn(
        t: WeaponType
    ): AbstractWeapon = {
        if (t == WeaponType.SNIPER) {
            new Sniper()
        } else {
            new MachineGun()
        }
    }
}
