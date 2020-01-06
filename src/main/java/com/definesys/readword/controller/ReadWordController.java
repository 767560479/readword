package com.definesys.readword.controller;

import com.definesys.mpaas.common.http.Response;
import com.definesys.mpaas.query.MpaasQueryFactory;
import com.definesys.readword.dao.TestReadWordDAO;
import com.definesys.readword.pojo.TestReadWord;
import com.definesys.readword.service.UploadService;
import com.definesys.readword.utils.TraceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: zhengfei.tan
 * @since: 2019/8/20 17:55
 * @history: 1.2019/8/20 created by zhengfei.tan
 */
@Controller
public class ReadWordController {

    @Autowired
    private TestReadWordDAO dao;

    @Autowired
    private UploadService service;

    @Autowired
    private MpaasQueryFactory sw;

    @GetMapping("/index")
    public String toIndex(){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Response fileUpload(HttpServletRequest request, String type){
        return service.fileUpload(request, type);
    }


    @GetMapping("/test")
    public String test(){
        return "test";
    }




}
