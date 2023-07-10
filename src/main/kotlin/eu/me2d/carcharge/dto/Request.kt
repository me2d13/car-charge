package eu.me2d.carcharge.dto

data class Request(
    val carId: String = "car1",
    val mode: ChargingMode? = null,
    val cableConnected: Boolean? = null,
    val charging: Boolean? = null,
    val carBatteryLevel: Double? = null,
    val houseBatteryLevel: Double? = null,
)

enum class ChargingMode {
    OFF,
    FORCED,
    BY_HOUSE_BATTERY,
}
