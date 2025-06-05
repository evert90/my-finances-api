package br.com.erp.client;

import br.com.erp.bean.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class IdpClient {

    @Value("${IDP_URL}")
    private String idpUrl;

    private final RestTemplate restTemplate;

    @Cacheable("userInfo")
    public UserInfo getUserInfo(String token) {
        String gatewayUrl = idpUrl + "/oauth2/userInfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                gatewayUrl,
                HttpMethod.GET,
                entity,
                UserInfo.class
        ).getBody();
    }
}
