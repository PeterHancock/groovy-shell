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
	
	@Bean
	public GroovyShellService groovyShellService() {
		println 'goovy shell on port 1111'
		def groovyShellService = new GroovyShellService([context: springContext], 1111)
		groovyShellService.launchInBackground()
		return groovyShellService
	}
}
