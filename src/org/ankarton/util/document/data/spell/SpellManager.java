package org.ankarton.util.document.data.spell;


import org.ankarton.network.entity.Client;
import org.ankarton.util.document.loader.SpellLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ryukis on 16/11/2015.
 * SpellManager
 */
public class SpellManager {
    private static SpellManager ourInstance = new SpellManager();

    public static SpellManager getInstance() {
        return ourInstance;
    }
    private Map<Integer, Spell> spellList = new HashMap<>();

    private SpellManager() {
    }

    public void castSpell(Client client, long FighterID, int spellID, int posX, int posY, short posZ){
        /*Fighter fighter = Database.getFighterData().find(FighterID);
        Spell spell = fighter.getSpellByID(spell);
        if(spell == null){
            return;
        }
        if(fighter.PA >= spell.PARequired) {
            spell.cast(client, posX, posY, posZ);
        }*/
    }

    public void addSpell(Spell spell){
        spellList.put(spell.id, spell);
    }

    public Spell getNewSpellInstance(int spellID){
        Spell spell = getSpell(spellID);
        if(spell == null){
            return null;
        }
        Spell spellInstance = new Spell(
                spell.id,
                spell.PARequired,
                spell.castFrequencyMaxPerPlayers,
                spell.castFrequencyMaxPerTurn,
                spell.castFrequencyMaxPerTurn,
                spell.castTestLos,
                spell.castOnlyLine,
                spell.needFreeCell,
                spell.POMin,
                spell.POMax,
                spell.Price,
                spell.AiTargetID,
                spell.scriptID,
                spell.breedID,
                spell.criterion,
                spell.useAutoDescription
        );
        return spellInstance;
    }

    public void clean(){
        spellList.clear();
    }

    public void load(){
        new SpellLoader().load();
    }

    public Spell getSpell(int spellID){
        return spellList.get(spellID);
    }

    public List<Spell> getSpells(int ...args){
        List<Spell> list = new ArrayList<>();
        for(int i = 0;i < args.length;i++){
            list.add(spellList.get(args[i]));
        }
        return list;
    }
    public int getSpellCount(){
        return spellList.size();
    }
}
