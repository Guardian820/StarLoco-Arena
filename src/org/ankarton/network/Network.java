package org.ankarton.network;

import org.ankarton.network.login.LoginServer;

public class Network {
	public static boolean beta = true;
	public static State state = State.UNVAILABLE;
	
	/** General Server **/
	public static final byte version = 2;
	public static final short revision = 04;
	public static final String build = "7025";
	
	/** Login Server **/
	public static LoginServer login;
	public static final short loginPort = 450;
	
	public enum State {
		AVAILABLE,
		UNVAILABLE,
		IN_SAVE
	}
}