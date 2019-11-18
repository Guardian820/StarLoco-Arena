package org.ankarton.util.factory;

import org.ankarton.util.document.data.card.CoachCardTemplate;
import org.ankarton.util.document.loader.CoachCardLoader;

/**
 * Created by Locos on 03/07/2017.
 */
public class FactoryCoachCard extends Factory<CoachCardTemplate> {

   public FactoryCoachCard() {
       this.loader = new CoachCardLoader();
   }

    @Override
    public void load() throws Exception {
        this.loader.load();
    }
}
