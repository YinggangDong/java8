package cn.dyg.optional;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Employee 类是 员工类
 *
 * @author dongyinggang
 * @date 2020-10-21 20:36
 **/
@AllArgsConstructor
@Data
public class Employee {

    /**
     * 姓名
     */
    private String name;

    public Employee changeName(Employee employee){
        employee.setName("修改后的姓名");
        return employee;
    }
}
