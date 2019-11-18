package org.ankarton.world.entity.coach;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import org.ankarton.database.Database;
import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Buffer;
import org.ankarton.world.World;
import org.ankarton.world.entity.card.CoachCard;
import org.ankarton.world.entity.client.Account;
import org.ankarton.world.entity.team.Fighter;
import org.ankarton.world.entity.team.Team;

@DatabaseTable(tableName = "coachs")
public class Coach {


	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(unique = true, canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false)
	private byte skin, hair, sex;
	@DatabaseField(dataType = DataType.SERIALIZABLE)
	private Position position;

	private Account account;
	private ArrayList<Team> teams;
    private ArrayList<Fighter> fighters = new ArrayList<>();

	@ForeignCollectionField()
	private ForeignCollection<CoachCard> inventory;

	public Coach() {}

	public Coach(Account account, String name, byte skin, byte hair, byte sex) {
		this.account = account;
		this.name = name;
		this.skin = skin;
		this.hair = hair;
		this.sex = sex;
		this.position = new Position();
	}
	
	public long getId() {
		return account.getId();
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public byte getSkin() {
		return skin;
	}

	public byte getHair() {
		return hair;
	}

	public byte getSex() {
		return sex;
	}

	public ForeignCollection<CoachCard> getInventory() {
		return inventory;
	}

	public CoachCard getCard(long uid) {
		for(CoachCard card : this.inventory) {
			if(card.getId() == uid) {
				return card;
			}
		}
		return null;
	}

	public Position getPosition() {
		return position;
	}

    public void setPosition(int x, int y, short z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

	public ArrayList<Team> getTeams() {
		if(this.teams == null) {
			this.teams = new ArrayList<>(Short.MAX_VALUE);
			Dao<Team, Integer> dao = Database.getInstance().getTeamsData();
			try {
				List<Team> teams = dao.query(dao.queryBuilder().where().eq("coach_id", this).prepare());
				this.teams.addAll(teams);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return teams;
	}

    public ArrayList<Fighter> getFighters() {
        return fighters;
    }

	public void sendInformation() {
		short inventory = 0, equipment = 0;
		if(this.inventory != null)
			inventory = (short) (this.inventory.size() * 15);
		if(this.inventory != null)
			for(CoachCard card : this.inventory) if(card.getPos() != 0) equipment += 15;


		Buffer buffer = new Buffer(19 + this.getName().length() + inventory + equipment, Send.COACH_INFORMATION);

		buffer.putLong(this.getId()).put(this.getName());// Id and Name
		//Position is ignored in this case
		//Look and Style
		buffer.put(this.getSkin()).put(this.getHair()).put(this.getSex());

		// Equipment
		if(this.inventory == null || this.inventory.isEmpty()) {
			buffer.putShort(0);
		} else {
			buffer.putShort(equipment);

			for(CoachCard card : this.inventory) {
				if(card.getPos() != 0) {
					buffer.putShort(card.getPos()).putInt(card.getTemplateId()).putLong(card.getId()).put(card.getFlag());
				}
			}
		}

		// Inventory
		if(this.inventory == null || this.inventory.isEmpty()) {
			buffer.putShort(0);
		} else {
			buffer.putShort(inventory);
			for(CoachCard coachCard : this.inventory)
				buffer.putInt(coachCard.getTemplateId()).putLong(coachCard.getId()).put(coachCard.getFlag()).putShort(coachCard.getQuantity());
		}

		//LocketSet
		buffer.putShort(0);
		//Ladder
		buffer.put(0).send(this.getAccount().getClient());
	}

	public void enterInWorld() {
		new Buffer(13, Send.ENTER_WORLD_INSTANCE)
		.putFloat(this.getPosition().x).putFloat(this.getPosition().y)
		.putShort(this.getPosition().z).putShort(0).put(0).send(this.getAccount().getClient());

		World.getInstance().addOnlineCoach(Coach.this);
	}
		
	public void onJoinMap() {
		ArrayList<Coach> coachs = new ArrayList<>(World.getInstance().getOnlineCoachs());
		coachs.remove(this);
		short size = 4;
		
		for(Coach coach : coachs)	
			size +=  27 + coach.getName().length();

		final Buffer buffer = new Buffer(size, Send.ACTOR_SPAWN).putInt(coachs.size());

        coachs.stream().filter(coach -> coach != null).forEach(coach -> buffer.put(1)
                .putLong(coach.getId()).put(coach.getName())
                .putInt(coach.getPosition().x).putInt(coach.getPosition().y).putShort(coach.getPosition().z).put(1)
                .put(coach.getSkin()).put(coach.getHair()).put(coach.getSex())
                .putShort(0)
                .put(0));
		
		buffer.send(this.getAccount().getClient());
	}
	
	public void onLeftMap(Coach coach) {
		new Buffer(9, Send.ACTOR_DESPAWN).put(1).putLong(coach.getId()).send(this.getAccount().getClient());
	}

    public String parseFighters() {
        String fighters = "";
        for(Fighter fighter : this.fighters)
            if(fighter != null)
                fighters += (fighters.isEmpty() ? "" : ",") + fighter.getId();
        return fighters;
    }

	public static class Position implements Serializable {
		public int x = 1, y = 1;
		public short z = 0;
		
		public Position() {}

		public Position(String position) {
			String[] split = position.split(",");
			this.x = Integer.parseInt(split[0]);
			this.y = Integer.parseInt(split[1]);
			this.z = Short.parseShort(split[2]);
		}

		@Override
		public String toString() {
			return x + "," + y + "," + z;
		}
	}
}