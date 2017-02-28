/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;

/**
 *
 * @author Lorenzo
 */
public class BeansUtil {

    public static <T> T copyOf(T src, T dst) {
        Method[] methods = src.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                String attrName = method.getName().substring(3);
                try {
                    method.invoke(dst, dst.getClass().getMethod("get" + attrName).invoke(src));
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return dst;
    }
    
    public static <T> T copyOf(ResultSet rs, T dst){
        Method[] methods = dst.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                String attrName = method.getName().substring(3);
                try {
                    Object obj = ResultSet.class.getMethod("getObject",String.class).invoke(rs,attrName.toLowerCase());
                    if(obj != null){
                        method.invoke(dst, obj);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return dst;
    }
}
