package com.definesys.readword.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: zhengfei.tan
 * @since: 2019/11/4 18:50
 * @history: 1.2019/11/4 created by zhengfei.tan
 */
public class DateUtils {
    /**
     * @param dateStr:String
     * @param timeType:格式类型
     * @return 描述：String类型转Date类型
     */
    public static Date StringToDate(String dateStr, String timeType) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeType);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
}
