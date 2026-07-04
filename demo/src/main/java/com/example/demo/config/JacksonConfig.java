package com.example.demo.config;

import com.example.demo.utils.RequestContextUtils;
import com.example.demo.utils.SpringContextUtils;
import com.example.demo.utils.TimeZoneUtils;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * 使用@JsonComponent注释会自动被注册到Jackson中.
 **/
//@JsonComponent
@Slf4j
public class JacksonConfig {

    @PostConstruct
    public void init() {
        log.info("JacksonConfig init ...");
    }

    private static final String dateRegex = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

    private static final String timestampRegex = "\\d{13}";

    /**
     * 自定义反序列化器,格式化时间
     */
    public static class DateDeserializer extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String dateStr = jsonParser.getText();
            if (Pattern.matches(dateRegex, dateStr)) {
                SimpleDateFormat sdf = setTimeZone();
                try {
                    return sdf.parse(dateStr);
                } catch (ParseException e) {
                    return null;
                }
            }

            if (Pattern.matches(timestampRegex, dateStr)) {
                Long timestamp = Long.valueOf(dateStr);
                return new Date(timestamp);
            }

            return null;
        }
    }

    private static SimpleDateFormat setTimeZone() {
        String site = RequestContextUtils.getParam("site");
        if (!StringUtils.hasText(site)) {
            return null;
        }
        TimeZoneUtils timeZoneUtils = SpringContextUtils.getBean(TimeZoneUtils.class);
        String timezone = timeZoneUtils.getTimezoneBySite(site);
        if (!StringUtils.hasText(timezone)) {
            return null;
        }
        TimeZone tz = TimeZone.getTimeZone(timezone);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(tz);
        return sdf;
    }

    public static class TimestampDeserializer extends JsonDeserializer<Long> {

        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            String dateStr = jsonParser.getText();
            if (Pattern.matches(dateRegex, dateStr)) {
                SimpleDateFormat sdf = setTimeZone();
                try {
                    return sdf.parse(dateStr).getTime();
                } catch (ParseException e) {
                    return null;
                }
            }

            if (Pattern.matches(timestampRegex, dateStr)) {
                return Long.valueOf(dateStr);
            }

            return null;
        }
    }

    /**
     * 自定义序列化器,格式化数值
     */
    public static class DateStrFromDateSerializer extends JsonSerializer<Date> {

        /**
         * 序列化操作,继承JsonSerializer，重写Serialize函数
         */
        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            SimpleDateFormat sdf = setTimeZone();
            String dateStr = sdf.format(date);
            jsonGenerator.writeString(dateStr);
        }
    }

    /**
     * 自定义序列化器,格式化数值
     */
    public static class DateStrFromTimestampSerializer extends JsonSerializer<Long> {

        /**
         * 序列化操作,继承JsonSerializer，重写Serialize函数
         */
        @Override
        public void serialize(Long timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            SimpleDateFormat sdf = setTimeZone();
            String dateStr = sdf.format(new Date(timestamp));
            jsonGenerator.writeString(dateStr);
        }
    }

    /**
     * 自定义序列化器,格式化数值
     */
    public static class TimestampStrFromDateSerializer extends JsonSerializer<Date> {

        /**
         * 序列化操作,继承JsonSerializer，重写Serialize函数
         */
        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(String.valueOf(date.getTime()));
        }
    }
}
