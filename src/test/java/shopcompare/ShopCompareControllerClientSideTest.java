package shopcompare;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the client side view of the system: Verify that the mapped views actually return HTML pages, and that the status codes are as defined.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShopCompareControllerClientSideTest {

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    @LocalServerPort
    int port;
    private String baseAddress;

    @BeforeEach
    public void setup(){
        this.baseAddress="http://localhost:"+port+"/";
    }
    @Test
    public void testMainPageController(){
        ResponseEntity<String> forEntity = testRestTemplate.getForEntity(baseAddress, String.class);
        String htmlPage = forEntity.getBody();
        assertThat(HttpStatus.OK).as("Http status").isEqualTo(forEntity.getStatusCode());
        assertThat(htmlPage).contains("<meta name=\"login_page\"/>");
        Approvals.verify(htmlPage);
    }

    @Test
    public void testSuccessfulLogin(){
        MultiValueMap<String, Object> params = new LinkedMultiValueMap();
        params.add("login","Amit");
        params.add("password","wertheimer");
        baseAddress = "http://localhost:" + port + "/";
        ResponseEntity<String> forEntity = testRestTemplate.postForEntity(baseAddress + "login", params,String.class);
        String htmlPage = forEntity.getBody();
        assertThat(HttpStatus.OK).as("Http status").isEqualTo(forEntity.getStatusCode());
        assertThat(htmlPage).contains("<meta name=\"search_page\"/>");
//        Approvals.verify(htmlPage);
    }

    @Test
    public void testSuccessfulProductSearch(){
        MultiValueMap<String, Object> params = new LinkedMultiValueMap();
        params.add("product_name","product");
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(baseAddress + "searchProductName", params, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("<meta name=\"product_search_results\"/>");

    }
}
