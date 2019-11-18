package org.ankarton.world;

import java.util.ArrayList;

import ch.qos.logback.classic.Logger;
import org.ankarton.util.factory.FactoryManager;
import org.ankarton.world.entity.coach.Coach;
import org.slf4j.LoggerFactory;

public class World {
	
	private static World singleton = new World();
    public static final Logger logger = (Logger) LoggerFactory.getLogger("World");

	private ArrayList<Coach> onlineCoachs = new ArrayList<>();
	
	public static World getInstance() {
		return singleton;
	}

    public void initialize() {
		FactoryManager.getInstance().initialize();
    }

	public ArrayList<Coach> getOnlineCoachs() {
		return onlineCoachs;
	}
	
	public void addOnlineCoach(Coach coach) {
		this.onlineCoachs.add(coach);
        this.onlineCoachs.forEach(Coach::onJoinMap);
	}
	
	public void removeOnlineCoach(Coach coach) {
		this.onlineCoachs.remove(coach);
		for(Coach entity : this.onlineCoachs)
			entity.onLeftMap(coach);
	}
}