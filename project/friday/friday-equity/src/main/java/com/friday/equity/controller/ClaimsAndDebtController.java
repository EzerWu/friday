package com.friday.equity.controller;

import com.friday.equity.entity.ClaimsAndDebt;
import com.friday.equity.service.ClaimsAndDebtService;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

/**
 * (ClaimsAndDebt)表控制层
 *
 * @author makejava
 * @since 2021-02-25 16:29:59
 */
@RestController
@RequestMapping("claimsAndDebt")
public class ClaimsAndDebtController {
    /**
     * 服务对象
     */
    @Resource
    private ClaimsAndDebtService claimsAndDebtService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ClaimsAndDebt selectOne(Integer id) {
        return this.claimsAndDebtService.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @param  claimsAndDebt
     * @return 对象列表
     */
    @GetMapping("selectAll")
    public ResponseEntity<Map<String,Object>> selectAll(Integer offset ,Integer limit , ClaimsAndDebt claimsAndDebt){
        Map<String,Object> map = new HashMap<>();
        claimsAndDebt.setOffset(offset);
        claimsAndDebt.setLimit(limit);
        PageInfo<ClaimsAndDebt> allData = claimsAndDebtService.queryAllByEntity(claimsAndDebt);
        map.put("count",allData.getTotal());
        map.put("data",allData.getList());
        return ResponseEntity.ok(map);

    }



    /**
     * 新增数据
     *
     * @param  claimsAndDebt
     * @return Void
     */
    @PostMapping("insert")
    public ResponseEntity<Void> insert(@RequestBody ClaimsAndDebt claimsAndDebt){
        claimsAndDebtService.insert(claimsAndDebt);
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
        boolean b = claimsAndDebtService.deleteById(id);
        return ResponseEntity.ok(b);
    }


    /**
     * 修改数据
     *
     * @param claimsAndDebt
     * @return Void
     */
    @PutMapping("update")
    public ResponseEntity<Void> update(@RequestBody ClaimsAndDebt claimsAndDebt){
        claimsAndDebtService.update(claimsAndDebt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}