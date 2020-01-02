package org.noear.solon.core;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
/**
 * 协议编码：
 * line 1:{key}
 * line 2:{uri}
 * line 3:{data}
 * */
public final class SocketMessage {
    /**
     * 消息key
     * */
    public String key;
    /**
     * 资源描述
     * */
    public String resourceDescriptor;
    /**
     * 消息内容
     * */
    public byte[] content;
    /**
     * 消息编码
     * */
    public Charset charset = StandardCharsets.UTF_8;


    protected SocketMessage(){

    }

    /**
     * 打包
     * */
    public static SocketMessage wrap(String resourceDescriptor, String str) {
        return wrap(resourceDescriptor, str.getBytes(Charset.defaultCharset()));
    }

    /**
     * 打包
     * */
    public static SocketMessage wrap(String resourceDescriptor, byte[] bytes) {
        return wrap(UUID.randomUUID().toString(), resourceDescriptor, bytes);
    }

    /**
     * 打包
     * */
    public static SocketMessage wrap(String key ,String resourceDescriptor, byte[] bytes) {
        SocketMessage msg = new SocketMessage();

        msg.key = key;
        msg.resourceDescriptor = resourceDescriptor;
        msg.content = bytes;

        return msg;
    }

    /**
     * 解码
     * */
    public static SocketMessage decode(ByteBuffer buffer) {
        //1.解码key and uri
        ByteBuffer sb = ByteBuffer.allocate(Math.min(256, buffer.limit()));

        String key = readStr(buffer, sb);
        if (key == null) {
            return null;
        }

        String uri = readStr(buffer, sb);
        if (uri == null) {
            return null;
        }

        //2.解码body
        byte[] bytes = new byte[buffer.limit() - buffer.position()];
        buffer.get(bytes, 0, buffer.limit() - buffer.position());

        SocketMessage msg = new SocketMessage();
        msg.key = key;
        msg.resourceDescriptor = uri;
        msg.content = bytes;

        return msg;
    }

    /**
     * 编码
     */
    public ByteBuffer encode() {
        byte[] keyB = key.getBytes(charset);
        byte[] rdB = resourceDescriptor.getBytes(charset);

        int len = keyB.length + rdB.length + content.length + 2 * 2;

        ByteBuffer buffer = ByteBuffer.allocate(len);
        buffer.put(keyB);
        buffer.putChar('\n');

        buffer.put(rdB);
        buffer.putChar('\n');

        buffer.put(content);

        return buffer;
    }

    @Override
    public String toString() {
        if (content == null) {
            return null;
        } else {
            return new String(content, charset);
        }
    }

    private static String readStr(ByteBuffer buffer, ByteBuffer sb) {
        sb.position(0);

        while (true) {
            byte c = buffer.get();

            if (c == 10) { //10:'\n'
                break;
            } else if (c > 32 || c < 0) { //32:' '
                sb.put(c);
            }

            //url 太长了
            if (256 < sb.position()) {
                return null;
            }
        }

        sb.flip();
        if (sb.limit() < 1) {
            return null;
        }

        return new String(sb.array(), 0, sb.limit());
    }
}
