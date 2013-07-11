package com.github.peterhancock.groovyshell.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import com.github.peterhancock.groovyshell.GroovyShellService

@Configuration
class GroovyShellConfig {

    @Autowired
    private ApplicationContext springContext

    @Bean(destroyMethod = "destroy")
    public GroovyShellService groovyShellService() {
        def bean = new GroovyShellService([context: springContext], 1112)
        bean.start()
        bean
    }
}
