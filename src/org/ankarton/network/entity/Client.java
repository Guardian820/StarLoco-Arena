package org.ankarton.network.entity;

import org.ankarton.util.TimerWaiter;
import org.ankarton.world.entity.client.Account;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public abstract class Client {

	public static final Logger logger = (Logger) LoggerFactory.getLogger(Client.class);

	protected final IoSession ioSession;
	protected Account account;
	protected final TimerWaiter waiter = new TimerWaiter();
	
	public Client(IoSession ioSession) {
		this.ioSession = ioSession;
	}
	
	public void send(IoBuffer buffer) {
		this.ioSession.write(buffer);
	}

	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

	public TimerWaiter getWaiter() {
		return waiter;
	}
	
	public boolean kick() {
		return this.ioSession.close(true).isClosed();
	}
	
	public abstract void disconnect();
}