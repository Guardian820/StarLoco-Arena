package org.ankarton.network.login;

import org.ankarton.network.Network;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import org.ankarton.network.OpCode.Send;
import org.ankarton.network.entity.Buffer;
import org.ankarton.network.login.parser.Parser;

public class LoginHandler implements IoHandler {

    @Override
    public void exceptionCaught(IoSession arg0, Throwable arg1) throws Exception {
        LoginClient client = (LoginClient) arg0.getAttribute("client");
        client.logger.error("exception : {}", arg1);
    }

    @Override
    public void messageReceived(IoSession arg0, Object arg1) throws Exception {
        LoginClient client = (LoginClient) arg0.getAttribute("client");
        Parser.parser(client, (IoBuffer) arg1);     
    }

    @Override
    public void messageSent(IoSession arg0, Object arg1) throws Exception {}

    @Override
    public void sessionClosed(IoSession arg0) throws Exception {
        LoginClient client = (LoginClient) arg0.getAttribute("client");
        client.disconnect();
        client.logger.debug("has been closed.");
    }

    @Override
    public void sessionCreated(IoSession arg0) throws Exception {
    	LoginClient client = new LoginClient(arg0);
        arg0.setAttribute("client", client);
        
        if(!Network.beta) {
        	new Buffer(1, Send.AUTHENTICATION_RESULT).put(127).send(client);
        	client.kick();
        } else if(Network.state.equals(Network.State.UNVAILABLE)) {
        	new Buffer(0, Send.WORLD_SERVER_UNAVAILABLE).send(client);
        	client.kick();
        }
    }

    @Override
    public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
        LoginClient client = (LoginClient) arg0.getAttribute("client");
        client.logger.info("idle");
    }

    @Override
    public void sessionOpened(IoSession arg0) throws Exception {}
}
