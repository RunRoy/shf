package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author lystart
 * @create 2023-05-06 12:52
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends BaseController {

    private final static String LIST_ACTION = "redirect:/house/";
    private final static String PAGE_UPLOED_SHOW = "house/upload";


    @Reference
   protected HouseImageService houseImageService;

    /**
     * 跳转上传页面
     */
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(ModelMap map, @PathVariable Long houseId,@PathVariable Long type){
        map.addAttribute("houseId",houseId);
        map.addAttribute("type",type);
        return PAGE_UPLOED_SHOW;
    }

    /**
     * 点击提交
     */
    @RequestMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable Long houseId, @PathVariable Integer type, @RequestParam(value = "file") MultipartFile[] files) throws Exception {
        if (files.length>0) {
            for (MultipartFile file : files) {
                String newFileName =  UUID.randomUUID().toString() ;
                // 上传图片
                QiniuUtils.upload2Qiniu(file.getBytes(),newFileName);
                String url= "http://ru7w8odka.hn-bkt.clouddn.com/"+ newFileName;

                HouseImage houseImage = new HouseImage();
                houseImage.setHouseId(houseId);
                houseImage.setImageName(newFileName);
                houseImage.setImageUrl(url);
                houseImage.setType(type);
                houseImageService.insert(houseImage);
            }
        }
        return Result.ok();
    }

    /**
     * 删除
     */
    ///houseImage/delete/[[${house.id}]]/'+id);
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable Long id){
        // 根据id找到图片
        HouseImage houseImage = houseImageService.getById(id);
        // 删除数据库图片
        houseImageService.delete(id);
        // 删除云服务器图片
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());
        return LIST_ACTION + houseId;
    }
}

