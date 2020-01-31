package com.tvschedule.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class TVProgram {

    private String ID;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String channel;
    @NotBlank
    @Length(min=10, max=10)
    @Pattern(regexp="([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")
    @NotEmpty
    private String aired;// Date
    @NotNull
    @NotEmpty
    @Pattern(regexp="\\d{2}:\\d{2}")
    private String startTime;
    private String endTime;
    @NotNull
    @NotEmpty
    @Pattern(regexp="^[0-9]+$")
    private String airTime;

    /*
    * Constructor with Parameters
    * */
    public TVProgram(String ID, @NotNull @NotEmpty String name, @NotNull @NotEmpty String channel,
                        @NotBlank @Length(min = 10, max = 10) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
                            @NotEmpty String aired,@NotNull @NotEmpty @Pattern(regexp = "\\d{2}:\\d{2}") String startTime,
                                @Null String endTime, @NotNull @NotEmpty @Pattern(regexp = "^[0-9]+$") String airTime) {
        this.ID = ID;
        this.name = name;
        this.channel = channel;
        this.aired = aired;
        this.startTime = startTime;
        this.endTime = endTime;
        this.airTime = airTime;
    }

    public TVProgram() {
        //
    }

    public void setProgram(String ID, String name, String channel, String aired, String startTime, String endTime){
        this.ID = ID;
        this.name = name;
        this.channel = channel;
        this.aired = aired;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public String getAirTime() {
        return airTime;
    }

    public void setAirTime(String airTime) {
        this.airTime = airTime;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setAired(String aired) {
        this.aired = aired;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getChannel() {
        return channel;
    }

    public String getAired() {
        return aired;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Program [id=" + this.ID + ", name=" + this.name + ", channel="
                + this.channel + ", startTime=" + this.startTime + ", endTime=" + this.endTime
                    + ", aired=" + this.aired + "]";
    }

    public void setProgram(TVProgram tvProgram) {
        this.aired = tvProgram.aired;
        this.channel = tvProgram.channel;
        this.ID = tvProgram.ID;
        this.startTime = tvProgram.startTime;
        this.endTime = tvProgram.endTime;
        this.airTime = tvProgram.airTime;
        this.aired = tvProgram.aired;
    }
}
