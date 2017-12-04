package com.saran.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.saran.utils.EncryptionUtil;

public class EncryptionFilter extends GenericFilterBean {

	private String key = "Duc@nt";

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {

		// Caching the Request/Response object
		RequestWrapper requestWrapper = new RequestWrapper(((HttpServletRequest) request));
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

		// Decrypt the Request data and set it to the request.
		String encryptedRequest = requestWrapper.getRequestBody();
		String decryptedRequest = decrypt(encryptedRequest);
		requestWrapper.setRequestBody(decryptedRequest);
		
		// pass the wrappers on to the next entry
		filterChain.doFilter(requestWrapper, responseWrapper);
		
		// Read the result and Write back the encrypted value to Response
		String responseData = IOUtils.toString(responseWrapper.getContentInputStream());
		String encryptedResponseData = encrypt(responseData);
		response.reset();
		response.getWriter().write(encryptedResponseData);
	}

	private String encrypt(String responseData) {
		return EncryptionUtil.encrypt(responseData, key);
	}

	private String decrypt(String requestData) {
		return EncryptionUtil.decrypt(requestData, key);
	}
}