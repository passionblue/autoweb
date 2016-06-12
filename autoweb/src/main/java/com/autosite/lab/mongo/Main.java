package com.autosite.lab.mongo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.autosite.db.CleanerPickupDelivery;
import com.autosite.db.CleanerPickupDeliveryConfig;
import com.autosite.lab.mongo.core.AutoRepository;
import com.autosite.lab.mongo.core.TestJo;
import com.autosite.lab.mongo.core.TestJoRepository;
import com.autosite.repository.IdGenerator;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        AutoRepository repo = ctx.getBean(AutoRepository.class);
        
        CleanerPickupDelivery c = new CleanerPickupDelivery();
        CleanerPickupDelivery d = (CleanerPickupDelivery)repo.save(c);

        
        long id = IdGenerator.getInstance().nextSequence(CleanerPickupDeliveryConfig.class.getSimpleName());
        CleanerPickupDeliveryConfig cd = new CleanerPickupDeliveryConfig();
        cd.setId(id);
        CleanerPickupDeliveryConfig dd= (CleanerPickupDeliveryConfig)repo.save(cd);
        
    
    }
    
    public static void main_working(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        TestJoRepository repo = ctx.getBean(TestJoRepository.class);
        
        TestJo dave = new TestJo();
        dave.setColor("pink");
        dave.setDepth("20cm");

        TestJo result = repo.save(dave);
    }
}
