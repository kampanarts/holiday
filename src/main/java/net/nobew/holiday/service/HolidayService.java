package net.nobew.holiday.service;

import net.nobew.holiday.exception.HolidayAPIConnectionException;
import net.nobew.holiday.exception.HolidayAPIParsingException;
import net.nobew.holiday.exception.HolidayAPIStatusCodeException;
import net.nobew.holiday.model.Holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface HolidayService {
    public Map<String,List<Holiday>> getHoliday(LocalDate date, List<String> countryList) throws HolidayAPIConnectionException, HolidayAPIStatusCodeException, HolidayAPIParsingException;
}
