package com.definesys.readword.service;

import com.definesys.mpaas.common.http.Response;
import com.definesys.readword.dao.TestReadWordDAO;
import com.definesys.readword.pojo.TestReadWord;
import com.definesys.readword.utils.DateUtils;
import com.definesys.readword.utils.TraceUtils;
import com.sun.tools.javac.comp.Todo;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import java.util.UUID;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: zhengfei.tan
 * @since: 2019/8/20 22:20
 * @history: 1.2019/8/20 created by zhengfei.tan
 */
@Service
public class WordToBeanService {

    @Autowired
    private TestReadWordDAO dao;
    /**
     * @Copyright: Shanghai Definesys Company.All rights reserved.
     * @Description: 广西人才网
     * @author: zhengfei.tan
     * @since:  2019/8/20 22:24
     * @history: 1.2019/8/20 created by zhengfei.tan
     **/
    public Response toGXRCW(MultipartFile file){

        TestReadWord word = new TestReadWord();
        try {
            InputStream inputStream = file.getInputStream();
            WordExtractor wordExtractor = new WordExtractor(inputStream);
            String text = wordExtractor.getText();
            String[] paragraph = wordExtractor.getParagraphText();

            // 单位
            if (text.indexOf("投递职位")>0){
                word.setJob(text.substring(text.indexOf("投递职位")+ 5, text.indexOf("投递时间")).trim());
            }

            String no1 = paragraph[1];
            String[]  n1  = no1.split("\\s+");
            // 姓名
            word.setName(n1[0]);
            // 性别
            word.setSex(n1[1]);
            // 年龄
            //TODO 分割年龄
            word.setBirthday(n1[3]);

            String schoolInfo = text.substring(text.indexOf("就读学校：")+5);
            String[] info =   schoolInfo.trim().split("\\s+");

            // 学校
            word.setSchool(info[0]);
            // 毕业时间
            word.setGraduationDate(info[4]);
            // 专业
            word.setMajor(info[6]);
            // 学历
            word.setEducation(info[8]);

            dao.addTestReadWord(word);

            TraceUtils.print(word);

        } catch (IOException e) {
            e.printStackTrace();
            Response.error(file.getName()+"文件读取失败!");
        }
        return Response.ok();
    }

    /**
     * @Copyright: Shanghai Definesys Company.All rights reserved.
     * @Description: 智联招聘
     * @author: zhengfei.tan
     * @since:  2019/8/20 22:37
     * @history: 1.2019/8/20 created by zhengfei.tan
     **/
    public Response toZLZP(MultipartFile file){
        TestReadWord word = new TestReadWord();
        try {
            InputStream inputStream = file.getInputStream();
            WordExtractor wordExtractor = new WordExtractor(inputStream);
            String text = wordExtractor.getText();


            String[] paragraph = wordExtractor.getParagraphText();

            // 应聘职位
            word.setJob(text.substring(text.indexOf("应聘职位：")+5, text.indexOf("智联招聘")).trim());
            // 单位
            word.setUnit(text.substring(text.indexOf("应聘机构：")+5, text.indexOf("工作地点")).trim());

            String info = text.substring(text.indexOf("ID：")+3, text.indexOf("求职意向")).trim();
            String [] message = info.split("\\s+");
            // 姓名
            word.setName(message[1]);
            // 性别
            word.setSex(message[3]);
            // 出生年月
            word.setBirthday(message[5].substring( message[5].indexOf("(")+1, message[5].indexOf(")") ));
            // 籍贯
            word.setPlace(message[12].trim().substring(3));

            String education = text.substring(text.indexOf("教育经历")+4, text.indexOf("培训经历")).trim();
            String [] eduList = education.split("\\s+");
            //毕业时间
            word.setGraduationDate(eduList[2]);
            //学校
            word.setSchool(eduList[3].trim());
            //专业
            word.setMajor(eduList[4].trim());
            //学历
            word.setEducation(eduList[5].trim());
//
//            String no15 = paragraph[15];
//            String[] n15 =  no15.split(String.valueOf((char)12288)+"|\\s+");

            dao.addTestReadWord(word);
            TraceUtils.print(word);

        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(file.getName()+"文件读取失败!");
        }

        return Response.ok();
    }

