package shopcompare.services;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class SuperGetApiTest {
    protected static final String API_URL = "http://myUrl.com";
    protected SuperGetApi superGetApi;
    protected RestTemplate restTemplate;
    protected ApiKeyProvider apiKeyProvider;

    @BeforeEach
    protected void setup() {
        restTemplate = mock(RestTemplate.class);
        apiKeyProvider = mock(ApiKeyProvider.class);
        superGetApi = new SuperGetApi(restTemplate, apiKeyProvider, API_URL);
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(createResponseWithHttpStatus(HttpStatus.OK));
    }

    protected ResponseEntity<String> createResponseWithHttpStatus(HttpStatus httpStatus) {
        return new ResponseEntity<>(null, httpStatus);
    }
}
