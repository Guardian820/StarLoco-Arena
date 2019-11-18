package org.ankarton.util.document;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * Created by Locos on 18/08/2015.
 */
public abstract class DocumentLoader implements DocumentContainer {

    private ByteBuffer buffer;

    public abstract void load() throws Exception;

    public void open(String fileName) throws Exception {
        InputStream stream = null;
        try {
            stream = new URL(fileName).openStream();
        } catch (Exception e) {
            stream = new FileInputStream(new File(fileName));
        }
        if(stream != null)
            open(stream);
    }

    public void open(InputStream stream) throws Exception {
        int fileLength = stream.available();

        if (fileLength == 0) {
            stream.close();
            return;
        }

        byte[] streamBuffer = new byte[fileLength];
        int bytesRead = stream.read(streamBuffer);

        if (bytesRead != fileLength) {
            stream.close();
            return;
        }

        this.buffer = ByteBuffer.wrap(streamBuffer);
        this.buffer.order(ByteOrder.BIG_ENDIAN);
        this.buffer.rewind();
        this.read(this);

        this.buffer.clear();
        stream.close();
    }

    public boolean readBoolean() {
        return buffer.get() != 0;
    }

    public int readInteger() {
        return buffer.getInt();
    }

    public float readFloat() {
        return buffer.getFloat();
    }

    public short readShort() {
        return buffer.getShort();
    }

    public byte readByte() {
        return buffer.get();
    }

    public int[] readIntArray(){
        int length = buffer.getInt();
        int[] ints = new int[length];
        for(int i = 0;i < length;i++){
            ints[i] = buffer.getInt();
        }
        return ints;
    }

    public short[] readShortArray(){
        int length = buffer.getInt();
        short[] shorts = new short[length];
        for(int i = 0;i < length;i++){
            shorts[i] = buffer.getShort();
        }
        return shorts;
    }

    public float[] readFloatArray(){
        int length = buffer.getInt();
        float[] floats = new float[length];
        for(int i = 0;i < length;i++){
            floats[i] = buffer.getFloat();
        }
        return floats;
    }

    public String readString() {
        int length = buffer.getInt();
        byte[] bytes = new byte[length];
        buffer.get(bytes, 0, length);
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {}
        return null;
    }
}
