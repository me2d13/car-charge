package eu.me2d.carcharge

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class WebController {

    @GetMapping("/")
    fun index(mav: ModelAndView): ModelAndView {
        mav.viewName = "index"
        mav.model["logs"] = listOf("log1", "log2", "log3")
        return mav
    }

}