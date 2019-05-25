package shopcompare.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
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
public class SuperGetApi {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SuperGetApi.class);
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
            try {
                Price[] prices = objectMapper.readValue(pricesJson, Price[].class);
                return Arrays.asList(prices);
            } catch (Exception e) {
                return Arrays.asList(objectMapper.readValue(getFakeResult(), Price[].class));
            }
        }
        return new LinkedList<>();
    }

    private String getFakeResult() {
        return "[\n" +
                "\t{\n" +
                "\t\t\"store_product_id\": \"16806448\",\n" +
                "\t\t\"store_id\": \"788\",\n" +
                "\t\t\"product_id\": \"749\",\n" +
                "\t\t\"store_product_barcode\": \"0\",\n" +
                "\t\t\"store_product_status\": \"1\",\n" +
                "\t\t\"store_product_price\": \"5.9\",\n" +
                "\t\t\"store_product_last_price\": \"4.9\",\n" +
                "\t\t\"store_product_price_quantity\": \"5.9\",\n" +
                "\t\t\"store_product_update_time\": \"2018-08-12 02:11:43\",\n" +
                "\t\t\"store_product_update_time_real\": \"2018-07-30 06:52:00\",\n" +
                "\t\t\"manufacturer_id\": \"4934\",\n" +
                "\t\t\"product_name\": \"Cabbage\",\n" +
                "\t\t\"product_image\": \"\",\n" +
                "\t\t\"product_description\": \"Cabbage\",\n" +
                "\t\t\"product_barcode\": \"7290000000138\",\n" +
                "\t\t\"product_is_real_barcode\": \"1\",\n" +
                "\t\t\"product_unit_type\": \"2\",\n" +
                "\t\t\"product_quantity\": \"0\",\n" +
                "\t\t\"product_quantity_item\": \"0\",\n" +
                "\t\t\"country_id\": \"128\",\n" +
                "\t\t\"product_update_time\": \"2016-11-02 11:29:59\",\n" +
                "\t\t\"country_name\": \"ישראל\",\n" +
                "\t\t\"country_code\": \"IL\",\n" +
                "\t\t\"manufacturer_name\": \"קטיף\",\n" +
                "\t\t\"chain_id\": \"17\",\n" +
                "\t\t\"sub_chain_id\": \"113\",\n" +
                "\t\t\"city_id\": \"391\",\n" +
                "\t\t\"store_area\": \"unknown, unknown\",\n" +
                "\t\t\"store_type\": \"1\",\n" +
                "\t\t\"store_name\": \"Tesco \",\n" +
                "\t\t\"store_code\": \"29\",\n" +
                "\t\t\"store_address\": \"unknown\",\n" +
                "\t\t\"store_zip_code\": \"0\",\n" +
                "\t\t\"store_gps_lat\": \"32.794044\",\n" +
                "\t\t\"store_gps_lng\": \"34.989571\",\n" +
                "\t\t\"store_update_time\": \"2018-05-06 06:18:03\",\n" +
                "\t\t\"store_insert_time\": \"2017-08-08 21:33:42\",\n" +
                "\t\t\"chain_name\": \"Tesco\",\n" +
                "\t\t\"chain_code\": \"7290876100000\",\n" +
                "\t\t\"chain_image\": \"\",\n" +
                "\t\t\"sub_chain_name\": \"Tesco\",\n" +
                "\t\t\"sub_chain_code\": \"1\",\n" +
                "\t\t\"sub_chain_image\": \"fresh_market.jpg\",\n" +
                "\t\t\"city_name\": \"Jerusalem\",\n" +
                "\t\t\"city_name2\": \"\",\n" +
                "\t\t\"city_name3\": \"\",\n" +
                "\t\t\"city_gps_lat\": \"32.830360\",\n" +
                "\t\t\"city_gps_lng\": \"34.974339\",\n" +
                "\t\t\"product_unit_id\": \"2\",\n" +
                "\t\t\"product_unit_name\": \"קילו גרם\",\n" +
                "\t\t\"promo\": []\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"store_product_id\": \"15565727\",\n" +
                "\t\t\"store_id\": \"788\",\n" +
                "\t\t\"product_id\": \"777\",\n" +
                "\t\t\"store_product_barcode\": \"9370\",\n" +
                "\t\t\"store_product_status\": \"1\",\n" +
                "\t\t\"store_product_price\": \"5.9\",\n" +
                "\t\t\"store_product_last_price\": \"109\",\n" +
                "\t\t\"store_product_price_quantity\": \"5.9\",\n" +
                "\t\t\"store_product_update_time\": \"2018-02-11 06:10:29\",\n" +
                "\t\t\"store_product_update_time_real\": \"2018-01-30 07:09:00\",\n" +
                "\t\t\"manufacturer_id\": \"4934\",\n" +
                "\t\t\"product_name\": \"Green Apples\",\n" +
                "\t\t\"product_image\": \"\",\n" +
                "\t\t\"product_description\": \"Apples\",\n" +
                "\t\t\"product_barcode\": \"7290000000572\",\n" +
                "\t\t\"product_is_real_barcode\": \"1\",\n" +
                "\t\t\"product_unit_type\": \"2\",\n" +
                "\t\t\"product_quantity\": \"0\",\n" +
                "\t\t\"product_quantity_item\": \"0\",\n" +
                "\t\t\"country_id\": \"128\",\n" +
                "\t\t\"product_update_time\": \"2016-11-02 11:29:59\",\n" +
                "\t\t\"country_name\": \"ישראל\",\n" +
                "\t\t\"country_code\": \"IL\",\n" +
                "\t\t\"manufacturer_name\": \"קטיף\",\n" +
                "\t\t\"chain_id\": \"17\",\n" +
                "\t\t\"sub_chain_id\": \"113\",\n" +
                "\t\t\"city_id\": \"391\",\n" +
                "\t\t\"store_area\": \"unknown, unknown\",\n" +
                "\t\t\"store_type\": \"1\",\n" +
                "\t\t\"store_name\": \"Aldi \",\n" +
                "\t\t\"store_code\": \"29\",\n" +
                "\t\t\"store_address\": \"unknown\",\n" +
                "\t\t\"store_zip_code\": \"0\",\n" +
                "\t\t\"store_gps_lat\": \"32.794044\",\n" +
                "\t\t\"store_gps_lng\": \"34.989571\",\n" +
                "\t\t\"store_update_time\": \"2018-05-06 06:18:03\",\n" +
                "\t\t\"store_insert_time\": \"2017-08-08 21:33:42\",\n" +
                "\t\t\"chain_name\": \"Aldi\",\n" +
                "\t\t\"chain_code\": \"7290876100000\",\n" +
                "\t\t\"chain_image\": \"\",\n" +
                "\t\t\"sub_chain_name\": \"Aldi\",\n" +
                "\t\t\"sub_chain_code\": \"1\",\n" +
                "\t\t\"sub_chain_image\": \"fresh_market.jpg\",\n" +
                "\t\t\"city_name\": \"חיפה\",\n" +
                "\t\t\"city_name2\": \"\",\n" +
                "\t\t\"city_name3\": \"\",\n" +
                "\t\t\"city_gps_lat\": \"32.830360\",\n" +
                "\t\t\"city_gps_lng\": \"34.974339\",\n" +
                "\t\t\"product_unit_id\": \"2\",\n" +
                "\t\t\"product_unit_name\": \"קילו גרם\",\n" +
                "\t\t\"promo\": []\n" +
                "\t}\n" +
                "]";
    }
}
