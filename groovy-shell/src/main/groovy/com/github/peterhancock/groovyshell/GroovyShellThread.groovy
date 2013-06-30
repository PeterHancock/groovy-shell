
package com.github.peterhancock.groovyshell 
import groovy.lang.Binding;

import org.codehaus.groovy.tools.shell.Groovysh
import org.codehaus.groovy.tools.shell.IO

import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class GroovyShellThread extends Thread {
    public static final String OUT_KEY = "out";

    private Socket socket;
    private Binding binding;

    public GroovyShellThread(Socket socket, Binding binding) {
        super();
        this.socket = socket;
        this.binding = binding;
    }

    @Override
    public void run() {
        try {
            final PrintStream out = new PrintStream(socket.getOutputStream());
            final InputStream input = socket.getInputStream();

            binding.setVariable(OUT_KEY, out);
            final Groovysh groovy = new Groovysh(binding, new IO(input, out, out));

            try {
                groovy.run();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            out.close();
            input.close();
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
