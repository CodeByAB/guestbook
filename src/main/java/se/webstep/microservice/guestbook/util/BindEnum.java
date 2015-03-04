package se.webstep.microservice.guestbook.util;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation(BindEnum.BindEnumFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindEnum {

    String value() default "enum";

    public static class BindEnumFactory implements BinderFactory {
        @Override
        public Binder build(Annotation annotation) {
            return new Binder<BindEnum, Enum<?>>() {
                @Override
                public void bind(SQLStatement<?> q, BindEnum bind, Enum<?> arg) {
                    q.bind(bind.value(), arg.name());
                }
            };
        }
    }

}
