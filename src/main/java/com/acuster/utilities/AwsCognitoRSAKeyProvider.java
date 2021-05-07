package com.acuster.utilities;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * A class that implements RSAKeyProvider
 * Modified from a class created by AndiDev in this stackoverflow post https://stackoverflow.com/questions/48356287/is-there-any-java-example-of-verification-of-jwt-for-aws-cognito-api
 */

public class AwsCognitoRSAKeyProvider implements RSAKeyProvider {

    final Logger logger = LogManager.getLogger(this.getClass());

    private URL awsKidUrl = null;
    private final JwkProvider provider;

    public AwsCognitoRSAKeyProvider(String awsCognitoRegion, String awsUserPoolsId) {
        String url = String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", awsCognitoRegion, awsUserPoolsId);
        try {
            awsKidUrl = new URL(url);
        } catch (MalformedURLException e) {
            logger.error(String.format("Invalid URL provided, URL=%s", url));
        }
        provider = new JwkProviderBuilder(awsKidUrl).build();
    }

    public RSAPublicKey getPublicKeyById(String kid) {
        RSAPublicKey rsaPublicKey = null;

        try {
            rsaPublicKey = (RSAPublicKey) provider.get(kid).getPublicKey();
        } catch (JwkException e) {
            logger.error(String.format("Failed to get JWT kid=%s from awsKidUrl=%s", kid, awsKidUrl));
        }

        return rsaPublicKey;
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return null;
    }

    @Override
    public String getPrivateKeyId() {
        return null;
    }
}
