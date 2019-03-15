package com.mall.back.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mall.back.service.ProductService;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dao.CategoryMapper;
import com.mall.dao.ProductMapper;
import com.mall.pojo.Category;
import com.mall.pojo.Product;
import com.mall.utils.DateTimeConvertUtil;
import com.mall.vo.ProductListVo;
import com.mall.vo.ProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增和更新商品信息
     * @param product
     * @return
     */
    @Override
    public ServerResponse<String> saveProduct(Product product) {
        if (product!=null){
            //取出第一张子图作为主图
            String subImages = product.getSubImages();
            String[] split = subImages.split(",");
            product.setMainImage(split[0]);
            if (product.getId()!=null){
                int resultValue = productMapper.updateByPrimaryKeySelective(product);
                if (resultValue>0){
                    return ServerResponse.createBySuccessMessage("更新商品成功");
                }
                return ServerResponse.createByErrorMessage("更新商品失败");
            }else {
                int resultValue = productMapper.insert(product);
                if (resultValue>0){
                    return ServerResponse.createBySuccessMessage("新增商品成功");
                }
                return ServerResponse.createByErrorMessage("新增商品失败");
            }
        }
        return ServerResponse.createByErrorMessage("参数有误");
    }

    @Override
    public ServerResponse<String> setProoductStatus(Integer productId, Integer status) {
        if (productId!=null && status!=null){
            Product product=new Product();
            product.setId(productId);
            product.setStatus(status);
            int resultValue = productMapper.updateByPrimaryKeySelective(product);
            if (resultValue>0){
                return ServerResponse.createBySuccessMessage("设置商品状态成功");
            }
            return ServerResponse.createByErrorMessage("设置商品状态失败");
        }
        return  ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getMessage());
    }

    @Override
    public ServerResponse<ProductVo> getProductInfo(Integer productId) {
        if (productId!=null){
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product==null){
                return ServerResponse.createByErrorMessage("商品不存在");
            }
            ProductVo productVo = assemblyProduct(product);
            return ServerResponse.createBySuccess(productVo);
        }
        return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getMessage());
    }
    private ProductVo assemblyProduct(Product product){
        ProductVo productVo=new ProductVo();
        productVo.setId(product.getId());
        productVo.setName(product.getName());
        productVo.setPrice(product.getPrice());
        productVo.setStatus(product.getStatus());
        productVo.setStock(product.getStock());
        productVo.setDetail(product.getDetail());
        productVo.setSubtitle(product.getSubtitle());
        productVo.setDetail(product.getDetail());
        productVo.setCategoryId(product.getCategoryId());
        productVo.setMainImage("");
        productVo.setSubImages("");
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category==null){
            productVo.setParentCategoryId(0);
        }
        productVo.setParentCategoryId(product.getCategoryId());
        //时间转换
        productVo.setCreateTime(DateTimeConvertUtil.dateToStr(product.getCreateTime()));
        productVo.setCreateTime(DateTimeConvertUtil.dateToStr(product.getUpdateTime()));
        return productVo;
    }

    @Override
    public ServerResponse<PageInfo> getProoductList(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selecList();
        List<ProductListVo> productListVoList= Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = assemblyProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo=new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private ProductListVo assemblyProductListVo(Product product) {
        ProductListVo productListVo=new ProductListVo();
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setId(product.getId());
        productListVo.setImageHost("");
        productListVo.setMainImage("");
        productListVo.setName(product.getName());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    @Override
    public ServerResponse<PageInfo> searchProductList(Integer productId, String productName, Integer pageSize, Integer pageNum) {
        if (StringUtils.isNotBlank(productName)){
            StringBuilder stringBuilder=new StringBuilder();
            productName=stringBuilder.append("%").append(productName).append("%").toString();//拼接查询条件
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.searchProductList(productId,productName);
        List<ProductListVo> productListVoList= Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = assemblyProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo=new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
