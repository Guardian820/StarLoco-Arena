package org.ankarton.util.document.loader;

import org.ankarton.util.document.DocumentContainer;
import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.data.card.CoachCardTemplate;
import org.ankarton.util.factory.FactoryManager;

import java.util.ArrayList;

/**
 * Created by Locos on 18/08/2015.
 */
public class CoachCardLoader extends DocumentLoader {

    @Override
    public void load() throws Exception {
        this.open("data/cards.dat");
    }

    @Override
    public void read(DocumentContainer container) {
        if (container == null)
            return;

        this.loadCoachCards();
    }

    private void loadCoachCards() {
        int count = readInteger();
        // load coachs items
        for (int i = 0; i < count; i++) {
            CoachCardTemplate template = new CoachCardTemplate(this);
            FactoryManager.getInstance().getCoachCard().add(template);
        }
    }
}
