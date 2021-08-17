package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.eduservice.vo.TeacherQuery;
import com.atguigu.servicebase.GuliException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-06-04
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @ApiOperation("查询讲师")
    @GetMapping("findAll")
    public Result findAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.ok().data("items",list);
    }

    @ApiOperation("删除讲师")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        return b ? Result.ok():Result.error();
    }

    @ApiOperation("分页查询讲师")
    @GetMapping ("/page/{current}/{size}")
    public Result delete(@PathVariable long current,@PathVariable long size){
        Page<EduTeacher> page = new Page<>(current,size);
        eduTeacherService.page(page, null);

        try{
            int i = 10/0;
        }catch(Exception e){
            throw new GuliException(20001,"执行了自定义异常");

        }
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        return Result.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("分页加参数查询讲师")
    @PostMapping  ("/pageCondition/{current}/{size}")
    public Result pageCondition(@PathVariable long current, @PathVariable long size, @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> page = new Page<>(current,size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery != null ? teacherQuery.getName():"";
        Integer level = teacherQuery != null ? teacherQuery.getLevel():null;
        String begin = teacherQuery != null ? teacherQuery.getBegin():"";
        String end = teacherQuery != null ? teacherQuery.getEnd():"";
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        eduTeacherService.page(page, wrapper);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        return Result.ok().data("total",total).data("rows",records);
    }


    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.save(eduTeacher);
        return b ? Result.ok():Result.error();
    }



}

