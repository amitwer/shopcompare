package shopcompare.datacontainers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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
}