package com.sergio.main.model.savers;

import com.sergio.main.model.datasources.User;

public class UserDataSaver {

	private static boolean userLoged;
	private static User user;

	public static boolean isUserLoged() {
		return userLoged;
	}

	public static void setUserLoged(boolean userLoged) {
		UserDataSaver.userLoged = userLoged;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		UserDataSaver.user = user;
	}
	
}
