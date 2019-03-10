package com.mall.back.controller;

import com.mall.back.service.ProductService;
import com.mall.common.ConstantCode;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.pojo.Product;
import com.mall.pojo.User;
import com.mall.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/product/")
public class ProductManagerController {

    @Autowired
    private ProductService productService;

    /**
     * 保存商品信息
     * @param session
     * @param product
     * @return
     */
    @RequestMapping("saveProduct")
    public ServerResponse<String> saveProduct(HttpSession session, Product product){
        //校验用户是否登录及是否具有权限
        User user = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMessage());
        }
        //校验当前用户是否具有权限
        if (user.getRole() == ConstantCode.Role.ROLE_ADMIN) {
            //进行相关的操作
            return productService.saveProduct(product);
        }
        return ServerResponse.createByErrorMessage("没有权限操作");

    }

    /**
     * 商品上下架
     * @param session
     * @param
     * @return
     */
    @RequestMapping("setProoductStatus")
    public ServerResponse<String> setProoductStatus(HttpSession session, Integer productId,Integer status){
        //校验用户是否登录及是否具有权限
        User user = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMessage());
        }
        //校验当前用户是否具有权限
        if (user.getRole() == ConstantCode.Role.ROLE_ADMIN) {
            //进行相关的操作
            return productService.setProoductStatus(productId,status);
        }
        return ServerResponse.createByErrorMessage("没有权限操作");

    }

    /**
     * 获取商品信息
     * @param session
     * @param productId
     * @param
     * @return
     */
    @RequestMapping("getProoductInfo")
    public ServerResponse<ProductVo> getProoductInfo(HttpSession session, Integer productId){
        //校验用户是否登录及是否具有权限
        User user = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMessage());
        }
        //校验当前用户是否具有权限
        if (user.getRole() == ConstantCode.Role.ROLE_ADMIN) {
            //进行相关的操作
            return productService.getProductInfo(productId);
        }
        return ServerResponse.createByErrorMessage("没有权限操作");

    }
}
