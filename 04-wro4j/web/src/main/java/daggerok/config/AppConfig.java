package daggerok.config;

import daggerok.web.error.FallbackConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ FallbackConfig.class })
public class AppConfig {}
