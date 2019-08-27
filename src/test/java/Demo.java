import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo {

    @Test
    public void test(){
        System.err.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis()-10*24*1000*60*60);
    }

    @Test
    public void getTimeString(){
        Date date = new Date();
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
        System.out.println(dateFormat);
    }

    @Test
    public void testJson(){
        String str = "{\"projectId\":\"H12\",\"map\":[[\"123456789104\",10],[\"123456789107\",10]]}";
        System.err.println(str);
        str = str.replace(",\"", ":\"")
                .replace(":\"map\":[", ",\"map\":{")
                .replace("]]","}")
                .replace("[","")
                .replace("]", "");
        System.out.println(str);
    }

}
