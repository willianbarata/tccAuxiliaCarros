package com.willianfernando.novoprojeto.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.willianfernando.novoprojeto.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		System.out.println("Entrou no USERSS");
		try {
			System.out.println("Passou 1");
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			System.out.println("Passou 2");
			return null;
		}
	}
}
