package shopcompare.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import shopcompare.datacontainers.Location;
import shopcompare.datacontainers.Store;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SuperGetApiGetStoresTest {

    private static final String API_URL = "http://myUrl.com";
    private SuperGetApi superGetApi;
    private RestTemplate restTemplate;
    private ApiKeyProvider apiKeyProvider;

    @BeforeEach
    private void setup() {
        restTemplate = mock(RestTemplate.class);
        apiKeyProvider = mock(ApiKeyProvider.class);
        superGetApi = new SuperGetApi(restTemplate, apiKeyProvider, API_URL);
    }

    @Test
    void getStoresByGpsInvokesRestApi() {
        superGetApi.getStoresByGps(new Location(10.0, 11.0), 5);
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), eq(String.class));
    }

    @Test
    void getStoresByGpsReturnsListOfStores() {
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(createResponseWith2Stores());
        List<Store> stores = superGetApi.getStoresByGps(new Location(0, 0), 8);
        assertThat(stores).hasSize(2).hasOnlyElementsOfType(Store.class);
        verify(restTemplate, times(1)).postForEntity(anyString(), any(), any());
    }

    @Test
    void getStoresCallsTheCorrectApi() {
        superGetApi.getStoresByGps(new Location(0, 0), 1);
        verify(restTemplate, times(1)).postForEntity(eq(API_URL), any(), any());
    }

    @Test
    void locationIsNull() {
        assertThatThrownBy(() -> superGetApi.getStoresByGps(null, 0)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void radiusIsNotPositiveThrowsException(int radius) {
        assertThatThrownBy(() -> superGetApi.getStoresByGps(new Location(1, 1), radius))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Radius must be non-negative");
    }


    @Test
    void getStoresByGpsHasAllParameters() {
        String apiKey = "1234";
        when(apiKeyProvider.getApiKey()).thenReturn(apiKey);
        Location location = new Location(10, 12);
        superGetApi.getStoresByGps(location, 5);
        Collection<Invocation> invocations = Mockito.mockingDetails(restTemplate).getInvocations();
        //noinspection unchecked
        HttpEntity<MultiValueMap<String, String>> request = (HttpEntity<MultiValueMap<String, String>>) invocations.iterator().next().getRawArguments()[1];
        MultiValueMap<String, String> body = request.getBody();
        assertThat(invocations).hasSize(1);
        assertThat(body).containsAllEntriesOf(getExpectedRequestParameters(apiKey, location, 5));
    }

    private MultiValueMap<String, String> getExpectedRequestParameters(String apiKey, Location location, int radiusInKm) {
        MultiValueMap<String, String> expectedValues = new LinkedMultiValueMap<>();
        expectedValues.add("latitude", Double.toString(location.getLatitude()));
        expectedValues.add("longitude", Double.toString(location.getLongitude()));
        expectedValues.add("km_radius", Integer.toString(radiusInKm));
        expectedValues.add("order", Double.toString(1));//order 1 is "near first"
        expectedValues.add("action", "GetStoresByGPS");
        expectedValues.add("api_key", apiKey);
        return expectedValues;
    }


    private ResponseEntity<String> createResponseWith2Stores() {
        String storesJsonString = "[\n" +
                "    {\n" +
                "        \"store_id\": \"241\",\n" +
                "        \"chain_id\": \"10\",\n" +
                "        \"sub_chain_id\": \"106\",\n" +
                "        \"city_id\": \"391\",\n" +
                "        \"store_area\": \"\",\n" +
                "        \"store_type\": \"1\",\n" +
                "        \"store_name\": \"דיל חיפה- קניון\",\n" +
                "        \"store_code\": \"352\",\n" +
                "        \"store_address\": \"כניסה דרומית לחי\",\n" +
                "        \"store_zip_code\": \"35084\",\n" +
                "        \"store_gps_lat\": \"32.799530\",\n" +
                "        \"store_gps_lng\": \"35.005409\",\n" +
                "        \"store_update_time\": \"2016-03-24 09:36:12\",\n" +
                "        \"store_insert_time\": \"2017-08-08 21:33:42\",\n" +
                "        \"city_name\": \"חיפה\",\n" +
                "        \"city_name2\": \"\",\n" +
                "        \"city_name3\": \"\",\n" +
                "        \"city_gps_lat\": \"32.830360\",\n" +
                "        \"city_gps_lng\": \"34.974339\",\n" +
                "        \"chain_name\": \"שופרסל\",\n" +
                "        \"chain_code\": \"7290027600007\",\n" +
                "        \"chain_image\": \"\",\n" +
                "        \"sub_chain_name\": \"שופרסל דיל\",\n" +
                "        \"sub_chain_code\": \"2\",\n" +
                "        \"sub_chain_image\": \"super_sal_deal.jpg\",\n" +
                "        \"distance\": \"0.6409635721984985\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"store_id\": \"142\",\n" +
                "        \"chain_id\": \"10\",\n" +
                "        \"sub_chain_id\": \"102\",\n" +
                "        \"city_id\": \"391\",\n" +
                "        \"store_area\": \"\",\n" +
                "        \"store_type\": \"1\",\n" +
                "        \"store_name\": \"שלי חיפה- ורדיה\",\n" +
                "        \"store_code\": \"212\",\n" +
                "        \"store_address\": \"ורדיה 10\",\n" +
                "        \"store_zip_code\": \"3465710\",\n" +
                "        \"store_gps_lat\": \"32.794846\",\n" +
                "        \"store_gps_lng\": \"34.995907\",\n" +
                "        \"store_update_time\": \"2016-03-24 09:36:03\",\n" +
                "        \"store_insert_time\": \"2017-08-08 21:33:42\",\n" +
                "        \"city_name\": \"חיפה\",\n" +
                "        \"city_name2\": \"\",\n" +
                "        \"city_name3\": \"\",\n" +
                "        \"city_gps_lat\": \"32.830360\",\n" +
                "        \"city_gps_lng\": \"34.974339\",\n" +
                "        \"chain_name\": \"שופרסל\",\n" +
                "        \"chain_code\": \"7290027600007\",\n" +
                "        \"chain_image\": \"\",\n" +
                "        \"sub_chain_name\": \"שופרסל שלי\",\n" +
                "        \"sub_chain_code\": \"1\",\n" +
                "        \"sub_chain_image\": \"super_sal_sheli.jpg\",\n" +
                "        \"distance\": \"0.9466511554081535\"\n" +
                "    }\n" +
                "]";
        return new ResponseEntity<>(storesJsonString, HttpStatus.OK);
    }
}