package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/brand")
//@Controller
@RestController  //组合注解 Controller 和 ResponseBody；类上面的该注解对所有方法生效
public class BrandController {

    //从注册中心引入服务代理对象
    @Reference
    private BrandService brandService;


    /**
     * 接收品牌数据并保存品牌
     * @param brand 品牌数据
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody TbBrand brand) {
        try {
            brandService.add(brand);
            return Result.ok("新增品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("新增品牌失败");
    }

    /**
     * 根据主键查询品牌
     * @param id
     * @return
     */
    @GetMapping("/findOne")
    public TbBrand findOne(long id) {
        return brandService.findOne(id);
    }
    /**
     * 修改后并保存
     * @param brand
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody TbBrand brand) {
        try {
            brandService.update(brand);
            return Result.ok("修改品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("修改品牌失败");
    }

    @GetMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            brandService.deleteByIds(ids);
            return Result.ok(" 删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(" 删除失败");
    }

    /**
     * 根据分页条件查询，查询第1页每页5条品牌列表
     * @param page 页号
     * @param rows 页大小
     * @return 品牌列表
     */
    @GetMapping("/testPage")
    public List<TbBrand> testPage(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                  @RequestParam(value = "rows",defaultValue = "10")Integer rows) {
        //return brandService.testPage(page, rows);
        return (List<TbBrand>) brandService.findPage(page,rows).getRows();
    }

    //@RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @GetMapping("/findAll")
    //@ResponseBody
    public List<TbBrand> findAll() {
        //return brandService.queryAll();
        return brandService.findAll();
    }

    //分页全部数据
    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo,
                               @RequestParam(value = "rows",defaultValue = "10")Integer rows) {
        return brandService.findPage(pageNo,rows);
    }

    /**
     * 根据分页条件查询
     * @param pageNo 页号
     * @param rows 页大小
     * @return 分页对象
     */
    @PostMapping("/search")
    public PageResult search(@RequestBody TbBrand brand,
                             @RequestParam(value="pageNo", defaultValue = "1")Integer pageNo,
                             @RequestParam(value="rows", defaultValue = "10")Integer rows){
        return brandService.search(brand, pageNo, rows);
    }

    /**
     * 用于模板管理的关联品牌下拉条功能,返回的数据格式符合 select2 格式
     * @return
     */
    @GetMapping("/selectOptionList")
    public List<Map<String, String>> selectOptionList() {
        return brandService.selectOptionList();
    }
}
