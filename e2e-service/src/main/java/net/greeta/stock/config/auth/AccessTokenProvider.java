package net.greeta.stock.config.auth;

import net.greeta.stock.client.OAuth2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccessTokenProvider {

    @Autowired
    private OAuth2Client oAuth2Client;

    @Autowired
    private UserCredentialsProvider userCredentialsProvider;

    @Value("${security.oauth2.client-id}")
    private String securityOauth2ClientId;

    @Value("${security.oauth2.grant-type}")
    private String securityOauth2GrantType;

    public String getAccessToken() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", userCredentialsProvider.getUsername());
        params.put("password", userCredentialsProvider.getPassword());
        params.put("grant_type", securityOauth2GrantType);
        params.put("client_id", securityOauth2ClientId);
        var tokenDto = oAuth2Client.getToken(params);
        return tokenDto.getAccessToken();
    }
}
