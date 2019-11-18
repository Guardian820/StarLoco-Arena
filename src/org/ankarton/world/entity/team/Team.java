package org.ankarton.world.entity.team;

import java.sql.SQLException;
import java.util.ArrayList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.ankarton.database.Database;
import org.ankarton.world.entity.coach.Coach;

@DatabaseTable(tableName = "teams")
public class Team {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField(foreign = true, columnName = "coach_id", canBeNull = false, foreignAutoCreate = true)
	private Coach coach;

	private ArrayList<Fighter> fighters = new ArrayList<>(8);

	public Team() {

	}

	public Team(Coach coach, short id, String name, String fighters) {
		this.id = id;
		this.coach = coach;
		this.name = name;

        if(!fighters.isEmpty()) {
			for (String fighter : fighters.split(",")) {
				try {
					this.fighters.add(Database.getInstance().getFightersData().queryForId(Long.parseLong(fighter)));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Coach getCoach() {
		return coach;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Fighter> getFighters() {
		return fighters;
	}
	
	public String parseFighters() {
		String fighters = "";
		for(Fighter fighter : this.fighters) 
			fighters += (fighters.isEmpty() ? "" : ",") + fighter.getId();
		return fighters;
	}
	
	public static short getNextId(ArrayList<Team> teams) {
		short id = 1;
		for(Team team : teams)
			if(team.getId() >= id)
				id = (short) (team.getId() + 1);
		return id;
	}
	
	public static Team getById(ArrayList<Team> teams, short id) {
		for(Team team : teams)
			if(team.getId() == id)
				return team;
		return null;
	}
}