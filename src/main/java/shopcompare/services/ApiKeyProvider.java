package shopcompare.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ApiKeyProvider {

    @Value("${api-key:}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
