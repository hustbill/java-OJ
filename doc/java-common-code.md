
下面是20个非常有用的Java程序片段，希望能对你有用。

```java
1. 字符串有整型的相互转换

1
2
3
  
String a = String.valueOf(2);   //integer to numeric string  
int i = Integer.parseInt(a); //numeric string to an int 

2. 向文件末尾添加内容

1
2
3
4
5
6
7
8
9
10
11
12
  
BufferedWriter out = null;  
try {  
    out = new BufferedWriter(new FileWriter(”filename”, true));  
    out.write(”aString”);  
} catch (IOException e) {  
    // error processing code  
} finally {  
    if (out != null) {  
        out.close();  
    }  
} 
3. 得到当前方法的名字

1
String methodName = Thread.currentThread().getStackTrace()[1].getMethodName(); 
4. 转字符串到日期

1
2
  
java.util.Date = java.text.DateFormat.getDateInstance().parse(date String); 
或者是：

1
2
3
  
SimpleDateFormat format = new SimpleDateFormat( "dd.MM.yyyy" );  
Date date = format.parse( myString ); 
5. 使用JDBC链接Oracle

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
public class OracleJdbcTest  
{  
    String driverClass = "oracle.jdbc.driver.OracleDriver";  
  
    Connection con;  
  
    public void init(FileInputStream fs) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException  
    {  
        Properties props = new Properties();  
        props.load(fs);  
        String url = props.getProperty("db.url");  
        String userName = props.getProperty("db.user");  
        String password = props.getProperty("db.password");  
        Class.forName(driverClass);  
  
        con=DriverManager.getConnection(url, userName, password);  
    }  
  
    public void fetch() throws SQLException, IOException  
    {  
        PreparedStatement ps = con.prepareStatement("select SYSDATE from dual");  
        ResultSet rs = ps.executeQuery();  
  
        while (rs.next())  
        {  
            // do the thing you do  
        }  
        rs.close();  
        ps.close();  
    }  
  
    public static void main(String[] args)  
    {  
        OracleJdbcTest test = new OracleJdbcTest();  
        test.init();  
        test.fetch();  
    }  
} 
6. 把 Java util.Date 转成 sql.Date

1
2
java.util.Date utilDate = new java.util.Date();  
java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
7. 使用NIO进行快速的文件拷贝
 

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
public static void fileCopy( File in, File out )  
            throws IOException  
    {  
        FileChannel inChannel = new FileInputStream( in ).getChannel();  
        FileChannel outChannel = new FileOutputStream( out ).getChannel();  
        try 
        {  
//          inChannel.transferTo(0, inChannel.size(), outChannel);      // original -- apparently has trouble copying large files on Windows  
  
            // magic number for Windows, 64Mb - 32Kb)  
            int maxCount = (64 * 1024 * 1024) - (32 * 1024);  
            long size = inChannel.size();  
            long position = 0;  
            while ( position < size )  
            {  
               position += inChannel.transferTo( position, maxCount, outChannel );  
            }  
        }  
        finally 
        {  
            if ( inChannel != null )  
            {  
               inChannel.close();  
            }  
            if ( outChannel != null )  
            {  
                outChannel.close();  
            }  
        }  
    } 
8. 创建图片的缩略图

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
private void createThumbnail(String filename, int thumbWidth, int thumbHeight, int quality, String outFilename)  
        throws InterruptedException, FileNotFoundException, IOException  
    {  
        // load image from filename  
        Image image = Toolkit.getDefaultToolkit().getImage(filename);  
        MediaTracker mediaTracker = new MediaTracker(new Container());  
        mediaTracker.addImage(image, 0);  
        mediaTracker.waitForID(0);  
        // use this to test for errors at this point: System.out.println(mediaTracker.isErrorAny());  
  
        // determine thumbnail size from WIDTH and HEIGHT  
        double thumbRatio = (double)thumbWidth / (double)thumbHeight;  
        int imageWidth = image.getWidth(null);  
        int imageHeight = image.getHeight(null);  
        double imageRatio = (double)imageWidth / (double)imageHeight;  
        if (thumbRatio < imageRatio) {  
            thumbHeight = (int)(thumbWidth / imageRatio);  
        } else {  
            thumbWidth = (int)(thumbHeight * imageRatio);  
        }  
  
        // draw original image to thumbnail image object and  
        // scale it to the new size on-the-fly  
        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);  
        Graphics2D graphics2D = thumbImage.createGraphics();  
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);  
  
        // save thumbnail image to outFilename  
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFilename));  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);  
        quality = Math.max(0, Math.min(quality, 100));  
        param.setQuality((float)quality / 100.0f, false);  
        encoder.setJPEGEncodeParam(param);  
        encoder.encode(thumbImage);  
        out.close();  
    } 
9. 创建 JSON 格式的数据

请先阅读这篇文章 了解一些细节，
并下面这个JAR 文件：json-rpc-1.0.jar (75 kb)

1
2
3
4
5
6
7
8
9
import org.json.JSONObject;  
...  
...  
JSONObject json = new JSONObject();  
json.put("city", "Mumbai");  
json.put("country", "India");  
...  
String output = json.toString();  
... 
10. 使用iText JAR生成PDF

阅读这篇文章 了解更多细节

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.OutputStream;  
import java.util.Date;  
  
import com.lowagie.text.Document;  
import com.lowagie.text.Paragraph;  
import com.lowagie.text.pdf.PdfWriter;  
  
public class GeneratePDF {  
  
    public static void main(String[] args) {  
        try {  
            OutputStream file = new FileOutputStream(new File("C:\\Test.pdf"));  
  
            Document document = new Document();  
            PdfWriter.getInstance(document, file);  
            document.open();  
            document.add(new Paragraph("Hello Kiran"));  
            document.add(new Paragraph(new Date().toString()));  
  
            document.close();  
            file.close();  
  
        } catch (Exception e) {  
  
            e.printStackTrace();  
        }  
    }  
} 

## 11. HTTP 代理设置
System.getProperties().put("http.proxyHost", "someProxyURL");  
System.getProperties().put("http.proxyPort", "someProxyPort");  
System.getProperties().put("http.proxyUser", "someUserName");  
System.getProperties().put("http.proxyPassword", "somePassword"); 

## 12. 单实例Singleton 示例


  
public class SimpleSingleton {  
    private static SimpleSingleton singleInstance =  new SimpleSingleton();  
  
    //Marking default constructor private  
    //to avoid direct instantiation.  
    private SimpleSingleton() {  
    }  
  
    //Get instance for class SimpleSingleton  
    public static SimpleSingleton getInstance() {  
  
        return singleInstance;  
    }  
} 
另一种实现

public enum SimpleSingleton {  
    INSTANCE;  
    public void doSomething() {  
    }  
}  
  
//Call the method from Singleton:  
SimpleSingleton.INSTANCE.doSomething(); 
13. 抓屏程序


import java.awt.Dimension;  
import java.awt.Rectangle;  
import java.awt.Robot;  
import java.awt.Toolkit;  
import java.awt.image.BufferedImage;  
import javax.imageio.ImageIO;  
import java.io.File;  
  
...  
  
public void captureScreen(String fileName) throws Exception {  
  
   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
   Rectangle screenRectangle = new Rectangle(screenSize);  
   Robot robot = new Robot();  
   BufferedImage image = robot.createScreenCapture(screenRectangle);  
   ImageIO.write(image, "png", new File(fileName));  
  
}  
... 
 

 

14. 列出文件和目录

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
File dir = new File("directoryName");  
  String[] children = dir.list();  
  if (children == null) {  
      // Either dir does not exist or is not a directory  
  } else {  
      for (int i=0; i < children.length; i++) {  
          // Get filename of file or directory  
          String filename = children[i];  
      }  
  }  
  
  // It is also possible to filter the list of returned files.  
  // This example does not return any files that start with `.'.  
  FilenameFilter filter = new FilenameFilter() {  
      public boolean accept(File dir, String name) {  
          return !name.startsWith(".");  
      }  
  };  
  children = dir.list(filter);  
  
  // The list of files can also be retrieved as File objects  
  File[] files = dir.listFiles();  
  
  // This filter only returns directories  
  FileFilter fileFilter = new FileFilter() {  
      public boolean accept(File file) {  
          return file.isDirectory();  
      }  
  };  
  files = dir.listFiles(fileFilter); 
