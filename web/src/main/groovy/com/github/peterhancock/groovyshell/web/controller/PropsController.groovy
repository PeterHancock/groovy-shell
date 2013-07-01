package com.github.peterhancock.groovyshell.web.controller

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/props")
public class PropsController {

    @RequestMapping(method = RequestMethod.GET)
    public String printProps(ModelMap model) {
        def props = System.properties.collect().join('<br/>')
        model.addAttribute('props', props)
        return 'props'
    }

}
