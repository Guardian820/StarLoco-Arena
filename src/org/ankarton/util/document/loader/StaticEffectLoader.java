package org.ankarton.util.document.loader;

import org.ankarton.util.document.DocumentContainer;
import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.data.effects.StaticEffect;

/**
 * Created by Ryukis on 16/11/2015.
 */
public class StaticEffectLoader extends DocumentLoader {

    public void load() {
        try {
            this.open("data/staticEffects.dat");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read(DocumentContainer container) {
        if(container == null) return;

        try {
            int staticEffectCount = readInteger();

            /*for(int i = 0; i < staticEffectCount; i++)
                StaticEffectManager.getInstance().addStaticEffect(new StaticEffect(readInteger(), readInteger(), readShort(),
                        readIntArray(), readIntArray(), readIntArray(), readShort(), readIntArray(), readIntArray(), readInteger(),
                        readString(), readIntArray(), readInteger()));
*/
            int effectCount = readInteger();

            for(int i = 0; i < effectCount; i++) {
                int effectId = readInteger();
                String effectParentType = readString();
                int effectParentId = readInteger();
                readShort();
                int[] effectDuration = readIntArray();
                int effectActionId = readInteger();
                boolean effectIsCritical = readBoolean();
                float[] effectParams = readFloatArray();
                short effectAreaShape = readShort();
                int[] effectAreaSize = readIntArray();
                int[] effectTargets = readIntArray();
                int[] effectTriggersAfter = readIntArray();
                int[] effectTriggersBefore = readIntArray();
                boolean affectedByLocalisation = readBoolean();
               // StaticEffectManager.getInstance().getStaticEffect(effectParentId).addEffect(effectId, effectParentType, effectParentId, effectDuration, effectActionId, effectIsCritical, effectParams, effectAreaShape, effectAreaSize, effectTargets, effectTriggersAfter, effectTriggersBefore, affectedByLocalisation);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
