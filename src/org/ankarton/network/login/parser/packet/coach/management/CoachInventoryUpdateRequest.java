package org.ankarton.network.login.parser.packet.coach.management;

import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Buffer;
import org.ankarton.network.entity.Client;
import org.ankarton.world.entity.card.CoachCard;
import org.ankarton.world.entity.coach.Coach;
import org.apache.mina.core.buffer.IoBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathieu on 08/09/2015.
 */
public class CoachInventoryUpdateRequest {

    private List<CoachCard> addEquipments = new ArrayList<>(), removeEquipments = new ArrayList<>();
    private List<CoachCard> addInventory = new ArrayList<>(), removeInventory = new ArrayList<>();

    private void setAddEquipments(Client client, Buffer buffer) {

    }
    private void setRemoveEquipments(Buffer buffer) {

    }

    public void setAddInventory(CoachCard card) {
        this.addInventory.add(card);
    }

    private void setRemoveInventory(Buffer buffer) {

    }

    public void parse(Client client, IoBuffer recv) {
        final Coach coach = client.getAccount().getCoach();

        short nb = 0;
        for (CoachCard card : coach.getInventory()) {
            if (card.getPos() != 0) {
                nb += 17;
            }
        }

        Buffer buffer = new Buffer(23 + nb + this.addInventory.size() * 15, OpCode.Send.COACH_INVENTORY_UPDATE_MESSAGE);
        // 4 parts: addEquipment, removeEquipment, addInventory, removeInventory

        /* first part addEquipment **/
        buffer.putShort(nb/17);
        for (CoachCard card : coach.getInventory()) {
            if (card.getPos() != 0) {
                buffer.putShort(card.getPos());
                buffer.putInt(card.getTemplateId());
                buffer.putLong(card.getId());
                buffer.put(card.getFlag());
                buffer.putShort(card.getQuantity());
            }
        }




        /* second part removeEquipment **/
        buffer.putShort(0);


        /* third part addInventory **/
        if(this.addInventory.isEmpty()) {
            buffer.putShort(0);
        } else {
            buffer.putShort(this.addInventory.size());
            for(CoachCard card : this.addInventory) {
                buffer.putInt(card.getTemplateId());
                buffer.putLong(card.getId());
                buffer.put(card.getFlag());
                buffer.putShort(card.getQuantity());
            }
        }


        /* fourth part removeInventory **/
        buffer.putShort(0);


        buffer.sendToAll();
    }
}
