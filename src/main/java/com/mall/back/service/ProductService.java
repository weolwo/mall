package com.mall.back.service;

import com.github.pagehelper.PageInfo;
import com.mall.common.ServerResponse;
import com.mall.pojo.Product;
import com.mall.vo.ProductVo;

public interface ProductService {

    ServerResponse<String> saveProduct(Product product);

    ServerResponse<String> setProoductStatus(Integer productId, Integer status);

    ServerResponse<ProductVo> getProductInfo(Integer productId);

    ServerResponse<PageInfo> getProoductList(Integer pageSize, Integer pageNum);

    ServerResponse<PageInfo> searchProductList(Integer productId, String productName, Integer pageSize, Integer pageNum);
}
