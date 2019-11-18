package org.ankarton.util.document.loader;

import org.ankarton.util.document.DocumentContainer;
import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.data.summon.Summoning;
import org.ankarton.util.document.data.summon.SummoningManager;

import java.util.List;

/**
 * Created by Ryukis on 17/11/2015.
 */
public class SummoningLoader extends DocumentLoader {

    public void load() {
        try{
            this.open("data/summoning.dat");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void read(DocumentContainer container) {
        if(container == null) return;

        try {
            int summoningCount = readInteger();

            for(int i = 0; i < summoningCount; i++)
                SummoningManager.getInstance().addSummoning(new Summoning(readInteger(), readInteger(), readInteger(), readInteger(), readInteger(), readInteger()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
