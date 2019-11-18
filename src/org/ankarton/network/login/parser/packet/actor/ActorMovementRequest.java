package org.ankarton.network.login.parser.packet.actor;

import org.ankarton.network.entity.Buffer;
import org.ankarton.world.entity.coach.Coach;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;

public class ActorMovementRequest {

	// question : CoachActorMovementRequestMessage
	// answer : ActorMovementMessage

	public static void parse(Client client, IoBuffer recv) {
		int numCells = ((recv.limit() - 5) / 10);
		int x = 0, y = 0, z = 0;
		
		Coach.Position position = client.getAccount().getCoach().getPosition();
		Buffer buffer = new Buffer(18 + numCells * 8 + numCells * 2, Send.ACTOR_MOVEMENT).putLong(client.getAccount().getCoach().getId())
				.putInt(position.x).putInt(position.y).putShort(position.z);
		
		for(int i = 0; i < numCells; i++) {
			x = recv.getInt();
			y = recv.getInt();
			z = recv.getShort();		
			buffer.putInt(x).putInt(y).putShort(z);	
		}
		
		buffer.sendToAll();
		client.getAccount().getCoach().setPosition(x, y, (short) z);
	}
}