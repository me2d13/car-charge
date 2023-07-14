package eu.me2d.carcharge.service

import eu.me2d.carcharge.log.LoggingService
import org.springframework.stereotype.Service

interface IControlService {
    fun startCharging(carId:String)
    fun stopCharging(carId:String)
}

@Service
class ControlService(
    val loggingService: LoggingService,
) : IControlService {
    override fun startCharging(carId:String) {
        loggingService.logForCar(carId,"Requesting start charging")
    }

    override fun stopCharging(carId:String) {
        loggingService.logForCar(carId,"Requesting stop charging")
    }
}