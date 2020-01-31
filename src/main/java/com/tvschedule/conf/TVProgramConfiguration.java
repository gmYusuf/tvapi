package com.tvschedule.conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class TVProgramConfiguration extends Configuration {
    private String writerName;
    @JsonProperty
    public String getWriterName() {
        return writerName;
    }
    @JsonProperty
    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }
}
