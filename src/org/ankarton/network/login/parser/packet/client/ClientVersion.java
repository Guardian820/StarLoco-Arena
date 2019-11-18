package org.ankarton.network.login.parser.packet.client;

import org.ankarton.network.Network;
import org.ankarton.network.entity.Buffer;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;
import org.ankarton.network.login.parser.Parser;

public class ClientVersion {

	public static void parse(Client client, IoBuffer recv) {
        if(Network.state.equals(Network.State.IN_SAVE)) {
        	new Buffer(1, Send.AUTHENTICATION_RESULT).put(4).send(client);
        	return;
        }
        
		byte version = recv.get();
		short revision = recv.getShort();
		byte[] bytes = new byte[recv.get()];
		recv.get(bytes);
		String build = Parser.getStringByBytes(bytes).replace(" ", "");
		System.out.println(build + "! !" + Network.build + "! " + build.equals(Network.build));
		if(version != Network.version || revision != Network.revision || !build.equals(Network.build)) {
			new Buffer(3, Send.INVALID_VERSION).put(Network.version).putShort(Network.revision).send(client);
			client.kick();
		}	
		
	}
}