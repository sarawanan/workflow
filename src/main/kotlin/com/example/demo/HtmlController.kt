package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController @Autowired constructor(
    val service: CommonService,
) {
    @GetMapping("/")
    fun getMemos(model: Model): String {
        model["memos"] = service.getAllMemos().map { it.toDto() }
        return "memo"
    }
}

