package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService extends BaseService<TbBrand> {

    /**
     * 查询所有品牌列表
     * @return 品牌列表
     */
    List<TbBrand> queryAll();

    /**
     * 根据分页条件查询，查询第1页每页5条品牌列表
     * @param page 页号
     * @param rows 页大小
     * @return 品牌列表
     */
    List<TbBrand> testPage(Integer page, Integer rows);

    //条件分页
    PageResult search(TbBrand brand,Integer page, Integer rows);

    List<Map<String, String>> selectOptionList();
}
