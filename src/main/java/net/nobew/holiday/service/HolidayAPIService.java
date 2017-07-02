package net.nobew.holiday.service;

import net.nobew.holiday.exception.HolidayAPIConnectionException;
import net.nobew.holiday.exception.HolidayAPIParsingException;
import net.nobew.holiday.exception.HolidayAPIStatusCodeException;
import net.nobew.holiday.model.Holiday;

import java.util.List;

public interface HolidayAPIService {
    public List<Holiday> getHoliday(String country, int year) throws HolidayAPIConnectionException, HolidayAPIStatusCodeException, HolidayAPIParsingException;
}
