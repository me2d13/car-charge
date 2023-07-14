package eu.me2d.carcharge

import eu.me2d.carcharge.log.LoggingService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class WebController(val loggingService: LoggingService) {

    @GetMapping("/")
    fun index(mav: ModelAndView): ModelAndView {
        mav.viewName = "index"
        mav.model["logs"] = loggingService.logMessages
        // date formatting maybe improved by https://medium.com/@pdouvitsas/global-localdate-format-in-spring-boot-and-thymeleaf-29ff83b8f4c8
        return mav
    }

}