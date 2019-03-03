package com.mall.back.service;

import com.mall.common.ServerResponse;

public interface CategoryService {

    public ServerResponse<String> addCategory(Integer parentId, String categoryName);

    ServerResponse<String> updateCategoryName(Integer parentId, String categoryName);
}
