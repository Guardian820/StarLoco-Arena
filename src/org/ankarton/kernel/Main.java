package org.ankarton.kernel;

import org.ankarton.database.Database;
import org.ankarton.network.Network;
import org.ankarton.network.login.LoginServer;
import org.ankarton.world.World;

public class Main {

	public static Network network;
	
	public static void main(String[] arg) {
		Database.getInstance();
        World.getInstance().initialize();

		Network.login = new LoginServer();
		Network.login.start();
		Network.state = Network.State.AVAILABLE;
		//check(7);
	}

    /** Fonction test/rappel **/
	public static void check(int opt) {
		System.out.println(
		((opt & 0x1) == 1) + " " +
		((opt & 0x2) == 2) + " " +
		((opt & 0x4) == 4) + " " +
		((opt & 0x10) == 16) + " " +
		((opt & 0x8) == 8));
	}
}