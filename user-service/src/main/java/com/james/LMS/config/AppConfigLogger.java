//package com.james.LMS.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.core.env.EnumerablePropertySource;
//import org.springframework.stereotype.Component;
//
////@Component
//public class AppConfigLogger {
//
//    private static final Logger log = LoggerFactory.getLogger(AppConfigLogger.class);
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void logProperties(ApplicationReadyEvent event) {
//        ConfigurableEnvironment env = event.getApplicationContext().getEnvironment();
//        log.info("========== APPLICATION CONFIGURATIONS ==========");
//        env.getPropertySources().stream()
//                .filter(ps -> ps instanceof EnumerablePropertySource)
//                .map(ps -> (EnumerablePropertySource<?>) ps)
//                .forEach(ps -> {
//                    for (String propName : ps.getPropertyNames()) {
//                        log.info("{} = {}", propName, env.getProperty(propName));
//                    }
//                });
//        log.info("================================================");
//    }
//}
