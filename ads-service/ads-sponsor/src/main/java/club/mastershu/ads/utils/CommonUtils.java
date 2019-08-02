package club.mastershu.ads.utils;

import club.mastershu.ads.exception.AdsException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class CommonUtils {
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };
    public static String md5(String val) {
        return DigestUtils.md5Hex(val).toUpperCase();
    }

    public static Date parseStringDate(String dateString) throws AdsException {
        try {
            return DateUtils.parseDate(
                    dateString, parsePatterns
            );
        } catch (ParseException e) {
            throw new AdsException(e.getMessage());
        }
    }
}
