package shopcompare.datacontainers;

public class Location {
    private double latitude;
    private double longitude;

    @java.beans.ConstructorProperties({"latitude", "longitude"})
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Location)) return false;
        final Location other = (Location) o;
        if (!other.canEqual(this)) return false;
        if (Double.compare(this.getLatitude(), other.getLatitude()) != 0) return false;
        return Double.compare(this.getLongitude(), other.getLongitude()) == 0;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Location;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $latitude = Double.doubleToLongBits(this.getLatitude());
        result = result * PRIME + (int) ($latitude >>> 32 ^ $latitude);
        final long $longitude = Double.doubleToLongBits(this.getLongitude());
        result = result * PRIME + (int) ($longitude >>> 32 ^ $longitude);
        return result;
    }

    public String toString() {
        return "Location(latitude=" + this.getLatitude() + ", longitude=" + this.getLongitude() + ")";
    }
}
