package org.ankarton.util.document.loader;

import org.ankarton.util.document.DocumentContainer;
import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.data.effects.Effect;
import org.ankarton.util.factory.FactoryManager;

/**
 * Created by Locos on 18/08/2015.
 */
public class EffectLoader extends DocumentLoader {

    @Override
    public void load() throws Exception {
        this.open("data/cards.dat");
    }

    @Override
    public void read(DocumentContainer container) {
        if (container == null)
            return;

        this.skipCoachCards();
        this.skipFighterCards();
        this.loadEffects();
    }

    private void skipCoachCards() { // pass coachs items
        int count = readInteger();
        for (int i = 0; i < count; i++) {
            readInteger();
            readInteger();
            readInteger();
            readInteger();
        }
    }

    private void skipFighterCards() { // pass coachs items
        int count = readInteger();
        for (int i = 0; i < count; i++) {
            readInteger();readByte();readByte();
            readBoolean();readInteger();
            readInteger();readBoolean();
            readBoolean();readInteger();
            readBoolean();readBoolean();
            readInteger();readInteger();
        }
    }

    private void loadEffects() {
        int count = readInteger();
        // load coachs items
        for (int i = 0; i < count; i++)
            FactoryManager.getInstance().getEffect().add(new Effect(this));
    }
}

