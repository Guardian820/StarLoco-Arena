package org.ankarton.world.entity.fight.fighter;

import org.ankarton.network.entity.Client;
import org.ankarton.world.entity.coach.Coach;
import org.ankarton.world.entity.fight.Fight;
import org.ankarton.world.entity.team.Fighter;
import org.apache.mina.core.buffer.IoBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Locos on 14/09/2015.
 */
public class TempCoachFighter {

    private final Coach coach;
    private final Fight fight;
    private final List<Fighter> fighters = new ArrayList<>();

    public TempCoachFighter(Coach coach, Fight fight, long[] fightersId) {
        this.coach = coach;
        this.fight = fight;

        for(long id : fightersId) {
            for(Fighter fighter : coach.getFighters()) {
                if(fighter.getId() == id) {
                    this.fighters.add(fighter);
                    break;
                }
            }
        }
    }
    public Client getClient(){
        return coach.getAccount().getClient();
    }
    public void send(IoBuffer buffer){
        coach.getAccount().getClient().send(buffer);
    }
}
