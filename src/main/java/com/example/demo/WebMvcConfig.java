package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.BeforeActionInterceptor;
import com.example.demo.interceptor.NeedLoginInterceptor;
import com.example.demo.interceptor.NeedLogoutInterceptor;

@Component
public class WebMvcConfig implements WebMvcConfigurer  {
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;
	@Autowired
	NeedLogoutInterceptor needLogoutInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**");
		
		registry.addInterceptor(needLoginInterceptor).addPathPatterns("/usr/article/write")
		.addPathPatterns("/usr/article/doWrite").addPathPatterns("/usr/article/modify")
		.addPathPatterns("/usr/article/doModify").addPathPatterns("/usr/article/doDelete")
		.addPathPatterns("/usr/member/doLogout").addPathPatterns("/usr/member/doIncreaseCountRd")
		.addPathPatterns("/usr/reactionPoint/doGoodReaction");
		
		registry.addInterceptor(needLogoutInterceptor).addPathPatterns("/usr/member/login")
		.addPathPatterns("/usr/member/doLogin").addPathPatterns("/usr/member/join")
		.addPathPatterns("/usr/member/doJoin");
	}
}
