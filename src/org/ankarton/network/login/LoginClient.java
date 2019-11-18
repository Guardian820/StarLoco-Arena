package org.ankarton.network.login;

import org.apache.mina.core.session.IoSession;

import org.ankarton.database.Database;
import org.ankarton.network.entity.Client;
import org.ankarton.world.World;
import org.ankarton.world.entity.coach.Coach;

import java.sql.SQLException;

public class LoginClient extends Client {

	public LoginClient(IoSession ioSession) {
		super(ioSession);
		logger.info("has been created.");
	}

	@Override
	public void disconnect() {
		if(this.account != null) {
			this.account.setConnected(false);
			Coach coach = this.account.getCoach();
			
			if(coach != null) {
				try {
					Database.getInstance().getCoachsData().update(coach);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				World.getInstance().removeOnlineCoach(coach);		
			}
		}
		
		if(this.ioSession.isConnected())
			this.ioSession.close(true);
	}
}
