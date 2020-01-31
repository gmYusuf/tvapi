package com.tvschedule.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.tvschedule.dao.TVProgramDB;
import com.tvschedule.resource.TVProgramsResource;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import io.dropwizard.testing.junit.ResourceTestRule;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static com.tvschedule.dao.TVProgramUtilities.JSONtoTVProgramsParser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class TVProgramsResourceTest {
    // Sample TV Program List
    private ArrayList<TVProgram> tvPrograms = new  ArrayList<TVProgram>(){
        {
            add(new TVProgram("id1","Program F", "BBC","2020-01-22",
                    "09:20","","6"));
            add(new TVProgram("id2","Program ONE", "BBC ONE","2020-01-22",
                    "09:20","","6"));
            add(new TVProgram("id3","Program", "2","2020-01-22",
                    "09:20","","6"));
            add(new TVProgram("id4","Program C TURK", "2","2020-01-23",
                    "09:20","","6"));
            add(new TVProgram("id5","Program K", "2","2020-01-24",
                    "09:20","","6"));
        }
    };
     /*
     * Create DB from this list
     * */
    private TVProgramDB programList = new TVProgramDB(tvPrograms);
    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TVProgramsResource(validator,programList)).build();

    private static Validator validator;

    @BeforeClass
    public static  void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void getTVPrograms()  {
        String response = resources.client().target("/tvprograms").request().get(String.class);
        // The expected json that will be returned
        ArrayList<TVProgram> responseTVPrograms = new ArrayList<TVProgram>();;
        JSONtoTVProgramsParser(response,responseTVPrograms);
        assertThat(responseTVPrograms.get(0).getName()).isEqualTo("Program F");
        // With toString All Fields Compared.
        assertEquals(tvPrograms.get(0).toString(),responseTVPrograms.get(0).toString());
    }
    @Test
    public void getTVProgramsByName()  {
        String response = resources.client().target("/tvprograms/name/one").request().get(String.class);
        // The expected json that will be returned
        ArrayList<TVProgram> responseTVPrograms = new ArrayList<TVProgram>();;
        JSONtoTVProgramsParser(response,responseTVPrograms);
        assertThat(responseTVPrograms.get(0).getName()).contains("ONE");
        // With toString All Fields Compared.
        assertEquals(tvPrograms.get(1).toString(),responseTVPrograms.get(0).toString());
    }
    @Test
    public void getTVProgramsByDate()  {
        String response = resources.client().target("/tvprograms/date/2020-01-22").request().get(String.class);
        // The expected json that will be returned
        ArrayList<TVProgram> responseTVPrograms = new ArrayList<TVProgram>();;
        JSONtoTVProgramsParser(response,responseTVPrograms);
        // With toString All Fields Compared.
        assertEquals(3,responseTVPrograms.size());
    }
    @Test
    public void createTVPrograms()  {
        TVProgram tvProgram = new TVProgram("idCreate","New BBC", "2","2020-01-22",
                "09:20","","666");
        Response response = resources.client().target("/tvprograms").request()
                .post(Entity.entity(tvProgram, MediaType.APPLICATION_JSON));

        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
    }
    @Test
    public void updateTVPrograms()  {
        TVProgram tvProgram = new TVProgram("id1","New BBC", "2","2020-01-22",
                "09:20","","666");
        Response response = resources.client().target("/tvprograms").request()
                .put(Entity.entity(tvProgram, MediaType.APPLICATION_JSON));

        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
    }
    @Test
    public void deleteTVPrograms()  {
        Response response = resources.client().target("/tvprograms/id1").request().delete();
        // The expected json that will be returned
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
    }


}
