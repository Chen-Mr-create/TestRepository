package com.gec.web.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {
    SimpleDateFormat[] sdf = {
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("yyyy/MM/dd"),
            new SimpleDateFormat("yyyy年MM月dd日"),
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    };

    @Override
    public Date convert(String time) {
        for (int i = 0; i < sdf.length; i++) {
            try {
                return sdf[i].parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }
        }
        //throw new IllegalArgumentException();
        return null;
    }

}
