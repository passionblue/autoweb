/*
 * Created on Nov 16, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.util;

/*
 * Some SMTP servers require a username and password authentication before you
 * can use their Server for Sending mail. This is most common with couple of
 * ISP's who provide SMTP Address to Send Mail.
 * 
 * This Program gives any example on how to do SMTP Authentication (User and
 * Password verification)
 * 
 * This is a free source code and is provided as it is without any warranties
 * and it can be used in any your code for free.
 * 
 * Author : Sudhir Ancha
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;


/*
 * To use this program, change values for the following three constants,
 * 
 * SMTP_HOST_NAME -- Has your SMTP Host Name SMTP_AUTH_USER -- Has your SMTP
 * Authentication UserName SMTP_AUTH_PWD -- Has your SMTP Authentication
 * Password
 * 
 * Next change values for fields
 * 
 * emailMsgTxt -- Message Text for the Email emailSubjectTxt -- Subject for
 * email emailFromAddress -- Email Address whose name will appears as "from"
 * address
 * 
 * Next change value for "emailList". This String array has List of all Email
 * Addresses to Email Email needs to be sent to.
 * 
 * 
 * Next to run the program, execute it as follows,
 * 
 * SendMailUsingAuthentication authProg = new SendMailUsingAuthentication();
 * 
 */

public class SendMailUsingAuthentication {

//    private static final String SMTP_HOST_NAME = "smtp.1and1.com";
//    private static final String SMTP_AUTH_USER = "joshua@jtrend.com";
//    private static final String SMTP_AUTH_PWD = "p4ssw0rd";

    private static final String POP3_HOST_NAME = "pop.secureserver.net";
    private static final String SMTP_HOST_NAME = "smtpout.secureserver.net";
    private static final String SMTP_AUTH_USER = "site@uppereastcleaner.com";
    private static final String SMTP_AUTH_PWD = "mailp4ssw0rd";
    
    
    private static final String emailMsgTxt = "Online Order Confirmation Message. Also include the Tracking Number.";
    private static final String emailSubjectTxt = "Order Confirmation Subject";
    private static final String emailFromAddress = "info@financejobforum.com";

    // Add List of Email address to who email needs to be sent to

    private String m_username;
    private String m_password;
    private Session m_session;

    public SendMailUsingAuthentication(String username, String password) {
        m_username = username;
        m_password = password;

        Authenticator auth = new SMTPAuthenticator(m_username, m_password);
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "25");

