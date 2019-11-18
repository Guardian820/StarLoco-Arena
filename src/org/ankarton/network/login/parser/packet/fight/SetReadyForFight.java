package org.ankarton.network.login.parser.packet.fight;

import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Buffer;
import org.ankarton.network.entity.Client;
import org.ankarton.world.entity.coach.Coach;
import org.ankarton.world.entity.fight.Fight;
import org.ankarton.world.entity.fight.FightsManager;
import org.ankarton.world.entity.fight.fighter.TempCoachFighter;
import org.ankarton.world.entity.opponent.WaitingOpponent;
import org.ankarton.world.entity.opponent.WaitingOpponent.Opponent;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * Created by Locos on 14/09/2015.
 */
public class SetReadyForFight {

    public static void parse(Client client, IoBuffer recv) {
        final long fightId = recv.getLong();

        byte size = recv.get();
        long[] fightersId = new long[size];
        for (byte i = 0; i < size; i++) fightersId[i] = recv.getLong();

        Fight fight = FightsManager.getInstance().getFight(fightId);
        System.out.println("Fight = " + fight + ", fightID = "+fightId);
        if (fight != null) {
            Coach coach = client.getAccount().getCoach();
            Opponent opponent = WaitingOpponent.getOpponentByCoach(coach);
            System.out.println("Opponent = " + opponent);

            if (opponent != null) {
                opponent.setReady((byte) 1);
                fight.setTempCoachFighter(new TempCoachFighter(coach, fight, fightersId));
                new Buffer(9, OpCode.Send.READY_FOR_FIGHT).put(0).putLong(coach.getId()).send(client);

                if(opponent.getOpponent().isReady()){
                    fight.startPresentation();
                }
            }
        }
    }
}
