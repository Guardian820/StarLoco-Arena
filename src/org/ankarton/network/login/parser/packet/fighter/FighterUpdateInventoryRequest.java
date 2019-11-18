package org.ankarton.network.login.parser.packet.fighter;

import org.ankarton.database.Database;
import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Buffer;
import org.ankarton.util.document.data.spell.Spell;
import org.ankarton.util.document.data.spell.SpellManager;
import org.ankarton.world.entity.coach.Coach;
import org.ankarton.world.entity.team.Fighter;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class FighterUpdateInventoryRequest {

	public static void parse(Client client, IoBuffer recv) {
        long id = recv.getLong();
        try {
            Coach coach = client.getAccount().getCoach();
            Fighter fighter = null;

            for (Fighter fighter1 : coach.getFighters()) {
                if (fighter1.getId() == id) {
                    fighter = fighter1;
                    break;
                }
            }

            if (fighter == null) throw new Exception("fighter " + id + " don't found");

            byte[] spells = new byte[recv.getShort()];
            recv.get(spells);

            //TODO: A check
            List<Spell> spellList = new ArrayList<>();
            for(int i = 0;i < spells.length;i++){
                spellList.add(SpellManager.getInstance().getSpell(spells[i]));
            }
            fighter.setSpells(spellList);

            IoBuffer buffer = IoBuffer.wrap(spells);
            System.out.println(buffer.getInt());


            //TODO: A check
            byte[] objects = new byte[recv.getShort()];
            recv.get(objects);
            /*List<ItemTemplate> items = new ArrayList<>();
            for(int i = 0;i < objects.length;i++){
                items.add(ItemsManager.getInstance().getItemTemplate(objects[i]));
            }*/
            //fighter.setObjects(items);

            Database.getInstance().getFightersData().update(fighter);
            new Buffer(13 + spells.length + objects.length, OpCode.Send.FIGHTER_UPDATED_INFORMATION_INVENTORY).putLong(id).put(0).putShort(spells.length).put(spells).putShort(objects.length).put(objects).send(client);
        } catch(Exception e) {
            e.printStackTrace();
            new Buffer(9, OpCode.Send.FIGHTER_UPDATED_INFORMATION_INVENTORY).putLong(id).put(1).send(client);
        }
	}
}