package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //1.查询名字是小红的employee
//    @Query("select name from Employee where name = ?1")
//    String findByName(String name);
    Employee findFirstByName(String name);

    //2.找出Employee表中第一个姓名包含`n`字符的雇员所有个人信息
//    @Query("select u.name from Employee u where u.name like %?1")
//    String findByNameContainN(String str);

//  2.找出Employee表中第一个姓名包含`*`字符并且薪资大于*的雇员个人信息
    Employee findFirstByNameLikeAndSalaryIsGreaterThan(String character,Integer salary);
    //3.找出一个薪资最高且公司ID是1的雇员以及该雇员的name
    @Query("select u.name from Employee u where u.salary = (select max(a.salary) from Employee a where a.companyId = ?1)")
    String findByMaxSalaryAndCompanyId(int compayId);

    //4.实现对Employee的分页查询，每页两条数据，一共三页数。
    @Query(value = "select u from Employee u")
    Page<Employee>  findAllEmployee(Pageable pageable);

    //5.查找xiaohong的所在的公司的公司名称
    @Query("select c.companyName from Company c where c.id = (select companyId from Employee where name = ?1)")
    String findCompayNameByEmployeeName(String employeeName);

    //6.将xiaohong的名字改成xiaobai,输出这次修改影响的行数
    @Modifying
    @Query("update Employee u set u.name = ?1 where u.name = ?2")
    int updateEmployeeName(String newName,String oldName);

    //7.删除姓名是xiaohong的employee
    @Modifying
    @Query("delete from Employee u where u.name = ?1")
    void deleteByEmployeeName(String name);

    //以下所有的*都代表变量

    //1.查询名字是*的第一个employee

    //2.找出Employee表中第一个姓名包含`*`字符并且薪资大于*的雇员个人信息

    //3.找出一个薪资最高且公司ID是*的雇员以及该雇员的姓名

    //4.实现对Employee的分页查询，每页两个数据

    //5.查找**的所在的公司的公司名称

    //6.将*的名字改成*,输出这次修改影响的行数

    //7.删除姓名是*的employee


}
