package com.sergio.main.model.datasources.user;

public class UserState {
	
	private static boolean userLogged;
	private static User userLoggedUser;
	
	
	public static boolean isUserLogged() {
		return userLogged;
	}
	
	public static void userLogIn(User user) {
		
		userLoggedUser = user;
		userLogged = true;
		
	}
	
	public static void userLogOut() {
		
		userLoggedUser = null;
		userLogged = false;
		
	}
	
	public static User getUserLoggedData() {
		
		return userLoggedUser;
		
	}

	
}
