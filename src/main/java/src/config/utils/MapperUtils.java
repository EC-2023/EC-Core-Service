package src.config.utils;

import java.lang.reflect.Field;

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
            if (field.get(src) != null && !field.getName().contains("id")) {
                Field desField = des.getClass().getDeclaredField(field.getName());
                desField.setAccessible(true);
                desField.set(des, field.get(src));
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
