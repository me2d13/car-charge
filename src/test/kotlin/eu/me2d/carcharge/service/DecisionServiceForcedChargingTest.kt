package eu.me2d.carcharge.service

import eu.me2d.carcharge.dto.AppProps
import eu.me2d.carcharge.dto.ChargingMode
import eu.me2d.carcharge.dto.Conditions
import eu.me2d.carcharge.log.LoggingService
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.string.shouldContain
import io.mockk.*

class DecisionServiceForcedChargingTest : FunSpec({

    val controlServiceMock = mockk<ControlService>()
    val loggingService = LoggingService(AppProps())
    val decisionService = DecisionService(loggingService, controlServiceMock)

    isolationMode = IsolationMode.InstancePerTest

    test("charging should stop when car battery is full") {
        every { controlServiceMock.stopCharging(any()) } just Runs
        val newConditions = Conditions.default().copy(
            mode = ChargingMode.FORCED,
            cableConnected = true,
            charging = true,
            carBatteryLevel = 91.0,
            maxCarBatteryLevel = 90.0,
        )
        decisionService.performUpdate("car1", newConditions)
        verify(exactly = 1) {
            controlServiceMock.stopCharging("car1")
        }
    }

    test("charging should start when car battery is not full") {
        every { controlServiceMock.startCharging(any()) } just Runs
        val newConditions = Conditions.default().copy(
            mode = ChargingMode.FORCED,
            cableConnected = true,
            charging = false,
            carBatteryLevel = 60.0,
            maxCarBatteryLevel = 90.0,
        )
        decisionService.performUpdate("car1", newConditions)
        verify(exactly = 1) {
            controlServiceMock.startCharging("car1")
        }
    }

    test("charging should stop when mode is changed to OFF") {
        every { controlServiceMock.stopCharging(any()) } just Runs
        val newConditions = Conditions.default().copy(
            mode = ChargingMode.OFF,
            cableConnected = true,
            charging = true,
            carBatteryLevel = 81.0,
            maxCarBatteryLevel = 90.0,
        )
        decisionService.performUpdate("car1", newConditions)
        verify(exactly = 1) {
            controlServiceMock.stopCharging("car1")
        }
    }

    test("charging should not stop when car battery is not full") {
        every { controlServiceMock.stopCharging(any()) } just Runs
        val oldConditions = Conditions.default().copy(
            mode = ChargingMode.FORCED,
            cableConnected = true,
            charging = true,
            carBatteryLevel = 80.0,
            maxCarBatteryLevel = 90.0,
        )
        decisionService.performUpdate("car1", oldConditions)
        val newConditions = Conditions.default().copy(
            mode = ChargingMode.FORCED,
            cableConnected = true,
            charging = true,
            carBatteryLevel = 82.0,
            maxCarBatteryLevel = 90.0,
        )
        decisionService.performUpdate("car1", newConditions)
        verify(exactly = 0) {
            controlServiceMock.stopCharging("car1")
        }
    }


})
