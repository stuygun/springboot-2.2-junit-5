package com.viooh.sandbox.todo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Task Entity Tests")
public class TaskTest {

    @Test
    @DisplayName("Entity class must have Entity Annotation")
    public void taskEntityMustHaveEntityAnnotation() {
        Annotation entityAnnotation = Task.class.getDeclaredAnnotation(Entity.class);
        assertNotNull(entityAnnotation, "No class-level javax.persistence.Entity annotation exists on Task Entity!");
    }

    @Test
    @DisplayName("Entity ID annotation must exits")
    public void taskEntityMustHaveIdAnnotatedField() {
        Field[] declaredFields = Task.class.getDeclaredFields();
        boolean found = false;
        for(Field field : declaredFields) {
            field.setAccessible(true);
            Id declaredAnnotation = field.getDeclaredAnnotation(Id.class);
            if(declaredAnnotation != null) {
                found = true;
                break;
            }
        }

        assertTrue(found, "No javax.persistence.Id annotated field exists on Task Entity!");
    }
}
