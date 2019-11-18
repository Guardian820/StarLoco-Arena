package org.ankarton.util.document.data.events;

import org.ankarton.util.document.loader.EventLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryukis on 16/11/2015.
 */
public class EventsManager {
    private static EventsManager ourInstance = new EventsManager();
    private Map<Integer, Event> eventsList = new HashMap<>();
    public static EventsManager getInstance() {
        return ourInstance;
    }

    private EventsManager() {
    }
    public void addEvent(Event evt){
        eventsList.put(evt.ID, evt);
    }

    public void load(){new EventLoader().load();}

    public void clean(){eventsList.clear();}

    public Event getEvent(int ID){
        return eventsList.get(ID);
    }
    public int getEventsCount(){return eventsList.size();}
}
