package com.friday.bills.controller;

import com.friday.bills.entity.UserExpenses;
import com.friday.bills.service.UserExpensesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

/**
 * (UserExpenses)表控制层
 *
 * @author makejava
 * @since 2021-02-25 15:25:47
 */
@RestController
@RequestMapping("userExpenses")
public class UserExpensesController {
    /**
     * 服务对象
     */
    @Resource
    private UserExpensesService userExpensesService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserExpenses selectOne(Integer id) {
        return this.userExpensesService.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @param  userExpenses
     * @return 对象列表
     */
    @GetMapping("selectAll")
    public ResponseEntity<Map<String,Object>> selectAll(
            @RequestParam(value = "offset" ,required = false) Integer offset,
            Integer limit , UserExpenses userExpenses){
        Map<String,Object> map = new HashMap<>();
        if (offset == null || offset == 0){
            userExpenses.setOffset(1);
        }else{
            userExpenses.setOffset(offset);
        }

        if (StringUtils.isNotBlank(userExpenses.getExpensesTime())){
            String[] split = StringUtils.split(userExpenses.getExpensesTime(), ',');
            userExpenses.setDate1(split[0].replaceAll("-",""));
            userExpenses.setDate2(split[1].replaceAll("-",""));
        }
        userExpenses.setLimit(limit);
        PageInfo<UserExpenses> allData = userExpensesService.queryAllByEntity(userExpenses);
        map.put("count",allData.getTotal());
        map.put("data",allData.getList());
        return ResponseEntity.ok(map);

    }



    /**
     * 新增数据
     *
     * @param  userExpenses
     * @return Void
     */
    @PostMapping("insert")
    public ResponseEntity<Void> insert(@RequestBody UserExpenses userExpenses){
        userExpensesService.insert(userExpenses);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @GetMapping("delete")
    public ResponseEntity<Boolean> delete(Integer id){
        boolean b = userExpensesService.deleteById(id);
        return ResponseEntity.ok(b);
    }


    /**
     * 修改数据
     *
     * @param userExpenses
     * @return Void
     */
    @PutMapping("update")
    public ResponseEntity<Void> update(@RequestBody UserExpenses userExpenses){
        userExpensesService.update(userExpenses);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}