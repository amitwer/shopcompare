package shopcompare.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import shopcompare.datacontainers.Location;
import shopcompare.datacontainers.Store;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class SuperGetApi {

    private final String apiUrl;
    private final RestTemplate restTemplate;
    private final ApiKeyProvider apiKeyProvider;

    @Autowired
    public SuperGetApi(RestTemplate restTemplate, ApiKeyProvider apiKeyProvider, @Value("${superget.api-url}") String apiUrl) {

        this.restTemplate = restTemplate;
        this.apiKeyProvider = apiKeyProvider;
        this.apiUrl = apiUrl;
    }

    public List<Store> getStoresByGps(@NotNull Location location, int radiusInKm) {
        if (Objects.isNull(location)) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        if (radiusInKm <= 0) {
            throw new IllegalArgumentException("Radius must be non-negative");
        }
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("api_key", apiKeyProvider.getApiKey());
        body.add("action", "GetStoresByGPS");
        body.add("latitude", Double.toString(location.getLatitude()));
        body.add("longitude", Double.toString(location.getLongitude()));
        body.add("km_radius", Integer.toString(radiusInKm));
        body.add("order", Double.toString(1));//order 1 is "near first"
        MultiValueMap<String, String> headers = null;
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, new HttpEntity<>(body, headers), String.class);
        if (Objects.nonNull(response)) {
            try {
                return parseStores(response.getBody());
            } catch (IOException e) {
                log.error("Could not parse stores from response", e);
            }
        }
        return new LinkedList<>();
    }

    private List<Store> parseStores(String storesJson) throws IOException {
        if (StringUtils.isNotEmpty(storesJson)) {
            ObjectMapper mapper = new ObjectMapper();
            Store[] stores = mapper.readValue(storesJson, Store[].class);

            return Arrays.asList(stores);
        }
        return new LinkedList<>();

    }
}
