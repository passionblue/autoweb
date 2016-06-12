package com.autosite.repository;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.autosite.db.CleanerPickupDelivery;
import com.autosite.db.CleanerPickupDeliveryConfig;
import com.autosite.lab.mongo.core.AutoRepository;
import com.autosite.lab.mongo.core.TestJo;
import com.autosite.lab.mongo.core.TestJoRepository;
import com.autosite.repository.mongo.AutositeMongoApplicationConfig;

public class RepositoryTest {

    public static void main(String[] args) {
        test();
    }
    
    public static void test() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AutositeMongoApplicationConfig.class);
        AutoRepository repo = ctx.getBean(AutoRepository.class);
        
        CleanerPickupDelivery c = new CleanerPickupDelivery();
        CleanerPickupDelivery d = (CleanerPickupDelivery)repo.save(c);

        
        long id = IdGenerator.getInstance().nextSequence(CleanerPickupDeliveryConfig.class.getSimpleName());
        CleanerPickupDeliveryConfig cd = new CleanerPickupDeliveryConfig();
        cd.setId(id);
        CleanerPickupDeliveryConfig dd= (CleanerPickupDeliveryConfig)repo.save(cd);
        
    
    }

}
