package org.ankarton.network.login.parser.packet.fighter;

import org.ankarton.database.Database;
import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Buffer;
import org.ankarton.world.entity.team.Fighter;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.entity.Client;

import java.util.ArrayList;

public class FighterDeleteRequest {

	public static void parse(Client client, IoBuffer recv) {
		try {
            long id = recv.getLong();

            ArrayList<Fighter> fighters = client.getAccount().getCoach().getFighters();
            new ArrayList<>(fighters).stream().filter(fighter -> fighter.getId() == id).forEach(fighters::remove);

            Database.getInstance().getFightersData().queryForId(id);
            new Buffer(9, OpCode.Send.FIGHTER_DELETION_RESULT).put(0).putLong(id).send(client);
        } catch(Exception ignored) {
            new Buffer(9, OpCode.Send.FIGHTER_DELETION_RESULT).put(1).send(client);
        }
	}
}