15. 创建ZIP和JAR文件

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
  
import java.util.zip.*;  
import java.io.*;  
  
public class ZipIt {  
    public static void main(String args[]) throws IOException {  
        if (args.length < 2) {  
            System.err.println("usage: java ZipIt Zip.zip file1 file2 file3");  
            System.exit(-1);  
        }  
        File zipFile = new File(args[0]);  
        if (zipFile.exists()) {  
            System.err.println("Zip file already exists, please try another");  
            System.exit(-2);  
        }  
        FileOutputStream fos = new FileOutputStream(zipFile);  
        ZipOutputStream zos = new ZipOutputStream(fos);  
        int bytesRead;  
        byte[] buffer = new byte[1024];  
        CRC32 crc = new CRC32();  
        for (int i=1, n=args.length; i < n; i++) {  
            String name = args[i];  
            File file = new File(name);  
            if (!file.exists()) {  
                System.err.println("Skipping: " + name);  
                continue;  
            }  
            BufferedInputStream bis = new BufferedInputStream(  
                new FileInputStream(file));  
            crc.reset();  
            while ((bytesRead = bis.read(buffer)) != -1) {  
                crc.update(buffer, 0, bytesRead);  
            }  
            bis.close();  
            // Reset to beginning of input stream  
            bis = new BufferedInputStream(  
                new FileInputStream(file));  
            ZipEntry entry = new ZipEntry(name);  
            entry.setMethod(ZipEntry.STORED);  
            entry.setCompressedSize(file.length());  
            entry.setSize(file.length());  
            entry.setCrc(crc.getValue());  
            zos.putNextEntry(entry);  
            while ((bytesRead = bis.read(buffer)) != -1) {  
                zos.write(buffer, 0, bytesRead);  
            }  
            bis.close();  
        }  
        zos.close();  
    }  
} 
16. 解析/读取XML 文件

