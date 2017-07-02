package net.nobew.holiday.resource;

import net.nobew.holiday.exception.HolidayAPIConnectionException;
import net.nobew.holiday.exception.HolidayAPIParsingException;
import net.nobew.holiday.exception.HolidayAPIStatusCodeException;
import net.nobew.holiday.model.Holiday;
import net.nobew.holiday.service.HolidayService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/holiday")
public class HolidayResource {

    private static final Logger log = Logger.getLogger(HolidayResource.class);

    private HolidayService holidayService;

    @Autowired
    public HolidayResource(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @RequestMapping(value = "/{date}/{countryList}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HolidayResponse getHoliday(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                           @PathVariable("countryList") List<String> countryList) throws HolidayAPIConnectionException, HolidayAPIStatusCodeException, HolidayAPIParsingException {

        Map<String, List<Holiday>> holidayOfCountries = holidayService.getHoliday(date, countryList);

        Map<String, List<String>> holidays = holidayOfCountries.entrySet()
                .stream()
                .collect(
                    Collectors.toMap(
                        e -> e.getKey(),
                        v -> v.getValue().stream().map(Holiday::getName).collect(Collectors.toList())
                    )
                );

        return new HolidayResponse(date, holidays);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({HolidayAPIConnectionException.class, HolidayAPIParsingException.class, HolidayAPIStatusCodeException.class})
    @ResponseBody
    public String exceptionHandler(HttpServletResponse resp, Exception e) {
        log.error("An error on HolidayResource!", e);
        return "Something wrong on our end!";
    }

    class HolidayResponse {
        private LocalDate date;
        private Map<String, List<String>> holidays;

        public HolidayResponse(LocalDate date, Map<String, List<String>> holidays) {
            this.date = date;
            this.holidays = holidays;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Map<String, List<String>> getHolidays() {
            return holidays;
        }

        public void setHolidays(Map<String, List<String>> holidays) {
            this.holidays = holidays;
        }
    }

}
