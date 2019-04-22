package shopcompare.datacontainers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PriceResultByStore {
    private final List<PriceResult> priceList = new LinkedList<>();
    private final String storeName;

    public PriceResultByStore(String storeName, PriceResult... prices) {
        this.storeName = storeName;
        this.priceList.addAll(Arrays.asList(prices));
    }

    public void add(PriceResult price) {
        priceList.add(price);
    }

    public List<PriceResult> getPriceList() {
        return this.priceList;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PriceResultByStore)) return false;
        final PriceResultByStore other = (PriceResultByStore) o;
        if (!other.canEqual(this)) return false;


        if (!Objects.equals(this.priceList, other.priceList)) return false;


        return Objects.equals(this.storeName, other.storeName);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PriceResultByStore;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;

        result = result * PRIME + (this.priceList == null ? 43 : this.priceList.hashCode());

        result = result * PRIME + (this.storeName == null ? 43 : this.storeName.hashCode());
        return result;
    }

    public String toString() {
        return "PriceResultByStore(priceList=" + this.getPriceList() + ", storeName=" + this.getStoreName() + ")";
    }
}
