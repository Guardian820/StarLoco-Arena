package org.ankarton.util.document.data.card;

import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.Template;

/**
 * Created by flore on 03/07/2017.
 */
public class CoachCardTemplate implements Template {

    private final int id, type, value, set;

    public CoachCardTemplate(DocumentLoader loader) {
        this.id = loader.readInteger();
        this.type = loader.readInteger();
        this.value = loader.readInteger();
        this.set = loader.readInteger();
    }

    @Override
    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getSet() {
        return set;
    }
}
