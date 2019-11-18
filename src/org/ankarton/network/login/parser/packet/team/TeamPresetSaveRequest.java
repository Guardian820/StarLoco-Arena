package org.ankarton.network.login.parser.packet.team;

import org.ankarton.database.Database;
import org.ankarton.network.entity.Buffer;
import org.ankarton.world.entity.coach.Coach;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;
import org.ankarton.network.login.parser.Parser;
import org.ankarton.world.entity.team.Team;

public class TeamPresetSaveRequest {

	public static void parse(Client client, IoBuffer recv) {
		try {
			short id = recv.getShort();
			byte[] bytes = new byte[recv.get()];

			recv.get(bytes);
			String name = Parser.getStringByBytes(bytes);
			
			int count = recv.get();

			Coach coach = client.getAccount().getCoach();
			Team team = Team.getById(coach.getTeams(), id);
			
			if(team == null) {	
				team = new Team(coach, Team.getNextId(coach.getTeams()), name, "");
				Database.getInstance().getTeamsData().create(team);
			}

            if(!coach.getTeams().contains(team))
			    coach.getTeams().add(team);

            team.getFighters().clear();

            for (int i = 0; i < count; i++)
                team.getFighters().add(Database.getInstance().getFightersData().queryForId(recv.getLong()));

            Database.getInstance().getTeamsData().update(team);

            final Buffer buffer = new Buffer(5 + name.length() + 8 * team.getFighters().size(), Send.TEAM_PRESET_SAVE).put(0).putShort(id).put(name).put(team.getFighters().size());
            team.getFighters().stream().filter(fighter -> fighter != null).forEach(fighter -> buffer.putLong(fighter.getId()));
            buffer.send(client);
		} catch(Exception e) {
            e.printStackTrace();
			new Buffer(1, Send.TEAM_PRESET_SAVE).put(1).send(client);
		}	
	}
}