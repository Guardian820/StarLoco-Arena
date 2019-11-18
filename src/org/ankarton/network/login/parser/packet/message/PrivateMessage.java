package org.ankarton.network.login.parser.packet.message;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import org.ankarton.database.Database;
import org.ankarton.network.entity.Buffer;
import org.ankarton.world.entity.coach.Coach;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;

import org.ankarton.network.login.parser.Parser;

import java.sql.SQLException;
import java.util.List;

public class PrivateMessage {

	public static void parse(Client client, IoBuffer recv) {
		byte[] bytes = new byte[recv.get()];
		recv.get(bytes);
		String name = Parser.getStringByBytes(bytes);

		Dao<Coach, Integer> dao = Database.getInstance().getCoachsData();
		Coach coach = null;
		try {
			List<Coach> coachs = dao.query(dao.queryBuilder().where().eq("name", name).prepare());
			coach = coachs.isEmpty() ? null : coachs.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(coach == null) {
			new Buffer(1 + name.length(), Send.USER_NOT_FOUND).put(name).send(client);
			recv.clear();
			return;
		}
		
		Client receiver = coach.getAccount().getClient();
		
		if(receiver == null) {
			new Buffer(1 + name.length(), Send.USER_NOT_FOUND).put(name).send(client);
			recv.clear();
			return;
		}
		
		bytes = new byte[recv.get()];
		recv.get(bytes);
		String nameSender = client.getAccount().getCoach().getName();
		
		new Buffer(10 + nameSender.length() + bytes.length, Send.PRIVATE_MESSAGE).put(nameSender)
		.putLong(client.getAccount().getCoach().getId()).put(bytes.length).put(bytes).send(receiver);
	}
}