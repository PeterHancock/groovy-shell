package com.github.peterhancock.groovyshell.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import com.github.peterhancock.groovyshell.GroovyShellService

@Configuration
class GroovyShellConfig {

	private static GroovyShellService groovyShellService

	@Autowired
	private ApplicationContext springContext
	
	@Bean
	public GroovyShellService groovyShellService() {
		//TODO why is this method called twice?
		if (groovyShellService == null) {
			def bindings = [context: springContext]
			println 'goovy shell on port 1111'
			groovyShellService = new GroovyShellService(bindings, 1111)
			groovyShellService.launchInBackground()
		}
		return groovyShellService
	}
}
