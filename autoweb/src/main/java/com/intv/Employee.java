package com.intv;

public class Employee {

    private String name;
    private int id;
    private int age;
    private String type;
    
    public Employee(){
        
    }

    public Employee(String name, int id, int age, String type) {
        super();
        this.name = name;
        this.id = id;
        this.age = age;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Employee(String type, String name) {
        super();
        this.type = type;
        this.name = name;
    }
    
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
}
