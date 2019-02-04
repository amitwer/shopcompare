package shopcompare.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConfigurationProperties(prefix = "direct")
@Data
public class DirectSearchParams {
    private Set<String> stores;
    private Set<String> products;

}
