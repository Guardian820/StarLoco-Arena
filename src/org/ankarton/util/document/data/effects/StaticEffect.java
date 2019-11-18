package org.ankarton.util.document.data.effects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryukis on 16/11/2015.
 */
public class StaticEffect {
    public int ID;
    public int ScriptID;
    public int areaID;
    public int[] area_Params;
    public int[] effectAreaAppTriggers;
    public int[] effectAreaUnAppTriggers;
    public int maxExecution;
    public int[] applicationTargets;
    public int[] unApplicationTargets;
    public int TargetsToShow;
    public String effectAreaType;
    public int[] deactivationDelay;
    public int applicationCondition;

    private List<Effect> effects = new ArrayList<Effect>();

    public StaticEffect(int Id, int script, int area, int[] area_params, int[] areaAppTrigger, int[] areaUnAppTriggers, int maxExec, int[] appTargets, int[] unAppTargets, int targetsToShow, String areaType, int[] deactivation, int appCondition){
        ID = Id;
        ScriptID = script;
        areaID = area;
        area_Params = area_params;
        effectAreaAppTriggers = areaAppTrigger;
        effectAreaUnAppTriggers = areaUnAppTriggers;
        maxExecution = maxExec;
        applicationTargets = appTargets;
        unApplicationTargets = unAppTargets;
        TargetsToShow = targetsToShow;
        effectAreaType = areaType;
        deactivationDelay = deactivation;
        applicationCondition = appCondition;
    }
    public void addEffect(int effectID, String parentType, int ParentID, int[] Duration, int ActionID, boolean isCritical, float[] _params, short AreaShape, int[] areaSize, int[] targets, int[] triggersAfter, int[] triggersBefore, boolean affectedByLoc){
        //effects.add(new Effect(effectID, parentType, ParentID, Duration, ActionID, isCritical, _params, AreaShape, areaSize, targets, triggersAfter, triggersBefore, affectedByLoc));
    }
    public List<Effect> getEffect(){
        return effects;
    }
}
