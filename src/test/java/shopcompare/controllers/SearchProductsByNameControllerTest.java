package shopcompare.controllers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import shopcompare.datacontainers.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tags({@Tag("Short"), @Tag("All"), @Tag("Search-Products")})
class SearchProductsByNameControllerTest {
    @Test
    void searchProductName() {
        SearchProductsByNameController searchProductsByNameController = getSearchProductByNameController();
        assertThat(searchProductsByNameController.searchProductName("name", new ExtendedModelMap())).isEqualTo("productSearchResults");
    }

    @Test
    void searchProductByNameIsDynamic() {
        SearchProductsByNameController searchProductsByNameController = getSearchProductByNameController();
        ExtendedModelMap model = new ExtendedModelMap();
        searchProductsByNameController.searchProductName("dummy", model);
        Object products = model.get("products");
        assertThat(products).isNotNull().isInstanceOf(List.class);
        //noinspection unchecked
        assertThat((List) products).isNotEmpty().first().isInstanceOf(Product.class);
    }

    private SearchProductsByNameController getSearchProductByNameController() {
        return new SearchProductsByNameController();
    }
}