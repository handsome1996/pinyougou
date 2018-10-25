package com.pinyougou.mapper;

import com.pinyougou.pojo.TbSpecification;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpecificationMapper extends Mapper<TbSpecification> {

    //用于模板管理的关联规格下拉条功能
    List<Map<String, String>> selectOptionList();

}
