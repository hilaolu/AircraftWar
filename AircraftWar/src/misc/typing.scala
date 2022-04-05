package misc.typing

object AircraftType extends Enumeration {
    type AircraftType = Value
    val HERO, ENEMY = Value
}

object EnemyType extends Enumeration {
    type EnemyType = Value
    val BOSS, ELITE, TRIVIAL = Value
}

object ItemType extends Enumeration {
    type ItemType = Value
    val BOMB, BULLET, BLOOD = Value
}

object WeaponType extends Enumeration {
    type WeaponType = Value
    val SNIPER, MACHINEGUN = Value
}
