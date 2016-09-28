package com.ob.scripter.controller;

import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.ob.scripter.service.ScripterService;

@RestController
@RequestMapping(value = "/scripter")
public class ScripterController {
	
	@Autowired
	private ExecutorService executorService;
	
	@Autowired
	private ScripterService scripterrService;
	
	@RequestMapping(value = "/eval", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public DeferredResult<String> evalScript(@RequestBody String script, HttpServletResponse response) {
		DeferredResult<String> result = new DeferredResult<>();
		Runnable runnable = () -> {
			try {
				scripterrService.eval(script);
			} catch (Exception e) {
				result.setErrorResult(new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR));
			}
		};
		executorService.submit(runnable);
		return result;
	}
	
	@RequestMapping(value = "/stop", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public String stopScript() {
		return scripterrService.stop();
	}
	
	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String scriptStatus() {
		return scripterrService.status();
	}

}
