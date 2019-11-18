package org.ankarton.network.login.parser.packet.fighter;

import org.ankarton.world.entity.coach.Coach;
import org.ankarton.world.entity.team.Fighter;
import org.ankarton.network.entity.Buffer;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;

import java.util.ArrayList;

public class FightInformationListRequest {

	public static void parse(Client client, IoBuffer recv) {
        Coach coach = client.getAccount().getCoach();
        ArrayList<Fighter> fighters = new ArrayList<>(coach.getFighters());

        int totalNameLength = 0;
        for(Fighter fighter : fighters)
            if(fighter != null)
                totalNameLength += fighter.getName().length();

		final Buffer buffer = new Buffer(1 + 21 * fighters.size() + totalNameLength, Send.FIGHTER_INFORMATION_LIST).put(fighters.size());

        fighters.stream().filter(fighter -> fighter != null).forEach(fighter -> {
            byte nameLength = (byte) fighter.getName().length();
            buffer.putLong(fighter.getId()).putShort(11 + nameLength).put(1).putShort(fighter.getBudget())
                    .put(fighter.getBreed()).put(nameLength).put(fighter.getName().getBytes())
                    .put(fighter.getSex()).put(fighter.getSkin()).putShort(0).putShort(0);
        });

        buffer.send(client);
	}
}