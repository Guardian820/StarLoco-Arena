package org.ankarton.util.document.data.effects;

import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.Template;
import org.ankarton.util.document.data.card.FighterCardTemplate;
import org.ankarton.util.factory.FactoryManager;

/**
 * Created by Ryukis on 16/11/2015.
 */
public class Effect implements Template {

    private int id;
    private int[] duration;
    private int actionId;
    private boolean isCritical;
    private float[] params;
    private short areaShape;
    private int[] areaSize;
    private int[] targets;
    private int[] triggersAfter;
    private int[] triggersBefore;
    private boolean affectedByLocalisation;

    public Effect(DocumentLoader loader) {
        this.id = loader.readInteger();
        String parentType = loader.readString();
        int parentId = loader.readInteger();
        loader.readShort();
        this.duration = loader.readIntArray();
        this.actionId = loader.readInteger();
        this.isCritical = loader.readBoolean();
        this.params = loader.readFloatArray();
        this.areaShape = loader.readShort();// change to AreaOfEffect with areasize
        this.areaSize = loader.readIntArray();//too
        this.targets = loader.readIntArray(); // change to TargetValidator (see client src)
        this.triggersAfter = loader.readIntArray();
        this.triggersBefore = loader.readIntArray();
        this.affectedByLocalisation = loader.readBoolean();
        this.onEffectLoaded(parentId, parentType);
    }

    @Override
    public int getId() {
        return id;
    }

    private void onEffectLoaded(int parentId, String parentType) {
        if(parentType.startsWith("FIGHTER_CARD")) {
            FighterCardTemplate template = FactoryManager.getInstance().getFighterCard().get(parentId);
            if(template != null) {
                template.addEffect(this);
            }
        }
    }
}
