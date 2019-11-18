package org.ankarton.util.document.data.summon;

/**
 * Created by Ryukis on 16/11/2015.
 */
public class Summoning {

    public int id;
    public int hp;
    public int pa;
    public int pm;
    public int gfx;
    public int spell;

    public Summoning(int Id, int HP, int PA, int PM, int gfx, int spell){
        id = Id;
        hp = HP;
        pa = PA;
        pm = PM;
        this.gfx = gfx;
        this.spell = spell;
    }
}
