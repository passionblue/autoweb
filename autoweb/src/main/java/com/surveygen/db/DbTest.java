package com.surveygen.db;

import java.util.List;

public class DbTest {

	public static void main2(String[] args) {
		Check2tableDAO dao = new Check2tableDAO();
		Check2table ch = new Check2table();
		ch.setCheck2Val("TEST");
		dao.save(ch);
	}
    public static void main3(String[] args) {
        CheckTableDAO dao = new CheckTableDAO();
        CheckTable ch = new CheckTable();
        ch.setCheckBool(true);
        ch.setCheckLong(123);
        ch.setChecVal(1);
        dao.save(ch);
    }
    public static void main4(String[] args) {
        UserDAO dao = new UserDAO();

/*        User obj = new User();

        obj.setUsername("username");
        obj.setPassword("password");
        obj.setEmail("email");
        obj.setCreatedT(new Date());
        
        dao.save(obj);
*/
        
        List all = dao.findAll();
        System.out.println(all);
    }
    
    public static void main(String[] args) {
        UserPropsDAO dao = new UserPropsDAO();
        List all = dao.findAll();
        System.out.println(all);
    }
}
