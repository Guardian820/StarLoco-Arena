package org.ankarton.util.factory;

import ch.qos.logback.classic.Logger;
import org.ankarton.util.document.DocumentLoader;
import org.slf4j.LoggerFactory;

/**
 * Created by Locos on 03/07/2017.
 */
public class FactoryManager {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(FactoryManager.class);
    private static FactoryManager manager;

    public static FactoryManager getInstance() {
        if(manager == null)
            return manager = new FactoryManager();
        return manager;
    }

    private FactoryCoachCard coachCard = new FactoryCoachCard();
    private FactoryFighterCard fighterCard = new FactoryFighterCard();
    private FactoryEffect effect = new FactoryEffect();

    public void initialize() {
        try {
            this.coachCard.load();
            logger.debug(" > " + this.coachCard.size() + " coach cards loaded successfully.");
            this.fighterCard.load();
            logger.debug(" > " + this.fighterCard.size() + " fighter cards loaded successfully.");
            this.effect.load();
            logger.debug(" > " + this.effect.size() + " effects loaded successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FactoryCoachCard getCoachCard() {
        return coachCard;
    }

    public FactoryFighterCard getFighterCard() {
        return fighterCard;
    }

    public FactoryEffect getEffect() {
        return effect;
    }
}
