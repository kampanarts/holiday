package net.nobew.holiday.service.impl;

import net.nobew.holiday.HolidayAPIConfiguration;
import net.nobew.holiday.exception.HolidayAPIConnectionException;
import net.nobew.holiday.exception.HolidayAPIParsingException;
import net.nobew.holiday.exception.HolidayAPIStatusCodeException;
import net.nobew.holiday.model.Holiday;
import net.nobew.holiday.model.HolidayAPI;
import net.nobew.holiday.model.HolidayAPIResponse;
import net.nobew.holiday.service.HolidayAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HolidayAPIServiceImpl implements HolidayAPIService {

    private RestTemplate restTemplate;
    private HolidayAPIConfiguration appConfig;

    @Autowired
    public HolidayAPIServiceImpl(RestTemplate restTemplate, HolidayAPIConfiguration appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
    }

    @Cacheable(cacheNames = "holiday", key = "#country + '-' + #year")
    @Override
    public List<Holiday> getHoliday(String country, int year) throws HolidayAPIConnectionException, HolidayAPIStatusCodeException, HolidayAPIParsingException {

        List<Holiday> holidaysInYear = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(appConfig.getHolidayApiURL())
                .queryParam("key", appConfig.getHolidayApiKey())
                .queryParam("country", country)
                .queryParam("year", year);

        ResponseEntity<HolidayAPIResponse> response = null;
        try {
            response = restTemplate.exchange(
                    urlBuilder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity<Object>(headers),
                    HolidayAPIResponse.class);
        } catch (Exception e){
            throw new HolidayAPIConnectionException(e);
        }

        if(response.getStatusCode().equals(HttpStatus.OK)) {
            Map<String, List<HolidayAPI>> holidays = response.getBody().getHolidays();
            holidaysInYear = holidays.entrySet().stream()
                    .flatMap(m -> m.getValue().stream())
                    .map(h -> toHoliday(country, h))
                    .collect(Collectors.toList());
        } else {
            throw new HolidayAPIStatusCodeException(String.format("Bad Status Code: %d", response.getStatusCode().value()));
        }

        return holidaysInYear;
    }

    private Holiday toHoliday(String country, HolidayAPI apiData) {
        Holiday holiday = new Holiday();
        holiday.setCountry(country);
        holiday.setName(apiData.getName());
        holiday.setDate(apiData.getDate());
        return holiday;
    }
}
