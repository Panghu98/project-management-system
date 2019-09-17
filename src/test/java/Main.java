public class Main {

     static class ReadThread implements Runnable{
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                Cache.put("hello","胖虎"+i);
            }
        }
    }

    static class WriteThread implements Runnable{
        @Override
        public void run() {
            Cache.get("hello");
        }
    }

    public static void main(String[] args) {



        ReadThread readThread = new ReadThread();
        Thread thread1 = new Thread(readThread);
        thread1.start();

        WriteThread writeThread = new WriteThread();
        Thread thread2 = new Thread(writeThread);
        Thread thread3 = new Thread(writeThread);
        thread2.start();
        thread3.start();


    }

}
