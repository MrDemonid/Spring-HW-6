package mr.demonid.spring.hw6.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching                                  // включаем кэширование.
@OpenAPIDefinition(info = @Info(                // и openAPI
        title = "Lesson-6 Spring Framework",
        description = "Написание простого REST-сервиса",
        version = "первая и последняя",
        contact = @Contact(
                name = "Andrey Hlus",
                email = "MrDemonid@yandex.ru"
        )
))
public class AppConfigurations {
}
