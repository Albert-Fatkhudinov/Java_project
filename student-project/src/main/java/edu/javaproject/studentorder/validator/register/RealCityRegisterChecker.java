package edu.javaproject.studentorder.validator.register;


import edu.javaproject.studentorder.config.Config;
import edu.javaproject.studentorder.domain.Person;
import edu.javaproject.studentorder.domain.register.CityRegisterRequest;
import edu.javaproject.studentorder.domain.register.CityRegisterResponse;
import edu.javaproject.studentorder.exception.CityRegisterException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

public class RealCityRegisterChecker implements CityRegisterChecker {

    public CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException {
        try {
            CityRegisterRequest cityRegisterRequest = new CityRegisterRequest(person);

            Client client = ClientBuilder.newClient();
            CityRegisterResponse cityRegisterResponse = client.target(Config.getProperty(Config.CR_URL))
                    .request("application/json")
                    .post(Entity.entity(cityRegisterRequest, "application/json"))
                    .readEntity(CityRegisterResponse.class);

            return cityRegisterResponse;
        } catch (Exception exception) {
            throw new CityRegisterException("1", exception.getMessage(), exception);
        }
    }
}
