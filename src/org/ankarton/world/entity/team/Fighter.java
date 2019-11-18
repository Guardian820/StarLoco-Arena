package org.ankarton.world.entity.team;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.ankarton.network.entity.Buffer;
import org.ankarton.util.document.data.spell.Spell;
import org.ankarton.util.document.data.spell.SpellManager;


import java.util.*;

@DatabaseTable(tableName = "fighters")
public class Fighter {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private byte breed, sex, skin;
    @DatabaseField
    private short budget;

    //FIXME: Add it in database, need template of spells / objects (DataPersister ORMLite)
    private List<Spell> spells = new ArrayList<>();
    //private List<ItemTemplate> objects = new ArrayList<>();

    //Spell & Equipement

    public Fighter() {

    }

    public Fighter(String name, byte breed, byte sex, byte skin, short budget, String objects, String spells) {
        this.name = name;
        this.breed = breed;
        this.sex = sex;
        this.skin = skin;
        this.budget = budget;

        this.parseSpellsToByte(spells);
        this.parseObjectsToByte(objects);
        this.applyObjectsCharacteristics();
    }

    private void applyObjectsCharacteristics() {
       /* for (ItemTemplate object : objects) {
            this.applyStats(object.getEffects(), object.getEffectsParams());
        }*/
    }

