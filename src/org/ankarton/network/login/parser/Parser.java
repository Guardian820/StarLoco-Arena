package org.ankarton.network.login.parser;

import java.io.UnsupportedEncodingException;

import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Client;
import org.ankarton.network.login.LoginClient;
import org.ankarton.network.login.parser.packet.actor.ActorMovementRequest;
import org.ankarton.network.login.parser.packet.client.ClientAuthentication;
import org.ankarton.network.login.parser.packet.client.ClientVersion;
import org.ankarton.network.login.parser.packet.coach.CoachCreation;
import org.ankarton.network.login.parser.packet.coach.management.CoachEquipmentUpdateRequest;
import org.ankarton.network.login.parser.packet.fight.SetReadyForFight;
import org.ankarton.network.login.parser.packet.fight.SpellCast;
import org.ankarton.network.login.parser.packet.fighter.FightInformationListRequest;
import org.ankarton.network.login.parser.packet.fighter.FighterCreateRequest;
import org.ankarton.network.login.parser.packet.fighter.FighterDeleteRequest;
import org.ankarton.network.login.parser.packet.fighter.FighterUpdateInventoryRequest;
import org.ankarton.network.login.parser.packet.message.PrivateMessage;
import org.ankarton.network.login.parser.packet.message.VicinityMessage;
import org.ankarton.network.login.parser.packet.opponent.OpponentSearchCancel;
import org.ankarton.network.login.parser.packet.opponent.OpponentSearchRequest;
import org.ankarton.network.login.parser.packet.team.TeamPresetDeleteRequest;
import org.ankarton.network.login.parser.packet.team.TeamPresetListRequest;
import org.ankarton.network.login.parser.packet.team.TeamPresetSaveRequest;
import org.apache.mina.core.buffer.IoBuffer;

public class Parser {

	public static void parser(LoginClient client, IoBuffer recv) {
		try {
			while (recv.hasRemaining()) {
				if(recv.limit() - recv.position() < 5) {
					Client.logger.error("Size too short : " + recv);
					return;
				}
				short size = recv.getShort();
				byte type = recv.get();
				short num = recv.getShort();
				OpCode.Recv id = OpCode.Recv.get(num);
				
				if(id == null) {
					Client.logger.error("Id de buffer non gerée : " + num + " : " + recv);
					recv.clear();
					return;
				}
				
				Client.logger.info("recv < " + id + " < (id : " + id.value + " | size : " + size + " | type : " + type + ") < " + recv);
	
				switch (id) {// DofusArenaMessageDecoder -> AbstractClientMessageDecoder
					case DISCONNECT:
						client.disconnect();
						break;
					case VERSION: // ClientVersionMessage
						ClientVersion.parse(client, recv);
						break;
					case AUTHENTICATION: // ClientAuthenticationMessage
						ClientAuthentication.parse(client, recv);
						break;
					case COACH_CREATION: // CoachCreation
						CoachCreation.parse(client, recv);
						break;
					case OPPONENT_SEARCH_REQUEST:
						OpponentSearchRequest.parse(client, recv);
						break;
					case OPPONENT_SEARCH_CANCEL:
						OpponentSearchCancel.parse(client, recv);
						break;
					case PRIVATE_MESSAGE:
						PrivateMessage.parse(client, recv);
						break;
					case VICINITY_MESSAGE:
						VicinityMessage.parse(client, recv);
						break;
					case SET_READY_FOR_FIGHT:
						SetReadyForFight.parse(client, recv);
						break;
					case ACTOR_MOVEMENT_REQUEST:
						ActorMovementRequest.parse(client, recv);
						break;
					case COACH_EQUIPMENT_UPDATE_REQUEST:
						CoachEquipmentUpdateRequest.parse(client, recv);
						break;
					case FIGHTER_CREATE_REQUEST:
						FighterCreateRequest.parse(client, recv);
						break;
					case FIGHTER_INFORMATION_LIST_REQUEST:
						FightInformationListRequest.parse(client, recv);
						break;
					case FIGHTER_DELETE_REQUEST:
						FighterDeleteRequest.parse(client, recv);
						break;
					case FIGHTER_UPDATE_INVENTORY_REQUEST:
						FighterUpdateInventoryRequest.parse(client, recv);
						break;
					case TEAM_PRESET_LIST_REQUEST:
						TeamPresetListRequest.parse(client, recv);
						break;
					case TEAM_PRESET_DELETE_REQUEST:
						TeamPresetDeleteRequest.parse(client, recv);
						break;
					case TEAM_PRESET_SAVE_REQUEST:
						TeamPresetSaveRequest.parse(client, recv);
						break;
					default:
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getStringByBytes(byte[] bytes) {
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}