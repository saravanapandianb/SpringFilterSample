package com.saran.ws.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saran.models.Welcome;

public interface BaseWebService  {

	public String getMessage(HttpServletRequest request, HttpServletResponse response);
	public Welcome getMessage(Welcome welcome);
	
}
