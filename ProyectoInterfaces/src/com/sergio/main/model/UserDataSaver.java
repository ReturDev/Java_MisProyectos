package com.sergio.main.model;

public class UserDataSaver {

	private static boolean userLoged;

	public static boolean isUserLoged() {
		return userLoged;
	}

	public static void setUserLoged(boolean userLoged) {
		UserDataSaver.userLoged = userLoged;
	}
	
	
}
