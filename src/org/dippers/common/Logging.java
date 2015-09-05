package org.dippers.common;

public class Logging 
{
	/**
	 * Logs a generic message
	 * @param msg Text to log out
	 */
	public static void LogMsg(String msg)
	{
		System.out.println("[DIPP] " + msg);
	}
	
	/**
	 * Logs an error message
	 * @param where Where in the code the error happened. General summary, task etc.
	 * @param error Specific error message which can be used for debugging.
	 */
	public static void LogError(String where, String error)
	{
		LogMsg("[ERROR: " + where + "] " + error);
	}
}
