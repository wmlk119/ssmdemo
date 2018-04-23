package com.archer.ssm.utils.common;

import com.alibaba.fastjson.JSON;
import com.archer.ssm.utils.pojo.DogRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮件工具类
 */
public class MailUtil {
    protected static Logger log = LoggerFactory.getLogger(MailUtil.class);

    /**
     * 构造消息HTML
     * @param infos
     * @return
     */
    public static String getInfosHtml(String infos){
        StringBuffer sb = new StringBuffer();
        sb.append("<html><head><meta charset='utf-8'><style type='text/css'>body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}td,input,button,select,body{font-family:Helvetica,'Microsoft Yahei',verdana}</style></head>");
        sb.append("<body><table style='margin:0 auto' height='40' width='665'><tbody><tr><td></td></tr></tbody></table><table width='800px' style='font-size:14px;margin:0 auto;border:2px solid #84bf96'><tbody><tr><td style='padding:20px 44px;font-size:14px'>");
        sb.append(infos);
        sb.append("</td></tr><tr><td style='padding:13px 0 10px 550px;font-size:14px;font-weight:bold'>徐州市房管局信息中心<br>");
        sb.append(DateUtils.formatDateTime(new Date())+"</td></tr></tbody></table></body></html>");
        return sb.toString();
    }

    /**
     * 构造加密狗注册HTML
     * @param regUrl
     * @return
     */
    public static String getDogRegHtml(String regUrl){
        StringBuffer sb = new StringBuffer();
        sb.append("<html><head><meta charset='utf-8'><base target='_blank'><style type='text/css'>");
        sb.append("body {font-size: 14px;font-family: arial, verdana, sans-serif;line-height: 1.666;padding: 0;margin: 0;overflow: auto;white-space: normal;word-wrap: break-word;min-height: 100px}");
        sb.append("td,input,button,select,body {font-family: Helvetica, 'Microsoft Yahei', verdana}");
        sb.append("#divNeteaseBigAttach,#divNeteaseBigAttach_bak {display: none;}");
        sb.append("pre {white-space: pre-wrap;white-space: -moz-pre-wrap;white-space: -pre-wrap;white-space: -o-pre-wrap;word-wrap: break-word;width: 95%}");
        sb.append("th,td {font-family: arial, verdana, sans-serif;line-height: 1.666}img {border: 0}");
        sb.append("header,footer,section,aside,article,nav,hgroup,figure,figcaption {display: block}a,td a {color: #138144}");
        sb.append("</style></head><body>");
        sb.append("<table style='margin:0 auto;' height='40' width='665'><tbody><tr><td></td></tr></tbody></table>");
        sb.append("<table width='665px' height='570px' style='font-size:14px;margin:0 auto;border:2px solid #84bf96;'><tbody>");
        sb.append("<tr><td style='padding:10px 44px 0px;font-size:14px;font-weight:bold;'>尊敬的用户，您好！</td></tr>");
        sb.append("<tr><td style='padding:0px 44px;font-size:14px;'>请点击下面的链接，完成加密狗注册：</td></tr>");
        sb.append("<tr><td style='padding:5px 0 5px 44px;'><div style='width:555px;word-break:break-all;'><a target='_blank' href='");
        sb.append(regUrl);
        sb.append("' style='cursor:pointer;text-decoration:none;color:#007FFF;font-family: arial,verdana,sans-serif;line-height: 1.666;font-size: medium;font-variant: normal;font-style: normal;'>");
        sb.append(regUrl+"</a></div></td></tr>");
        sb.append("<tr><td style='padding:0px 44px;font-size:14px;'>（如果点击链接没反应，请复制激活链接，粘贴到浏览器地址栏后访问）</td></tr>");
        sb.append("<tr><td style='padding:30px 44px 0px;color:#959393;font-size:14px;'>激活链接24小时内有效。</td></tr>");
        sb.append("<tr><td style='padding:0px 44px;color:#959393;font-size:14px;'>请使用IE浏览器打开(IE10以上)。</td></tr>");
        sb.append("<tr><td style='padding:0px 44px;color:#959393;font-size:14px;'>激活链接将在您激活一次后失效。</td></tr>");
        sb.append("<tr><td style='padding:13px 0px 10px 400px;font-size:14px;font-weight:bold;'>徐州市房管局信息中心<br>"+DateUtils.formatDateTime(new Date())+"</td></tr>");
        sb.append("<tr><td style='padding:0px 0px 50px 44px;color:#959393;font-size:14px;'>这是一封系统自动发出的邮件，请不要直接回复，如您有任何疑问，请联系0516-83601272</td></tr>");
        sb.append("</tbody></table></body></html>");
        return sb.toString();
    }

