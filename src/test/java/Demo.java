import org.junit.Test;

public class Demo {

    @Test
    public void test(){
        System.err.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis()-10*24*1000*60*60);
    }

}
