package shopcompare.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shopcompare.datacontainers.Location;
import shopcompare.datacontainers.Store;

import java.util.List;

@Component
public class StoreService {

    private final SuperGetApi superGetApi;

    @Autowired
    public StoreService(SuperGetApi superGetApi) {
        this.superGetApi = superGetApi;
    }

    public List<Store> getStoresByGps(Location location, int radiusInKm) {
        return superGetApi.getStoresByGps(location, radiusInKm);
    }
}
