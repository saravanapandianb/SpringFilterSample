package com.saran.filters;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.http.MediaType;

public class RequestWrapper extends HttpServletRequestWrapper {

	private String _body;

	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		_body = "";
		BufferedReader bufferedReader = request.getReader();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			_body += line;
		}
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(_body.getBytes());
		return new ServletInputStream() {
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
		};
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}
	
	public String getHeader(String headerName) {
		String headerValue = super.getHeader(headerName);
		if ("Accept".equalsIgnoreCase(headerName)) {
			return headerValue.replaceAll(MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE);
		} else if ("Content-Type".equalsIgnoreCase(headerName)) {
			return headerValue.replaceAll(MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE);
		}
		return headerValue;
	}
	
	public String request() {
		String contentTypeValue = super.getContentType();
		if (MediaType.TEXT_PLAIN_VALUE.equalsIgnoreCase(contentTypeValue)) {
			return MediaType.APPLICATION_JSON_VALUE;
		}
		return contentTypeValue;
	}
	
	public String getRequestBody() throws IOException {
		
		StringBuilder builder = new StringBuilder();
		String aux = "";
		BufferedReader reader = getReader();

		while ((aux = reader.readLine()) != null) {
		    builder.append(aux);
		}
		String requestBody = builder.toString();
		return requestBody;
	}
	
	public void setRequestBody(String decryptedText) {

		_body = decryptedText;
	}
}