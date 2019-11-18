package org.ankarton.network.login.parser.packet.coach.management;

import org.ankarton.database.Database;
import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Buffer;
import org.ankarton.network.entity.Client;
import org.ankarton.world.entity.card.CoachCard;
import org.ankarton.world.entity.coach.Coach;
import org.apache.mina.core.buffer.IoBuffer;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Mathieu on 08/09/2015.
 */
public class CoachEquipmentUpdateRequest {

    public static void parse(Client client, IoBuffer recv) {
        final Coach coach = client.getAccount().getCoach();

        for (byte i = 0; i < 14; i++) {
            long uid = recv.getLong();
            CoachCard coachCard = coach.getCard(uid);
            if(coachCard != null) {
                coachCard.setPos(i);
                try {
                    Database.getInstance().getCoachCardsData().update(coachCard);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        CoachEquipmentUpdateMessage.parse(client, null);
        //new CoachInventoryUpdateRequest().parse(client, null);
    }
}
