package com.tvschedule.dao;

import com.tvschedule.domain.TVProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class RetrieveSchedule {
    private static RetrieveSchedule scheduleInstance;
    public final String REQUEST = "http://api.tvmaze.com/schedule?country=GB&date=";
    /*
    * Get next week data
    * */
    public final int weekDayNumber = 7;
    public final String cityID = "Europe/London";//"Asia/Riyadh"
    private ArrayList<TVProgram> tvProgramsAllWeek = null;

    public RetrieveSchedule() {
        this.tvProgramsAllWeek = new ArrayList<TVProgram>();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(cityID));
         Date date = null;

        for (int i = 0; i < weekDayNumber; i++) {
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String stringMonth = "";
            String stringDay = "";

            stringMonth = String.format("%02d",  month);
            stringDay = String.format("%02d",  day);

            String requestURL = REQUEST + year + "-" + stringMonth  + "-" + stringDay;
            try {
                tvProgramsAllWeek.addAll(requestAPIData(requestURL));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Number of Days to add
            cal.add(Calendar.DAY_OF_MONTH, 1);
            //Date after adding the days to the given date
            date = cal.getTime();
            cal.setTime(date);
        }
    }
    public static RetrieveSchedule getInstance() {
        if (scheduleInstance == null)
            scheduleInstance = new RetrieveSchedule();
        return scheduleInstance;
    }
    /*
    * Get Data from tv maze API
    *
    * */
    public ArrayList<TVProgram> getTvProgramsAllWeek() {
        return this.tvProgramsAllWeek;
    }
    /*
    * Get TV Program from List with Date
    * */


    public void setTvProgramsAllWeek(ArrayList<TVProgram> tvProgramsAllWeek) {
        this.tvProgramsAllWeek = tvProgramsAllWeek;
    }

    public void addTvProgramsAllWeek(TVProgram tvProgramsValue) {
        this.tvProgramsAllWeek.add(tvProgramsValue);
    }

    //get data from URL daily
    private ArrayList<TVProgram> requestAPIData(String apiURL) throws IOException {
        ArrayList tvPrograms = new ArrayList<TVProgram>();
        URL url = new URL(apiURL);
        HttpURLConnection con = null;
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json;utf-8");
        con.setRequestProperty("Accept", "application/json");
         BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String jsonString = response.toString();
        TVProgramUtilities.tvMazeJSONtoTVProgramsParser(jsonString, tvPrograms);//all programs  that day
        return tvPrograms;
    }

}
