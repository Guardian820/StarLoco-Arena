package org.ankarton.network.login.parser.packet.client;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import org.ankarton.database.Database;
import org.ankarton.network.entity.Buffer;
import org.ankarton.network.entity.Client;
import org.ankarton.world.entity.client.Account;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.login.parser.Parser;
import org.h2.command.dml.Query;

import java.sql.SQLException;
import java.util.List;

public class ClientAuthentication {

	/* 2 = invalidLogin
	 * 3 = alreadyConnected
	 * 4 = saveInProgress
	 * 127 = closedBeta
	 * default = invalidLogin
	 */

	public static void parse(Client client, IoBuffer recv) {
		byte[] login = new byte[recv.get()];
		recv.get(login);
		String name = Parser.getStringByBytes(login);

		byte[] password = new byte[recv.get()];
		recv.get(password);
		String sPassword = Parser.getStringByBytes(password);

		Dao<Account, Integer> dao = Database.getInstance().getAccountsData();
		Account account = null;
		try {
			List<Account> accounts = dao.query(dao.queryBuilder().where().eq("name", name).prepare());
			account = accounts.isEmpty() ? null : accounts.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(account == null) {
			new Buffer(1, Send.AUTHENTICATION_RESULT).put(2).send(client);
			client.kick();
		} else {
			if (!account.getPassword().equals(sPassword)) {
				new Buffer(1, Send.AUTHENTICATION_RESULT).put(2).send(client);
				client.kick();
			} else if(account.isConnected()) {
				new Buffer(1, Send.AUTHENTICATION_RESULT).put(3).send(client);
                account.setConnected(false);
				client.kick();
			} else {
				client.setAccount(account);
				account.setClient(client);
				account.setConnected(true);
			
				
				new Buffer(1, Send.AUTHENTICATION_RESULT).put(0).send(client);
				new Buffer(4, Send.QUEUE_NOTIFICATION).putInt(-1).send(client);
				
				if(account.getCoach() == null) {
					new Buffer(0, Send.COACH_CREATION_REQUEST).send(client);
				} else {
					account.getCoach().setAccount(account);
					account.getCoach().sendInformation();
					account.getCoach().enterInWorld();
				}
			}
		}
	}
}