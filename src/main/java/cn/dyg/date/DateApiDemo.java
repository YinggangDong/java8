package cn.dyg.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * DateApiDemo 类是 新的日期API
 *
 * @author dongyinggang
 * @date 2020-10-29 18:50
 **/
public class DateApiDemo {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.of(2020,10,29);
        withTest();

    }

    public static void withTest(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.of(2020, 1, 1);

        //获取指定年的第一个周日的日期
        LocalDate start = localDate.with(DayOfWeek.SUNDAY);
        //获取指定年的最后一天的日期
        LocalDate last = localDate.with(TemporalAdjusters.lastDayOfYear());

        List<String> list = new ArrayList<>();
        //一年最多53周
        for (int i = 0; i < 53; i++) {
            //存入list，然后跳到下一周周日，继续进行记录
            list.add(start.format(dtf));
            start = start.plusDays(7);
            //判断日期是否超过指定年，超过则退出
            if (start.until(last, ChronoUnit.DAYS) < 0) {
                break;
            }
        }
        list.forEach(System.out::println);
    }
}