    /**
     * @Copyright: Shanghai Definesys Company.All rights reserved.
     * @Description: 猎聘网
     * @author: zhengfei.tan
     * @since:  2019/8/20 22:55
     * @history: 1.2019/8/20 created by zhengfei.tan
     **/
    public Response toLPW(MultipartFile multipartFile){
        TestReadWord word = new TestReadWord();
        try {
            // MultipartFile 转为 io.File
            File file = null;
            file=File.createTempFile("tmp", null);
            multipartFile.transferTo(file);
            file.deleteOnExit();

            Document doc=Jsoup.parse( file, "UTF-8");
            String htm = doc.select("p.MsoNormal").text();
            // 姓名
           word.setName(htm.substring(htm.indexOf("姓名：")+3,htm.indexOf("性别：") ));
            // 性别
            word.setSex(htm.substring(htm.indexOf("性别：")+3,htm.indexOf("手机号码：") ));
            // 年龄
            word.setBirthday(htm.substring(htm.indexOf("年龄：")+3,htm.indexOf("电子邮件：") ));
            // 教育程度
            word.setEducation(htm.substring(htm.indexOf("教育程度：")+5,htm.indexOf("工作年限：") ));
            // 籍贯
            word.setPlace(htm.substring(htm.indexOf("所在地：")+4,htm.indexOf("国籍：") ));

            String education =  htm.substring(htm.indexOf("教育经历")+4);
            String[] eduList = education.split(String.valueOf((char)8211));

            String[] school = eduList[0].trim().split("\\s+");
            // 学校
            word.setSchool(school[0]);

            String[] graduationInfo = eduList[1].split("\\s+");
            //毕业时间
            word.setGraduationDate(graduationInfo[0]);
            // 专业
            word.setMajor(graduationInfo[2]);

            dao.addTestReadWord(word);

            TraceUtils.print(word);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(multipartFile.getOriginalFilename()+" 文件读取失败!");
        }

        return Response.ok();
    }

