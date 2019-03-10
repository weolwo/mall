package com.mall.back.service;

import com.mall.common.ServerResponse;
import com.mall.pojo.Product;
import com.mall.vo.ProductVo;

public interface ProductService {

    ServerResponse<String> saveProduct(Product product);

    ServerResponse<String> setProoductStatus(Integer productId, Integer status);

    ServerResponse<ProductVo> getProductInfo(Integer productId);
}
