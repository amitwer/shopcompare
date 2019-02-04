package shopcompare.services;

import org.apache.commons.compress.utils.Sets;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import shopcompare.datacontainers.Price;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SuperGetApiGetPricesTest extends SuperGetApiTest {


    @Test
    void getPricesCallsSuperGetCorrectly() {
//        when(restTemplate.postForEntity(anyString()))
        String apiKey = "1234";
        when(apiKeyProvider.getApiKey()).thenReturn(apiKey);
        HashSet<String> productIds = Sets.newHashSet("aaa");
        String storeId = "storeId";
        List<Price> prices = superGetApi.getPrices(storeId, productIds);
        Assertions.assertThat(prices).isNotNull().isEmpty();
        verify(restTemplate, times(1)).postForEntity(eq(API_URL), any(), eq(String.class));
        Collection<Invocation> invocations = Mockito.mockingDetails(restTemplate).getInvocations();
        //noinspection unchecked
        HttpEntity<MultiValueMap<String, String>> request = (HttpEntity<MultiValueMap<String, String>>) invocations.iterator().next().getRawArguments()[1];
        MultiValueMap<String, String> body = request.getBody();
        assertThat(invocations).hasSize(1);
        assertThat(body).containsAllEntriesOf(getExpectedRequestParameters(apiKey, storeId, productIds));
    }

    @Test
    void verifyApiCanParseResponseProperly() {
        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(createTwoPricesResult());
        List<Price> prices = superGetApi.getPrices("dummy", new HashSet<>());
        assertThat(prices).isNotEmpty().hasSize(2);
    }

    private ResponseEntity<Object> createTwoPricesResult() {
        String results = "[\n" +
                "  {\n" +
                "    \"store_product_id\": \"1940056\",\n" +
                "    \"store_id\": \"100\",\n" +
                "    \"product_id\": \"103\",\n" +
                "    \"store_product_barcode\": \"0\",\n" +
                "    \"store_product_status\": \"0\",\n" +
                "    \"store_product_price\": \"15.1\",\n" +
                "    \"store_product_last_price\": \"0\",\n" +
                "    \"store_product_price_quantity\": \"15.1\",\n" +
                "    \"store_product_update_time\": \"2017-06-28 04:43:46\",\n" +
                "    \"store_product_update_time_real\": \"2017-06-27 21:01:00\",\n" +
                "    \"manufacturer_id\": \"10682\",\n" +
                "    \"product_name\": \"מים מינרליים\",\n" +
                "    \"product_image\": \"\",\n" +
                "    \"product_description\": \"מים מינרליים\",\n" +
                "    \"product_barcode\": \"3068320017846\",\n" +
                "    \"product_is_real_barcode\": \"1\",\n" +
                "    \"product_unit_type\": \"3\",\n" +
                "    \"product_quantity\": \"1\",\n" +
                "    \"product_quantity_item\": \"0\",\n" +
                "    \"country_id\": \"128\",\n" +
                "    \"product_update_time\": \"2016-11-03 05:41:21\",\n" +
                "    \"country_name\": \"ישראל\",\n" +
                "    \"country_code\": \"IL\",\n" +
                "    \"manufacturer_name\": \"evian\",\n" +
                "    \"chain_id\": \"10\",\n" +
                "    \"sub_chain_id\": \"106\",\n" +
                "    \"city_id\": \"572\",\n" +
                "    \"store_area\": \"\",\n" +
                "    \"store_type\": \"1\",\n" +
                "    \"store_name\": \"דיל שבירוכפר סבא\",\n" +
                "    \"store_code\": \"144\",\n" +
                "    \"store_address\": \"רפפורט 6\",\n" +
                "    \"store_zip_code\": \"0\",\n" +
                "    \"store_gps_lat\": \"32.192451\",\n" +
                "    \"store_gps_lng\": \"34.891903\",\n" +
                "    \"store_update_time\": \"2016-03-24 09:35:58\",\n" +
                "    \"store_insert_time\": \"2017-08-08 21:33:42\",\n" +
                "    \"chain_name\": \"שופרסל\",\n" +
                "    \"chain_code\": \"7290027600007\",\n" +
                "    \"chain_image\": \"\",\n" +
                "    \"sub_chain_name\": \"שופרסל דיל\",\n" +
                "    \"sub_chain_code\": \"2\",\n" +
                "    \"sub_chain_image\": \"super_sal_deal.jpg\",\n" +
                "    \"city_name\": \"כפר סבא\",\n" +
                "    \"city_name2\": \"כפר-סבא\",\n" +
                "    \"city_name3\": \"\",\n" +
                "    \"city_gps_lat\": \"32.178196\",\n" +
                "    \"city_gps_lng\": \"34.907612\",\n" +
                "    \"product_unit_id\": \"3\",\n" +
                "    \"product_unit_name\": \"ליטר\",\n" +
                "    \"promo\": []\n" +
                "  },\n" +
                "  {\n" +
                "    \"store_product_id\": \"1941475\",\n" +
                "    \"store_id\": \"100\",\n" +
                "    \"product_id\": \"985\",\n" +
                "    \"store_product_barcode\": \"0\",\n" +
                "    \"store_product_status\": \"1\",\n" +
                "    \"store_product_price\": \"4.3\",\n" +
                "    \"store_product_last_price\": \"4.5\",\n" +
                "    \"store_product_price_quantity\": \"5.38\",\n" +
                "    \"store_product_update_time\": \"2018-12-11 03:59:22\",\n" +
                "    \"store_product_update_time_real\": \"2018-05-22 09:20:00\",\n" +
                "    \"manufacturer_id\": \"4955\",\n" +
                "    \"product_name\": \"במבה 80 גרם\",\n" +
                "    \"product_image\": \"\",\n" +
                "    \"product_description\": \"במבה\",\n" +
                "    \"product_barcode\": \"7290000066318\",\n" +
                "    \"product_is_real_barcode\": \"1\",\n" +
                "    \"product_unit_type\": \"1\",\n" +
                "    \"product_quantity\": \"80\",\n" +
                "    \"product_quantity_item\": \"0\",\n" +
                "    \"country_id\": \"128\",\n" +
                "    \"product_update_time\": \"2016-09-11 15:57:41\",\n" +
                "    \"country_name\": \"ישראל\",\n" +
                "    \"country_code\": \"IL\",\n" +
                "    \"manufacturer_name\": \"אסם\",\n" +
                "    \"chain_id\": \"10\",\n" +
                "    \"sub_chain_id\": \"106\",\n" +
                "    \"city_id\": \"572\",\n" +
                "    \"store_area\": \"\",\n" +
                "    \"store_type\": \"1\",\n" +
                "    \"store_name\": \"דיל שבירוכפר סבא\",\n" +
                "    \"store_code\": \"144\",\n" +
                "    \"store_address\": \"רפפורט 6\",\n" +
                "    \"store_zip_code\": \"0\",\n" +
                "    \"store_gps_lat\": \"32.192451\",\n" +
                "    \"store_gps_lng\": \"34.891903\",\n" +
                "    \"store_update_time\": \"2016-03-24 09:35:58\",\n" +
                "    \"store_insert_time\": \"2017-08-08 21:33:42\",\n" +
                "    \"chain_name\": \"שופרסל\",\n" +
                "    \"chain_code\": \"7290027600007\",\n" +
                "    \"chain_image\": \"\",\n" +
                "    \"sub_chain_name\": \"שופרסל דיל\",\n" +
                "    \"sub_chain_code\": \"2\",\n" +
                "    \"sub_chain_image\": \"super_sal_deal.jpg\",\n" +
                "    \"city_name\": \"כפר סבא\",\n" +
                "    \"city_name2\": \"כפר-סבא\",\n" +
                "    \"city_name3\": \"\",\n" +
                "    \"city_gps_lat\": \"32.178196\",\n" +
                "    \"city_gps_lng\": \"34.907612\",\n" +
                "    \"product_unit_id\": \"1\",\n" +
                "    \"product_unit_name\": \"גרם\",\n" +
                "    \"promo\": [\n" +
                "      {\n" +
                "        \"store_promo_product_id\": \"109019696\",\n" +
                "        \"store_product_id\": \"1941475\",\n" +
                "        \"store_promo_id\": \"2471446\",\n" +
                "        \"store_id\": \"100\",\n" +
                "        \"product_id\": \"985\",\n" +
                "        \"store_promo_product_is_gift\": \"0\",\n" +
                "        \"sub_chain_id\": \"106\",\n" +
                "        \"store_promo_code\": \"1901194\",\n" +
                "        \"store_promo_is_all_sub_chain\": \"0\",\n" +
                "        \"store_promo_no_product\": \"0\",\n" +
                "        \"store_promo_insert_time\": \"2019-01-29 04:09:57\",\n" +
                "        \"store_promo_reward_type\": \"10\",\n" +
                "        \"store_promo_reward_type_other\": \"\",\n" +
                "        \"store_promo_description\": \"6ב 20חטיפים מלוחים אסם 40-80 גרם\",\n" +
                "        \"store_promo_description_more\": \"\",\n" +
                "        \"store_promo_start\": \"2019-01-29 00:00:00\",\n" +
                "        \"store_promo_end\": \"2019-02-25 23:59:00\",\n" +
                "        \"store_promo_min_item\": \"6\",\n" +
                "        \"store_promo_max_item\": \"0\",\n" +
                "        \"store_promo_item_price\": \"0\",\n" +
                "        \"store_promo_percent\": \"0\",\n" +
                "        \"store_promo_total_price\": \"20\",\n" +
                "        \"store_promo_min_price\": \"10\",\n" +
                "        \"store_promo_min_purchase_amount\": \"0\",\n" +
                "        \"store_promo_club_1\": \"0\",\n" +
                "        \"store_promo_club_2\": \"0\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    private MultiValueMap<String, String> getExpectedRequestParameters(String apiKey, String storeId, Collection<String> products) {
        MultiValueMap<String, String> expectedValues = new LinkedMultiValueMap<>();
        products.forEach(pid -> expectedValues.add("product_id[]", pid));
        expectedValues.add("store_id", storeId);
        expectedValues.add("action", "GetPriceByProductID");
        expectedValues.add("api_key", apiKey);
        return expectedValues;
    }


}