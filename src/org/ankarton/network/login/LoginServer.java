package org.ankarton.network.login;

import ch.qos.logback.classic.Logger;

import org.ankarton.network.Network;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class LoginServer {

    private final SocketAcceptor acceptor = new NioSocketAcceptor();

    public LoginServer() {
    	acceptor.setReuseAddress(true);
        acceptor.setHandler(new LoginHandler());
    }

    public void start() {
        if (acceptor.isActive())
            return;
        Logger logger = (Logger) LoggerFactory.getLogger(LoginServer.class);
        try {
            acceptor.bind(new InetSocketAddress(Network.loginPort));
            logger.info("Login server started on port " + Network.loginPort );
        } catch (IOException e) {
            logger.error("Fail to bind acceptor : " + e);
            System.exit(3);
        }
    }

    public void stop() {
        if (!acceptor.isActive())
            return;
        acceptor.unbind();
        acceptor.getManagedSessions().values().stream().parallel().filter(
                session -> session.isConnected() || !session.isClosing()).forEach(session -> session.close(true));
        acceptor.dispose();

        Logger logger = (Logger) LoggerFactory.getLogger(LoginServer.class);
        logger.info("Login server stopped");
    }
}
