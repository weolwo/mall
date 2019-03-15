package com.mall.back.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mall.back.service.FileUploadService;
import com.mall.back.service.ProductService;
import com.mall.common.ConstantCode;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.pojo.Product;
import com.mall.pojo.User;
import com.mall.utils.PropertiesUtil;
import com.mall.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequestMapping("/manage/product/")
public class ProductManagerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileUploadService fileUploadService;
    /**
     * 保存商品信息
     * @param session
     * @param product
     * @return
     */
    @RequestMapping("saveProduct.do")
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
    @RequestMapping("setProoductStatus.do")
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
    @RequestMapping("getProoductInfo.do")
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

    /**
     * 分页查询
     * @param
     * @param session
     * @return
     */
    @RequestMapping("getProductList.do")
    public ServerResponse<PageInfo> getProductList(HttpSession session, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, @RequestParam(value = "pageSize",defaultValue = "1")Integer pageNum){
        //校验用户是否登录及是否具有权限
        User user = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMessage());
        }
        //校验当前用户是否具有权限
        if (user.getRole() == ConstantCode.Role.ROLE_ADMIN) {
            //进行相关的操作
            return productService.getProoductList(pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("没有权限操作");

    }
    @RequestMapping("searchProductList.do")
    public ServerResponse<PageInfo> searchProductList(Integer productId,String productName, HttpSession session, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, @RequestParam(value = "pageSize",defaultValue = "1")Integer pageNum){
        //校验用户是否登录及是否具有权限
        User user = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMessage());
        }
        //校验当前用户是否具有权限
        if (user.getRole() == ConstantCode.Role.ROLE_ADMIN) {
            //进行相关的操作
            return productService.searchProductList(productId,productName,pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("没有权限操作");

    }

    /**
     * 图片上传
     * @return
     */
    @RequestMapping("uploadFile.do")
    public ServerResponse uploadFile(HttpServletRequest request, MultipartFile file){
        String path = request.getSession().getServletContext().getRealPath("uploadFile");
        String fileName = fileUploadService.uploadFile(path, file);
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("uri",fileName);
        map.put("url", PropertiesUtil.getPropertis("ftp.server.http.prefix")+fileName);
        return ServerResponse.createBySuccess(map);
    }
}
