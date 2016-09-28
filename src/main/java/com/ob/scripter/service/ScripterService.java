package com.ob.scripter.service;

import java.io.StringWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.stereotype.Component;

@Component
public class ScripterService {
	
	final private ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	
	private Thread thread;
	
	private StringWriter stringWriter;
	
	public String eval(String script) {
		//TODO threading in progress
		thread = Thread.currentThread();
		stringWriter = new StringWriter();
		engine.getContext().setWriter(stringWriter);
        try {
            engine.eval(script);
        }catch (ScriptException e) {
            return "ScriptException " + e.getMessage();
        }
        return stringWriter.toString();
	}

	public String stop() {
		if (thread.isAlive()) {
			thread.stop();
			return "script stopped";
		}
		return "nothing to stop";
	}

	public String status() {
		return thread.isAlive() ? "script working" : "the server is free";
	}

}
