package org.ankarton.network.login.parser.packet.fighter;

import org.ankarton.database.Database;
import org.ankarton.world.World;
import org.ankarton.world.entity.coach.Coach;
import org.ankarton.network.entity.Buffer;
import org.ankarton.world.entity.team.Fighter;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;
import org.ankarton.network.login.parser.Parser;

import java.sql.SQLException;

public class FighterCreateRequest {

	public static void parse(Client client, IoBuffer recv) {
		recv.getShort(); 
		recv.get();// 1 = ?
		short budget = recv.getShort();
		byte breed = recv.get();
		byte[] bytes = new byte[recv.get()];
		
		recv.get(bytes);
		String name = Parser.getStringByBytes(bytes);
		byte sex = recv.get();
		byte skin = recv.get();
		
		short spellsLength = recv.getShort();
		if(spellsLength > 0) {
			bytes = new byte[spellsLength];
			recv.get(bytes);
		}
		
		short cardsLength = recv.getShort();
		if(cardsLength > 0) {
			bytes = new byte[cardsLength];
			recv.get(bytes);
		}
		
		Fighter fighter =  new Fighter(name, breed, sex, skin, budget, "" , "");
        bytes = fighter.serialize();

		try {
			Database.getInstance().getFightersData().create(fighter);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Coach coach = client.getAccount().getCoach();
        coach.getFighters().add(fighter);

		try {
			Database.getInstance().getCoachsData().update(coach);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		new Buffer(11 + bytes.length, Send.FIGHTER_CREATE_RESULT).put(0).putLong(fighter.getId()).putShort(bytes.length).put(bytes).send(client);
	}
}