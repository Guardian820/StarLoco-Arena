package org.ankarton.util.document.data.spell;

import org.ankarton.network.entity.Client;
import org.ankarton.util.document.data.effects.Effect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryukis on 16/11/2015.
 * Liste des effets dispo dans : RunningEffectConstants.java
 */
public class Spell {
    public int id;
    private List<Effect> effects = new ArrayList<Effect>();
    public byte PARequired;
    public byte castFrequencyMaxPerPlayers;
    public byte castFrequencyMaxPerTurn;
    public byte castFrequencyMinInterval;
    public boolean castTestLos;
    public boolean castOnlyLine;
    public byte POMin;
    public byte POMax;
    public int Price;
    public int AiTargetID;
    public boolean needFreeCell;
    public int scriptID;
    public int breedID;
    public String criterion;
    public boolean useAutoDescription;

    public Spell(int ID, byte PA, byte FreqPerPlayers, byte FreqPerTurns, byte FreqMinInterval, boolean TestLos, boolean OnlyLine, boolean freeCell, byte RangeMin, byte RangeMax, int price, int TargetID, int ScriptId, int BreedId, String criterionString, boolean useAutoDesc){
        id = ID;
        PARequired = PA;
        castFrequencyMaxPerPlayers = FreqPerPlayers;
        castFrequencyMaxPerTurn = FreqPerTurns;
        castFrequencyMinInterval = FreqMinInterval;
        castTestLos = TestLos;
        castOnlyLine = OnlyLine;
        POMin = RangeMin;
        POMax = RangeMax;
        Price = price;
        AiTargetID = TargetID;
        needFreeCell = freeCell;
        scriptID = ScriptId;
        breedID = BreedId;
        criterion = criterionString;
        useAutoDescription = useAutoDesc;
    }
    public void cast(Client client, int posX, int posY, short posZ){
        for(int i = 0;i < effects.size();i++){
            executeEffect();
        }
    }
    private void executeEffect(){

    }
    public void addEffect(int effectID, String parentType, int ParentID, int[] Duration, int ActionID, boolean isCritical, float[] _params, short AreaShape, int[] areaSize, int[] targets, int[] triggersAfter, int[] triggersBefore, boolean affectedByLoc){
       // effects.add(new Effect(effectID, parentType, ParentID, Duration, ActionID, isCritical, _params, AreaShape, areaSize, targets, triggersAfter, triggersBefore, affectedByLoc));
    }
    public List<Effect> getEffects(){
        return effects;
    }
}
