package daggerok.config;

import daggerok.SseApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(Sink.class)
@ComponentScan(basePackageClasses = SseApplication.class)
public class BinderConfig {}
