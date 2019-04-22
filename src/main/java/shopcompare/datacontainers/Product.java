package shopcompare.datacontainers;

import java.util.Objects;

public class Product {
    private String name;//dummy. for the moment

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        final Product other = (Product) o;
        if (!other.canEqual(this)) return false;


        return Objects.equals(this.name, other.name);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Product;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;

        result = result * PRIME + (this.name == null ? 43 : this.name.hashCode());
        return result;
    }

    public String toString() {
        return "Product(name=" + this.getName() + ")";
    }
}
