package se.webstep.microservice.guestbook.util;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.setup.Environment;

import javax.validation.Validation;

public class Environments {

    public static Environment create() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null)
            classLoader = ClassLoader.getSystemClassLoader();

        if (classLoader == null)
            throw new IllegalStateException("Could not resolve a usable class loader!");

        return new Environment("test environment", JsonSupport.objectMapper(),
                Validation.buildDefaultValidatorFactory().getValidator(), new MetricRegistry(), classLoader);
    }

}
