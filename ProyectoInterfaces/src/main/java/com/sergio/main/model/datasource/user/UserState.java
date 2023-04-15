package com.sergio.main.model.datasource.user;


/**
 * Controla el estado en el que se encuentra la aplicación respecto al logueo del usuario.
 */
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
