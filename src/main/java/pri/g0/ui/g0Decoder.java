/**
 * THIS IS A DIY  WEBSHELL DECODER PROBLEM
 *
 * @author ga0weI
 * @version 1.0
 * @since 2023-05-19
 */

package pri.g0.ui;

import pri.g0.utils.MyUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class g0Decoder {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel normal_decode;
    private JTextArea textArea_ciphertext;
    private JTextArea textArea_plain;
    private JCheckBox DURLCheckBox;
    private JCheckBox DBase64CheckBox;
    private JCheckBox DAESECBCheckBox;
    private JCheckBox UGzipCheckBox;
    private JCheckBox unserializeCheckBox;
    private JButton button_decode;
    private JTextField textField_iv;
    private JCheckBox DAESCBCCheckBox;
    private JTextField textField_key;
    private JTextField textField_filename;
    private JTextField textField_cloumname;
    private JTextField textField_paramname;
    private JTextField textField_fileddnotice;
    private JButton button_clear;
    private JButton button_filedecode;
    private JCheckBox PKCS5paddingCheckBox;
    private JCheckBox nopaddingCheckBox;
    private JTextField textField_notice;
    private JButton copy_button;
    private JCheckBox del16_checkBox;
    private JPanel file_decode;
    private JPanel problem_jPanel;
    private JPanel other_jPanel;
    private JTextArea info_textArea;
    private JTextArea problem_textArea;
    //日志
    Logger logger = Logger.getLogger("pri.g0.ui.g0Decoder");
    //工具类
    MyUtils myUtils  = new MyUtils();
    ExecutorService executor = Executors.newFixedThreadPool(30);//后续用于反编译的线程池


    public g0Decoder() {

        textArea_plain.setLineWrap(true);
        textArea_ciphertext.setLineWrap(true);
        textField_notice.setEnabled(false);

        /**
         * 字体初始化
         */
        Font myfont = new Font("SimHei", Font.PLAIN, 22);
        Component[] components = normal_decode.getComponents();
        for(Component component : components) {
            if(component instanceof JCheckBox) {
                JCheckBox checkbox = (JCheckBox) component;
                checkbox.setFont(myfont);
            }
            if(component instanceof JButton){
                JButton jButton = (JButton) component;
                jButton.setFont(myfont);
            }
        }
        Component[] components1 = file_decode.getComponents();
        for(Component component : components1) {
            if(component instanceof JLabel) {
                JLabel jlabel = (JLabel) component;
                jlabel.setFont(myfont);
            }
            if(component instanceof JButton){
                JButton jButton = (JButton) component;
                jButton.setFont(myfont);
            }
        }
        /**
         * 工具介绍初始化
         */
        info_textArea.setFont(myfont);
        info_textArea.setLineWrap(true);
        info_textArea.setText("\n\n本工具主要用于webshell流量解密场景：\n" +
                "\n\t1、单条流量解密" +
                "\n\t2、批量流量解密" +
                "\n\t3、对类冰蝎以class字节码作为通信流量的webshell进行了自动反编译还原类" +
                "\n\t4、解密过程自定义，兼容常见混淆流量webshell，如Godzilla 、 冰蝎 等部分魔改webshell" +
                "\n\n 结果：" +
                "\n\t1、批量解密的结果输出到result.txt文件中" +
                "\n\t2、批量解密的结果中存在类冰蝎webshell传输流量为字节码的时候,还原的class文件以及反编译的java文件存放到/res文件加中" +
                "\n\n" +
                "                                                                        by MSS ga0weI" );
        info_textArea.setEnabled(false);
        /**
         * 常见webshell解密方法 初始化
         */
        problem_textArea.setEnabled(false);
        problem_textArea.setFont(myfont);
        problem_textArea.setLineWrap(true);
        problem_textArea.setText("\n\n 常见webshell 自定义解密的使用\n\n\n " +
                "冰蝎3：" +
                "\n\tDBase64——>DAES/ECB——>Nopadding\n\n " +
                "Godzilla:" +
                "\n\t首次连接流量或者加载新类的流量：DURL——>DBase64——>DAES/ECB——>Nopadding"+
                "\n\t正常连接流量:DURL——>DBase64——>DAES/ECB——>Nopadding——>gzip——>Unserialize" +
                "\n\t响应流量：del_16——>DBase64——>DAES/ECB——>Nopadding——>gzip");

        /**
         * 单条解密
         */
        button_decode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textArea_plain.setText("");

                String cipher = textArea_ciphertext.getText().trim().replaceAll("[\\r\\n]", "");;
//                System.out.println("获取到的密文："+cipher);
                if (del16_checkBox.isSelected()){
                    cipher = cipher.substring(16,cipher.length()-16);
//                    System.out.println("del16之后："+cipher);
                }
                byte [] byte_cipher = cipher.getBytes(StandardCharsets.UTF_8);
                if (DURLCheckBox.isSelected())
                {
                    String cipher_du = new String(byte_cipher);
                    byte_cipher = URLDecoder.decode(cipher_du).getBytes(StandardCharsets.UTF_8);
                }
                if (DBase64CheckBox.isSelected()){
                     try {
                         byte_cipher = Base64.getDecoder().decode(new String(byte_cipher));

                     }catch (Exception be){
                         textField_notice.setText("base64解码失败！！");
                         logger.log(Level.SEVERE,null,be);
                         return;
                     }
                }
                if (DAESECBCheckBox.isSelected() || DAESCBCCheckBox.isSelected()){
                    if(DAESECBCheckBox.isSelected() && DAESCBCCheckBox.isSelected()){
                        textField_notice.setText("选择使用AES的模式，请不要两个都选！！！");
                        return;
                    }
                    if(PKCS5paddingCheckBox.isSelected() && nopaddingCheckBox.isSelected() ){
                        textField_notice.setText("选择使用AES解密请选择对应的填充模式，不要两个都选！！！");
                        return;
                    }
                    if(!(PKCS5paddingCheckBox.isSelected() || nopaddingCheckBox.isSelected())){
                        textField_notice.setText("选择使用AES解密的情况下请选择对应的填充模式！！！");
                        return;
                    }
                    if (textField_key.getText() .equals("")){
                        textField_notice.setText("选择使用AES解密的情况下请输入密钥！！！");
                        return;
                    }
                    String key = textField_key.getText().trim();
                    String padding = "Nopadding";
                    if (PKCS5paddingCheckBox.isSelected()){
                        padding = "PKCS5padding";
                    }
                    if (DAESECBCheckBox.isSelected()){
                        try {
                            /*
                                修补下ecb nopadding，java里面的ecbnopadding对长度还是有要求，要使用0填充
                                （很多解码工具不需要做这一步，但更具原理来看，ECB是块加密算法，所以最小加密单元就是16字节128位）
                             */
//                            System.out.println("修补前"+myUtils.bytesTohexString(byte_cipher));
                            int yushu  = byte_cipher.length%16;
                            if (yushu!=0 && padding.equals("Nopadding")){
//                                System.out.println("余数是："+yushu+"\n 新长度："+(byte_cipher.length+16-yushu));
                                byte_cipher = Arrays.copyOf(byte_cipher,byte_cipher.length+16-yushu);
                            }

//                            System.out.println("修补后"+myUtils.bytesTohexString(byte_cipher));

                            byte_cipher = myUtils.myAESECB(byte_cipher,key,padding,2);
                        } catch (Exception ex) {
                            textField_notice.setText("AES/ECB解密出现问题，密文长度为："+byte_cipher.length+",请核对密文长度以及密钥！");
                            logger.log(Level.SEVERE,"解密失败",ex);
                            return;
                        }

                    }else {
                        if(textField_iv.getText().equals("")){
                            textField_notice.setText("选择CBC模式请填写iv初始向量");
                            return;
                        }
                        try {
                            String iv = textField_iv.getText().trim();
                            byte_cipher = myUtils.myAESCBC(byte_cipher,key,padding,2,iv);
                        } catch (Exception ex) {
                            textField_notice.setText("AES/CBC解密出现问题，密文长度为："+byte_cipher.length+",请核对密文长度以及密钥！");
                            logger.log(Level.SEVERE,null,ex);
                            return;
                        }

                    }
                }
                if (UGzipCheckBox.isSelected()){
                    try {
                        System.out.println("gzip前"+myUtils.bytesTohexString(byte_cipher));
                        byte_cipher = myUtils.uncompress(byte_cipher);
                    } catch (IOException ex) {
                        textField_notice.setText("Gzip格式解压解压出现错误，请求核对gzip解压处！");
                        logger.log(Level.SEVERE,null,ex);
                        return;
                    }
                }
                if (unserializeCheckBox.isSelected()){
                    byte_cipher = myUtils.unSerialize(byte_cipher).getBytes(StandardCharsets.UTF_8);
                }
                textArea_plain.setText(new String(byte_cipher));
                textField_notice.setText("流量还原成功！");
                logger.log(Level.INFO,null,"流量还原成功！" );
                e.consume();
                super.mouseClicked(e);
            }
        });

        /**
         * 单条解密的clear
         */
        button_clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textArea_plain.setText("");
                textArea_ciphertext.setText("");
                super.mouseClicked(e);
            }
        });

        /**
         * file文件批量解密
         */
        button_filedecode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField_fileddnotice.setText("");

                //遍历删除res里面之前的class文件
                File folder = new File("./res");
                for (File subFile : folder.listFiles()) {
                    if (subFile.getName().endsWith(".class")||subFile.getName().endsWith(".java")){
                        subFile.delete();
                    }
                }

                textField_fileddnotice.setText("");
                //检测配置文件
                File file = new File("./res/autorecomplie.txt");

                // 判断文件是否存在
                if (!file.exists()) {
                    textField_fileddnotice.setText("没有找到res文件夹下的autorecomplie.txt文件");

                }
                textField_fileddnotice.setText("");
                String cloumname = textField_cloumname.getText().trim();
                String paramname = textField_paramname.getText().trim();
                String filename = textField_filename.getText().trim();
                if (filename.equals("")) {
                    String s = textField_fileddnotice.getText();
                    textField_fileddnotice.setText(s+"请填写待解密数据xlxs文件名称！");
                    return;
                }
                //调用python 冲
                try {
                    String path = System.getProperty("user.dir");
                    String system = System.getProperty("os.name");
                    String cmd;
                    if (!cloumname.equals("")) {

                        if (system.startsWith("Windows")) {
                            cmd="C:/Windows/System32/cmd.exe /c python ./getdata.py " + filename + " " + cloumname;
                        }else {
                            cmd="python ./getdata.py " + filename + " " + cloumname;
                        }
                        Process process = Runtime.getRuntime().exec(cmd, null, new File(path));
                        process.waitFor();
                    }else {
                        if (system.startsWith("Windows")) {
                            cmd="C:/Windows/System32/cmd.exe /c python ./getdata.py " + filename + " data";
                        }else {
                            cmd="python ./getdata.py " + filename + " data";
                        }
                        Process process = Runtime.getRuntime().exec(cmd, null, new File(path));
                        process.waitFor();
                    }
                    String s = textField_fileddnotice.getText();
                    textField_fileddnotice.setText(s+"利用getdata.py 成功提取datas");
                    Logger.getGlobal().info("利用getdata.py 成功提取datas");
                }catch ( Exception x){
                    String s = textField_fileddnotice.getText();
                    textField_fileddnotice.setText(s+"在调用getdata.py过程中出现问题:请检查输入的日志文件格式是否正确，并确认其存在\""+cloumname+"\"列，否则请将请求体内容所在列修改为该列名"+x);
                    Logger.getGlobal().severe("在调用getdata.py过程中出现问题:请检查输入的日志文件格式是否正确，并确认其存在\"数据(data)\"列，否则请将请求体内容所在列修改为该列名"+x);
                }
                //调解码方法冲

                try {
                    try(BufferedReader bufferedReader = new BufferedReader(new FileReader("data.txt"));
                        FileOutputStream fileOutputStream = new FileOutputStream("result.txt"))
                    {
                        String line;
                        byte[] result;
                        String result_hex ="";
                        line = bufferedReader.readLine();
                        int i= 1;//文件命名计数
                        while (line != null) {
                            try{
                                result = run(line,paramname);

                                result_hex = myUtils.bytesTohexString(result);
                                if (result_hex.startsWith("cafebabe")){
                                    String classfilename = "myclass"+Integer.toString(i)+".class";
                                    String javafilename = "myclass"+Integer.toString(i)+".java";
                                    try(FileOutputStream fileOut = new FileOutputStream("./res/"+classfilename)){
                                        fileOut.write(result);
                                        System.out.println("___________________________________________________写class");
                                    }
                                    try {
                                        String pathx = System.getProperty("user.dir");
                                        String system = System.getProperty("os.name");
                                        String cmd;
                                        if (system.startsWith("Windows")) {
                                            cmd="C:/Windows/System32/cmd.exe /c java -jar .\\cfr-0.152.jar .\\res\\" + classfilename + " >.\\res\\" + javafilename;
                                        }else {
                                            cmd = "java -jar .\\cfr-0.152.jar .\\res\\" + classfilename + " >.\\res\\" + javafilename;
                                        }
                                      //使用线程池反编译
                                        Runnable task = new Runnable() {
                                            public void run() {
                                                try {
                                                    Runtime.getRuntime().exec(cmd,null,new File(pathx));// 线程执行的代码
                                                } catch (IOException ex) {
                                                    ex.printStackTrace();
                                                }
                                            }
                                        };
                                        executor.execute(task);
//                                        单线程阻塞反编译
//                                        Process processfanbianyi = Runtime.getRuntime().exec(cmd,null,new File(pathx));
//                                        processfanbianyi.waitFor();

//                                        BufferedReader reader = new BufferedReader(new InputStreamReader(processfanbianyi.getErrorStream()));
//                                        String l= " ";
//                                        while ((l = reader.readLine())!=null){
//                                            System.out.println(l);
//                                        }

                                    }catch ( Exception xxx)
                                    {
                                        //反编译出现问题的时候记录下，比如字节码文件反编译失败
                                        try(FileWriter fileWriter = new FileWriter("./res/autorecomplie.txt",true)){
                                            fileWriter.write((classfilename+"反编译失败"));
                                        }catch (Exception xxxx){
                                            continue;
                                        }
                                    }
                                    i= i+1;
                                    }
                                    fileOutputStream.write(result);
                                    fileOutputStream.write("\n".getBytes(StandardCharsets.UTF_8));

                                }catch (Exception xx){
                                    line = "failed_________________________________________________________________________\r\n";
                                    fileOutputStream.write(line.getBytes());
                                }
                            line = bufferedReader.readLine();
                        }
                    }
                    String s = textField_fileddnotice.getText();
                    textField_fileddnotice.setText(s+"批量解码成功，输出result.txt，如有class文件则dump到res文集夹下，并自动反编译成java文件");
                    Logger.getGlobal().info("批量解码成功，输出result.txt");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                super.mouseClicked(e);
            }
        });
        copy_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                StringSelection stringSelection = new StringSelection(textArea_plain.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                textField_notice.setText("复制成功！");
                super.mouseClicked(e);
            }
        });
    }

    private byte[] run(String line,  String paramname) {

        if (!paramname.equals(""))
        {
            String [] lines = line.split("&");
            for (String s:lines
                 ) {
                if (s.startsWith(paramname+"=")){
                    line = s.substring(paramname.length()+1,s.length());
                }
            }
        }
        System.out.println("获取到的密文："+line);

        if (del16_checkBox.isSelected()){
            line = line.substring(16,line.length()-16);
            System.out.println("del16之后："+line);
        }
        byte [] byte_cipher = line.getBytes(StandardCharsets.UTF_8);
        if (DURLCheckBox.isSelected())
        {
            String cipher_du = new String(byte_cipher);
            byte_cipher = URLDecoder.decode(cipher_du).getBytes(StandardCharsets.UTF_8);
        }
        if (DBase64CheckBox.isSelected()){
            byte_cipher = Base64.getDecoder().decode(new String(byte_cipher));
        }
        if (DAESECBCheckBox.isSelected() || DAESCBCCheckBox.isSelected()){
            if(DAESECBCheckBox.isSelected() && DAESCBCCheckBox.isSelected()){
                return "解密失败\n".getBytes(StandardCharsets.UTF_8);
            }
            if(PKCS5paddingCheckBox.isSelected() && nopaddingCheckBox.isSelected() ){
                return "解密失败\n".getBytes(StandardCharsets.UTF_8);
            }
            if(!(PKCS5paddingCheckBox.isSelected() || nopaddingCheckBox.isSelected())){
                return "解密失败\n".getBytes(StandardCharsets.UTF_8);
            }
            if (textField_key.getText() .equals("")){

            }
            String key = textField_key.getText().trim();
            String padding = "Nopadding";
            if (PKCS5paddingCheckBox.isSelected()){
                padding = "PKCS5padding";
            }
            if (DAESECBCheckBox.isSelected()){
                try {

                    /*
                      修补下ecb nopadding，java里面的ecbnopadding对长度还是有要求，要使用0填充
                      （很多解码工具不需要做这一步，但更具原理来看，ECB是块加密算法，所以最小加密单元就是16字节128位）
                    */
//                    System.out.println("修补前"+myUtils.bytesTohexString(byte_cipher));
                    int yushu  = byte_cipher.length%16;
                    if (yushu!=0 && padding.equals("Nopadding")){
//                      System.out.println("余数是："+yushu+"\n 新长度："+(byte_cipher.length+16-yushu));
                        byte_cipher = Arrays.copyOf(byte_cipher,byte_cipher.length+16-yushu);
                    }
//                    System.out.println("修补后"+myUtils.bytesTohexString(byte_cipher));
                    byte_cipher = myUtils.myAESECB(byte_cipher,key,padding,2);
                } catch (Exception ex) {
                    return "解密失败\n".getBytes(StandardCharsets.UTF_8);
                }

            }else {
                try {
                    String iv = textField_iv.getText().trim();
                    byte_cipher = myUtils.myAESCBC(byte_cipher,key,padding,2,iv);
                } catch (Exception ex) {
                    return "解密失败\n".getBytes(StandardCharsets.UTF_8);
                }

            }
        }
        if (UGzipCheckBox.isSelected()){
            try {
                byte_cipher = myUtils.uncompress(byte_cipher);
            } catch (IOException ex) {
                return "解密失败\n".getBytes(StandardCharsets.UTF_8);
            }
        }
        if (unserializeCheckBox.isSelected()){
            byte_cipher = myUtils.unSerialize(byte_cipher).getBytes(StandardCharsets.UTF_8);
        }
        return byte_cipher;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MSS Webshell Decoder V1  /by ga0weI");
        frame.setContentPane(new g0Decoder().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1000, 800);
        frame.setResizable(false);
    }
}
