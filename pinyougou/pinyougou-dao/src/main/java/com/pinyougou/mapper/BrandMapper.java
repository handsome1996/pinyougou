package com.pinyougou.mapper;

import com.pinyougou.pojo.TbBrand;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends Mapper<TbBrand> {

    List<TbBrand> queryAll();


    //用于模板管理的关联品牌下拉条功能
    List<Map<String, String>> selectOptionList();
}
