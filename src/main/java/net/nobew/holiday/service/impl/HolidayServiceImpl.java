package net.nobew.holiday.service.impl;

import net.nobew.holiday.exception.HolidayAPIConnectionException;
import net.nobew.holiday.exception.HolidayAPIParsingException;
import net.nobew.holiday.exception.HolidayAPIStatusCodeException;
import net.nobew.holiday.model.Holiday;
import net.nobew.holiday.service.HolidayAPIService;
import net.nobew.holiday.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HolidayServiceImpl implements HolidayService {

    private HolidayAPIService holidayAPIService;

    @Autowired
    public HolidayServiceImpl(HolidayAPIService holidayAPIService) {
        this.holidayAPIService = holidayAPIService;
    }

    @Override
    public Map<String, List<Holiday>> getHoliday(LocalDate date, List<String> countryList) throws HolidayAPIConnectionException, HolidayAPIStatusCodeException, HolidayAPIParsingException {

        Map<String, List<Holiday>> holidayOfCountries = new HashMap<String, List<Holiday>>();

        for(String country : countryList) {
            List<Holiday> holidays = this.holidayAPIService.getHoliday(country, date.getYear());

            List<Holiday> holidayAsCriteria = Optional.ofNullable(holidays).orElseGet(Collections::emptyList)
                    .stream().filter(h -> h.getDate().isEqual(date))
                    .collect(Collectors.toList());

            holidayOfCountries.put(country, holidayAsCriteria);
        }

        return holidayOfCountries;
    }
}
