package org.ankarton.world.entity.fight;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryukis on 19/11/2015.
 */
public class FightsManager {
    private static FightsManager ourInstance = new FightsManager();
    private Map<Long, Fight> fightList = new HashMap<>();
    private long id = 0;
    public static FightsManager getInstance() {
        return ourInstance;
    }

    private FightsManager() {
    }
    public Fight getFight(long ID){
        return fightList.get(ID);
    }
    public Fight createFight(byte type){
        Fight fight = new Fight(type, id);
        fightList.put(id, fight);
        id++;
        return fight;
    }
}
