package com.autosite.mail;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


// Spring mail needs spring-context-support, which doesnt exist in myeclipse. So i manually linked from maven repository. 

public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-mail.xml");
         
//        MailMail mm = (MailMail) context.getBean("mailMail");
//        mm.sendMail("Yong Mook Kim", "This is text content");
        

//      AutositeMailService service = AutositeMailServiceFactory.getInstance().getAutositeService("joshua@jtrend.com");
//      AutositeMailService service = AutositeMailServiceFactory.getInstance().getAutositeService("passionbluedirect@gmail.com");
        AutositeMailService service = AutositeMailServiceFactory.getInstance().getAutositeService("support@littlejcleaner.com");
        
//      service.sendMail("joshuayoo@yahoo.com", "Test", "Test Content");
        service.sendMail("joshuayoo@yahoo.com", "Test", "<html><body><img src='http://blogcafe.dreamwiz.com/cafeimage/w/o/woo4366/woo4366_20120213153401_7079012_1.jpg'></body></html>", "c:\\work\\autosite\\santa1-thumb.jpg");
        
//      service.sendHtml("joshuayoo@yahoo.com", "Test", "<html><body><img src='http://blogcafe.dreamwiz.com/cafeimage/w/o/woo4366/woo4366_20120213153401_7079012_1.jpg'></body></html>");
//      service.sendHtml("joshuayoo@yahoo.com", "Test", "<html><body><img src='http://blogcafe.dreamwiz.com/cafeimage/w/o/woo4366/woo4366_20120213153401_7079012_1.jpg'></body></html>", "c:\\work\\autosite\\santa1-thumb.jpg");
        
    }
}
