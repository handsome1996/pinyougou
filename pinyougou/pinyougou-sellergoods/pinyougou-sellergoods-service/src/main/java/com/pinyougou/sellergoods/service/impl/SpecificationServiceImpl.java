package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SpecificationService.class)
public class SpecificationServiceImpl extends BaseServiceImpl<TbSpecification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * 1.分页条件查询
     * @param page
     * @param rows
     * @param specification
     * @return
     */
    @Override
    public PageResult search(Integer page, Integer rows, TbSpecification specification) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(specification.getSpecName())){
            criteria.andLike("specName", "%" + specification.getSpecName() + "%");
        }

        List<TbSpecification> list = specificationMapper.selectByExample(example);
        PageInfo<TbSpecification> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 2.保存规格及其选项列表到数据库中
     * @param specification 规格信息（规格及选项列表）；如：
     * {"specificationOptionList":[{"optionName":"蓝色","orders":"1"}],"specification":{"specName":"颜色"}}
     */
    @Override
    public void add(Specification specification) {
        //保存规格,在通用mapper中插入完之后可以回填新增对象的主键值
        specificationMapper.insertSelective(specification.getSpecification());

        //2、保存规格选项列表中的每一个选项
        if(specification.getSpecificationOptionList() != null && specification.getSpecificationOptionList().size() > 0) {
            for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
                //设置规格id
                specificationOption.setSpecId(specification.getSpecification().getId());
                //保存规格选项
                specificationOptionMapper.insertSelective(specificationOption);
            }
        }
    }


    /**
     * 3.根据规格id到数据库中查询规格及其选项
     * @param id 规格id
     * @return 规格及其选项
     */
    @Override
    public Specification findOne(Long id) {
        Specification specification = new Specification();
        //3.1、根据规格id查询规格
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        specification.setSpecification(tbSpecification);

        //3.2、根据规格id查询该规格对应的所有选项
        /**
         *数据库执行语句如：
         *  select * from tb_specification_option where spec_id = ?
         */
        TbSpecificationOption param = new TbSpecificationOption();
        param.setSpecId(id);
        List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.select(param);

        //3.3设置规格选项集合
        specification.setSpecificationOptionList(specificationOptionList);
        return specification;
    }


    /**
     * 4.规格、选项集合更新到数据库中
     * @param specification 规格及其选项
     * @return 操作结果
     */
    @Override
    public void update(Specification specification) {
        //4.1、更新规格
        specificationMapper.updateByPrimaryKey(specification.getSpecification());

        //4.2、删除该规格对应的所有选项，根据规格id查询所有对应的选项delete from tb_specification_option where spec_id=?
        TbSpecificationOption param = new TbSpecificationOption();
        param.setSpecId(specification.getSpecification().getId());
        specificationOptionMapper.delete(param);

        //4.3、新增该规格最新的规格选项集合
        if (specification.getSpecificationOptionList() != null && specification.getSpecificationOptionList().size() > 0) {
            for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
                //设置规格id
                specificationOption.setSpecId(specification.getSpecification().getId());
                //保存规格选项
                specificationOptionMapper.insertSelective(specificationOption);
            }
        }
    }


    /**
     * 5.删除规格及其对应的所有选项
     * @param ids 规格id集合
     * @return 操作结果
     */
    @Override
    public void deleteSpecificationByIds(Long[] ids) {
        //5.1、根据规格id删除规格
        deleteByIds(ids);

        //5.2、根据规格id集合删除规格选项
        //delete from tb_specification_option where spec_id in (?,?);

        Example example = new Example(TbSpecificationOption.class);
        example.createCriteria().andIn("specId", Arrays.asList(ids));

        specificationOptionMapper.deleteByExample(example);
    }

    //6.用于模板管理的关联规格下拉条功能
    @Override
    public List<Map<String, String>> selectOptionList() {
        return specificationMapper.selectOptionList();
    }
}
