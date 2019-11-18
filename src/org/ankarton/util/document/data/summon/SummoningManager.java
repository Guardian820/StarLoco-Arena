package org.ankarton.util.document.data.summon;

import org.ankarton.util.document.loader.SummoningLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryukis on 16/11/2015.
 */
public class SummoningManager {
    private static SummoningManager ourInstance = new SummoningManager();
    private Map<Integer, Summoning> summoningList = new HashMap<>();

    public static SummoningManager getInstance() {
        return ourInstance;
    }

    private SummoningManager() {
    }
    public void addSummoning(Summoning summoning){
        summoningList.put(summoning.id, summoning);
    }
    public Summoning getSummoning(int ID){
        return summoningList.get(ID);
    }
    public void clean(){summoningList.clear();}
    public void load(){new SummoningLoader().load();}
    public int getSummoningCount(){return summoningList.size();}
}
