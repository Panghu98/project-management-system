import java.util.LinkedList;
import java.util.List;

public class Demo {

    public static void main(String[] args) {

        /*
            =============================基本类型包装类型================================
         */
        //short
        System.out.println("short所占二进制位数为" + Short.SIZE);

        //int
        System.out.println("int 所占二进制位数为" + Integer.SIZE);

        //long
        System.out.println("long 所占二进制位数为" + Long.SIZE);

        //float
        System.out.println("float所占二进制位数为" + Float.SIZE);

        //double
        System.out.println("double所占二进制位数为" + Double.SIZE);

        //byte
        System.out.println("byte所占二进制位数为" + Byte.SIZE);

        //char
        System.out.println("char所占二进制位数为" + Character.SIZE);

        System.out.println();

        /*
            =============================算术表达式======================================
         */

        int a = 10;

        int b = 5;

        System.out.println("a-b=" + (a - b));
        System.out.println("a+b=" + (a + b));
        System.out.println("a*b=" + a * b);
        System.out.println("a/b=" + a / b);
        System.out.println("a%b=" + a % b);
        System.out.println();

        /*
        ==================================赋值表达式=======================================
         */

        System.out.println("当前a的值为" + a);
        a += 1;
        System.out.println(a);

        a /= 2;
        System.out.println(a);

        a *= a;
        System.out.println(a);

        a /= a;
        System.out.println(a);

        a %= a;
        System.out.println(a);

        System.out.println();


        /*
            ===============================条件表达式==========================================
         */
        System.out.println("=======================================");
        System.out.println("开始执行for循环");
        for (int i = 0; i < 3; i++) {
            System.out.println("第" + i + "执行循环");
            if (i / 2 == 0) {
                System.out.println(i + "能够被2整除");
            }

            if (i != 1) {
                System.out.println("当前i的值不为1");
            }
        }

        System.out.println("=======================================");
        System.out.println("开始执行while循环");

        int counter = 1;
        while (a < 5) {
            a++;
            System.out.println("ａ第" + (counter++) + "次自增．．．．");
        }

        System.out.println("=======================================");
        System.out.println("开始执行foreach循环");
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            list.add(i);
        }
        for (Integer num : list
        ) {
            System.out.println(num);
        }


        System.out.println("=======================================");
        System.out.println("逻辑运算符");
        int i = 3 & 5;
        System.out.println("3 & 5 = "+i);

        i = 3 | 5;
        System.out.println("3 | 5 = "+i);

        if (i < 3 || i > -1){
            System.out.println("满足或逻辑,进入判断分之");
        }

        if(i < 3 && i > -1){
            System.out.println("满足与逻辑,进入判断分之");
        }


    }

}
