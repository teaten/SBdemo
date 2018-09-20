package com.example.SBdemo.configuration.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 根据不同runtime处理，返回给用户
 * 
 * @author Wenjun
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	private Map<String, Object> exceptionHandler(HttpServletRequest request, Exception e) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("rtcd", "false");
		resMap.put("errMsg", e.getMessage());
		return resMap;
	}
}
