package com.mall.back.service.impl;

import com.mall.back.service.CategoryService;
import com.mall.common.ServerResponse;
import com.mall.dao.CategoryMapper;
import com.mall.pojo.Category;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ServerResponse<String> updateCategoryName(Integer parentId, String categoryName) {
        if (parentId != null && StringUtils.isNotBlank(categoryName)) {
            Category category = new Category();
            category.setParentId(parentId);
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

}
