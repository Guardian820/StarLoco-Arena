package org.ankarton.network.login.parser.packet.fight;

import org.ankarton.network.entity.Client;
import org.ankarton.util.document.data.spell.SpellManager;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * Created by Ryukis on 18/11/2015.
 */
public class SpellCast {

    public static void parse(Client client, IoBuffer recv){
        final long fighterId = recv.getLong();
        final int spellId = recv.getInt();
        final int castPositionX = recv.getInt();
        final int castPositionY = recv.getInt();
        final short castPositionZ = recv.getShort();

        SpellManager.getInstance().castSpell(client, fighterId, spellId, castPositionX, castPositionY, castPositionZ);
    }
}
