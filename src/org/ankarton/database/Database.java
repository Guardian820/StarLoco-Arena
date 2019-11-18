package org.ankarton.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.ankarton.world.entity.card.CoachCard;
import org.ankarton.world.entity.client.Account;
import org.ankarton.world.entity.coach.Coach;
import org.ankarton.world.entity.team.Fighter;
import org.ankarton.world.entity.team.Team;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import java.sql.SQLException;

public class Database {
	
	private static Database singleton;

    private ConnectionSource connection;
	private final static String host = "127.0.0.1", database = "arenaa", user = "root", password = "";
    private final static short port = 3306;

    private Dao<Account, Integer> accountsData;
    private Dao<CoachCard, Integer> coachCardsData;
    private Dao<Coach, Integer> coachsData;
    private Dao<Team, Integer> teamsData;
    private Dao<Fighter, Long> fightersData;

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	public static Database getInstance() {
		if(singleton == null)
			return singleton = new Database();
		return singleton;
	}

	public Database() {
        this.initialize();
        try {
            this.setup();
            UpdateBuilder<Account, Integer> builder = this.accountsData.updateBuilder();
            builder.where().eq("connected", 1);
            builder.updateColumnValue("connected", 0);
            builder.update();
        } catch (Exception e) {
            logger.error("You've an error in your database dao initialization : ", e);
        }
    }
	
	private void initialize() {
        try {
            logger.info("Initializing of connection.");
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

            this.connection = new JdbcConnectionSource(url, user, password);
            logger.info("Database connection established.");
        } catch (Exception e) {
            logger.error("You've an error in your database connection : ", e);
            logger.error("Pleaz check your username, password, database and host connection.");
            System.exit(0);
        }
    }

    private void setup() throws Exception {
        this.accountsData = DaoManager.createDao(this.connection, Account.class);
        TableUtils.createTableIfNotExists(this.connection, Account.class);

        this.coachCardsData = DaoManager.createDao(this.connection, CoachCard.class);
        TableUtils.createTableIfNotExists(this.connection, CoachCard.class);

        this.coachsData = DaoManager.createDao(this.connection, Coach.class);
        TableUtils.createTableIfNotExists(this.connection, Coach.class);

        this.teamsData = DaoManager.createDao(this.connection, Team.class);
        TableUtils.createTableIfNotExists(this.connection, Team.class);

        this.fightersData = DaoManager.createDao(this.connection, Fighter.class);
        TableUtils.createTableIfNotExists(this.connection, Fighter.class);
    }

    public Dao<Account, Integer> getAccountsData() {
        return accountsData;
    }

    public Dao<CoachCard, Integer> getCoachCardsData() {
        return coachCardsData;
    }

    public Dao<Coach, Integer> getCoachsData() {
        return coachsData;
    }

    public Dao<Team, Integer> getTeamsData() {
        return teamsData;
    }

    public Dao<Fighter, Long> getFightersData() {
        return fightersData;
    }
}