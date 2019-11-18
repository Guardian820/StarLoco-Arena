package org.ankarton.network.login.parser.packet.team;

import java.sql.SQLException;
import java.util.ArrayList;

import org.ankarton.database.Database;
import org.ankarton.network.entity.Buffer;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;
import org.ankarton.world.entity.team.Team;

public class TeamPresetDeleteRequest {

	public static void parse(Client client, IoBuffer recv) {
		ArrayList<Team> teams = client.getAccount().getCoach().getTeams();
		Team team = Team.getById(teams, recv.getShort());

		if(team != null) {
			teams.remove(team);
			try {
				Database.getInstance().getTeamsData().delete(team);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			new Buffer(3, Send.TEAM_PRESET_DELETION).put(0).putShort(team.getId()).send(client);
		}
	}
}