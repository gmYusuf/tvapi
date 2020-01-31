package com.tvschedule.domain;

import com.tvschedule.TVProgramApplication;
import com.tvschedule.conf.TVProgramConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Rule;
import org.junit.Test;
import javax.ws.rs.client.Client;
import java.util.ArrayList;

import static com.tvschedule.dao.TVProgramUtilities.JSONtoTVProgramsParser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/*
*
* */
public class TVProgramApplicationTest {
    @Rule
    public final DropwizardAppRule<TVProgramConfiguration> RULE =
            new DropwizardAppRule<TVProgramConfiguration>(TVProgramApplication.class,
                    ResourceHelpers.resourceFilePath("config.yml"));

    @Test
    public void runServerTest() {
       Client client = new JerseyClientBuilder().build();
        String response = client.target( String.format("http://localhost:%d/tvprograms", RULE.getLocalPort())
        ).request().get(String.class);
        ArrayList<TVProgram> responseTVPrograms = new ArrayList<TVProgram>();;
        JSONtoTVProgramsParser(response,responseTVPrograms);
        assertEquals(false,responseTVPrograms.isEmpty());
    }
}
