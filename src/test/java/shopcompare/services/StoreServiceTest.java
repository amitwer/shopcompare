package shopcompare.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shopcompare.datacontainers.Location;
import shopcompare.datacontainers.Store;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StoreServiceTest {

    private StoreService storeService;
    private SuperGetApi superGetApi;
    private Location location;

    @SuppressWarnings("WeakerAccess")
    @BeforeEach
    private void setup() {
        superGetApi = mock(SuperGetApi.class);
        storeService = new StoreService(superGetApi);
        location = new Location(10, 10);
    }

    @Test
    void getStoreByGPSReturnsListOfStores() {
        List<Store> storeList = storeService.getStoresByGps(location, 10);
        assertThat(storeList).isNotNull();
    }

    @Test
    void getStoresCallsSuperGetApi() {
        storeService.getStoresByGps(location, 10);
        verify(superGetApi, times(1)).getStoresByGps(location, 10);
    }

}