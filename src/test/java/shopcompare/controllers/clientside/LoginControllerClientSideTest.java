package shopcompare.controllers.clientside;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import shopcompare.services.CityService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the client side view of the system: Verify that the mapped views actually return HTML pages, and that the status codes are as defined.
 */
//@ActiveProfiles("test")
@Tags({@Tag("Short"), @Tag("All")})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerClientSideTest {

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    @LocalServerPort
    int port;
    @MockBean
    private CityService cityService;
    private String baseAddress;

    private static Stream<List<String>> testCitiesAreFromJavaProvider() {
        return Stream.of(Arrays.asList("City1", "City2"),
                Arrays.asList("City1", "City2", "City3")
        );
    }

    @BeforeEach
    void setup() {
        this.baseAddress = "http://localhost:" + port + "/";
    }

    @Test
    void testSuccessfulLogin() {
        ResponseEntity<String> loginResponse = login();
        String htmlPage = loginResponse.getBody();
        assertThat(htmlPage).contains("<meta name=\"main_application_page\"/>");
//        Approvals.verify(htmlPage);
    }

    private ResponseEntity<String> login() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", "Amit");
        params.add("password", "wertheimer");
//        baseAddress = "http://localhost:" + port + "/";
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity(baseAddress + "login", params, String.class);
        assertThat(loginResponse.getStatusCode()).as("Http status").isEqualTo(HttpStatus.OK);
        return loginResponse;
    }

    @ParameterizedTest(name = "test cities come from Java [{index}] {arguments}")
    @MethodSource("testCitiesAreFromJavaProvider")
    void testCitiesAreFromJava(List<String> expectedCities) {
        Mockito.when(cityService.getCities()).thenReturn(expectedCities);
        ResponseEntity<String> loginResponse = login();
        Document html = Jsoup.parse(Optional.ofNullable(loginResponse.getBody()).orElseThrow(() -> new IllegalStateException("Body was empty")));
        List<String> actualCities = html.getElementById("city").children().stream()
                .map(Element::val)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
        assertThat(actualCities).containsExactlyInAnyOrderElementsOf(expectedCities);
    }
}
