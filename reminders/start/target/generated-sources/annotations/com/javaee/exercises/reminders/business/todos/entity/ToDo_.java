package com.javaee.exercises.reminders.business.todos.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-02-20T16:54:08", comments="EclipseLink-3.0.2.v20210716-re8d4b571c9")
@StaticMetamodel(ToDo.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class ToDo_ { 

    public static volatile SingularAttribute<ToDo, String> caption;
    public static volatile SingularAttribute<ToDo, String> description;
    public static volatile SingularAttribute<ToDo, Long> id;
    public static volatile ListAttribute<ToDo, String> contexts;
    public static volatile SingularAttribute<ToDo, Integer> priority;
    public static volatile SingularAttribute<ToDo, Boolean> done;
    public static volatile SingularAttribute<ToDo, Date> updated;
    public static volatile SingularAttribute<ToDo, Long> version;

}