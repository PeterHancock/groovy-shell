package com.github.peterhancock.groovyshell

import org.codehaus.groovy.tools.shell.Groovysh
import org.codehaus.groovy.tools.shell.IO

public final class GroovyShellService {

    private final bindings
    private final port
    private serverThread
    private serverSocket
    private clientConnections = [:]
    private alive = true

    public GroovyShellService(Map bindings, int port) {
        this.bindings = bindings
        println "goovy shell on port $port"
        this.port = port
    }

    public GroovyShellService start() {
        serverThread = Thread.startDaemon {
            try {
                startServer()
            }
            catch (ex) {
                logEx ex
            }
        }
        this
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(port)
            println "GroovyShellService starting: $serverSocket"
            while (alive) {
                def clientSocket
                try {
                    clientSocket = serverSocket.accept()
                    println "GroovyShellService launch() clientSocket: $clientSocket"
                }
                catch (ex) {
                    logEx ex
                    return
                }

                def t
                t = Thread.start {
                    try {
                        final PrintStream out = new PrintStream(clientSocket.getOutputStream())
                        final InputStream input = clientSocket.getInputStream()
                        final binding = createBinding()
                        binding.setVariable('out', out)
                        final Groovysh groovy = new Groovysh(binding, new IO(input, out, out))
                        try {
                            groovy.run()
                        }  catch (ex) {
                             logEx ex
                        }
                        println "closing conneciton $t.id"
                        clientConnections.remove(t.id)
                        out.close()
                        input.close()
                        clientSocket.close()
                    }
                    catch (ex) {
                        logEx ex
                    }
                }
                clientConnections[t.id] = clientSocket

            }
        } catch (IOException ex) {
            logEx ex
        } finally {
            destroy()
        }
    }

    private Binding createBinding() {
        this.bindings.inject(new Binding()) { binding, entry ->
            binding.setVariable(entry.key, entry.value)
            binding
        }
    }

    public void destroy() {
        alive = false
        try {
            clientConnections.each { id, socket ->
                println "closing connection $id"
                socket.close()
            }
        }
        catch (IOException ex) {
           logEx(ex)
        } finally {
            println "closing serverSocket: $serverSocket"
            serverSocket.close()
        }
    }

    private logEx(ex) {
        println "Exception: $ex"
        ex.printStackTrace()
    }

}
