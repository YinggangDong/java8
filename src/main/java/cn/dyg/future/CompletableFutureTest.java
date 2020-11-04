package cn.dyg.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFutureTest
 * @desc: CompletableFutureTest
 * @author: liuzicheng
 * @date: 2020/10/23 16:06
 * @return:
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //futureTest();

        //创建
        //createCompletableFuture();

        //任务串行
        //asyncTest();

        //异常处理
        //exceptionally();

        //任务完成时
        //whenComplete();

        //or聚合
        //or();

        //all聚合
        //all();

        //futureTest2();

        /**
         * 复杂的业务场景:
         * 一些长任务需要被拆解为多个子任务,子任务也能向下划分
         * 这些子任务有些是可以并行处理的,有些是依赖顺序的
         * TaskA 进行划分
         * 1.可以并行处理 Task1.1 Task1.2 Task1.3
         * 2.依照第一步的三个任务的执行结果 执行 Task2
         * 3.根据Task2 的结果 异步执行Task3
         */

        /**
         * 对一个或多个 Future 合并操作，生成一个新的 Future， 参考 allOf，anyOf，runAsync， supplyAsync。
         * 为 Future 添加后置处理动作， thenAccept， thenApply， thenRun。
         * 两个人 Future 任一或全部完成时，执行后置动作：applyToEither， acceptEither， thenAcceptBothAsync， runAfterBoth，runAfterEither 等。
         * 当 Future 完成条件满足时，异步或同步执行后置处理动作： thenApplyAsync， thenRunAsync。所有异步后置处理都会添加 Async 后缀。
         * 定义 Future 的处理顺序 thenCompose 协同存在依赖关系的 Future，thenCombine。合并多个 Future的处理结果返回新的处理结果。
         * 异常处理 exceptionally ，如果任务处理过程中抛出了异常。
         */



    }

    /**
     * or聚合
     * @desc: or聚合
     * @author: liuzicheng
     * @date: 2020/10/28 15:03
     * @return: void
     */
    private static void or() {
        CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(()->{
                    int i = new Random().nextInt(5);
                    try {
                        TimeUnit.SECONDS.sleep(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "任务1 " + i;
                });

        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(()->{
                    int i = new Random().nextInt(5);
                    try {
                        TimeUnit.SECONDS.sleep(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "任务2 " + i;
                });

        CompletableFuture<String> f3 =
                f1.applyToEither(f2,s -> s);

        System.out.println(f3.join());
        //accept
        //f1.acceptEither(f2, System.out::println).join();
        //run
        //f1.runAfterEither(f2, () -> System.out.println("执行结束")).join();


    }

    /**
     * all聚合
     * @desc: all聚合
     * @author: liuzicheng
     * @date: 2020/10/28 14:31
     * @return: void
     */
    private static void all() {
        //创建一个固定的线程池
        ExecutorService executor = Executors.newFixedThreadPool(4);

        long start = System.currentTimeMillis();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {

            long currentTimeMillis = System.currentTimeMillis();

            System.out.println("[商品服务调用]开始");
            System.out.println(1/0);
            try {
                TimeUnit.MILLISECONDS.sleep(1000 + new Random().nextInt(1000));
                System.out.println("[商品服务调用], 耗时:" + (System.currentTimeMillis() - currentTimeMillis));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "商品信息";
        }, executor)
        .exceptionally(exception -> {
            System.out.println("[商品服务调用]出现异常:" + exception);
            return "商品服务调用异常";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {

            long currentTimeMillis = System.currentTimeMillis();

            System.out.println("[商户服务调用]开始");
            System.out.println(1/0);
            try {
                TimeUnit.MILLISECONDS.sleep(1000 + new Random().nextInt(1000));
                System.out.println("[商户服务调用], 耗时:" + (System.currentTimeMillis() - currentTimeMillis));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "卖家信息";
        }, executor)
        //参数为结果和异常 相当于finally操作
        .handle((res, exception) ->{
            //出现异常
            if (exception != null) {
                System.out.println("[商户服务调用]:出现异常:" + exception);
                return "未知卖家";
            } else {
                return res;
            }
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {

            long currentTimeMillis = System.currentTimeMillis();

            System.out.println("[库存服务调用]开始");
            try {
                TimeUnit.MILLISECONDS.sleep(1000 + new Random().nextInt(1000));
                System.out.println("[库存服务调用]结束, 耗时:" + (System.currentTimeMillis() - currentTimeMillis));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "库存信息";
        }, executor);

        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {

            long currentTimeMillis = System.currentTimeMillis();

            System.out.println("[订单服务调用]开始:");
            try {
                TimeUnit.MILLISECONDS.sleep(1000 + new Random().nextInt(1000));
                System.out.println("[订单服务调用]结束, 耗时:" + (System.currentTimeMillis() - currentTimeMillis));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "订单信息";
        }, executor);

        CompletableFuture all = CompletableFuture.allOf(future1, future2, future3, future4).thenRun(
                () -> {
                    System.out.println("聚合任务开始");
                    try {
                        System.out.println(future1.get());
                        System.out.println(future2.get());
                        System.out.println(future3.get());
                        System.out.println(future4.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        );
        System.out.println("等待全部任务完成");
        all.join();

//        System.out.println(future1.join() + future2.join() + future3.join() + future4.join());

        System.out.println("总耗时:" + (System.currentTimeMillis() - start));
    }

    /**
     * 异常处理
     * @desc: 异常处理
     * @author: liuzicheng
     * @date: 2020/10/28 14:27
     * @return: void
     */
    private static void exceptionally() {
        CompletableFuture<Integer> f0 = CompletableFuture.supplyAsync(() -> 7 /0 )
                                                            .thenApply(r->r*10)
                                                            .exceptionally(e->0);
        System.out.println(f0.join());
    }


    /**
     * future创建
     * @desc: future创建
     * @author: liuzicheng
     * @date: 2020/10/24 8:34
     * @return: void
     */
    private static void futureTest2() throws InterruptedException, ExecutionException {

        /**
         * 首先创建一个线程池，然后向线程池中提交了一个任务
         * submit方法提交后会立即返回一个Future
         * Future就有意思了，他是不会等任务完成就返回的，这就是未来的意思吧，直接把还未完成的是抽象出来
         * 在调用get()去阻塞的获取执行结果
         * 消费者异步处理生产者提交的任务时,生产者线程也可以拿到消费者线程的处理结果
         */
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<String> future = executor.submit(() -> {
            Thread.sleep(100);
            return "I am ok.";
        });

        System.out.println(future.isDone());
        //get()是阻塞的
        System.out.println(future.get());

    }

    /**
     * 任务串行
     * @desc: 任务串行
     * @author: liuzicheng
     * @date: 2020/10/28 14:21
     * @return: void
     */
    private static void asyncTest() {
        CompletableFuture.supplyAsync(() -> "hello")
                         .thenAccept(System.out::println)
                         .thenRun(() -> System.out.println("end"));
    }

    /**
     * 任务完成时
     * @desc: 任务完成时
     * @author: liuzicheng
     * @date: 2020/10/23 17:00
     * @return: void
     */
    private static void whenComplete() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello")
                                                    .whenComplete((v,e) -> {
                                                        if (e == null) {
                                                            System.out.println(v);
                                                        } else {
                                                            System.out.println("null");
                                                        }
                                                    });
    }

    /**
     * 简单的Future接口调用
     *
     * 在简单的场景使用Future并没有什么不方便的
     * 但是在复杂的业务场景下就会很麻烦
     * 创建2个业务线程
     * 其中一个有结果就直接返回,Future用起来就不方便
     * 因为获取执行结果需要调用get()方法,这个方法是阻塞的
     * 会让异步操作变为同步操作
     * 要么就是isDone()方法进行轮询,这样就会一直 消耗CPU资源
     *
     * @desc: 简单的Future接口调用
     * @author: liuzicheng
     * @date: 2020/10/23 16:11
     * @return: void
     */
    private static void futureTest() throws InterruptedException, java.util.concurrent.ExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<Integer> result = executor.submit(() -> {
            int sum = 0;
            for (int i = 0; i <= 100; i++) {
                sum += i;
            }
            return sum;
        });

        System.out.println(result.get());
    }

    /**
     * 创建CompletableFuture
     *
     * 用run/supply的方法创建CompletableFuture存在两种方式
     * 1.使用默认线程池 ForkJoinPool.commonPool()
     * 2.自定义线程池
     * @desc: 创建CompletableFuture
     * @author: liuzicheng
     * @date: 2020/10/23 16:36
     * @return: void
     */
    private static void createCompletableFuture() throws InterruptedException, ExecutionException {

        //completedFuture() 返回一个给定值
        CompletableFuture<Integer> future = CompletableFuture.completedFuture(100);

        System.out.println(future.get());
        //runAsync() 函数式接口 Runnable ---> public abstract void run()
        CompletableFuture<Void> voidFuture = CompletableFuture.runAsync(() -> System.out.println("hello"));

        System.out.println(voidFuture.get());
        //supplyAsync() 函数式接口 Supplier ---> T get() 供给型接口
        CompletableFuture<String> stringFuture = CompletableFuture.supplyAsync(() -> "hello");

        System.out.println(stringFuture.get());
    }


}
