package ba.edu.ibu.aitodo.rest.configuration;

import ba.edu.ibu.aitodo.api.impl.openai.OpenAICategorySuggester;
import ba.edu.ibu.aitodo.core.api.categorysuggester.CategorySuggester;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAIConfiguration {
    @Value("${openai.secret}")
    private String apiSecret;

    @Bean
    public CategorySuggester categorySuggester() {
        return new OpenAICategorySuggester(this.openAiService());
    }

    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(this.apiSecret);
    }
}
