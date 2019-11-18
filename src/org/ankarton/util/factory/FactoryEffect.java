package org.ankarton.util.factory;

import org.ankarton.util.document.data.effects.Effect;
import org.ankarton.util.document.loader.EffectLoader;

/**
 * Created by Locos on 03/07/2017.
 */
public class FactoryEffect extends Factory<Effect> {

   public FactoryEffect() {
       this.loader = new EffectLoader();
   }

    @Override
    public void load() throws Exception {
        this.clear();
        this.loader.load();
    }
}
