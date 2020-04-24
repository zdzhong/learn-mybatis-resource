package test;

import framework.MybatisV1;

public class MyTest {

    public static void main(String[] args) throws Exception {
        MybatisV1 mybatisV1 = new MybatisV1();
        System.out.println(mybatisV1.selectOne("selectOne", 101));
    }

}
