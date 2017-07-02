package net.nobew.holiday.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class HolidayAPIResponse {
    private int statusCode;
    private Map<String, List<HolidayAPI>> holidays;

    @JsonProperty("status")
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, List<HolidayAPI>> getHolidays() {
        return holidays;
    }

    public void setHolidays(Map<String, List<HolidayAPI>> holidays) {
        this.holidays = holidays;
    }
}
