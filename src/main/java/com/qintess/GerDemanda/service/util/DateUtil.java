package com.qintess.GerDemanda.service.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public abstract  class DateUtil {
    public static Date getCurrentDateTimeZero() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getCurrentDateTime(){
        return   Date.from( LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant());
    }

}
