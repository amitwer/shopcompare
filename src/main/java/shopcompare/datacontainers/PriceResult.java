package shopcompare.datacontainers;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

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

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStore() {
        return this.store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PriceResult)) return false;
        final PriceResult other = (PriceResult) o;
        if (!other.canEqual(this)) return false;


        if (!Objects.equals(this.productName, other.productName))
            return false;


        if (!Objects.equals(this.barcode, other.barcode)) return false;


        if (!Objects.equals(this.store, other.store)) return false;


        return Objects.equals(this.price, other.price);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PriceResult;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;

        result = result * PRIME + (this.productName == null ? 43 : this.productName.hashCode());

        result = result * PRIME + (this.barcode == null ? 43 : this.barcode.hashCode());

        result = result * PRIME + (this.store == null ? 43 : this.store.hashCode());

        result = result * PRIME + (this.price == null ? 43 : this.price.hashCode());
        return result;
    }

    public String toString() {
        return "PriceResult(productName=" + this.getProductName() + ", barcode=" + this.getBarcode() + ", store=" + this.getStore() + ", price=" + this.getPrice() + ")";
    }
}
