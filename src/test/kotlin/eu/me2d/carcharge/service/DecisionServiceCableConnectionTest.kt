package eu.me2d.carcharge.service

import eu.me2d.carcharge.dto.Conditions
import eu.me2d.carcharge.log.LoggingService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.string.shouldContain
import io.mockk.*

class DecisionServiceCableConnectionTest : FunSpec({

    val controlServiceMock = mockk<ControlService>()

    test("cable stays disconnected") {
        val loggingServiceMock = mockk<LoggingService>()
        val decisionService = DecisionService(loggingServiceMock, controlServiceMock)
        every { loggingServiceMock.logForCar(any(), any()) } just Runs
        val newConditions = Conditions.default()
        decisionService.performUpdate("car1", newConditions)
        verify(exactly = 1) {
            loggingServiceMock.logForCar(any(), withArg {
                it shouldContain "Cable is not connected"
            })
        }
    }

    test("cable got connected") {
        val loggingServiceMock = mockk<LoggingService>()
        val decisionService = DecisionService(loggingServiceMock, controlServiceMock)
        val logArgs = mutableListOf<String>()
        every { loggingServiceMock.logForCar(any(), capture(logArgs)) } just Runs
        val newConditions = Conditions.default().copy(cableConnected = true)
        decisionService.performUpdate("car1", newConditions)
        verify { loggingServiceMock.logForCar(any(), any()) }
        logArgs shouldContain "Cable got connected"
    }

})
