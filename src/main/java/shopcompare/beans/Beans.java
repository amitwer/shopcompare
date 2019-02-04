package shopcompare.beans;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class Beans {
    private final RestTemplateBuilder restTemplateBuilder;

    Beans(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.requestFactory(this::getRequestFactory).build();
    }

    private ClientHttpRequestFactory getRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888));
        requestFactory.setProxy(proxy);
        return requestFactory;
    }
}
