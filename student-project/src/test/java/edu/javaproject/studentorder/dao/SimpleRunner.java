package edu.javaproject.studentorder.dao;

import org.junit.Test;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SimpleRunner {

    public static void main(String[] args) {

        SimpleRunner simpleRunner = new SimpleRunner();

        simpleRunner.runTest();
    }

    private void runTest() {
        try {
            Class<?> clazz = Class.forName("edu.javaproject" +
                    ".studentorder" +
                    ".dao" +
                    ".DictionaryDaoImplTest");
            Constructor<?> constructor = clazz.getConstructor();
            Object entity = constructor.newInstance();

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                Test annotation = method.getAnnotation(Test.class);
                if (annotation != null) {
                    method.invoke(entity);
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
