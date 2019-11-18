package org.ankarton.util.document.data.events;

import org.ankarton.util.document.data.effects.Effect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryukis on 16/11/2015.
 */
public class Event {

    public int ID;
    public boolean useAutoDescription;
    private List<Effect> effects = new ArrayList<Effect>();

    public Event(int Id, boolean useAutoDesc){
        useAutoDescription = useAutoDesc;
        ID = Id;
    }
    public List<Effect> getEffects(){
        return effects;
    }
    public Effect getEffect(int effectID){
        for(int i = 0;i < effects.size();i++){
           /* if(effects.get(i).effectId == effectID){
                return effects.get(i);
            }*/
        }
        return null;
    }
    public void addEffect(Effect effect){
        effects.add(effect);
    }
}
