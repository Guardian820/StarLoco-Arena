package org.ankarton.util.document.loader;

import org.ankarton.util.document.DocumentContainer;
import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.data.card.FighterCardTemplate;
import org.ankarton.util.document.data.card.WeaponFighterCardTemplate;
import org.ankarton.util.factory.FactoryManager;

/**
 * Created by Ryukis on 18/08/2015.
 */
public class FighterCardLoader extends DocumentLoader {

    @Override
    public void load() throws Exception {
        this.open("data/cards.dat");
    }

    @Override
    public void read(DocumentContainer container) {
        if (container == null) return;

        this.skip();
        this.loadFighterCards();
    }

    private void skip() { // pass coachs items
        int count = readInteger();
        for (int i = 0; i < count; i++) {
            readInteger();
            readInteger();
            readInteger();
            readInteger();
        }
    }

    private void loadFighterCards() {
        int count = readInteger(), id;
        FighterCardTemplate template;
        byte type;
        // load fighters items
        for (int i = 0; i < count; i++) {
            id = this.readInteger();
            type = this.readByte();
            template = type == 1 ? new WeaponFighterCardTemplate(id, type, this) : new FighterCardTemplate(id, type, this);
            FactoryManager.getInstance().getFighterCard().add(template);
        }
    }
}
