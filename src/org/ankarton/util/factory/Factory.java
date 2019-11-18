package org.ankarton.util.factory;

import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.Template;

import java.util.ArrayList;

/**
 * Created by Locos on 02/07/2017.
 */
public abstract class Factory<T extends Template> {

    protected DocumentLoader loader;
    private ArrayList<T> data = new ArrayList<>();

    public void add(T data) {
        if(!this.contains(data.getId())) {
            this.data.add(data);
        }
    }

    public T get(int id) {
        for(T data : this.data) {
            if(data.getId() == id) {
                return data;
            }
        }
        return null;
    }

    public int size() {
        return data.size();
    }

    public boolean contains(int id) {
        return get(id) != null;
    }

    public void clear() {
        this.data.clear();
    }

    public abstract void load() throws Exception;
}
