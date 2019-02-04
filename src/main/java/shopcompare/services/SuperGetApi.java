package shopcompare.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import shopcompare.datacontainers.Location;
import shopcompare.datacontainers.Price;
import shopcompare.datacontainers.Store;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class SuperGetApi {

    private final String apiUrl;
    private final RestTemplate restTemplate;
    private final ApiKeyProvider apiKeyProvider;
    private final ObjectMapper objectMapper;

    @Autowired
    public SuperGetApi(RestTemplate restTemplate, ApiKeyProvider apiKeyProvider, @Value("${superget.api-url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiKeyProvider = apiKeyProvider;
        this.apiUrl = apiUrl;
        objectMapper = new ObjectMapper();
    }

    public List<Store> getStoresByGps(@NotNull Location location, int radiusInKm) {
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
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, new HttpEntity<>(body, null), String.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            try {
                return parseStores(response.getBody());
            } catch (IOException e) {
                log.error("Could not parse stores from response", e);
            }
        } else {
            throw new IllegalStateException("Got HTTP response " + response.getStatusCode() + " when calling getStoresByGps");
        }
        return new LinkedList<>();
    }

    private List<Store> parseStores(String storesJson) throws IOException {
        if (StringUtils.isNotEmpty(storesJson)) {
            Store[] stores = objectMapper.readValue(storesJson, Store[].class);
            return Arrays.asList(stores);
        }
        return new LinkedList<>();

    }

    public List<Price> getPrices(String storeId, Set<String> products) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("api_key", apiKeyProvider.getApiKey());
        body.add("action", "GetPriceByProductID");
        body.add("store_id", storeId);
        products.forEach(pid -> body.add("product_id[]", pid));
        log.info("Sending POST Request for getPrices Store {}", storeId);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, new HttpEntity<>(body, null), String.class);
        log.info("done requesting prices for store {}", storeId);
        try {
            return parsePrices(response.getBody());
        } catch (IOException e) {
            log.error("Could not parse prices", e);
        }
        return new LinkedList<>();
    }

    private List<Price> parsePrices(String pricesJson) throws IOException {
        if (StringUtils.isNotEmpty(pricesJson)) {
            log.debug("Parsing prices {}", pricesJson);
            Price[] prices = objectMapper.readValue(pricesJson, Price[].class);
            return Arrays.asList(prices);
        }
        return new LinkedList<>();
    }
}
