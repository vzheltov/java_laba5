package com.reflection.lab;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Класс Injector осуществляет внедрение зависимостей в объекты.
 * Он использует рефлексию для поиска полей, помеченных
 * аннотацией @AutoInjectable,
 * и инициализирует их реализациями, указанными в файле конфигурации.
 */
public class Injector {
    private final Properties properties;

    /**
     * Конструктор загружает настройки из файла config.properties.
     * Файл должен находиться в папке resources.
     */
    public Injector() {
        properties = new Properties();
        try (InputStream input = Injector.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Файл config.properties не найден!");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Внедряет зависимости в переданный объект.
     *
     * @param <T>    Тип объекта
     * @param object Объект, в который нужно внедрить зависимости
     * @return Тот же объект с инициализированными полями
     */
    public <T> T inject(T object) {
        try {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(AutoInjectable.class)) {
                    String interfaceName = field.getType().getName();
                    String implClassName = properties.getProperty(interfaceName);

                    if (implClassName != null) {
                        Object instance = Class.forName(implClassName).getDeclaredConstructor().newInstance();
                        field.setAccessible(true);
                        field.set(object, instance);
                    } else {
                        System.out.println("Нет реализации для: " + interfaceName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}