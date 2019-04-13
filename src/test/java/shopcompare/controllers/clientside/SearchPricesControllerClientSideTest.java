package shopcompare.controllers.clientside;

import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
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
import shopcompare.datacontainers.PriceResult;
import shopcompare.services.PricesService;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
//                Collections.singletonList(new PriceResult("במבה", "123456", "רמי לוי תלפיות", "11.2")),
//                Collections.singletonList(new PriceResult("Bisli", "98765432", "שפע עוז", "15.0")),
                Arrays.asList(new PriceResult("product1", "barcode2", "store3", "price4"), new PriceResult("product-a", "barcode-b", "store-c", "price-d"))
        );
    }

    @ParameterizedTest
    @MethodSource("pricesAreFromModelDataProvider")
    void pricesAreFromModelData(List<PriceResult> expectedPrices) throws Exception {
        when(pricesService.getPrices(any(), any())).thenReturn(expectedPrices);
        ResultActions responsePage = mockMvc.perform(post("/getPrices").param("selectedProducts", Arrays.toString(new String[]{"dummy", "dummy"})));
        responsePage.andExpect(result -> validatePrices(result, expectedPrices));
    }

    @Test
    void testDirectAccess() throws Exception {
        String html = mockMvc.perform(get("/directAccess")).andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        Element metaId = Jsoup.parse(html).getElementById("price_table_meta_id");
        assertThat(metaId).isNotNull();
    }

    @Test
    void resultsAreSortedByProduct() throws Exception {
        when(pricesService.getPrices(any(), any())).thenReturn(Lists.newArrayList(
                new PriceResult("name1", "1", "Store1", "price11"),
                new PriceResult("name2", "2", "Store1", "price12"),
                new PriceResult("name3", "3", "Store1", "price13"),
                new PriceResult("name1", "1", "Store2", "price21"),
                new PriceResult("name1", "1", "Store2", "price22")
        ));
        String html = mockMvc.perform(post("/getPrices").param("selectedProducts", Arrays.toString(new String[]{"dummy", "dummy"}))).andReturn().getResponse().getContentAsString();
        assertThat(html).isNotBlank();
        Element pricesTable = Jsoup.parse(html).body().getElementById("prices_table");
        Elements headersRow = pricesTable.getElementsByTag("th");
        assertThat(headersRow.size()).as(headersRow.toString()).isEqualTo(4);
        assertThat(headersRow.eachText()).containsExactlyInAnyOrder("", "name1", "name2", "name3");
    }

    private void validatePrices(MvcResult mvcResult, List<PriceResult> expectedPrices) throws UnsupportedEncodingException {
        String html = mvcResult.getResponse().getContentAsString();
        Document parsedHtml = Jsoup.parse(html);
        Element pricesTable = parsedHtml.body().getElementById("prices_table");
        Elements tableBody = pricesTable.getElementsByTag("tbody");
        List<PriceResult> rows = tableBody.iterator().next().children().stream().map(this::transformRowToPrice).collect(Collectors.toList());
        assertThat(rows).containsExactlyInAnyOrderElementsOf(expectedPrices);
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
