package org.ankarton.util.document.data.card;

import org.ankarton.util.document.DocumentLoader;
import org.ankarton.util.document.Template;

/**
 * Created by Locos on 03/07/2017.
 */
public class WeaponFighterCardTemplate extends FighterCardTemplate {

    private byte weaponActionPoints;
    private boolean weaponOnlyLine;
    private int weaponRangeMin, weaponRangeMax;
    private boolean weaponTestLos, weaponTestFreeCell;
    private boolean weaponAllowedWhenCarried, isWeaponAllowedWhenCarrying;

    public WeaponFighterCardTemplate(int id, byte type, DocumentLoader loader) {
        this.id = id;
        this.type = type;
        this.weaponActionPoints = loader.readByte();
        this.weaponOnlyLine = loader.readBoolean();
        this.weaponRangeMin = loader.readInteger();
        this.weaponRangeMax = loader.readInteger();
        this.weaponTestLos = loader.readBoolean();
        this.weaponTestFreeCell = loader.readBoolean();
        this.value = loader.readInteger();
        this.weaponAllowedWhenCarried = loader.readBoolean();
        this.isWeaponAllowedWhenCarrying = loader.readBoolean();
        this.scriptId = loader.readInteger();
        this.subType = loader.readInteger();
        //System.out.println("W : Id: " + this.id + " | Type : " + this.type + " | " + this.weaponActionPoints);
    }

    public byte getWeaponActionPoints() {
        return weaponActionPoints;
    }

    public boolean isWeaponOnlyLine() {
        return weaponOnlyLine;
    }

    public int getWeaponRangeMin() {
        return weaponRangeMin;
    }

    public int getWeaponRangeMax() {
        return weaponRangeMax;
    }

    public boolean isWeaponTestLos() {
        return weaponTestLos;
    }

    public boolean isWeaponTestFreeCell() {
        return weaponTestFreeCell;
    }

    public boolean isWeaponAllowedWhenCarried() {
        return weaponAllowedWhenCarried;
    }

    public boolean isWeaponAllowedWhenCarrying() {
        return isWeaponAllowedWhenCarrying;
    }
}
