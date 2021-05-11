package com.acuster.persistence;

import com.acuster.entity.AwsTokenResponse;
import com.acuster.utilities.PropertiesLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;

public class AwsAuthorizationEndpoint implements PropertiesLoader {

    final Logger logger = LogManager.getLogger(this.getClass());

    public String postToken(String code, Properties awsCognito) {

        try {
            awsCognito = loadProperties("/awsCognito.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Client client = ClientBuilder.newClient();
        String responseType = "token";
        String clientID = awsCognito.getProperty("clientID");
        String redirect = awsCognito.getProperty("localLoginRedirect");
        String scope = "aws.cognito.signin.user.admin+openid+profile";

        WebTarget target =
                client.target("https://plant-collector.auth.us-east-2.amazoncognito.com/oauth2/token");
        Form form = new Form();
        form.param("grant_type", "authorization_code");
        form.param("client_id", clientID);
        form.param("code", code);
        form.param("redirect_uri", redirect);
        logger.error("Code: " + code);

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        String responseValue = response.readEntity(String.class);
        logger.error("post request response: " + responseValue);
        ObjectMapper mapper = new ObjectMapper();
        AwsTokenResponse tokenResponse = null;
        try {
            tokenResponse = mapper.readValue(responseValue, AwsTokenResponse.class);
        } catch (JsonProcessingException e) {
            logger.error("Could not map json to object" + e);
        }

        return tokenResponse.getIdToken();
    }
}
