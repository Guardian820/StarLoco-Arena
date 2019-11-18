package org.ankarton.network.entity;

import org.apache.mina.core.buffer.IoBuffer;

import org.ankarton.network.OpCode.Send;
import org.ankarton.world.World;
import org.ankarton.world.entity.coach.Coach;

public class Buffer {
	
	private IoBuffer buffer;
	private final Send send;
	private final int capacity;
	
	public Buffer(int capacity) {
		this.send = null;
		this.capacity = capacity;
		this.buffer = IoBuffer.allocate(this.capacity);
		this.buffer.clear();
	}
	
	public Buffer(int capacity, Send send) {
		this.send = send;
		this.capacity = 4 + capacity;
		this.buffer = IoBuffer.allocate(this.capacity);
		this.buffer.clear();
		this.putShort(this.capacity).putShort(send.value);
	}

	public void clear(){
		this.buffer.clear();
	}

	public void send(Client client) {
		this.buffer.flip();
		client.send(this.buffer);
		Client.logger.debug("send > " + this.getName(client) + this.send + " > (id : " + this.send.value + " | size : " + this.capacity + ") > " + this.buffer);
	}
	
	public Buffer put(int value) {
		this.buffer.put((byte) value);
		return this;
	}
	
	public Buffer put(byte[] value) {
		this.buffer.put(value);
		return this;
	}
	
	public Buffer put(String value) {
		this.put(value.length());
		this.buffer.put(value.getBytes());
		return this;
	}
	
	public Buffer putFloat(float value) {
		this.buffer.putFloat(value);
		return this;
	}
	
	public Buffer putShort(int value) {
		this.buffer.putShort((short) value);
		return this;
	}
	
	public Buffer putInt(int value) {
		this.buffer.putInt(value);
		return this;
	}
	
	public Buffer putLong(long value) {
		this.buffer.putLong(value);
		return this;
	}
	
	public IoBuffer getIoBuffer() {
		return buffer;
	}
	
	public String getName(Client client) {
		if(client.getAccount() != null)
			if(client.getAccount().getCoach() != null)			
				return client.getAccount().getCoach().getName() + " > ";
		return "";
	}

	public void sendTo(Client... clients){
		for(Client client : clients){
			this.sent(client.getAccount().getCoach());
		}
	}
	public void sendTo(Coach... coachs) {
		for(Coach coach : coachs)
			this.sent(coach);
	}

	public void sendToAll() {
		World.getInstance().getOnlineCoachs().forEach(this::sent);
	}
	
	public void sendToAllWithout(Coach arg) {
		World.getInstance().getOnlineCoachs().stream().filter(coach -> coach.getId() != arg.getId()).forEach(this::sent);
	}

	private void sent(Coach coach) {
		IoBuffer buffer = this.buffer.duplicate();
		buffer.flip();
		Client client = coach.getAccount().getClient();
		client.send(buffer);
		Client.logger.debug("send > " + coach.getName() + " > " + this.send + " > (id : " + this.send.value + " | size : " +  this.capacity + ") > " +  buffer);
	}
}