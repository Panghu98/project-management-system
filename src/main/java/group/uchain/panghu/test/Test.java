package group.uchain.panghu.test;


/**
 * @author panghu
 * @title: Test
 * @projectName panghu
 * @date 19-7-12 下午10:10
 */
public class Test {

    static int fib(int n){
        int[] list = new int[n + 1];
        if ( n==0){
            return 0;
        }
        if (n == 1){
            return 1;
        }
        if (list[n] == 0) {
            list[n] = fib(n-1)+fib(n-2);
        }
        return list[n];
    }

    static int fib2(int n){
         int[] list = new int[n+1];
        if (n==0){
            return 0;
        }
        if (n == 1){
            return 1;
        }
        for (int i = 2; i <=n ; i++) {
            list[i] = fib2(i-1) + fib2(i-2);
        }
        return list[n];

    }

    public static void main(String[] args) {
        for (int i = 1; i <= 40; i+=5) {
//            list = new int[i+1];
            long start = System.nanoTime();
            fib2(i);
            long end = System.nanoTime();
            System.out.println("当n="+i+"时"+ "算法总耗时"+(end-start)+"  ns");
        }
    }

}
