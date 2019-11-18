package org.ankarton.network.login.parser.packet.coach;

import com.j256.ormlite.dao.Dao;
import org.ankarton.database.Database;
import org.ankarton.network.entity.Buffer;
import org.ankarton.world.entity.coach.Coach;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;
import org.ankarton.network.login.parser.Parser;

import java.sql.SQLException;
import java.util.List;

public class CoachCreation {

	/* 
	 * 0 = ok
	 * 11 & 12 = invalidName
	 * 10 & 13 = undefinedMessage
	 */
	
	public static String[] forbidden = {"admin", "modo", " ", "&", "�", "'", "\"", 
		"(", "-", "�", "_", "�", "�", ")", "=", "~", "#",
		"{", "[", "|", "`", "^", "@", "]", "}", "�", "+",
		"^", "$", "�", "*", ",", ";", ":", "!", "<", ">",
		"�", "�", "%", "�", "?", ".", "/", "�", "\n", "`"};

	public static void parse(Client client, IoBuffer recv) {
		byte[] name = new byte[recv.get()];
		recv.get(name);
		String coachName = Parser.getStringByBytes(name).toLowerCase();
		byte skin = recv.get(),
		hair = recv.get(),
		sex = recv.get();

		for(int i = 0; i < forbidden.length; i++) {
			if(coachName.contains(String.valueOf(forbidden[i]))) {
				new Buffer(1, Send.COACH_CREATION_RESULT).put(11).send(client);
				return;
			}
		}	
		
		coachName = coachName.substring(0,1).toUpperCase() + coachName.substring(1);

		Dao<Coach, Integer> dao = Database.getInstance().getCoachsData();
		Coach coach = null;
		try {
			List<Coach> coachs = dao.query(dao.queryBuilder().where().eq("name", coachName).prepare());
			coach = coachs.isEmpty() ? null : coachs.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(coach != null) {
			new Buffer(1, Send.COACH_CREATION_RESULT).put(12).send(client);
			return;
		}
		
		coach = new Coach(client.getAccount(), coachName, skin, hair, sex);
		client.getAccount().setCoach(coach);

		try {
			Database.getInstance().getCoachsData().create(coach);
			Database.getInstance().getAccountsData().update(client.getAccount());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		new Buffer(1, Send.COACH_CREATION_RESULT).put(0).send(client);
		coach.sendInformation();
		coach.enterInWorld();
	}
}