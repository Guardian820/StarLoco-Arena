package org.ankarton.network.login.parser.packet.opponent;

import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Buffer;
import org.ankarton.network.entity.Client;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * Created by Locos on 13/09/2015.
 */
public class OpponentSearchCancel {

    public static void parse(Client client, IoBuffer recv) {

        new Buffer(0, OpCode.Send.OPPONENT_SEARCH_CANCEL_RESULT).send(client);
    }
}
