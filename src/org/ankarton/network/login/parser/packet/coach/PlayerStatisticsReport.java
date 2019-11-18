package org.ankarton.network.login.parser.packet.coach;

import org.ankarton.network.entity.Buffer;
import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Client;

public class PlayerStatisticsReport {

	public static void parse(Client client, IoBuffer recv) {
		// Name  Type
        // Int   1
        // Long  2
        // Float 3


		new Buffer(92, Send.PLAYER_STATISTICS_REPORT)
		.putShort(86)//size
		.putShort(1)//ModelId
        .putShort(1)//ModelId
		.putLong(1)//reportId

		.putShort(8)//nbEntries

                .putShort(1) // id
                .put(2) // type
                .putLong(1)//Value

                .putShort(2) // id
                .put(2) // type
                .putLong(1)//Value

                .putShort(3) // id
                .put(1) // type
                .putInt(1)//Value

                .putShort(4) // id
                .put(1) // type
                .putInt(1)//Value

                .putShort(5) // id
                .put(1) // type
                .putInt(1)//Value

                .putShort(6) // id
                .put(1) // type
                .putInt(2)//Value

                .putShort(7) // id
                .put(1) // type
                .putInt(2)//Value

                .putShort(8) // id
                .put(1) // type
                .putInt(1)//Value

                .send(client);
	}
}