XML文件
 

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
<?xml version="1.0"?> 
<students> 
    <student> 
        <name>John</name> 
        <grade>B</grade> 
        <age>12</age> 
    </student> 
    <student> 
        <name>Mary</name> 
        <grade>A</grade> 
        <age>11</age> 
    </student> 
    <student> 
        <name>Simon</name> 
        <grade>A</grade> 
        <age>18</age> 
    </student> 
</students> 
Java代码


  
package net.viralpatel.java.xmlparser;  
  
import java.io.File;  
import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
  
import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.Node;  
import org.w3c.dom.NodeList;  
  
public class XMLParser {  
  
    public void getAllUserNames(String fileName) {  
        try {  
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
            DocumentBuilder db = dbf.newDocumentBuilder();  
            File file = new File(fileName);  
            if (file.exists()) {  
                Document doc = db.parse(file);  
                Element docEle = doc.getDocumentElement();  
  
                // Print root element of the document  
                System.out.println("Root element of the document: " 
                        + docEle.getNodeName());  
  
                NodeList studentList = docEle.getElementsByTagName("student");  
  
                // Print total student elements in document  
                System.out  
                        .println("Total students: " + studentList.getLength());  
  
                if (studentList != null && studentList.getLength() > 0) {  
                    for (int i = 0; i < studentList.getLength(); i++) {  
  
                        Node node = studentList.item(i);  
  
                        if (node.getNodeType() == Node.ELEMENT_NODE) {  
  
                            System.out  
                                    .println("=====================");  
  
                            Element e = (Element) node;  
                            NodeList nodeList = e.getElementsByTagName("name");  
                            System.out.println("Name: " 
                                    + nodeList.item(0).getChildNodes().item(0)  
                                            .getNodeValue());  
  
                            nodeList = e.getElementsByTagName("grade");  
                            System.out.println("Grade: " 
                                    + nodeList.item(0).getChildNodes().item(0)  
                                            .getNodeValue());  
  
                            nodeList = e.getElementsByTagName("age");  
                            System.out.println("Age: " 
                                    + nodeList.item(0).getChildNodes().item(0)  
                                            .getNodeValue());  
                        }  
                    }  
                } else {  
                    System.exit(1);  
                }  
            }  
        } catch (Exception e) {  
            System.out.println(e);  
        }  
    }  
    public static void main(String[] args) {  
  
        XMLParser parser = new XMLParser();  
        parser.getAllUserNames("c:\\test.xml");  
    }  
} 
17. 把 Array 转换成 Map 

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
  
