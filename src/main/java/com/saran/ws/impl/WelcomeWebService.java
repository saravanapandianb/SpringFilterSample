package com.saran.ws.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saran.models.Welcome;
import com.saran.ws.api.BaseWebService;

@Controller
@RequestMapping("/home")
public class WelcomeWebService implements BaseWebService {

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String getMessage(HttpServletRequest request, HttpServletResponse response) {

		String message = "";
		try {
			message = "Hi Welcome";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@ResponseBody
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public Welcome getMessage(@RequestBody Welcome welcome) {

		try {
			welcome.setMessage(welcome.getMessage() + " -> Processed...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return welcome;
	}
}
