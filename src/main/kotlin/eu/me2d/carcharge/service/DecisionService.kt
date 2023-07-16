package eu.me2d.carcharge.service

import eu.me2d.carcharge.dto.ChargingMode
import eu.me2d.carcharge.dto.Conditions
import eu.me2d.carcharge.log.LoggingService
import eu.me2d.carcharge.tools.BooleanEvent
import org.springframework.stereotype.Service

@Service
class DecisionService(
    val loggingService: LoggingService,
    val controlService: IControlService,
) {
    val conditionsPerCarId: MutableMap<String, Conditions> = mutableMapOf()
    fun performUpdate(carId:String, conditions: Conditions) {
        val currentConditions = conditionsPerCarId[carId] ?: Conditions.default()
        makeDecisions(carId, currentConditions, conditions)
        conditionsPerCarId[carId] = conditions
    }

    private fun makeDecisions(carId:String, currentConditions: Conditions, newConditions: Conditions) {
        val cableEvent = BooleanEvent(currentConditions.cableConnected, newConditions.cableConnected)
        if (cableEvent.stayedFalse()) {
            loggingService.logForCar(carId, "Cable is not connected")
        } else if (cableEvent.becameTrue()) {
            loggingService.logForCar(carId, "Cable got connected")
            makeDecisionsForConnectedCable(carId, currentConditions, newConditions)
        } else if (cableEvent.becameFalse()) {
            loggingService.logForCar(carId, "Cable got disconnected")
        } else {
            makeDecisionsForConnectedCable(carId, currentConditions, newConditions)
        }
    }

    private fun makeDecisionsForConnectedCable(
        carId: String,
        currentConditions: Conditions,
        newConditions: Conditions
    ) {
        if (newConditions.mode == ChargingMode.OFF) {
            if (newConditions.charging == true) {
                loggingService.logForCar(carId, "Charging requested to stop by mode OFF")
                controlService.stopCharging(carId)
            } else {
                loggingService.logForCar(carId, "Charging already stopped by mode OFF")
            }
        } else if (newConditions.mode == ChargingMode.FORCED) {
            makeDecisionsForForcedCharging(carId, currentConditions, newConditions)
        } else if (newConditions.mode == ChargingMode.BY_HOUSE_BATTERY) {
            makeDecisionsForHouseBatteryCharging(carId, currentConditions, newConditions)
        }
    }

    private fun makeDecisionsForHouseBatteryCharging(
        carId: String,
        currentConditions: Conditions,
        newConditions: Conditions
    ) {
        TODO("Not yet implemented")
    }

    private fun makeDecisionsForForcedCharging(
        carId: String,
        currentConditions: Conditions,
        newConditions: Conditions
    ) {
        if (newConditions.charging == true) {
            if (shouldStopOnCarBatteryLevel(carId, newConditions)) {
                controlService.stopCharging(carId)
            } else {
                loggingService.logForCar(carId, "Forced charging in progress until reaching car battery level ${newConditions.maxCarBatteryLevel}. Current level is ${newConditions.carBatteryLevel}")
            }
        } else {
            if ((newConditions.carBatteryLevel ?: 0.0) < (newConditions.maxCarBatteryLevel ?: 100.0)) {
                loggingService.logForCar(carId, "Forced charging requested as car battery level ${newConditions.carBatteryLevel} is below max level ${newConditions.maxCarBatteryLevel}")
                controlService.startCharging(carId)
            } else {
                loggingService.logForCar(carId, "Forced charging not needed as car battery level ${newConditions.carBatteryLevel} is above max level ${newConditions.maxCarBatteryLevel}")
            }
        }
    }

    private fun shouldStopOnCarBatteryLevel(carId: String, newConditions: Conditions): Boolean {
        if ((newConditions.carBatteryLevel ?: 0.0) >= (newConditions.maxCarBatteryLevel ?: 100.0)) {
            loggingService.logForCar(carId, "Stopping charging as car battery level ${newConditions.carBatteryLevel} is at max level ${newConditions.maxCarBatteryLevel}")
            return true
        }
        return false
    }
}