package org.ankarton.util.factory;

import org.ankarton.util.document.data.card.FighterCardTemplate;
import org.ankarton.util.document.loader.FighterCardLoader;

/**
 * Created by Locos on 03/07/2017.
 */
public class FactoryFighterCard extends Factory<FighterCardTemplate> {

   public FactoryFighterCard() {
       this.loader = new FighterCardLoader();
   }

    @Override
    public void load() throws Exception {
        this.clear();
        this.loader.load();
    }
}
