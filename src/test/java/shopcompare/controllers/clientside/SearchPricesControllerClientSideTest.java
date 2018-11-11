package shopcompare.controllers.clientside;

import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shopcompare.datacontainers.PriceResult;
import shopcompare.services.PricesService;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

/**
 * Tests the client side view of the system: Verify that the mapped views actually return HTML pages, and that the status codes are as defined.
 */
//@ActiveProfiles("test")
@Tags({@Tag("Short"), @Tag("All")})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SearchPricesControllerClientSideTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PricesService pricesService;

    private static Stream<List<PriceResult>> pricesAreFromModelDataProvider() {
        return Stream.of(
                Collections.singletonList(new PriceResult("במבה", "123456", "רמי לוי תלפיות", "11.2")),
                Collections.singletonList(new PriceResult("Bisli", "98765432", "שפע עוז", "15.0")),
                Arrays.asList(new PriceResult("1", "2", "3", "4"), new PriceResult("a", "b", "c", "d"))
        );
    }

    @ParameterizedTest
    @MethodSource("pricesAreFromModelDataProvider")
    void pricesAreFromModelData(List<PriceResult> expectedPrices) throws Exception {
        when(pricesService.getPrices()).thenReturn(expectedPrices);
        ResultActions responsePage = mockMvc.perform(MockMvcRequestBuilders.post("/getPrices").param("selectedProducts", Arrays.toString(new String[]{"dummy", "dummy"})));
        responsePage.andExpect(result -> validatePrices(result, expectedPrices));
    }

    private void validatePrices(MvcResult mvcResult, List<PriceResult> expectedPrices) throws UnsupportedEncodingException {
        String html = mvcResult.getResponse().getContentAsString();
        Document parsedHtml = Jsoup.parse(html);
        Element pricesTable = parsedHtml.body().getElementById("prices_table");
        Elements tableBody = pricesTable.getElementsByTag("tbody");
        List<PriceResult> rows = tableBody.iterator().next().children().stream().map(this::transformRowToPrice).collect(Collectors.toList());
        Assertions.assertThat(rows).containsExactlyInAnyOrderElementsOf(expectedPrices);

    }

    private PriceResult transformRowToPrice(Element row) {
        List<String> cells = row.children().eachText();
        PriceResult priceResult = new PriceResult();
        priceResult.setProductName(cells.get(0));
        priceResult.setBarcode(cells.get(1));
        priceResult.setStore(cells.get(2));
        priceResult.setPrice(cells.get(3));
        return priceResult;
    }


}
