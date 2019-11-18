package org.ankarton.util.document.loader;

import org.ankarton.util.document.DocumentContainer;
import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.data.spell.Spell;
import org.ankarton.util.document.data.spell.SpellManager;

import java.util.List;

/**
 * Created by Ryukis on 16/11/2015.
 */
public class SpellLoader extends DocumentLoader {

    public void load() {
        try{
            this.open("data/spells.dat");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read(DocumentContainer container) {
        if(container == null) return;

        int spellsCount = readInteger();

        for(int i = 0; i < spellsCount; i++)
            SpellManager.getInstance().addSpell(new Spell(readInteger(), readByte(), readByte(), readByte(), readByte(), readBoolean(), readBoolean(), readBoolean(), readByte(), readByte(), readInteger(), readInteger(), readInteger(), readInteger(), readString(), readBoolean()));

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
            SpellManager.getInstance().getSpell(effectParentId).addEffect(effectId, effectParentType, effectParentId, effectDuration, effectActionId, effectIsCritical, effectParams, effectAreaShape, effectAreaSize, effectTargets, effectTriggersAfter, effectTriggersBefore, affectedByLocalisation);
        }
    }
}
