package com.project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaForwardController {

    // Forward root and single-level paths (excluding /api and static files)
    @RequestMapping(value = { "/", "/{path:^(?!api$)[^\\.]*$}" })
    public String forwardRootAndSingle() {
        return "forward:/index.html";
    }

    // Forward nested paths without a dot (excluding static files)
    @RequestMapping(value = "/**/{path:[^\\.]+}")
    public String forwardNested() {
        return "forward:/index.html";
    }
}
