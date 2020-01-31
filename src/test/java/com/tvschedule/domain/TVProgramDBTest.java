package com.tvschedule.domain;

import com.tvschedule.dao.TVProgramDB;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TVProgramDBTest {
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
    @Test
    public void isTVProgramDBFilled(){
        TVProgramDB tempTVProgramDB = new TVProgramDB(tvPrograms);
        assertEquals(false, tempTVProgramDB.getTvProgramsAllWeek().isEmpty());
    }
    @Test
    public void getTVProgramFromDB(){
        TVProgramDB tempTVProgramDB = new TVProgramDB(tvPrograms);
        assertEquals(tvPrograms.get(0).getName(), tempTVProgramDB.getTvProgramsAllWeek().get(0).getName());
    }
    @Test
    public void createTVProgramStoreDB(){
        int expectedSizeAfterCreateNewProgram = tvPrograms.size() + 1;
        TVProgramDB tempTVProgramDB = new TVProgramDB(tvPrograms);
        TVProgram tvProgram = new TVProgram("idCreate","New BBC", "2","2020-01-22",
                "09:20","","666");
        tempTVProgramDB.createNewTVProgram(tvProgram);
        assertEquals(expectedSizeAfterCreateNewProgram, tempTVProgramDB.getTvProgramsAllWeek().size());
    }
    @Test
    public void updateTVProgramStoreDB(){
        TVProgramDB tempTVProgramDB = new TVProgramDB(tvPrograms);
        TVProgram tvProgram = new TVProgram("id1","New Name", "2","2020-01-22",
                "09:20","","666");
        tempTVProgramDB.updateTVProgram(tvProgram);
        assertEquals(tvProgram.getName(), tempTVProgramDB.getTvProgramsAllWeek().get(0).getName());
    }
    @Test
    public void deleteTVProgramStoreDB(){
        TVProgramDB tempTVProgramDB = new TVProgramDB(tvPrograms);
        Boolean result = tempTVProgramDB.deleteTVProgram("id1");
        assertEquals(true, result);
    }
}
