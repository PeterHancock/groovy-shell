package com.github.peterhancock.groovyshell

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

public class GroovyShellService extends GroovyService {

    private ServerSocket serverSocket
    private int port
    private Thread serverThread
    private threads = []
    private alive = true

    public GroovyShellService() {
    }

    public GroovyShellService(int port) {
        this([:], port)
    }

    public GroovyShellService(Map bindings, int port) {
        super(bindings)
        println "goovy shell on port $port"
        this.port = port
    }

    public void launch() {
        println "GroovyShellService launch()"

        try {
            serverSocket = new ServerSocket(port);
            println("GroovyShellService launch() serverSocket: " + serverSocket)

            while (alive) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    println("GroovyShellService launch() clientSocket: " + clientSocket);
                }
                catch (IOException e) {
                    println("e: " + e);
                    return;
                }

                GroovyShellThread clientThread = new GroovyShellThread(clientSocket, createBinding());
                threads.add(clientThread);
                clientThread.start();
            }
        }
        catch (IOException e) {
            println("e: " + e);
        }
        finally {
            try {
                serverSocket.close();
                println("GroovyShellService launch() closed connection");
            }
            catch (IOException e) {
                println("e: " + e);
            }
        }
    }

    @Override
    public void destroy() {
        println("closing serverSocket: " + serverSocket);
        try {
            alive = false;
            serverSocket.close();
            threads.each { nextThread ->
                println("closing nextThread: " + nextThread);
                nextThread.getSocket().close();

            }
        }
        catch (IOException e) {
            println("e: " + e);
        }
    }

    public void setSocket(final int socket) {
        this.socket = socket;
    }
}
