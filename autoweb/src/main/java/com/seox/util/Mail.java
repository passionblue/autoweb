/*
 * Created on Oct 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.util;

import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Mail {

    private static final String SMTP_HOST_NAME = "smtp.1and1.com";
    private static final String SMTP_AUTH_USER = "joshua@jtrend.com";
    private static final String SMTP_AUTH_PWD = "p4ssw0rd";
    
    
    public static void sendMail(String messageText) {
        sendMail(messageText, " ");
    }

    public static void sendMail(String messageText, String subject) {
        sendMail(messageText, subject, "ALERT");
    }

    public static void sendMail(String messageText, String subject, String groupId) {
        // create some properties and get the default Session

        try {

            String sendServer = "smtp.1and1.com";// LocateProxyProperties.getInstance().getProperty("locateproxy.mail.server",
                                                    // "server unknown");
            String hostname = "smtp.1and1.com";// LocateProxyProperties.getInstance().getProperty("locateproxy.mail.smtphost",
                                                // null);
            String from = "info@financejobforum.com";// LocateProxyProperties.getInstance().getProperty("locateproxy.mail.from",
                                                        // null);
            String to = "joshuayoo@yahoo.com";// LocateProxyProperties.getInstance().getProperty("locateproxy.mail.to."
                                                // + groupId, null);
            String formatsubject = "test subject";// LocateProxyProperties.getInstance().getProperty("locateproxy.mail.subject",
                                                    // null);
            String bodyText = messageText + " \n\n Running on machine : " + sendServer;

            subject = formatsubject + subject;

            if (from == null)
                throw new Exception("Email Address not found in the config file");

            if (to == null || to.trim().equals(""))
                return;
            
            StringTokenizer tokens = new StringTokenizer(to, ",");

            String toList[] = new String[tokens.countTokens()];
            int i = 0; 
            while (tokens.hasMoreTokens()) {
                toList[i++] = tokens.nextToken();
            }
            
            SendMailUsingAuthentication smtpMailSender = new SendMailUsingAuthentication("","");
            smtpMailSender.postMail(toList, subject, bodyText, from);

//            sendMail(hostname, to, from, subject, bodyText);
            /*
             * Properties props = System.getProperties();
             * props.put("mail.smtp.host", hostname); props.put("mail.from",
             * from);
             * 
             * Session session = Session.getDefaultInstance(props, null); //
             * session.setDebug(debug); session.setDebug(false);
             * 
             * StringTokenizer tokens = new StringTokenizer(to, ",");
             * InternetAddress[] address = new
             * InternetAddress[tokens.countTokens()]; int i = 0; while
             * (tokens.hasMoreTokens()) { address[i++] = new
             * InternetAddress(tokens.nextToken()); }
             * 
             * MimeMessage msg = new MimeMessage(session); Multipart mp = new
             * MimeMultipart();
             * 
             * msg.setRecipients(Message.RecipientType.TO, address);
             * msg.setSubject(subject); msg.setSentDate(new Date());
             *  // create and fill the first message part MimeBodyPart mbp1 =
             * new MimeBodyPart(); mbp1.setText(messageText + " \n\n Running on
             * machine : " + sendServer); mp.addBodyPart(mbp1);
             *  // add the Multipart to the message msg.setContent(mp);
             *  // send the message Transport.send(msg);
             */
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
                ex.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMail(String smtpHost, String to, String from, String subject, String messageText) {
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.from", from);

            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getDefaultInstance(props, auth);
            // session.setDebug(debug);
            session.setDebug(false);

            StringTokenizer tokens = new StringTokenizer(to, ",");
            InternetAddress[] address = new InternetAddress[tokens.countTokens()];
            int i = 0;
            while (tokens.hasMoreTokens()) {
                address[i++] = new InternetAddress(tokens.nextToken());
            }

            MimeMessage msg = new MimeMessage(session);
            Multipart mp = new MimeMultipart();

            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(messageText);
            mp.addBodyPart(mbp1);

            // add the Multipart to the message
            msg.setContent(mp);

            // send the message
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
                ex.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
        boolean debug = false;

        // Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(props, auth);

        session.setDebug(debug);

        // create a message
        Message msg = new MimeMessage(session);

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
        msg.setContent(message, "text/plain");
        Transport.send(msg);
    }

    /**
     * SimpleAuthenticator is used to do simple authentication when the SMTP
     * server requires it.
     */
    public static class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }
    
    public static void main(String[] args) {
        Mail.sendMail("Test", "ttt");
    }
}

