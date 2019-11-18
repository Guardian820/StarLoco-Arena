package org.ankarton.world.entity.client;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import org.ankarton.database.Database;
import org.ankarton.network.entity.Client;
import org.ankarton.world.entity.coach.Coach;

import java.sql.SQLException;
import java.util.List;

@DatabaseTable(tableName = "accounts")
public class Account {

	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String password;
	@DatabaseField
	private boolean connected = false;
	@DatabaseField(foreign = true, columnName = "coach_id", foreignAutoCreate = true, foreignAutoRefresh = true)
	private Coach coach;

	private Client client;
	
	public Account() {

	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public Coach getCoach() {
		return coach;
	}
	
	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public boolean isConnected() {
		return connected;
	}
	
	public void setConnected(boolean connected) {
		this.connected = connected;
		try {
			Database.getInstance().getAccountsData().update(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}