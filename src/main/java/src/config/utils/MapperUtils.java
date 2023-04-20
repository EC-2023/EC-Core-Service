package src.config.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MapperUtils<T, D> {
    public static <T, D> void toDto(T src, D des) {
        try {
            mapFields(src, des, src.getClass().getDeclaredFields());
            // Map fields from the root superclass
            Class<?> rootSuperclass = getRootSuperclass(src.getClass());
            mapFields(src, des, rootSuperclass.getDeclaredFields());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T, D> void mapFields(T src, D des, Field[] fields) throws NoSuchFieldException, IllegalAccessException {
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!fieldName.contains("id")) {
                field.setAccessible(true);
                Object fieldValue = field.get(src);
                if (fieldValue != null) {
                    // Tìm phương thức getter tương ứng với trường hiện tại
                    try {
                        String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method getterMethod = src.getClass().getMethod(getterMethodName);
                        fieldValue = getterMethod.invoke(src);
                        // Tìm phương thức setter tương ứng với trường hiện tại
                        String setterMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method setterMethod = des.getClass().getMethod(setterMethodName, field.getType());
                        // Gọi phương thức setter trên đối tượng đích
                        setterMethod.invoke(des, fieldValue);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            }
        }
    }

    private static Class<?> getRootSuperclass(Class<?> cls) {
        Class<?> superClass = cls.getSuperclass();
        while (superClass.getSuperclass() != null && !superClass.getSuperclass().equals(Object.class)) {
            superClass = superClass.getSuperclass();
        }
        return superClass;
    }
}
