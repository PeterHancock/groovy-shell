Remote Groovy Shell
===================

This is an update of [Bruce Fancher] original project referenced in  [Embedding a Groovy Console in a Java Server Application], written in Groovy and compatible with Groovy 2.0.5.


Includes Spring @Configuration for convenient integration with existing Spring Web Apps.

Usage
-----

Drop the groovy-shell jar into the lib dir of your web app and configure your Spring Context to locate the @Configuration:

    <context:component-scan base-package="com.github.peterhancock.groovyshell.spring" />

or perhaps externalise the configuration:

    <context:component-scan base-package="${component.scan:''}" />

together with the system property

    component.scan=com.github.peterhancock.groovyshell.spring

[Embedding a Groovy Console in a Java Server Application]: http://groovy.codehaus.org/Embedding+a+Groovy+Console+in+a+Java+Server+Application

[Bruce Fancher]:https://bitbucket.org/bkumar/spring-groovy-console

