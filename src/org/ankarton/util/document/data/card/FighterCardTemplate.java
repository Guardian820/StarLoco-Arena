package org.ankarton.util.document.data.card;

import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.Template;
import org.ankarton.util.document.data.effects.Effect;

import java.util.ArrayList;

/**
 * Created by Locos on 03/07/2017.
 */
public class FighterCardTemplate implements Template {

    protected int id;
    protected byte type;
    protected int value;
    protected int scriptId, subType;
    protected ArrayList<Effect> effects = new ArrayList<>();

    public FighterCardTemplate(int id, byte type, DocumentLoader loader) {
        this.id = id;
        this.type = type;
        loader.readByte(); loader.readBoolean();
        loader.readInteger(); loader.readInteger();// useless content (weapon)
        loader.readBoolean(); loader.readBoolean();
        this.value = loader.readInteger();
        loader.readBoolean(); loader.readBoolean();
        this.scriptId = loader.readInteger();
        this.subType = loader.readInteger();
        //System.out.println("F : Id: " + this.id + " | Type : " + this.type + " | " + this.value);
    }

    public FighterCardTemplate() {}

    @Override
    public int getId() {
        return id;
    }

    public byte getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getScriptId() {
        return scriptId;
    }

    public int getSubType() {
        return subType;
    }

    public void addEffect(Effect effect) {
        this.effects.add(effect);
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }
}
