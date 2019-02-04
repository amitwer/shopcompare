package shopcompare.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
@Slf4j
public class Beans {
    private final RestTemplateBuilder restTemplateBuilder;
    @Value("${proxy.should-use:false}")
    private boolean shouldUseProxy;
    @Value("${proxy.port:}")
    private int proxyPort;

    Beans(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.requestFactory(this::getRequestFactory).build();
    }

    private ClientHttpRequestFactory getRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (shouldUseProxy) {
            log.info("Redirecting requests through proxy at localhost:{}", proxyPort);
            Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("localhost", proxyPort));
            requestFactory.setProxy(proxy);
        }
        return requestFactory;
    }
}
