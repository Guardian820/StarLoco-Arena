package org.ankarton.world.entity.opponent;

import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Buffer;
import org.ankarton.world.entity.coach.Coach;
import org.ankarton.world.entity.fight.Fight;
import org.ankarton.world.entity.fight.FightsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathieu on 08/09/2015.
 */
public class WaitingOpponent {

    private final static List<Opponent> list = new ArrayList<>();

    public static void add(Opponent opponent) {
        WaitingOpponent.search(opponent);
        WaitingOpponent.list.add(opponent);
    }

    public static synchronized void search(Opponent searcher) {
        if(WaitingOpponent.list.isEmpty()) return;
        WaitingOpponent.list.stream()
                .filter(opponent -> opponent.type == searcher.type && opponent.bet == searcher.bet && opponent.ready == -1 && searcher.ready == -1 && opponent != searcher)
                .forEach(opponent -> WaitingOpponent.found(searcher, opponent));
    }

    public static void found(Opponent searcher, Opponent opponent) {
        Fight fight = FightsManager.getInstance().createFight(searcher.type);
        searcher.setReady((byte) 0); searcher.setOpponent(opponent);
        opponent.setReady((byte) 0); opponent.setOpponent(searcher);
        new Buffer(13, OpCode.Send.OPPONENT_FOUND).putLong(fight.getId()).putInt(searcher.bet).put(searcher.type).sendTo(searcher.coach, opponent.coach);
    }

    public static Opponent getOpponentByCoach(Coach coach) {
        for(Opponent opponent : WaitingOpponent.list)
            if(opponent.coach.getId() == coach.getId())
                return opponent;
        return null;
    }

    public static class Opponent {

        private final Coach coach;
        private final byte type;
        private final int bet;
        private byte ready = -1;
        private Opponent opponent;

        public Opponent(Coach coach, byte type, int bet) {
            this.coach = coach;
            this.type = type;
            this.bet = bet;
        }

        public void setReady(byte ready) {
            this.ready = ready;
        }

        public byte getReady() {
            return ready;
        }

        public boolean isReady() {
            return ready == 1;
        }

        public void setOpponent(Opponent opponent) {
            this.opponent = opponent;
        }

        public Opponent getOpponent() {
            return opponent;
        }
    }
}
