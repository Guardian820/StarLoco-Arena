package org.ankarton.world.entity.fight;

import javafx.util.Pair;
import org.ankarton.network.OpCode;
import org.ankarton.network.entity.Buffer;
import org.ankarton.world.entity.coach.Coach;
import org.ankarton.world.entity.fight.fighter.TempCoachFighter;
import org.ankarton.world.entity.team.Fighter;
import org.apache.mina.core.buffer.IoBuffer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Locos & Ryukis on 14/09/2015.
 */
public class Fight {
    private final long id;
    private final byte type;
    private Timer fightTimer = new Timer();
    private TempCoachFighter Fighter_1;
    private TempCoachFighter Fighter_2;

    public Fight(byte type, long id) {
        this.id = id;
        this.type = type;
    }

    public TempCoachFighter getFirstCoach(){
        return Fighter_1;
    }
    public TempCoachFighter getSecondCoach() {
        return Fighter_2;
    }

    public void startPresentation(){
        int bytesCount = 25;
        Coach coach = Fighter_1.getClient().getAccount().getCoach();
        Fighter fighter;
        for(int i = 0;i < coach.getTeams().size();i++){
            bytesCount += 7 + coach.getTeams().get(i).getName().length();
            for(int j = 0;j < coach.getTeams().get(i).getFighters().size();j++){
                fighter = coach.getTeams().get(i).getFighters().get(j);
                bytesCount += 18 + fighter.getSpellToByte(fighter.parseSpellsToString()).length + fighter.getObjectsToByte(fighter.parseObjectsToString()).length;
            }
        }

        Buffer buffer = new Buffer(bytesCount, OpCode.Send.CREATE_FIGHT).put((byte) 0);

        buffer.putShort(1);
        buffer.put((int) 1);
        buffer.putLong(1);
        buffer.put((byte) 1);
        buffer.put((int) type);
        buffer.put((int) 0);
        buffer.put((byte) coach.getTeams().size());
        for(int i = 0;i < coach.getTeams().size();i++){
            buffer.put((byte) coach.getTeams().get(i).getId());
            buffer.put((byte) coach.getTeams().get(i).getName().length());
            buffer.put(coach.getTeams().get(i).getName().getBytes());
            buffer.put((byte) 1);
            buffer.put((int) coach.getTeams().get(i).getFighters().size());
            for(int j = 0;j < coach.getTeams().get(i).getFighters().size();j++){
                fighter = coach.getTeams().get(i).getFighters().get(j);
                buffer.putLong(fighter.getId());
                buffer.put((byte) fighter.getBreed());
                buffer.put((byte) fighter.getName().length());
                buffer.put(fighter.getName().getBytes());
                buffer.put((byte) fighter.getSex());
                buffer.put((byte) fighter.getSkin());
                buffer.putShort(fighter.getSpells().size());
                buffer.put(fighter.getSpellToByte(fighter.parseSpellsToString()));
                buffer.putShort(fighter.getObjects().size());
                buffer.put(fighter.getObjectsToByte(fighter.parseObjectsToString()));
            }
        }
        buffer.send(Fighter_1.getClient());
        buffer.clear();
        bytesCount = 0;
        coach = Fighter_2.getClient().getAccount().getCoach();

        for(int i = 0;i < coach.getTeams().size();i++){
            bytesCount += 7 + coach.getTeams().get(i).getName().length();
            for(int j = 0;j < coach.getTeams().get(i).getFighters().size();j++){
                fighter = coach.getTeams().get(i).getFighters().get(j);
                bytesCount += 18 + fighter.getSpellToByte(fighter.parseSpellsToString()).length + fighter.getObjectsToByte(fighter.parseObjectsToString()).length;
            }
        }

        buffer = new Buffer(2 + 4 + 8 + 1 + 4 + 4 + 1 + bytesCount, OpCode.Send.CREATE_FIGHT).put((byte) 0);


        buffer.putShort(1);
        buffer.put(1);
        buffer.putLong(1);
        buffer.put((byte) 1);
        buffer.put((int) type);
        buffer.put(0);
        buffer.put((byte) coach.getTeams().size());
        for(int i = 0;i < coach.getTeams().size();i++){
            buffer.put((byte) coach.getTeams().get(i).getId());
            buffer.put((byte) coach.getTeams().get(i).getName().length());
            buffer.put(coach.getTeams().get(i).getName().getBytes());
            buffer.put((byte) 1);
            buffer.put((int) coach.getTeams().get(i).getFighters().size());
            for(int j = 0;j < coach.getTeams().get(i).getFighters().size();j++){
                fighter = coach.getTeams().get(i).getFighters().get(j);
                buffer.putLong(fighter.getId());
                buffer.put((byte) fighter.getBreed());
                buffer.put((byte) fighter.getName().length());
                buffer.put(fighter.getName().getBytes());
                buffer.put((byte) fighter.getSex());
                buffer.put((byte) fighter.getSkin());
                buffer.putShort(fighter.getSpells().size());
                buffer.put(fighter.getSpellToByte(fighter.parseSpellsToString()));
                buffer.putShort(fighter.getObjects().size());
                buffer.put(fighter.getObjectsToByte(fighter.parseObjectsToString()));
            }
        }

        buffer.send(Fighter_2.getClient());
        System.out.println("Starting presentation");

        /*TimerTask endPlacementTask = new TimerTask(){
            @Override
            public void run(){
                sendToThem(0, OpCode.Send.END_PLACEMENT);
                //FightStarted
            }
        };

        TimerTask endPresentationTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("End of presentation...");
                sendToThem(0, OpCode.Send.END_PRESENTATION);

                sendToThem(0, OpCode.Send.END_PRESENTATION);

                fightTimer.scheduleAtFixedRate(endPlacementTask, 0, 30000);
            }
        };*/
        sendToThem(0, OpCode.Send.START_PRESENTATION);
    }

    /*private void sendToThem(Buffer buffer){
        buffer.sendTo(Fighter_1.getClient().getAccount().getCoach(), Fighter_2.getClient().getAccount().getCoach());
    }*/

    private void sendToThem(int size, OpCode.Send value){
        new Buffer(size, value).send(Fighter_1.getClient());
        new Buffer(size, value).send(Fighter_2.getClient());
    }

    public long getId() {
        return id;
    }

    public byte getType() {
        return type;
    }

    public synchronized void setTempCoachFighter(TempCoachFighter tempCoachFighter) {
        if(Fighter_1 == null){
            Fighter_1 = tempCoachFighter;
        }
        else{
            if(Fighter_2 == null){
                Fighter_2 = tempCoachFighter;
            }
        }
    }
}
