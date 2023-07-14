package eu.me2d.carcharge.dto

data class Conditions(
    val mode: ChargingMode? = null,
    val cableConnected: Boolean? = null,
    val charging: Boolean? = null,
    val carBatteryLevel: Double? = null,
    val houseBatteryLevel: Double? = null,
    val maxCarBatteryLevel: Double? = 100.0,
) {
    companion object {
        fun default(): Conditions {
            return Conditions(
                mode = ChargingMode.OFF,
                cableConnected = false,
                charging = false,
                carBatteryLevel = 0.0,
                houseBatteryLevel = 0.0,
            )
        }
    }
}

enum class ChargingMode {
    OFF,
    FORCED,
    BY_HOUSE_BATTERY,
}
