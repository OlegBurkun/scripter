package com.ob.scripter.service;

import java.io.StringWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.stereotype.Component;

@Component
public class ScripterService {
	
	final private ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	
	private StringWriter stringWriter;
	
	public String eval(String script) {
		stringWriter = new StringWriter();
		engine.getContext().setWriter(stringWriter);
        try {
            engine.eval(script);
        }catch (ScriptException e) {
            return "ScriptException "+e.getMessage();
        }
        return stringWriter.toString();
	}

}
