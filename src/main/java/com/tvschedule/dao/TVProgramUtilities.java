package com.tvschedule.dao;

import com.tvschedule.domain.TVProgram;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;
import java.util.ArrayList;

public class TVProgramUtilities {

    public TVProgramUtilities(String jsonString) {
    }

    /*
    * Initialize Date From tv maze API
    * */
    public static Boolean tvMazeJSONtoTVProgramsParser(String stringJSON, ArrayList<TVProgram> programs) {
        JSONArray jsonArray = new JSONArray(stringJSON);
        for (int i = 0; i < jsonArray.length(); i++) {
            String airDate = jsonArray.getJSONObject(i).getString("airdate");
            String airTime = jsonArray.getJSONObject(i).getString("airtime");
            Integer runTime = 0;
            try {
              runTime = jsonArray.getJSONObject(i).getInt("runtime");

            }catch (Exception e){

            }
            JSONObject jsonShow = jsonArray.getJSONObject(i).getJSONObject("show");
            String showName = jsonShow.getString("name");
            Object networkTemp = jsonArray.getJSONObject(i).getJSONObject("show").get("network");

            String channelName = "";
            // uniqueID generated
            UUID uniqueKey = UUID.randomUUID();
            // Control network Field
            if (!JSONObject.NULL.equals(networkTemp)) {
                JSONObject jsonShowNetwork = jsonArray.getJSONObject(i).getJSONObject("show").getJSONObject("network");
                channelName = jsonShowNetwork.getString("name");
            }
            TVProgram tempProgram = new TVProgram();
            String endTime = endTimeCalculation(airTime, runTime);
            tempProgram.setProgram(uniqueKey.toString(), showName, channelName, airDate, airTime, endTime);
            tempProgram.setAirTime(runTime.toString());
            programs.add(tempProgram);
        }
        return true;
    }
    //Add Start Time to Air Time for calculating End Time
    public  static  String endTimeCalculation(String startTime, int runTime){
        int minutes = runTime;
        int hour = minutes / 60 + Integer.parseInt(startTime.substring(0,2));
        int minute = minutes % 60 + Integer.parseInt(startTime.substring(3,5));

        String stringHour =  String.format("%02d",  hour );
        String stringMinute = String.format("%02d", minute );
        String endTime = stringHour + ":" + stringMinute;
        return endTime;
    }
    /*
    *  Controlling Date format
    *  Example Format : yyyy-mm-dd -> 2020-01-26
    *
    * */
    public  static Boolean dateFormatValidation(String Date){
        Boolean isDate = Date.matches("\\d{4}-\\d{2}-\\d{2}");
        return isDate;
    }
    /*
    *   For Test Cases Parser
    *
    * */
    public static Boolean JSONtoTVProgramsParser(String stringJSON, ArrayList<TVProgram> programs) {
        JSONArray jsonArray = new JSONArray(stringJSON);
        for (int i = 0; i < jsonArray.length(); i++) {
            String id = jsonArray.getJSONObject(i).getString("id");
            String name = jsonArray.getJSONObject(i).getString("name");
            String channel = jsonArray.getJSONObject(i).getString("channel");
            String startTime = jsonArray.getJSONObject(i).getString("startTime");
            String endTime = jsonArray.getJSONObject(i).getString("endTime");
            String runTime = jsonArray.getJSONObject(i).getString("airTime");
            String aired = jsonArray.getJSONObject(i).getString("aired");
            TVProgram tempProgram = new TVProgram();
            tempProgram.setProgram(id, name, channel, aired, startTime, endTime);
            tempProgram.setAirTime(runTime.toString());
            programs.add(tempProgram);
        }
        return true;
    }
}
