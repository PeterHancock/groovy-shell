package com.github.peterhancock.groovyshell

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

public class GroovyShellService extends GroovyService {

    private ServerSocket serverSocket
    private int socket
    private Thread serverThread
    private threads = []

    public GroovyShellService() {
    }

    public GroovyShellService(int socket) {
        this.socket = socket
    }

    public GroovyShellService(Map bindings, int socket) {
        super(bindings);
        this.socket = socket;
    }
    
    public void launch() {
        println "GroovyShellService launch()"

        try {
            serverSocket = new ServerSocket(socket);
            println("GroovyShellService launch() serverSocket: " + serverSocket)

            while (true) {
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
