package shopcompare.datacontainers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


/**
 * Created by JacksonGenerator on 2/1/19.
 */
@Data
public class Price {
    @JsonProperty("product_quantity_item")
    private String productQuantityItem;
    @JsonProperty("product_image")
    private String productImage;
    @JsonProperty("product_unit_id")
    private String productUnitId;
    @JsonProperty("store_product_price")
    private String storeProductPrice;
    @JsonProperty("store_product_update_time_real")
    private String storeProductUpdateTimeReal;
    @JsonProperty("sub_chain_id")
    private String subChainId;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("store_update_time")
    private String storeUpdateTime;
    @JsonProperty("product_unit_type")
    private String productUnitType;
    @JsonProperty("city_name2")
    private String cityName2;
    @JsonProperty("product_description")
    private String productDescription;
    @JsonProperty("city_name3")
    private String cityName3;
    @JsonProperty("manufacturer_id")
    private String manufacturerId;
    @JsonProperty("product_barcode")
    private String productBarcode;
    @JsonProperty("store_gps_lat")
    private String storeGpsLat;
    @JsonProperty("product_is_real_barcode")
    private String productIsRealBarcode;
    @JsonProperty("store_type")
    private String storeType;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("store_product_status")
    private String storeProductStatus;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("store_gps_lng")
    private String storeGpsLng;
    @JsonProperty("store_product_price_quantity")
    private String storeProductPriceQuantity;
    @JsonProperty("chain_code")
    private String chainCode;
    @JsonProperty("country_id")
    private String countryId;
    @JsonProperty("city_id")
    private String cityId;
    @JsonProperty("store_area")
    private String storeArea;
    @JsonProperty("store_product_last_price")
    private String storeProductLastPrice;
    @JsonProperty("chain_id")
    private String chainId;
    @JsonProperty("city_gps_lat")
    private String cityGpsLat;
    @JsonProperty("chain_image")
    private String chainImage;
    @JsonProperty("store_product_barcode")
    private String storeProductBarcode;
    @JsonProperty("store_address")
    private String storeAddress;
    @JsonProperty("sub_chain_image")
    private String subChainImage;
    @JsonProperty("city_gps_lng")
    private String cityGpsLng;
    @JsonProperty("product_quantity")
    private String productQuantity;
    @JsonProperty("product_unit_name")
    private String productUnitName;
    @JsonProperty("promo")
    private List promo;
    @JsonProperty("sub_chain_name")
    private String subChainName;
    @JsonProperty("city_name")
    private String cityName;
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("store_name")
    private String storeName;
    @JsonProperty("chain_name")
    private String chainName;
    @JsonProperty("store_id")
    private String storeId;
    @JsonProperty("store_code")
    private String storeCode;
    @JsonProperty("store_zip_code")
    private String storeZipCode;
    @JsonProperty("manufacturer_name")
    private String manufacturerName;
    @JsonProperty("store_product_id")
    private String storeProductId;
    @JsonProperty("store_insert_time")
    private String storeInsertTime;
    @JsonProperty("product_update_time")
    private String productUpdateTime;
    @JsonProperty("store_product_update_time")
    private String storeProductUpdateTime;
    @JsonProperty("sub_chain_code")
    private String subChainCode;
}