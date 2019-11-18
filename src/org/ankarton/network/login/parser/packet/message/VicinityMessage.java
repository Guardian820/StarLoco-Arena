package org.ankarton.network.login.parser.packet.message;

import org.ankarton.network.entity.Buffer;
import org.ankarton.network.login.parser.packet.coach.PlayerStatisticsReport;
import org.ankarton.network.login.parser.packet.coach.management.CoachInventoryUpdateRequest;
import org.ankarton.util.document.data.card.CoachCardTemplate;
import org.ankarton.util.factory.FactoryManager;
import org.ankarton.world.entity.card.CoachCard;
import org.ankarton.world.entity.coach.Coach;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;
import org.ankarton.network.login.parser.Parser;

import java.util.ArrayList;

public class VicinityMessage {

	public static void parse(Client client, IoBuffer recv) {
		byte[] msg = new byte[recv.getShort()];
		recv.get(msg);
		
		if(VicinityMessage.parse(client, Parser.getStringByBytes(msg))) return;
		
		Coach coach = client.getAccount().getCoach();
		
		new Buffer(10 + coach.getName().length() + msg.length, Send.VICINITY_MESSAGE).put(coach.getName().length())
		.put(coach.getName().getBytes()).putLong(coach.getId()).put(msg.length).put(msg).sendToAllWithout(coach);
	}
	
	private static boolean parse(Client client, String message) {
		if(message.charAt(0) != '/') return false;
		String[] infos = message.split(" ");
		switch(infos[0].substring(1).toUpperCase()) {
			case "STATS":
				PlayerStatisticsReport.parse(client, null);
				System.out.println("Send statistics packet");
				break;
			case "TP":
				new Buffer(13, Send.ENTER_WORLD_INSTANCE)
				.putFloat(Integer.parseInt(infos[1])).putFloat(Integer.parseInt(infos[2]))
				.putShort(Integer.parseInt(infos[3])).putShort(Integer.parseInt(infos[4])).put(0).send(client.getAccount().getClient());
				break;
			case "CARD":
					int id = Integer.parseInt(infos[1]);
					CoachCardTemplate template = FactoryManager.getInstance().getCoachCard().get(id);

					if(template != null) {
						CoachCard coachCard = new CoachCard((short) 1, (short) 0, (byte) 0, id);
						client.getAccount().getCoach().getInventory().add(coachCard);

						CoachInventoryUpdateRequest packet = new CoachInventoryUpdateRequest();
						packet.setAddInventory(coachCard);
						packet.parse(client, null);
						//CoachInventoryUpdateRequest.parse(client, null);
						System.out.println("Items type ("+ template.getType() +") generated");
					} else
						System.out.println("Unknown template");
				break;
		}
		return true;
	}
}