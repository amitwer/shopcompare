package shopcompare.datacontainers;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class PriceResult {
    private String productName;
    private String barcode;
    private String store;
    private String price;

    public PriceResult(String productName, String barcode, String store, String price) {
        this();
        this.productName = productName;
        this.barcode = barcode;
        this.store = store;
        if (StringUtils.isNotBlank(price)) {
            this.price = price;
        }
    }

    public PriceResult() {

    }
}
