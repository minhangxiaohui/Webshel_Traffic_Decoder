package pri.g0.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

/**
 * URL、Base64、AES、替换、哥斯拉序列化、Gzip
 */
public class MyUtils {
    public static final char[] hexCode = "0123456789abcdef".toCharArray();
    /**
     * 将base64编码的字节码还原成class文件
     * @param base64edString
     * @throws Exception
     */
    public void getBase64edClass(String base64edString) throws Exception{
        byte[] classcode = Base64.getDecoder().decode(base64edString);

        FileOutputStream fos = new FileOutputStream("orginal.class");
        fos.write(classcode);
        System.out.println("class还原成功，生成orginal.class");
    }


    /**
     * 计算md5前16位的大写
     * @param s
     * @return
     */
    public  String md5(String s) {
        String ret = null;

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            ret = (new BigInteger(1, m.digest())).toString(16).toUpperCase();
        } catch (Exception var3) {
        }
        return ret;
    }

    /**
     Gzip解压 用于哥斯拉请求流量解密
     */
    public  byte[] uncompress(byte[] bytes) throws ZipException, IOException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        GZIPInputStream ungzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = ungzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
    /**
     Godzilla客户端请求流量反格式化
     输入：Gzip解压之后的原始流量
     输出：哥斯拉构造的明文命令流量
     */
    public  String unSerialize(byte[] parameterByte) {
        StringBuilder result =new StringBuilder("");
        ByteArrayInputStream tStream = new ByteArrayInputStream(parameterByte);
        ByteArrayOutputStream tp = new ByteArrayOutputStream();
        String key = null;
        byte[] lenB = new byte[4];
//        Object var6 = null;

        try {
            ByteArrayInputStream inputStream = tStream;

            while(true) {
                while(true) {
                    byte t = (byte)inputStream.read();
                    if (t == -1) {
                        tp.close();
                        tStream.close();
                        inputStream.close();
                        return result.toString();
                    }

                    if (t == 2) {
                        key = tp.toString();
                        inputStream.read(lenB);//读后面四个字节
                        int len = (lenB[0] & 255) | ((lenB[1] & 255) << 8) | ((lenB[2] & 255) << 16) | ((lenB[3] & 255) << 24);//读取“2”后面四个字节里面的内容，获取data的长度
                        byte[] data = new byte[len];
                        int readOneLen = 0;

                        while((readOneLen += inputStream.read(data, readOneLen, data.length - readOneLen)) < data.length) {
                        }

                        //data存在包含class文件的情况 如：加载内存马，此时还原data中的class文件
                        String henxStrings = bytesTohexString(data);
//                        System.out.println("class字节码文件的16进制信息："+henxStrings);
                        if(henxStrings.startsWith("cafe"))
                        {
                            FileOutputStream fos = new FileOutputStream(key+"Eval.class");
                            //字节数组data转换成16进制然后正则匹配cafe 来获取class的字节码
                            fos.write(data);
                            System.out.println("检查到非首次请求流量里面有class文件信息，已还原"+key+"Eval.class文件");
                            fos.flush();
                            fos.close();
                            result.append(key+"="+"还原文件根目录下:"+key+"Eval.class"+"\n");
                            tp.reset();
                        }
                        else {
                            result.append(key+"="+new String(data)+"\n");
                            tp.reset();
                        }

                    } else {
                        tp.write(t);
                    }
                }
            }
        } catch (Exception var11) {
            var11.printStackTrace();
            return "格式错误";
        }
    }

    /**
     * 16进制string转字节数组
     * @param hexString
     * @throws Exception
     * @return out
     */
    public   byte[] hexStringTobytes(String hexString){
        char[] data= hexString.toCharArray();
        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;

    }
    /**
     * 字节数组转16进制string
     * @param data
     * @throws Exception
     * @return hexString
     */
    public  String bytesTohexString(byte[] data ){
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    /**
     * 将十六进制字符转换成一个整数
     *
     * @param ch
     *            十六进制char
     * @param index
     *            十六进制字符在字符数组中的位置
     * @return 一个整数
     * @throws RuntimeException
     *             当ch不是一个合法的十六进制字符时，抛出运行时异常
     */
    public  int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }
    public void FileforDxorFile(String filepath)throws Exception{
        Path p = Paths.get(filepath);
        byte[] bytes = Files.readAllBytes(p);
        StringBuilder stringBuilder = new StringBuilder();
        int i =0;
        for(i=0;i<bytes.length;i++){
            stringBuilder.append(bytes[i]^35);
        }
        try(FileOutputStream fis =new FileOutputStream("final.txt"))
        {fis.write(stringBuilder.toString().getBytes());
            System.out.println("异或解码后的文件生成：final.txt");
        }
    }
    /**
     * AES 加解密实现
     * @param data 带解码字节数组
     * @param key   解密使用密钥，字符串形式
     * @param padding   Nopadding、PKCS5Padding、ISO10126Padding  字符串形式
     * @param init  1 加密 ；2 解密
     * @param  iv  初始向量
     * @return 解密之后的字节码
     * @throws Exception 解密失败
     */
    public byte[] myAESCBC(byte[] data,String key, String padding, int init ,String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/"+padding);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        Key speckey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(init, speckey,ivParameterSpec);
        byte[] result = cipher.doFinal(data);
        return result;
    }

    /**
     * AES ECB加解密实现
     * @param data 带解码字节数组
     * @param key   解密使用密钥，字符串形式
     * @param padding   Nopadding、PKCS5Padding、ISO10126Padding  字符串形式
     * @param init  1 加密 ；2 解密
     * @return 解密之后的字节码
     * @throws Exception 解密失败
     */
    public byte[] myAESECB(byte[] data,String key, String padding, int init ) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/"+padding);
        Key speckey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(init, speckey);
        byte[] result = cipher.doFinal(data);
        return result;
    }

    public static void main(String[] args) throws  Exception{
//        MyUtils myUtils = new MyUtils();
//        String data = "HOAKx4VOUnBiAnRbxgIOyGWQUS2vH9hV7GYeYIIIKt9r3l9AYhmdyL8LEh/quFEWdCr9m36APwr/xzoSVFUH9bDMlq8HlOzbAlcUn6vMNnnjkYOJVqdTQLYis+0xzDf0MXFrgdHcG6nYX3/rXtxAePqIivarXmJPIv+oShSms2z2gjtug9mmKY7ZZ9ybiywQ+FMXxJ+eU650EKgu5gnLKwkHXmGsq9urHicsmLw6fA+X7ULCM4L5EW+S9AEAqTTWbk3iUNmwyB9DxkqLXuagb25Qd80SjfCdYaW0Elilb+AIA0z23tXIA2JHGxRdWvoR4yLci6H7vHQnG1PUtjS2yBXjGKlqy1iB9pr9i1yNEIfz/eodwOi4znVOVmaNAlT9fZF7JfYsSQ09oPqdd1mWmXmsWa0Vta0Glv746b8edVosIC+fxK08aPDgtJeqmUs+tEn1ljktA2lLn95EGIvQF3Vk8BVyEnvuDxB3rNMutUOtfQZ8uIwj6jtdiQE/h99g5TLzG2pBphJlKVr/gPe2L6r3x8tspKr4eixw38cMNrHYypvCkVI7VABIVqKeIeZEVxYGjeq5r5gZ3F1bblZtbJE9haLiBkW7oB5b0yjwiG1idwC760/TZt2oX81YPbplXfd6U09CjupRQSRModUMMWXZnjWXjbRd0eVZiYiqf+VS1mWp/P7PMuoJ2yUQbee8+Dzrgeh+7Zgr3jR0huZMYJQR6LoncLwFf0hK99nhgjvx3ctrSROkMB8XqJ9ZU5wrsIz6OhusTQXTZ9qDSoPY++Mb2+Lez1SPJuISg81gKxRSDosPufUs/Vvo+hvzCJf+cM+FoEf6ITnSazI51kCx6shX+Gd5aIfBJzI92lpFN2zP3v2zI6At9nW2o2LOHncevt4eS8Q31/b/H6BUPTSWwREoTqTFSmfRaQFojiICdompKb3wP6k7J7gWG6kqGdp4hKunpoyjFU5GW5rdSpK0K5jAJ8JywsySIMtaqv02xxx";
//        byte[] dataafterbase = Base64.getDecoder().decode(data);
//        String key = "16acacc05aafaf67";
//        String padding = "Nopadding";
//        int init = 2;
//        byte [] result = myUtils.myAESECB(dataafterbase,key,padding,init);
//        System.out.println(new String(result));
        System.out.println(System.getProperty("os.name"));

    }
}
