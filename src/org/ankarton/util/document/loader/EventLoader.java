package org.ankarton.util.document.loader;

import org.ankarton.util.document.DocumentContainer;
import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.data.events.Event;
import org.ankarton.util.document.data.events.EventsManager;

import java.util.List;

/**
 * Created by Ryukis on 16/11/2015.
 */
public class EventLoader extends DocumentLoader {

    public void load(){
        try {
            this.open("data/events.dat");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void read(DocumentContainer container) {
        if(container == null) return;

        try {
            int eventsCount = readInteger();

            for(int i = 0; i < eventsCount; i++)
                EventsManager.getInstance().addEvent(new Event(readInteger(), readBoolean()));

            int effectsCount = readInteger();

            for(int i = 0; i < effectsCount; i++){
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
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
