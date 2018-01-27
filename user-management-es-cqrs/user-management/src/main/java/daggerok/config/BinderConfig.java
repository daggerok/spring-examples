package daggerok.config;

import daggerok.EsAndCqrsSpringCloudStreamApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(Source.class)
@ComponentScan(basePackageClasses = EsAndCqrsSpringCloudStreamApplication.class)
public class BinderConfig {}
