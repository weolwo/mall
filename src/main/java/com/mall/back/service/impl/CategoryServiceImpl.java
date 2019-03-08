package com.mall.back.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mall.back.service.CategoryService;
import com.mall.common.ServerResponse;
import com.mall.dao.CategoryMapper;
import com.mall.pojo.Category;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse<String> addCategory(Integer parentId, String categoryName) {
        if (parentId != null && StringUtils.isNotBlank(categoryName)) {
            Category category = new Category();
            category.setParentId(parentId);
            category.setName(categoryName);
            int resultCount = categoryMapper.insert(category);
            if (resultCount > 0) {
                return ServerResponse.createBySuccess("添加成功");
            } else {
                return ServerResponse.createByErrorMessage("添加失败");
            }
        }
        return ServerResponse.createByErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<String> updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId != null && StringUtils.isNotBlank(categoryName)) {
            Category category = new Category();
            category.setId(categoryId);
            category.setName(categoryName);
            int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
            if (resultCount > 0) {
                return ServerResponse.createBySuccess("更新成功");
            } else {
                return ServerResponse.createByErrorMessage("更新失败");
            }
        }
        return ServerResponse.createByErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<List<Category>> selectChildByCategoryId(Integer categoryId) {
        List<Category> list = categoryMapper.selectChildByCategoryId(categoryId);
        if (CollectionUtils.isEmpty(list)){
            return ServerResponse.createBySuccess("当前分类的子分类为空",list);
        }
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<List<Integer>> deepSelectChildByCategoryId(Integer categoryId) {
        Set<Category> set= Sets.newHashSet();
        recurrence(set, categoryId);
        List<Integer> list= Lists.newArrayList();
        if (categoryId!=null){
            for (Category category : set) {
                list.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(list);
    }

    private Set<Category> recurrence(Set<Category> set,Integer categoryId){
       Category category = categoryMapper.selectByPrimaryKey(categoryId);
       if (category!=null){
           set.add(category);
       }
       //查找子节点
        List<Category> categoryList = categoryMapper.selectChildByCategoryId(category.getId());
        for (int i=0; i<categoryList.size(); i++) {

            for (Category category1 : categoryList) {
                recurrence(set,category1.getId());
            }
        }
        return set;
    }
}
