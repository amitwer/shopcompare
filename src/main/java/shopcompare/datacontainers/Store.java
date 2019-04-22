package shopcompare.datacontainers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Store {
    @JsonProperty("store_area")
    private String storeArea;
    @JsonProperty("chain_id")
    private String chainId;
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("city_gps_lat")
    private String cityGpsLat;
    @JsonProperty("chain_image")
    private String chainImage;
    @JsonProperty("store_address")
    private String storeAddress;
    @JsonProperty("sub_chain_image")
    private String subChainImage;
    @JsonProperty("sub_chain_id")
    private String subChainId;
    @JsonProperty("city_gps_lng")
    private String cityGpsLng;
    @JsonProperty("city_name")
    private String cityName;
    @JsonProperty("sub_chain_name")
    private String subChainName;
    @JsonProperty("store_update_time")
    private String storeUpdateTime;
    @JsonProperty("store_name")
    private String storeName;
    @JsonProperty("city_name2")
    private String cityName2;
    @JsonProperty("city_name3")
    private String cityName3;
    @JsonProperty("chain_name")
    private String chainName;
    @JsonProperty("store_id")
    private String storeId;
    @JsonProperty("store_code")
    private String storeCode;
    @JsonProperty("store_gps_lat")
    private String storeGpsLat;
    @JsonProperty("store_zip_code")
    private String storeZipCode;
    @JsonProperty("store_type")
    private String storeType;
    @JsonProperty("store_insert_time")
    private String storeInsertTime;
    @JsonProperty("store_gps_lng")
    private String storeGpsLng;
    @JsonProperty("chain_code")
    private String chainCode;
    @JsonProperty("city_id")
    private String cityId;
    @JsonProperty("sub_chain_code")
    private String subChainCode;
♥

    public String getStoreArea() {
        return this.storeArea;
    }

    public void setStoreArea(String storeArea) {
        this.storeArea = storeArea;
    }

    public String getChainId() {
        return this.chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCityGpsLat() {
        return this.cityGpsLat;
    }

    public void setCityGpsLat(String cityGpsLat) {
        this.cityGpsLat = cityGpsLat;
    }

    public String getChainImage() {
        return this.chainImage;
    }

    public void setChainImage(String chainImage) {
        this.chainImage = chainImage;
    }

    public String getStoreAddress() {
        return this.storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getSubChainImage() {
        return this.subChainImage;
    }

    public void setSubChainImage(String subChainImage) {
        this.subChainImage = subChainImage;
    }

    public String getSubChainId() {
        return this.subChainId;
    }

    public void setSubChainId(String subChainId) {
        this.subChainId = subChainId;
    }

    public String getCityGpsLng() {
        return this.cityGpsLng;
    }

    public void setCityGpsLng(String cityGpsLng) {
        this.cityGpsLng = cityGpsLng;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSubChainName() {
        return this.subChainName;
    }

    public void setSubChainName(String subChainName) {
        this.subChainName = subChainName;
    }

    public String getStoreUpdateTime() {
        return this.storeUpdateTime;
    }

    public void setStoreUpdateTime(String storeUpdateTime) {
        this.storeUpdateTime = storeUpdateTime;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCityName2() {
        return this.cityName2;
    }

    public void setCityName2(String cityName2) {
        this.cityName2 = cityName2;
    }

    public String getCityName3() {
        return this.cityName3;
    }

    public void setCityName3(String cityName3) {
        this.cityName3 = cityName3;
    }

    public String getChainName() {
        return this.chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public String getStoreId() {
        return this.storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreCode() {
        return this.storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreGpsLat() {
        return this.storeGpsLat;
    }

    public void setStoreGpsLat(String storeGpsLat) {
        this.storeGpsLat = storeGpsLat;
    }

    public String getStoreZipCode() {
        return this.storeZipCode;
    }

    public void setStoreZipCode(String storeZipCode) {
        this.storeZipCode = storeZipCode;
    }

    public String getStoreType() {
        return this.storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreInsertTime() {
        return this.storeInsertTime;
    }

    public void setStoreInsertTime(String storeInsertTime) {
        this.storeInsertTime = storeInsertTime;
    }

    public String getStoreGpsLng() {
        return this.storeGpsLng;
    }

    public void setStoreGpsLng(String storeGpsLng) {
        this.storeGpsLng = storeGpsLng;
    }

    public String getChainCode() {
        return this.chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public String getCityId() {
        return this.cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getSubChainCode() {
        return this.subChainCode;
    }

    public void setSubChainCode(String subChainCode) {
        this.subChainCode = subChainCode;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Store)) return false;
        final Store other = (Store) o;
        if (!other.canEqual(this)) return false;


        if (!Objects.equals(this.storeArea, other.storeArea)) return false;


        if (!Objects.equals(this.chainId, other.chainId)) return false;


        if (!Objects.equals(this.distance, other.distance)) return false;


        if (!Objects.equals(this.cityGpsLat, other.cityGpsLat))
            return false;


        if (!Objects.equals(this.chainImage, other.chainImage))
            return false;


        if (!Objects.equals(this.storeAddress, other.storeAddress))
            return false;


        if (!Objects.equals(this.subChainImage, other.subChainImage))
            return false;


        if (!Objects.equals(this.subChainId, other.subChainId))
            return false;


        if (!Objects.equals(this.cityGpsLng, other.cityGpsLng))
            return false;


        if (!Objects.equals(this.cityName, other.cityName)) return false;


        if (!Objects.equals(this.subChainName, other.subChainName))
            return false;


        if (!Objects.equals(this.storeUpdateTime, other.storeUpdateTime))
            return false;


        if (!Objects.equals(this.storeName, other.storeName)) return false;


        if (!Objects.equals(this.cityName2, other.cityName2)) return false;


        if (!Objects.equals(this.cityName3, other.cityName3)) return false;


        if (!Objects.equals(this.chainName, other.chainName)) return false;


        if (!Objects.equals(this.storeId, other.storeId)) return false;


        if (!Objects.equals(this.storeCode, other.storeCode)) return false;


        if (!Objects.equals(this.storeGpsLat, other.storeGpsLat))
            return false;


        if (!Objects.equals(this.storeZipCode, other.storeZipCode))
            return false;


        if (!Objects.equals(this.storeType, other.storeType)) return false;


        if (!Objects.equals(this.storeInsertTime, other.storeInsertTime))
            return false;


        if (!Objects.equals(this.storeGpsLng, other.storeGpsLng))
            return false;


        if (!Objects.equals(this.chainCode, other.chainCode)) return false;


        if (!Objects.equals(this.cityId, other.cityId)) return false;


        return Objects.equals(this.subChainCode, other.subChainCode);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Store;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;

        result = result * PRIME + (this.storeArea == null ? 43 : this.storeArea.hashCode());

        result = result * PRIME + (this.chainId == null ? 43 : this.chainId.hashCode());

        result = result * PRIME + (this.distance == null ? 43 : this.distance.hashCode());

        result = result * PRIME + (this.cityGpsLat == null ? 43 : this.cityGpsLat.hashCode());

        result = result * PRIME + (this.chainImage == null ? 43 : this.chainImage.hashCode());

        result = result * PRIME + (this.storeAddress == null ? 43 : this.storeAddress.hashCode());

        result = result * PRIME + (this.subChainImage == null ? 43 : this.subChainImage.hashCode());

        result = result * PRIME + (this.subChainId == null ? 43 : this.subChainId.hashCode());

        result = result * PRIME + (this.cityGpsLng == null ? 43 : this.cityGpsLng.hashCode());

        result = result * PRIME + (this.cityName == null ? 43 : this.cityName.hashCode());

        result = result * PRIME + (this.subChainName == null ? 43 : this.subChainName.hashCode());

        result = result * PRIME + (this.storeUpdateTime == null ? 43 : this.storeUpdateTime.hashCode());

        result = result * PRIME + (this.storeName == null ? 43 : this.storeName.hashCode());

        result = result * PRIME + (this.cityName2 == null ? 43 : this.cityName2.hashCode());

        result = result * PRIME + (this.cityName3 == null ? 43 : this.cityName3.hashCode());

        result = result * PRIME + (this.chainName == null ? 43 : this.chainName.hashCode());

        result = result * PRIME + (this.storeId == null ? 43 : this.storeId.hashCode());

        result = result * PRIME + (this.storeCode == null ? 43 : this.storeCode.hashCode());

        result = result * PRIME + (this.storeGpsLat == null ? 43 : this.storeGpsLat.hashCode());

        result = result * PRIME + (this.storeZipCode == null ? 43 : this.storeZipCode.hashCode());

        result = result * PRIME + (this.storeType == null ? 43 : this.storeType.hashCode());

        result = result * PRIME + (this.storeInsertTime == null ? 43 : this.storeInsertTime.hashCode());

        result = result * PRIME + (this.storeGpsLng == null ? 43 : this.storeGpsLng.hashCode());

        result = result * PRIME + (this.chainCode == null ? 43 : this.chainCode.hashCode());

        result = result * PRIME + (this.cityId == null ? 43 : this.cityId.hashCode());

        result = result * PRIME + (this.subChainCode == null ? 43 : this.subChainCode.hashCode());
        return result;
    }

    public String toString() {
        return "Store(storeArea=" + this.getStoreArea() + ", chainId=" + this.getChainId() + ", distance=" + this.getDistance() + ", cityGpsLat=" + this.getCityGpsLat() + ", chainImage=" + this.getChainImage() + ", storeAddress=" + this.getStoreAddress() + ", subChainImage=" + this.getSubChainImage() + ", subChainId=" + this.getSubChainId() + ", cityGpsLng=" + this.getCityGpsLng() + ", cityName=" + this.getCityName() + ", subChainName=" + this.getSubChainName() + ", storeUpdateTime=" + this.getStoreUpdateTime() + ", storeName=" + this.getStoreName() + ", cityName2=" + this.getCityName2() + ", cityName3=" + this.getCityName3() + ", chainName=" + this.getChainName() + ", storeId=" + this.getStoreId() + ", storeCode=" + this.getStoreCode() + ", storeGpsLat=" + this.getStoreGpsLat() + ", storeZipCode=" + this.getStoreZipCode() + ", storeType=" + this.getStoreType() + ", storeInsertTime=" + this.getStoreInsertTime() + ", storeGpsLng=" + this.getStoreGpsLng() + ", chainCode=" + this.getChainCode() + ", cityId=" + this.getCityId() + ", subChainCode=" + this.getSubChainCode() + ")";
    }
}