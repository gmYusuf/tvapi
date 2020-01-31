package com.tvschedule.dao;

import com.tvschedule.domain.TVProgram;
import java.util.ArrayList;
import java.util.UUID;

public class TVProgramDB {

    private ArrayList<TVProgram> tvProgramsAllWeek = null;
    /*
    * This constructor will get data from TV maze API
    * After that it will fill tvProgramsAllWeek List
     * */
    public  TVProgramDB(){
        RetrieveSchedule tvList = new RetrieveSchedule();
        this.tvProgramsAllWeek = tvList.getTvProgramsAllWeek();
    }
    public  TVProgramDB(ArrayList<TVProgram> tvPrograms){
        this.tvProgramsAllWeek = tvPrograms;
    }
    /**
    * Get All TV Programs That Day
    * */
    public ArrayList<TVProgram> getTvProgramByDate(String Date){
        ArrayList<TVProgram> TVProgramListByDate = new ArrayList<TVProgram>();
        int indexOfTVProgram = -1;
        for (int i = 0; i < tvProgramsAllWeek.size(); i++) {
            if (tvProgramsAllWeek.get(i).getAired().compareTo(Date) == 0) {
                TVProgramListByDate.add(tvProgramsAllWeek.get(i));
            }
        }
        return  TVProgramListByDate;
    }
    /**
     * Get All TV Programs Contains That Name
     * */
    public ArrayList<TVProgram> getTvProgramByName(String Name){
        ArrayList<TVProgram> TVProgramListByName = new ArrayList<TVProgram>();
        int indexOfTVProgram = -1;
        for (int i = 0; i < tvProgramsAllWeek.size(); i++) {
            if (tvProgramsAllWeek.get(i).getName().toLowerCase().contains(Name.toLowerCase())) {
                TVProgramListByName.add(tvProgramsAllWeek.get(i));
            }
        }
        return  TVProgramListByName;
    }
    public ArrayList<TVProgram> getTvProgramsAllWeek() {
        return this.tvProgramsAllWeek;
    }
    /**
    * Add new Tv program inputs validated with Validation Notations.
    * Client Will not send End Time and ID of TV Program
    * Field : tvProgramFromClient
    * That function fill missing fields and add to list.
    * */
    public  Boolean createNewTVProgram(TVProgram tvProgramFromClient){
        UUID uniqueKey = UUID.randomUUID();
        TVProgram tempTVProgram = new TVProgram();
        String starTime = tvProgramFromClient.getStartTime();
        int airTime = Integer.parseInt(tvProgramFromClient.getAirTime());
        String endTime = TVProgramUtilities.endTimeCalculation(starTime,airTime);
        tempTVProgram.setProgram(uniqueKey.toString(), tvProgramFromClient.getName(),
                tvProgramFromClient.getChannel(), tvProgramFromClient.getAired(), starTime, endTime);
        this.tvProgramsAllWeek.add(tempTVProgram);
        return true;
    }
    /**
    * Update TV program
    * Check Conflict Time
    * */
    public  Boolean updateTVProgram(TVProgram tvProgram){
        Boolean isUpdated = false;
        for (int i = 0; i <tvProgramsAllWeek.size() ; i++) {
            if(tvProgramsAllWeek.get(i).getID().equalsIgnoreCase(tvProgram.getID())){
                int airTime = Integer.parseInt(tvProgram.getAirTime());
                String endTime = TVProgramUtilities.endTimeCalculation(tvProgram.getStartTime(),airTime);
                tvProgram.setEndTime(endTime);
                tvProgramsAllWeek.set(i,tvProgram);
                isUpdated = true;
            }
        }
        return isUpdated;
    }
    /**
    * Delete Program From TV List
    *
    * */
    public Boolean deleteTVProgram(String tvProgramID) {
        Boolean isDeleted = false;
        for(int i = 0; i < tvProgramsAllWeek.size(); i++) {
            if(tvProgramsAllWeek.get(i).getID().equalsIgnoreCase(tvProgramID)){
                isDeleted = tvProgramsAllWeek.remove(tvProgramsAllWeek.get(i));
            }
        }
        return isDeleted;
    }

    /*
    * */
    public String performHealthCheck() {
        try {
            tvProgramsAllWeek.get(0);//if list is not fill
        } catch (Exception ex) {
            return "Not Healthy";
        }
        return null;
    }
}
