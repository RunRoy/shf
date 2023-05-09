package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

/**
 * @author lystart
 * @create 2023-04-28 9:09
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    private final static String LIST_ACTION = "redirect:/admin";

    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    private final static String PAGE_UPLOED_SHOW = "admin/upload";



    private final static String PAGE_ASSGIN_SHOW = "admin/assignShow";

    @Reference
   protected AdminService service;

    @Reference
    private RoleService roleService;

    @RequestMapping("/assignRole")
    public String assignRole(Long adminId,Long[] roleIds){
        roleService.insertAdminAndRole(adminId,roleIds);
        return PAGE_SUCCESS;
    }


    //opt.openWin('/admin/assignShow/'+id,'分配角色',550,450)
    // 显示页面
    @RequestMapping("/assignShow/{adminId}")
    public String assignShow(ModelMap modelMap,@PathVariable Long adminId){
        Map<String, Object> roleMap = service.findRoleByAdminId(adminId);
        modelMap.addAllAttributes(roleMap);
        modelMap.addAttribute("adminId",adminId);
        return PAGE_ASSGIN_SHOW;
    }



    /**
     * 跳转上传头像页面
     * @param map
     * @param id
     * @return
     */
    @RequestMapping("/uploadShow/{id}")
    public String  uploadShow(ModelMap map,@PathVariable Long id){
        map.addAttribute("id",id);
        return PAGE_UPLOED_SHOW;
    }

    /**
     * 提交上传头像
     * @param
     * @param request
     * @return
     */
    ///admin/upload/{id}(id=${id})
    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable Long id, @RequestParam(value = "file") MultipartFile file, HttpServletRequest request)   {
        try {
        String newName = UUID.randomUUID().toString();
        QiniuUtils.upload2Qiniu(file.getBytes(),newName);

        String url = "http://ru7w8odka.hn-bkt.clouddn.com/" + newName;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl(url);
        service.update(admin);
        return PAGE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping
    public String index(ModelMap model, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        PageInfo<Admin> page = service.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    @RequestMapping("/create")
    public String create(ModelMap modelMap){
        return PAGE_CREATE;
    }

     @RequestMapping("/save")
    public String save(Admin admin){
        service.insert(admin);
        return PAGE_SUCCESS;
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap modelMap){
        Admin admin = service.getById(id);
        modelMap.addAttribute(admin);
        return PAGE_EDIT;
    }

    @RequestMapping("/update")
    public  String update(Admin admin){
        service.update(admin);
        return PAGE_SUCCESS;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id,ModelMap modelMap){
        service.delete(id);
        return LIST_ACTION;
    }


}
