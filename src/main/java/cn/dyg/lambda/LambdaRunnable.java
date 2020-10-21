package cn.dyg.lambda;

/**
 * LambdaRunnable 类是 lambda实现Runnable接口
 *
 * @author dongyinggang
 * @date 2020-09-05 10:14
 **/
public class LambdaRunnable {
    public static void main(String[] args) {

        //1.1使用匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello Runnable");
            }
        }).start();

        //1.2使用lambda表达式
        new Thread(()-> System.out.println("Hello Lambda Runnable")).start();

        //2.1使用匿名内部类
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello Runnable");
            }
        };

        //2.2使用lambda表达式
        Runnable runnable1 = ()-> System.out.println("Hello Lambda Runnable");

        new Thread(runnable).start();
        new Thread(runnable1).start();
    }
}