        m_session = Session.getDefaultInstance(props, auth);

    }

    public void postMail(String recipient, String subject, String message, String from) throws MessagingException {
        String recipeients[] = new String[] { recipient };
        postMail(recipeients, subject, message, from);
    }

    public void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
        boolean debug = false;

        // Set the host smtp address

        m_session.setDebug(debug);

        // create a message
        Message msg = new MimeMessage(m_session);

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        // Setting the Subject and Content Type
        msg.setSubject(subject);

        boolean tryText = false;
        boolean tryHtml = false;
        boolean tryAttach = true;

        if (tryText) {
            msg.setContent(message, "text/plain");
        }
        else if (tryHtml) {
            msg.setContent("<h1> Hello World</h1> <a href=\"http://www.yahoo.com\"> <img src=\"http://photo.hankooki.com/adman/image/2009/11/idol_091119_22050.gif\"/></a>    ", "text/html");
        }
        else if (tryAttach) {
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
            messageBodyPart.setContent(htmlText, "text/html");

            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource("C:\\work\\fotolia\\nyc.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");

            // add it
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            msg.setContent(multipart);

        }
        else { // Attachement

            // create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // fill message
            messageBodyPart.setText("Hi");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource("C:\\work\\fotolia\\nyc.jpg");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Fotolia_7623669_XS.jpg"); // Display purpose
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            msg.setContent(multipart);

        }

        Transport.send(msg);
    }

    /**
     * SimpleAuthenticator is used to do simple authentication when the SMTP
     * server requires it.
     */
    private class SMTPAuthenticator extends javax.mail.Authenticator {

        String m_username;
        String m_password;

        public SMTPAuthenticator(String username, String password) {
            m_username = username;
            m_password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(m_username, m_password);
        }
    }

    public String reademail() throws Exception {

        Store store = m_session.getStore("pop3");
        store.connect(POP3_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);

        Folder folder = store.getFolder("inbox");
        if (folder == null)
            throw new Exception("No default folder");

        folder.open(Folder.READ_WRITE);

        
        // Open destination folder, create if reqd
        Folder dfolder = store.getFolder("FORWARDED");
        if (!dfolder.exists())
          dfolder.create(Folder.HOLDS_MESSAGES);        
        
        Message[] msgs = folder.getMessages();
        
        m_logger.info("------------ Number of Messages : " + msgs.length);
        

        if (msgs.length != 0) {

            
            for (int msgNum = 0; msgNum < msgs.length; msgNum++) {

                m_logger.debug("==================================================================================");
                printMessage(msgs[msgNum]);
            }
            
            folder.copyMessages(msgs, dfolder);
            folder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true);

            // Dump out the Flags of the moved messages, to insure that
            // all got deleted
            for (int i = 0; i < msgs.length; i++) {
              if (!msgs[i].isSet(Flags.Flag.DELETED))
                System.out.println("Message # " + msgs[i] + " not deleted");
            }
          }        
        
        // Close folders and store
        folder.close(false);
        store.close();

        return "";

    }

    public static void printMessage(Message message) {
        try {
            // Get the header information
            String from = ((InternetAddress) message.getFrom()[0]).getPersonal();
            if (from == null)
                from = ((InternetAddress) message.getFrom()[0]).getAddress();
            
            
            Address [] recipients = message.getRecipients(Message.RecipientType.TO);
            
            for (int i = 0; i < recipients.length; i++) {
                Address address = recipients[i];
                
                System.out.println(address);
            }
            
            System.out.println("FROM: " + from);

            String subject = message.getSubject();
            System.out.println("SUBJECT: " + subject);

            // -- Get the message part (i.e. the message itself) --
            Part messagePart = message;
            Object content = messagePart.getContent();

            // -- or its first body part if it is a multipart message --
            if (content instanceof Multipart) {
                messagePart = ((Multipart) content).getBodyPart(0);
                System.out.println("[ Multipart Message ]");
            }

            // -- Get the content type --
            String contentType = messagePart.getContentType();

            // -- If the content is plain text, we can print it --
            m_logger.debug("CONTENT:" + contentType);
            if (contentType.startsWith("text/plain") || contentType.startsWith("text/html")) {
                InputStream is = messagePart.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String thisLine = reader.readLine();

                while (thisLine != null) {
                    System.out.println(thisLine);
                    thisLine = reader.readLine();
                }
            }

            System.out.println("-----------------------------");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {

        String[] emailList = { "joshuayoo@yahoo.com", "joshuayoo@gmail.com" };

        SendMailUsingAuthentication smtpMailSender = new SendMailUsingAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
        long start = System.currentTimeMillis();
        //smtpMailSender.postMail("7187814061@vtext.com", emailSubjectTxt, emailMsgTxt, emailFromAddress);
//        smtpMailSender.postMail("joshuayoo@yahoo.com", emailSubjectTxt, emailMsgTxt, emailFromAddress);
        
        //smtpMailSender.postMail("joshuayooasdfasdfsasdfsfasf@yahoo.com", emailSubjectTxt, emailMsgTxt, emailFromAddress);
        
//        long end2 = System.currentTimeMillis();
//        System.out.println(end2 - end1);
//        smtpMailSender.postMail("passionbluedirect@gmail.com", emailSubjectTxt, emailMsgTxt, emailFromAddress);
//        
//        long end3 = System.currentTimeMillis();
//        System.out.println(end3 - end2);

        System.out.println("Sucessfully Sent mail to All Users");

        smtpMailSender.reademail();

        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start);

    }
    
    private static Logger m_logger = Logger.getLogger(SendMailUsingAuthentication.class);
}
