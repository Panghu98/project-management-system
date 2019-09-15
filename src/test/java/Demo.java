import java.util.Date;

public class Demo {

    public static void main(String[] args) {

        //short
        System.out.println("short所占二进制位数为"+Short.SIZE);

        //int
        System.out.println("int 所占二进制位数为"+Integer.SIZE);

        //long
        System.out.println("long 所占二进制位数为"+Long.SIZE);

        //float
        System.out.println("float所占二进制位数为"+Float.SIZE);

        //double
        System.out.println("double所占二进制位数为"+Double.SIZE);

        //byte
        System.out.println("byte所占二进制位数为"+Byte.SIZE);

        //char
        System.out.println("char所占二进制位数为"+Character.SIZE);

        Date start = new Date(System.currentTimeMillis()-18*60*60*24*1000);
        Date end = new Date(System.currentTimeMillis());
        System.err.println(System.currentTimeMillis()-18*60*60*24*1000);
        System.err.println(System.currentTimeMillis());
    }
    
}