    private void applyStats(int[] effectsId, int[] effectsParams) {
        for (int i = 0; i < effectsId.length; i++) {

            //TODO: A faire une class Stats pour cumuler tout
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte getBreed() {
        return breed;
    }

    public byte getSex() {
        return sex;
    }

    public int getSkin() {
        return skin;
    }

    public short getBudget() {
        return budget;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void setObjects(List<?> objects) {
        //this.objects = objects;
        applyObjectsCharacteristics();
    }

    public List<?> getObjects() {
        return null;
    }

    public byte[] getObjectsToByte(String objects) {
        if (objects.isEmpty()) return new byte[0];

        byte[] bytes = new byte[0];
        int i = 0;

        while (objects.contains(",")) {
            bytes[i] = Byte.parseByte(objects.substring(0, objects.indexOf(",")));
            i++;
            if (!objects.contains(","))
                bytes[i] = Byte.parseByte(objects);
            else
                objects = objects.substring(objects.indexOf(",") + 1);
        }
        return bytes;
    }

    public byte[] getSpellToByte(String spells) {
        if (spells.isEmpty()) return new byte[0];

        byte[] bytes = new byte[0];
        int i = 0;

        while (spells.contains(",")) {
            bytes[i] = Byte.parseByte(spells.substring(0, spells.indexOf(",")));
            i++;
            if (!spells.contains(",")) {
                bytes[i] = Byte.parseByte(spells);
            } else
                spells = spells.substring(spells.indexOf(",") + 1);
        }
        return bytes;
    }

    public void parseSpellsToByte(String spells) {
        if (spells == null || spells.isEmpty()) return;

        String[] split = spells.split(",");

        for (String spell : split) {
            this.spells.add(SpellManager.getInstance().getNewSpellInstance(Integer.parseInt(spell)));
        }
    }

    public void parseObjectsToByte(String objects) {
        if (objects == null || objects.isEmpty()) return;

        String[] split = objects.split(",");
        for (String object : split) {
           // this.objects.add(ItemsManager.getInstance().getItemTemplate(Integer.parseInt(object)));
        }
    }

    public Spell getSpellByID(int spellID) {
        for (int i = 0; i < this.spells.size(); i++) {
            if (this.spells.get(i).id == spellID) {
                return this.spells.get(i);
            }
        }
        return null;
    }

    public String parseSpellsToString() {
        String spells = "";
        for (Spell spell : this.spells)
            spells += (spells.isEmpty()) ? spell.id : "," + spell.id;
        return spells;
    }

    public String parseObjectsToString() {
        String object = "";
        //for (ItemTemplate object1 : this.objects)
        //    object += (object.isEmpty() ? object1.getId() : "," + object1.getId());
        return object;
    }

    public byte[] serialize() {
        final Buffer buffer = new Buffer(11 + this.name.length()).put(1).putShort(this.budget).put(this.breed)
                .put(this.name).put(this.sex).put(this.skin).putShort(0).putShort(0);
        buffer.getIoBuffer().flip();
        return buffer.getIoBuffer().array();
    }
}
/*
    private void applyStats(int[] effectsID, int[] effectsParams){
        for(int i = 0;i < effectsID.length;i++){
            switch(effectsID[i]){
                case 11 : //+ PDV
                    this.HP += effectsParams[i];
                    break;
                case 12 : // - PDV
                    this.HP -= effectsParams[i];
                    break;
                case 112 :
                case 13 : // + PA
                case 15 :
                    this.PA += effectsParams[i];
                    break;
                case 113 :
                case 14 : // - PA
                case 16 :
                    this.PA -= effectsParams[i];
                    break;
                case 122 :
                case 17 : // + PM
                case 19 :
                    this.PM += effectsParams[i];
                    break;
                case 123 :
                case 18 : // - PM
                case 20 :
                    this.PM -= effectsParams[i];
                    break;
                case 29 : //+ % res fire
                    this.Res_Fire_Percent += effectsParams[i];
                    break;
                case 30 : //- % res fire
                    this.Res_Fire_Percent -= effectsParams[i];
                    break;
                case 31 : //+ % res earth
                    this.Res_Earth_Percent += effectsParams[i];
                    break;
                case 32 : //- % res earth
                    this.Res_Earth_Percent -= effectsParams[i];
                    break;
                case 33 : //+ % res water
                    this.Res_Water_Percent += effectsParams[i];
                    break;
                case 34 : //- % res water
                    this.Res_Water_Percent -= effectsParams[i];
                    break;
                case 35 : //+ % res wind
                    this.Res_Wind_Percent += effectsParams[i];
                    break;
                case 36 : //- % res wind
                    this.Res_Wind_Percent -= effectsParams[i];
                    break;
                case 48 : // + % fire dmg
                    this.Dmg_Fire_Percent += effectsParams[i];
                    break;
                case 49 : // - % fire dmg
                    this.Dmg_Fire_Percent -= effectsParams[i];
                    break;
                case 50 : // + % earth dmg
                    this.Dmg_Earth_Percent += effectsParams[i];
                    break;
                case 51 : // - % earth dmg
                    this.Dmg_Earth_Percent -= effectsParams[i];
                    break;
                case 52 : // + % water dmg
                    this.Dmg_Water_Percent += effectsParams[i];
                    break;
                case 53 : // - % water dmg
                    this.Dmg_Water_Percent -= effectsParams[i];
                    break;
                case 54 : // + % wind dmg
                    this.Dmg_Wind_Percent += effectsParams[i];
                    break;
                case 55 : // - % wind dmg
                    this.Dmg_Wind_Percent -= effectsParams[i];
                    break;
                case 60 : // Morph
                    this.skin = effectsParams[i];
                    break;
                case 70 : //+ % CC
                    this.Critical_Rate_Percent += effectsParams[i];
                    break;
                case 71 : //+ % EC
                    this.Fumble_Rate_Percent += effectsParams[i];
                    break;
                case 72 : // + range
                    this.Range += effectsParams[i];
                    break;
                case 73 : // - range
                    this.Range -= effectsParams[i];
                    break;
                case 74 : // + invocations
                case 212 :
                    this.Summons += effectsParams[i];
                    break;
                case 76 : //+ initiative
                    this.Init += effectsParams[i];
                    break;
                case 77 : //- initiative
                    this.Init -= effectsParams[i];
                    break;
                case 78 : //+ % soins
                    this.Heal_Percent += effectsParams[i];
                    break;
                case 79 : // - % soins
                    this.Heal_Percent -= effectsParams[i];
                    break;
                case 80 : // + res all
                    this.Res_Percent += effectsParams[i];
                    break;
                case 81 : // - res all
                    this.Res_Percent -= effectsParams[i];
                    break;
                case 82 : //+ % dmg all
                    this.Dmg_Percent += effectsParams[i];
                    break;
                case 83 : //- % dmg all
                    this.Dmg_Percent -= effectsParams[i];
                    break;
                case 86 : //+ % res PA loose
                    this.Res_AP_Loose += effectsParams[i];
                    break;
                case 87 : //+ % res PM loose
                    this.Res_MP_Loose += effectsParams[i];
                    break;
                case 89 : //+ Dmg % rebound
                    this.Dmg_Rebound_Percent += effectsParams[i];
                    break;
                case 90 : //+ Dmg rebound
                    this.Dmg_Rebound += effectsParams[i];
                    break;
                case 130 : //+ Dmg all
                    this.Dmg += effectsParams[i];
                    break;
                case 131 : //+ Earth dmg
                    this.Dmg_Earth += effectsParams[i];
                    break;
                case 132 : //+ Fire dmg
                    this.Dmg_Fire += effectsParams[i];
                    break;
                case 133 : //+ Wind dmg
                    this.Dmg_Wind += effectsParams[i];
                    break;
                case 134 : //+ Water dmg
                    this.Dmg_Water += effectsParams[i];
                    break;
                case 135 : //+ Neutral dmg
                    this.Dmg_Neutral += effectsParams[i];
                    break;
                case 138 : //+ heal
                    this.Heal += effectsParams[i];
                    break;
                case 150 : //+ crit rate
                    this.Critical_Rate += effectsParams[i];
                    break;
                case 151 : //+ fumble rate
                    this.Fumble_Rate += effectsParams[i];
                    break;
                case 190 : //+ Reduct neutral
                    this.Res_Neutral += effectsParams[i];
                    break;
                case 191 : //+ Reduct earth
                    this.Res_Earth += effectsParams[i];
                    break;
                case 192 : //+ Reduct fire
                    this.Res_Fire += effectsParams[i];
                    break;
                case 193 : //+ Reduct wind
                    this.Res_Wind += effectsParams[i];
                    break;
                case 194 : //+ Reduct water
                    this.Res_Water += effectsParams[i];
                    break;
                case 195 : // + Reduct all
                    this.Res += effectsParams[i];
                    break;
            }
        }
    }

*/