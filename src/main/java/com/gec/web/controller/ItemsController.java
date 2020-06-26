package com.gec.web.controller;

import com.gec.web.pojo.Items;
import com.gec.web.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemsController {

    @Autowired
    private ItemService itemService;

    private static final int page_size=2;//每页显示的记录条数

    @GetMapping("/queryItems")
    public ModelAndView queryItems( @RequestParam(value="pageNum",required=false,defaultValue="1")int pageNum){
        ModelAndView mv = new ModelAndView();
        //mybatis分页插件拦截
        PageHelper.startPage(pageNum, page_size);
        List<Items> list = itemService.queryItems();

        PageInfo page = new PageInfo<>(list);
        mv.addObject("itemsList",list);
        mv.addObject("page",page);
        mv.setViewName("itemsList");
        return mv;
    }

    //参数列表主要定义：业务对象，二进制文件
    @RequestMapping("/addItemsSubmit")
    public String addItemsSubmit(Items items,MultipartFile picFile,HttpSession session){
        try {
            //1.上传的文件存放在，tomcat所在的服务器，部署的工程项目中，如果工程重新打包，重新部署，会删除上传的文件；
            //2.上传的文件存放在，tomcat所在的服务器，但是不是部署的工程项目中，是存放在某一个文件夹中，该文件夹要做映射配置
            String path = "D:/upload";

            File targetFile = new File(path,picFile.getOriginalFilename());
            picFile.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置图片名称
        items.setPic(picFile.getOriginalFilename());
        itemService.addItems(items);
        return "redirect:/queryItems";//redirect是重定向关键词
    }
}
