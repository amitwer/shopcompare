package shopcompare.datacontainers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
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
        this.price = price;
    }

    public PriceResult() {

    }
}
