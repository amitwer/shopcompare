package shopcompare.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ApiKeyProvider {

    @Value("${api-key:}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
