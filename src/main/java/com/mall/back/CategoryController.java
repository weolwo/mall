package com.mall.back;

import com.mall.back.service.CategoryService;
import com.mall.common.ConstantCode;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.pojo.Category;
import com.mall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/manage/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 增加品类
     * @param parentId
     * @param categoryName
     * @param session
     * @return
     */
    @RequestMapping(value = "addCategory.do", method = RequestMethod.POST)
    public ServerResponse<String> addCategory(@RequestParam(value = "parentId", defaultValue = "0") Integer parentId, String categoryName, HttpSession session) {
        //校验用户是否登录
        User user = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMessage());
        }
        //校验当前用户是否具有权限
        if (user.getRole() == ConstantCode.Role.ROLE_ADMIN) {
            //进行相关的操作
            return categoryService.addCategory(parentId, categoryName);
        }
        return ServerResponse.createByErrorMessage("没有权限操作");
    }

    /**
     * 更新品类的名称
     * @param parentId
     * @param categoryName
     * @param session
     * @return
     */
    @RequestMapping(value = "updateCategoryName.do", method = RequestMethod.POST)
    public ServerResponse<String> updateCategoryName(Integer categoryId, String categoryName, HttpSession session) {
        //校验用户是否登录
        User user = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMessage());
        }
        //校验当前用户是否具有权限
        if (user.getRole() == ConstantCode.Role.ROLE_ADMIN) {
            //进行相关的操作
            return categoryService.updateCategoryName(categoryId, categoryName);
        }
        return ServerResponse.createByErrorMessage("没有权限操作");
    }

    ////查询子节点的category信息,并且不递归,保持平级
    @RequestMapping(value = "selectChildByCategoryId.do", method = RequestMethod.POST)
    public ServerResponse<List<Category>> selectChildByCategoryId(@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId, HttpSession session) {
        //校验用户是否登录
        User user = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMessage());
        }
        //校验当前用户是否具有权限
        if (user.getRole() == ConstantCode.Role.ROLE_ADMIN) {
            //进行相关的操作
            return categoryService.selectChildByCategoryId(categoryId);
        }
        return ServerResponse.createByErrorMessage("没有权限操作");
    }
    //递归查询本节点及子节点的id
    @RequestMapping(value = "deepSelectChildByCategoryId.do", method = RequestMethod.POST)
    public ServerResponse<List<Integer>> deepSelectChildByCategoryId(@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId, HttpSession session) {
        //校验用户是否登录
        User user = (User) session.getAttribute(ConstantCode.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getMessage());
        }
        //校验当前用户是否具有权限
        if (user.getRole() == ConstantCode.Role.ROLE_ADMIN) {
            //进行相关的操作
            return categoryService.deepSelectChildByCategoryId(categoryId);
        }
        return ServerResponse.createByErrorMessage("没有权限操作");
    }
}
