package org.ankarton.network.login.parser.packet.opponent;

import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Buffer;
import org.ankarton.network.entity.Client;
import org.ankarton.world.entity.opponent.WaitingOpponent;
import org.ankarton.world.entity.opponent.WaitingOpponent.Opponent;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * Created by Mathieu on 08/09/2015.
 */
public class OpponentSearchRequest {

    // Type  |  Bet
    // 1        0 // sans mise aléa
    // 1        1 // mise aléa

    public static void parse(Client client, IoBuffer recv) {
        byte type = recv.get();
        int bet = recv.getInt();

        new Buffer(0, OpCode.Send.OPPONENT_SEARCH_IN_PROGRESS).send(client);

        client.getWaiter().addNow(() -> {
            try {
                WaitingOpponent.add(new Opponent(client.getAccount().getCoach(), type, bet));
            } catch (Exception e) {
                e.printStackTrace();
                new Buffer(1, OpCode.Send.OPPONENT_SEARCH_ERROR).put(1).send(client);
            }
        }, 0);
    }
}
