package se.webstep.microservice.guestbook.util;

import com.google.common.base.Optional;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.sql.Types;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@BindingAnnotation(BindFields.BindFieldsFactory.class)
public @interface BindFields {

    String value();

    public class BindFieldsFactory implements BinderFactory {

        @Override
        public Binder build(Annotation annotation) {
            return new Binder<BindFields, Object>() {
                @Override
                public void bind(SQLStatement<?> q, BindFields bind, Object arg) {
                    if (bind.value() == null || bind.value().isEmpty() || arg == null) {
                        throw new IllegalStateException("Empty bind value");
                    }

                    try {
                        bindFields(q, arg, arg.getClass(), bind.value());
                    } catch (Exception e) {
                        throw new IllegalStateException("Unable to bind field", e);
                    }
                }

                private void bindFields(SQLStatement<?> q, Object arg, Class<?> argClass, String prefix) throws Exception {
                    // traverse all fields
                    for (Field field : argClass.getFields()) {
                        if (shouldBindField(field)) {
                            Class<?> fieldType = field.getType();
                            Object value = getValue(arg, field);

                            if (handleAsOptionalType(field)) {
                                // we're dealing with an Optional - extract type and value from it instead of using the raw object
                                fieldType = getOptionalType(field);
                                value = ((Optional<?>) value).orNull();
                            }

                            if (hasFieldsToBind(fieldType)) {
                                // ok, field has child - let's traverse that path
                                bindFields(q, value, fieldType, getPrefix(prefix, field));
                            } else {
                                // no child, let's bind the value
                                if (value == null) {
                                    q.bindNull(getPrefix(prefix, field), Types.NULL);
                                } else {
                                    q.dynamicBind(fieldType, getPrefix(prefix, field), value);
                                }
                            }
                        }
                    }
                }

                private boolean handleAsOptionalType(Field field) {
                    return field.getType().equals(Optional.class) && field.getGenericType() instanceof ParameterizedType;
                }

                private Class<?> getOptionalType(Field field) {
                    ParameterizedType type = (ParameterizedType) field.getGenericType();
                    return (Class<?>) type.getActualTypeArguments()[0];
                }

                private String getPrefix(String prefix, Field field) {
                    return prefix + "." + field.getName();
                }

                private Object getValue(Object arg, Field field) throws IllegalAccessException {
                    if (arg == null) {
                        return null;
                    }

                    Object value = field.get(arg);
                    if (value instanceof Enum) {
                        return ((Enum) value).name();
                    }

                    return value;
                }

                private boolean hasFieldsToBind(Class clazz) {
                    if (!clazz.isEnum())
                        for (Field field : clazz.getFields()) {
                            if (shouldBindField(field)) {
                                return true;
                            }
                        }

                    return false;
                }

                private boolean shouldBindField(Field field) {
                    int m = field.getModifiers();
                    return Modifier.isPublic(m) && Modifier.isFinal(m) && !Modifier.isStatic(m);
                }
            };
        }
    }

}
