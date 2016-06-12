package com.autosite.lab;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {

    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.1.5");

        Jedis jedis = pool.getResource();
        try {
            // / ... do stuff here ... for example
            jedis.set("fss", "bb");
            String foobar = jedis.get("fss");

            System.out.println(foobar);
            jedis.zadd("sose", 0, "car");
            jedis.zadd("sose", 0, "bike");

            Set<String> sose = jedis.zrange("sose", 0, -1);
            
            System.out.println(sose);
            

            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                jedis.zinterstore("key" + i, "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasdddddddddddddddddddddddddddddd" + i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddb"+ i, 
                        "aasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffdsafaasddddddddddddddddddddddddddddddc"+i);
                
                //System.out.println("X");
            }
            long end = System.currentTimeMillis();
            
            System.out.println((end-start)/100);
            
            System.out.println(jedis.zrangeByScore("key"+1, 1, 2));
            
            
        }
        finally {
            // / ... it's important to return the Jedis instance to the pool
            // once you've finished using it
            pool.returnResource(jedis);
        }
        // / ... when closing your application:
        pool.destroy();

    }
    
    
    
    public Map getData(int i){
        Map ret = new HashMap();
        
        ret.put("ID", new Integer(i));
        ret.put("DATA", ""+System.currentTimeMillis());
        ret.put("DATE", new Date());
        
        return ret;
        
        
    }
    
    
}
