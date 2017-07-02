package net.nobew.holiday.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class HolidayAPI {

    private String name;
    private LocalDate date;
    private LocalDate observed;
    private boolean publicDay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getObserved() {
        return observed;
    }

    public void setObserved(LocalDate observed) {
        this.observed = observed;
    }

    public boolean isPublicDay() {
        return publicDay;
    }

    @JsonProperty("public")
    public void setPublicDay(boolean publicDay) {
        this.publicDay = publicDay;
    }
}
