package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/label")
public class LabelController {


    @Autowired
    private LabelService labelService;


    /**
     * 查询全部标签
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Label> list = labelService.findAll();

        return new Result(true, StatusCode.OK, "查询成功",list);
    }

       /**
     *  根据ID查询标签
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Result findById( @PathVariable String id){
        System.out.println("No.2");
        Label label = labelService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",label);
    }

    /**
     *  增加标签
     * @param label
     * @return
     */
    @RequestMapping( method = RequestMethod.POST)
    public Result add( @RequestBody Label label ){
        labelService.add(label);
        return new Result(true,StatusCode.OK,"增加成功");
    }


    /**
     *  修改标签
     * @param label
     * @return
     */
    @RequestMapping(value="/{id}" ,method = RequestMethod.PUT)
    public Result update( @RequestBody Label label,@PathVariable String id ){
        label.setId(id);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}" ,method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id ){
        labelService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }


    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap ){
        List<Label> list = labelService.findSearch(searchMap);
        return new Result(true, StatusCode.OK, "查询成功",list);
    }


    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap ,@PathVariable int page,@PathVariable int size){
        Page<Label> pagelist = labelService.findSearch(searchMap,page,size);
        PageResult pageResult=new PageResult(pagelist.getTotalElements(),pagelist.getContent());
        return new Result(true, StatusCode.OK, "查询成功",pageResult);
    }

    /**
     * 有效标签列表
     * @return
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public Result list(){
        List<Label> list = labelService.list();
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 推荐标签列表
     * @return
     */
    @RequestMapping(value="/toplist",method = RequestMethod.GET)
    public Result toplist(){
        List<Label> list = labelService.toplist();
        return new Result(true,StatusCode.OK,"查询成功",list);
    }


}
