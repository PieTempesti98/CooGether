package it.unipi.lmmsdb.coogether.coogetherapp.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePojo {
    private String $date;
    private Date toExport;

    public Date get$date(){
        Date date;
        try {
            SimpleDateFormat format;
            if ($date.length() > 20)
                format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            else
                format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            date = format.parse($date);
            return date;
        }catch(Exception e){
            e.printStackTrace();
            return new Date();
        }
    }

    public void set$date(String $date) {
        this.$date = $date;
    }
}
