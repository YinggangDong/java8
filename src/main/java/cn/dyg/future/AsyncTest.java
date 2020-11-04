package cn.dyg.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AsyncTest {
    public static void main(String[] args) {

        //任务1洗水壶,洗完之后就用水壶烧水
        CompletableFuture f1 = CompletableFuture.runAsync(() -> {
            System.out.println("[t1]:洗水壶");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRun(() -> {
            System.out.println("[t1]:烧开水");
            try {
                TimeUnit.SECONDS.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //任务2洗茶壶,洗完再洗茶杯
        CompletableFuture f2 = CompletableFuture.runAsync(() -> {
            System.out.println("[t2]:洗茶壶");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRun(() -> {
            System.out.println("[t2]:洗茶杯");
            try {
                TimeUnit.SECONDS.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenApply((v) -> {
            System.out.println("[t2]:拿茶叶");
            try {
                TimeUnit.SECONDS.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "龙井";
        });

        CompletableFuture f3 = f1.thenCombine(f2, (v, tf) -> {
            System.out.println("T1:拿到茶叶:" + tf);
            System.out.println("T1:泡茶...");
            return "上茶:" + tf;
        });

        System.out.println(f3.join());
    }
}