import java.util.Map;  
import org.apache.commons.lang.ArrayUtils;  
  
public class Main {  
  
  public static void main(String[] args) {  
    String[][] countries = { { "United States", "New York" }, { "United Kingdom", "London" },  
        { "Netherland", "Amsterdam" }, { "Japan", "Tokyo" }, { "France", "Paris" } };  
  
    Map countryCapitals = ArrayUtils.toMap(countries);  
  
    System.out.println("Capital of Japan is " + countryCapitals.get("Japan"));  
    System.out.println("Capital of France is " + countryCapitals.get("France"));  
  }  
} 
18. 发送邮件

import javax.mail.*;  
import javax.mail.internet.*;  
import java.util.*;  
  
public void postMail( String recipients[ ], String subject, String message , String from) throws MessagingException  
{  
    boolean debug = false;  
  
     //Set the host smtp address  
     Properties props = new Properties();  
     props.put("mail.smtp.host", "smtp.example.com");  
  
    // create some properties and get the default Session  
    Session session = Session.getDefaultInstance(props, null);  
    session.setDebug(debug);  
  
    // create a message  
    Message msg = new MimeMessage(session);  
  
    // set the from and to address  
    InternetAddress addressFrom = new InternetAddress(from);  
    msg.setFrom(addressFrom);  
  
    InternetAddress[] addressTo = new InternetAddress[recipients.length];  
    for (int i = 0; i < recipients.length; i++)  
    {  
        addressTo[i] = new InternetAddress(recipients[i]);  
    }  
    msg.setRecipients(Message.RecipientType.TO, addressTo);  
  
    // Optional : You can also set your custom headers in the Email if you Want  
    msg.addHeader("MyHeaderName", "myHeaderValue");  
  
    // Setting the Subject and Content Type  
    msg.setSubject(subject);  
    msg.setContent(message, "text/plain");  
    Transport.send(msg);  
} 
19. 发送代数据的HTTP 请求

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
  
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.net.URL;  
  
public class Main {  
    public static void main(String[] args)  {  
        try {  
            URL my_url = new URL("http://coolshell.cn/");  
            BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));  
            String strTemp = "";  
            while(null != (strTemp = br.readLine())){  
            System.out.println(strTemp);  
        }  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
    }  
}
20. 改变数组的大小

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
  
/** 
* Reallocates an array with a new size, and copies the contents 
* of the old array to the new array. 
* @param oldArray  the old array, to be reallocated. 
* @param newSize   the new array size. 
* @return          A new array with the same contents. 
*/ 
private static Object resizeArray (Object oldArray, int newSize) {  
   int oldSize = java.lang.reflect.Array.getLength(oldArray);  
   Class elementType = oldArray.getClass().getComponentType();  
   Object newArray = java.lang.reflect.Array.newInstance(  
         elementType,newSize);  
   int preserveLength = Math.min(oldSize,newSize);  
   if (preserveLength > 0)  
      System.arraycopy (oldArray,0,newArray,0,preserveLength);  
   return newArray;  
}  
  
// Test routine for resizeArray().  
public static void main (String[] args) {  
   int[] a = {1,2,3};  
   a = (int[])resizeArray(a,5);  
   a[3] = 4;  
   a[4] = 5;  
   for (int i=0; i<a.length; i++)  
      System.out.println (a[i]);  
}
```
