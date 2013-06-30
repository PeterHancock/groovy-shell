package com.github.peterhancock.groovyshell;

import groovy.lang.Binding;

import java.util.Map;


public abstract class GroovyService  {
    
    private Map<String, Object> bindings;
    private boolean launchAtStart;
    private Thread serverThread;

    public GroovyService() {
        super();
    }
    
    public GroovyService(Map<String, Object> bindings) {
        this();
        this.bindings = bindings;
    }

    public void launchInBackground() {
        serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    launch();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        serverThread.setDaemon(true);
        serverThread.start();
    }

    public abstract void launch();

    protected Binding createBinding() {
        Binding binding = new Binding();

        if (bindings != null)  {
            for (Map.Entry<String, Object> nextBinding : bindings.entrySet()) {
                binding.setVariable(nextBinding.getKey(), nextBinding.getValue());
            }
        }

        return binding;
    }
    
    public void initialize() {
        if (launchAtStart) {
            launchInBackground();
        }
    }
    
    public void destroy() {
    }

    public void setBindings(final Map<String, Object> bindings) {
        this.bindings = bindings;
    }

    public boolean isLaunchAtStart() {
        return launchAtStart;
    }

    public void setLaunchAtStart(boolean launchAtStart) {
        this.launchAtStart = launchAtStart;
    }
}
