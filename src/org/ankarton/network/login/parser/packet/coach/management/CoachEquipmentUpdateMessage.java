package org.ankarton.network.login.parser.packet.coach.management;

import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Buffer;
import org.ankarton.network.entity.Client;
import org.ankarton.world.entity.card.CoachCard;
import org.ankarton.world.entity.coach.Coach;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * Created by Locos on 04/07/2017.
 */
public class CoachEquipmentUpdateMessage {
    public static void parse(Client client, IoBuffer recv) {
        final Coach coach = client.getAccount().getCoach();

        int nbr = 0;
        for(CoachCard card : coach.getInventory()) {
            if(card.getPos() != 0) nbr += 15;
        }

        Buffer buffer = new Buffer(10 + nbr, OpCode.Send.COACH_EQUIPMENT_UPDATE_MESSAGE).putLong(coach.getId()).putShort(nbr);

        coach.getInventory().stream().filter(card -> card.getPos() != 0).forEach(card ->
                buffer.putShort(card.getPos()).putInt(card.getTemplateId()).putLong(card.getId()).put(card.getFlag()));

        buffer.sendToAll();
    }
}