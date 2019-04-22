package shopcompare.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "direct")
public class DirectSearchParams {
    private Set<String> stores;
    private Set<String> products;


    public Set<String> getStores() {
        return this.stores;
    }

    public void setStores(Set<String> stores) {
        this.stores = stores;
    }

    public Set<String> getProducts() {
        return this.products;
    }

    public void setProducts(Set<String> products) {
        this.products = products;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DirectSearchParams)) return false;
        final DirectSearchParams other = (DirectSearchParams) o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(this.stores, other.stores)) return false;
        return Objects.equals(this.products, other.products);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DirectSearchParams;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.stores == null ? 43 : this.stores.hashCode());
        result = result * PRIME + (this.products == null ? 43 : this.products.hashCode());
        return result;
    }

    public String toString() {
        return "DirectSearchParams(stores=" + this.getStores() + ", products=" + this.getProducts() + ")";
    }
}