    /**
     * 发送通知信息
     * @param addrlist 邮箱地址集合
     * @param infos 消息
     * @param title 标题
     * @return
     */
    public static boolean sendNoticeInfos(List<String> addrlist, String infos,String title){
        boolean res = true;
        String mailHost = PropertyUtil.getValue("properties/public.properties","mail_host");
        String mailAddr = PropertyUtil.getValue("properties/public.properties","mail_addr");
        String mailPwd = PropertyUtil.getValue("properties/public.properties","mail_pwd");
        Properties props = new Properties();
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.auth",true);
        Session session = Session.getInstance(props,null);
        try {
            // 构造邮箱地址
            List<InternetAddress> addrs = new ArrayList<InternetAddress>();
            for(String addr: addrlist){
                addrs.add(new InternetAddress(addr));
            }
            // 创建消息
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailAddr));
            InternetAddress[] address = addrs.toArray(new InternetAddress[addrs.size()]);
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSentDate(new Date());
            msg.setSubject(title);
            msg.setDataHandler(new DataHandler(new ByteArrayDataSource(getInfosHtml(infos), "text/html")));
            Transport.send(msg, mailAddr, mailPwd);
        } catch (Exception e) {
            log.error("发送通知信息异常：",e);
            res = false;
        }
        return res;
    }

    /**
     * 发送加密狗注册邮件
     * @param adddrlist
     * @param urllist
     * @param title
     * @return
     */
    public static boolean dogRegisterMail(List<String> adddrlist, List<String> urllist, String title){
        boolean res = true;
        String mailHost = PropertyUtil.getValue("properties/public.properties","mail_host");
        String mailAddr = PropertyUtil.getValue("properties/public.properties","mail_addr");
        String mailPwd = PropertyUtil.getValue("properties/public.properties","mail_pwd");
        Properties props = new Properties();
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.auth",true);
        Session session = Session.getInstance(props,null);
        int count = 0;
        try {
            List<DogRegister> dglist = new ArrayList<DogRegister>();
            for (int i=0,len=adddrlist.size();i<len;i++){
                DogRegister dr = new DogRegister();
                dr.setAddress(adddrlist.get(i));
                dr.setUrl(urllist.get(i));
                dglist.add(dr);
            }
            dglist.parallelStream().forEach(dog -> {
                try {
                    MimeMessage msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress(mailAddr));
                    InternetAddress[] address = { new InternetAddress(dog.getAddress())};
                    msg.setRecipients(Message.RecipientType.TO, address);
                    msg.setSentDate(new Date());
                    msg.setSubject(title);
                    msg.setDataHandler(new DataHandler(new ByteArrayDataSource(getDogRegHtml(dog.getUrl()), "text/html")));
                    Transport.send(msg, mailAddr, mailPwd);
                    System.out.println("已发送邮箱["+dog.getAddress()+"]："+dog.getUrl());
                    log.warn("已发送邮箱["+dog.getAddress()+"]："+dog.getUrl());
                } catch (Exception e) {
                    log.error("发送加密狗注册邮件异常[邮箱地址-"+dog.getAddress()+"]：",e);
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            log.error("发送加密狗注册邮件异常[已发送个数："+count+",邮箱地址："+ JSON.toJSONString(adddrlist)+"]：",e);
            res = false;
        }
        return res;
    }


}
