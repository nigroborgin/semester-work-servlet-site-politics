package ru.kpfu.itis.shkalin.service;

import ru.kpfu.itis.shkalin.dto.AbstractDto;
import ru.kpfu.itis.shkalin.entity.AbstractEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface EntityDtoConverterService {

    AbstractEntity convert(AbstractDto dto);

    AbstractDto convert(AbstractEntity entity);

    default void updateDto(AbstractEntity entity, AbstractDto dto) {
        update(entity, dto);
    }

    default void updateEntity(AbstractEntity entity, AbstractDto dto) {
        update(dto, entity);
    }

    private void update(Object updater, Object updatable) {

        Method[] updaterMethods = updater.getClass().getDeclaredMethods();
        Method[] updatableMethods = updatable.getClass().getDeclaredMethods();

        List<Method> settersList = Arrays.stream(updatableMethods)
                .filter(method -> method.getName().startsWith("set"))
                .collect(Collectors.toList());

        List<Method> gettersList = Arrays.stream(updaterMethods)
                .filter(method -> method.getName().startsWith("get"))
                .collect(Collectors.toList());

        String settersNameWithoutPrefix;
        String gettersNameWithoutPrefix;
        Object getterResult = null;

        for (Method setter : settersList) {
            settersNameWithoutPrefix = setter.getName().substring(3);

            for (Method getter : gettersList) {
                gettersNameWithoutPrefix = getter.getName().substring(3);

                if (settersNameWithoutPrefix.equals(gettersNameWithoutPrefix)) {

                    // TODO: add logging
                    try {
                        getterResult = getter.invoke(updater);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        // не должно происходить)
                        e.printStackTrace();
                    }

                    try {
                        setter.invoke(updatable, getterResult);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        // не должно происходить)
                        e.printStackTrace();
                    }

                    break;
                }
            }
        }
    }
}
