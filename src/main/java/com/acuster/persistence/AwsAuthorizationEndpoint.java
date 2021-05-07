package com.acuster.persistence;

import com.acuster.entity.AwsTokenResponse;
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

public class AwsAuthorizationEndpoint {

    final Logger logger = LogManager.getLogger(this.getClass());

    public void getToken(){
        Client client = ClientBuilder.newClient();
        //TODO read in the uri from a properties file
        WebTarget target =
                client.target("https://plant-collector.auth.us-east-2.amazoncognito.com/oath2/authorize?response_type=token&client_id=29fon9l71edrci2b398ohtm3tf&redirect_uri=http://localhost:8080/PlantCollector_war/logged-in&scope=aws.cognito.signin.user.admin+openid+profile");

        target.request();
//        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
//        ObjectMapper mapper = new ObjectMapper();
//        CatFact fact = null;
//        try {
//            fact = mapper.readValue(response, CatFact.class);
//        } catch (JsonProcessingException e) {
//            //TODO set up logging and write this to log
//            e.printStackTrace();
//        }
//        return fact.getText();
    }

    public String postToken(String code) {
        Client client = ClientBuilder.newClient();
        WebTarget target =
                client.target("https://plant-collector.auth.us-east-2.amazoncognito.com/oauth2/token");
        Form form = new Form();
        form.param("grant_type", "authorization_code");
        form.param("client_id", "29fon9l71edrci2b398ohtm3tf");
        form.param("code", code);
        form.param("redirect_uri", "http://localhost:8080/PlantCollector_war/logged-in");
        logger.error("Code: " + code);

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        String responseValue = response.readEntity(String.class);
        logger.error("post request response: " + responseValue);
        ObjectMapper mapper = new ObjectMapper();//.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AwsTokenResponse tokenResponse = null;
        try {
            tokenResponse = mapper.readValue(responseValue, AwsTokenResponse.class);
        } catch (JsonProcessingException e) {
            logger.error("Could not map json to object" + e);
        }

        return tokenResponse.getIdToken();
    }
}
