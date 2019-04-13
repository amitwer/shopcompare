package shopcompare.datacontainers;

import lombok.Data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Data
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
}
