package utils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class SimpleClassUtil {

    private final static Set<Class> simpleClassSet = new HashSet<Class>();

    static {
        simpleClassSet.add(Integer.class);
        simpleClassSet.add(int.class);
        simpleClassSet.add(Long.class);
        simpleClassSet.add(long.class);
        simpleClassSet.add(Float.class);
        simpleClassSet.add(float.class);
        simpleClassSet.add(Double.class);
        simpleClassSet.add(double.class);
        simpleClassSet.add(Character.class);
        simpleClassSet.add(char.class);
        simpleClassSet.add(Boolean.class);
        simpleClassSet.add(boolean.class);
        simpleClassSet.add(Byte.class);
        simpleClassSet.add(byte.class);
        simpleClassSet.add(Short.class);
        simpleClassSet.add(short.class);
        simpleClassSet.add(BigDecimal.class);
        simpleClassSet.add(String.class);
    }

    public static boolean isSimpleClass(Class<?> clazz){
        return simpleClassSet.contains(clazz);
    }
}
