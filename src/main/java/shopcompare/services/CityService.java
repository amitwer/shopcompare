package shopcompare.services;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CityService {
    public List<String> getCities() {
        return Collections.singletonList("Jerusalem");
    }
}
