package com.mall.back.service;

import com.mall.common.ServerResponse;
import com.mall.pojo.Category;

import java.util.List;

public interface CategoryService {

    public ServerResponse<String> addCategory(Integer parentId, String categoryName);

    ServerResponse<String> updateCategoryName(Integer parentId, String categoryName);

    ServerResponse<List<Category>> selectChildByCategoryId(Integer categoryId);

    ServerResponse<List<Integer>> deepSelectChildByCategoryId(Integer categoryId);
}
