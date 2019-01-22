package shopcompare.controllers.clientside;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopcompare.services.CityService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the client side view of the system: Verify that the mapped views actually return HTML pages, and that the status codes are as defined.
 */
//@ActiveProfiles("test")
@Tags({@Tag("Short"), @Tag("All")})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShopCompareControllerClientSideTest {

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    @LocalServerPort
    int port;
    @MockBean
    private CityService cityService;
    private String baseAddress;

    @BeforeEach
    void setup() {
        this.baseAddress = "http://localhost:" + port + "/";
    }

    @Test
    void testMainPageController() {
        ResponseEntity<String> forEntity = testRestTemplate.getForEntity(baseAddress, String.class);
        String htmlPage = forEntity.getBody();
        assertThat(HttpStatus.OK).as("Http status").isEqualTo(forEntity.getStatusCode());
        assertThat(htmlPage).contains("<meta name=\"login_page\"/>");
        Approvals.verify(htmlPage);
    }


}
