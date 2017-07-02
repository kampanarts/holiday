package net.nobew.holiday;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties
@PropertySource("classpath:application.properties")
public class HolidayAPIConfiguration {

    @Value("${holiday.api.url}")
    private String holidayApiURL;

    @Value("${holiday.api.key}")
    private String holidayApiKey;

    public String getHolidayApiURL() {
        return holidayApiURL;
    }

    public void setHolidayApiURL(String holidayApiURL) {
        this.holidayApiURL = holidayApiURL;
    }

    public String getHolidayApiKey() {
        return holidayApiKey;
    }

    public void setHolidayApiKey(String holidayApiKey) {
        this.holidayApiKey = holidayApiKey;
    }
}
