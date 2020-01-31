package com.tvschedule;

import com.tvschedule.conf.TVProgramConfiguration;
import com.tvschedule.dao.TVProgramApplicationHealthCheck;
import com.tvschedule.dao.TVProgramDB;
import com.tvschedule.resource.TVProgramsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/*
* TV Program API
* YUSUF ALTUN
* */
public class TVProgramApplication extends Application<TVProgramConfiguration> {
    private static final String TV_MAZE_PROGRAMS_API = "TV SCHEDULE API";


    @Override
    public void run(TVProgramConfiguration tvProgramConfiguration, Environment environment) throws Exception {
        TVProgramDB programList = new TVProgramDB();
        TVProgramApplicationHealthCheck healthCheck = new TVProgramApplicationHealthCheck(programList);
        environment.healthChecks().register(TV_MAZE_PROGRAMS_API, healthCheck);
        environment.jersey().register(new TVProgramsResource(environment.getValidator(),programList));
    }
    /*
    * Run API
    * */
    public static void main(final String[] args) throws Exception {
        new TVProgramApplication().run(args);

    }
}
