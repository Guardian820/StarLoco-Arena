package org.ankarton.network.login.parser.packet.team;

import java.util.ArrayList;

import org.ankarton.network.entity.Buffer;
import org.ankarton.world.entity.team.Fighter;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;
import org.ankarton.world.entity.team.Team;

public class TeamPresetListRequest {

	public static void parse(Client client, IoBuffer recv) {
        ArrayList<Team> teams = client.getAccount().getCoach().getTeams();
		create(teams).send(client);
	}
	
	private static Buffer create(ArrayList<Team> teams) {
		short size = 1;

        for(Team team : teams)
			size += (4 + team.getName().length()) + (8 * team.getFighters().size());

        final Buffer buffer = new Buffer(size, Send.TEAM_PRESET_LIST).put(teams.size());
		
		for(Team team : teams) {
			buffer.putShort(team.getId()).put(team.getName()).put(team.getFighters().size());

			for(Fighter fighter : team.getFighters())
				buffer.putLong(fighter.getId());
		}
		
		return buffer;
	}
}