    public Response toJob(MultipartFile multipartFile) {
        TestReadWord word = new TestReadWord();
        try {
            // 获取文件名
            String fileName = multipartFile.getOriginalFilename();
            // 获取文件后缀
            String prefix=fileName.substring(fileName.lastIndexOf("."));

            Session mailSession = Session.getDefaultInstance(System.getProperties(), null);
            InputStream inputStream = multipartFile.getInputStream();
            try {
                MimeMessage msg = new MimeMessage(mailSession, inputStream);
                Object content = msg.getContent();
                if (content instanceof Multipart) {
                    MimeMultipart mp = (MimeMultipart) content;
                    MimeBodyPart bodyPart = (MimeBodyPart) mp.getBodyPart(0);
                    String strEncodng = getEncoding(bodyPart);
                    InputStream ins =  bodyPart.getInputStream();
                    // MultipartFile 转为 io.File
                    File file = null;
                    file=File.createTempFile(UUID.randomUUID().toString(), prefix);
                    inputStreamToFile(ins, file);
                    Document document=Jsoup.parse(file, strEncodng);
                    String text = document.text();
                    //  应聘岗位
                    word.setJob(text.substring(text.indexOf("应聘职位：")+5, text.indexOf("应聘公司：")));
                    String[] name =  Objects.requireNonNull(multipartFile.getOriginalFilename()).split("_");
                    // 名字
                    word.setName(name[1]);
                    String info = text.substring(text.indexOf("ID:")+3, text.indexOf("求职意向"));
                    String[]  infoList =  info.trim().split("\\s+");
                    // 性别
                    word.setSex(infoList[4]);
                    // 出生年月
                    word.setBirthday(infoList[6].substring(infoList[6].indexOf("（")+1));
                    String birthday = infoList[6].substring(infoList[6].indexOf("（")+1);
                    Date date = DateUtils.StringToDate(birthday, "yyyy年M月dd");

                    TraceUtils.print(date);

                    // 籍贯
                    if ( text.indexOf("户口/国籍：") > 0 ){
                        word.setPlace(text.substring(text.indexOf("户口/国籍：")+6, text.indexOf("户口/国籍：")+9).trim());
                    }
                    // 专业
                    word.setMajor(text.substring(text.indexOf("专　业：")+4, text.indexOf("学　校：")).trim());
                    String education =  text.substring(text.indexOf("教育经历")+4);
                    String[] eduList =education.trim().split("\\s+");
                    // 学校
                    word.setSchool(eduList[1]);
                    // 毕业时间
                    String[] graduationDate = eduList[0].split("-");
                    word.setGraduationDate(graduationDate[1]);
                    String endDate = graduationDate[1] ;
                    // 如果是中文跳过
                    if (!endDate.matches("[\\u4e00-\\u9fa5]+")) {
                        Date date1 = DateUtils.StringToDate(endDate, "yyyy/M");
                        // 毕业时间
                        TraceUtils.print(date1);
                    }


                    //  学历/学位
                    String[] edu = eduList[2].split("\\|");
                    word.setEducation(edu[0].trim());
                    file.deleteOnExit();
                    if (file.exists()) {
                        file.delete();
                    }
                    dao.addTestReadWord(word);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            TraceUtils.print(word);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(multipartFile.getOriginalFilename()+" 文件读取失败!");
        }

        return Response.ok();
    }

    /**
     * @Copyright: Shanghai Definesys Company.All rights reserved.
     * @Description:  智联卓聘
     * @author: zhengfei.tan
     * @since:  2019/8/28 21:52
     * @history: 1.2019/8/28 created by zhengfei.tan
     **/
    public Response toZhuoPin(MultipartFile file){
        TestReadWord word = new TestReadWord();
        try {
            InputStream inputStream = file.getInputStream();
            WordExtractor wordExtractor = new WordExtractor(inputStream);
            String text = wordExtractor.getText();


            // 应聘职位
            word.setJob(text.substring(text.indexOf("推荐职位：")+5, text.indexOf("个人信息")).trim());
            // 单位
          //  word.setUnit(text.substring(text.indexOf("应聘机构：")+5, text.indexOf("工作地点")).trim());


            // 姓名
            word.setName(text.substring(text.indexOf("姓名：")+3, text.indexOf("性别：")).trim());
            // 性别
            word.setSex( text.substring(text.indexOf("性别：")+3, text.indexOf("出生年月：")).trim() );
            // 出生年月
            word.setBirthday(text.substring(text.indexOf("出生年月：")+5, text.indexOf("居住地：")).trim());
            // 籍贯
            word.setPlace(text.substring(text.indexOf("户口：")+3, text.indexOf("顾问评价  ")).trim() );

            String education =  text.substring(text.indexOf("教育经历")+4, text.indexOf("工作经历")).trim();
            String [] eduList = education.split(String.valueOf((char)12288)+"|\\s+");
            //毕业时间
            word.setGraduationDate(eduList[0].substring(eduList[0].indexOf("-")+1));
            //学校
            word.setSchool(eduList[1].trim());
            //专业
            word.setMajor(eduList[2].trim());
            //学历
            word.setEducation(eduList[3].trim());

            dao.addTestReadWord(word);

            TraceUtils.print(word);

        } catch (IOException e) {
            e.printStackTrace();
            Response.error(file.getName()+"文件读取失败!");
        }

        return Response.ok();
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取mht网页文件中内容代码的编码
     *
     * @param bp
     * @return
     */
    private static String getEncoding(MimeBodyPart bp) {
        if (bp == null) {
            return null;
        }
        try {
            Enumeration list = bp.getAllHeaders();
            while (list.hasMoreElements()) {
                javax.mail.Header head = (javax.mail.Header) list.nextElement();
                if (head.getName().equalsIgnoreCase("Content-Type")) {
                    String strType = head.getValue();
                    int pos = strType.indexOf("charset=");
                    if (pos >= 0) {
                        String strEncoding = strType.substring(pos + 8,
                                strType.length());
                        if (strEncoding.startsWith("\"")
                                || strEncoding.startsWith("\'")) {
                            strEncoding = strEncoding.substring(1,
                                    strEncoding.length());
                        }
                        if (strEncoding.endsWith("\"")
                                || strEncoding.endsWith("\'")) {
                            strEncoding = strEncoding.substring(0,
                                    strEncoding.length() - 1);
                        }
                        if (strEncoding.toLowerCase().compareTo("gb2312") == 0) {
                            strEncoding = "gbk";
                        }
                        return strEncoding;
                    }
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
