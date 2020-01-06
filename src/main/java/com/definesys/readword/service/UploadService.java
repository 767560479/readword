package com.definesys.readword.service;

import com.definesys.mpaas.common.http.Response;
import com.definesys.readword.dao.TestReadWordDAO;
import com.definesys.readword.pojo.TestReadWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: zhengfei.tan
 * @since: 2019/8/20 18:31
 * @history: 1.2019/8/20 created by zhengfei.tan
 */
@Service
public class UploadService {

    @Autowired
    private TestReadWordDAO dao;

    @Autowired
    private WordToBeanService wordToBeanService;

    //允许上传的文件
    private final static String ALLOW_UPLOAD_SUFFIX = "doc_docx_pdf_png_jpg_bmp_xlsx_xls_txt_html_pptx_ppt_emmx_zip_7z_hex_ulp";

    public Response fileUpload(HttpServletRequest request, String type){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        //文件名
        String defaultFileName;
        //文件类型
        String defaultContentType;
        //文件大小
        Long defaultSize;
        //文件后缀
        String defaultSuffix;

        for (MultipartFile file : files) {
            //获取文件详情
            defaultFileName = file.getOriginalFilename();
            defaultContentType = file.getContentType();
            defaultSize = file.getSize();
            defaultSuffix = defaultFileName.substring(defaultFileName.lastIndexOf(".") + 1);

            //判断文件是否合法(文件后缀合法和大小<20M)
            if (ALLOW_UPLOAD_SUFFIX.contains(defaultSuffix) && ((defaultSize / 1024) < 20 * 1024) && !"".equals(defaultFileName)) {
                // 广西人才网
                if ("1".equals(type))  {

                    try {
                        wordToBeanService.toGXRCW(file);
                    } catch (Exception e) {
                        return Response.error(file.getOriginalFilename()+ "  简历以及之后的文件解析失败！");
                    }
                }

                // 智联
                if ("2".equals(type))  {

                    try {
                        wordToBeanService.toZLZP(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Response.error(file.getOriginalFilename()+ "  简历以及之后的文件解析失败！");
                    }
                }

                // 51
                if ("3".equals(type))  {

                    try {
                        wordToBeanService.toJob(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Response.error(file.getOriginalFilename()+ "  简历以及之后的文件解析失败！");
                    }

                }

                // 猎聘
                if ("4".equals(type))  {
                    try {
                        wordToBeanService.toLPW(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Response.error(file.getOriginalFilename()+ "  简历以及之后的文件解析失败！");
                    }
                }

                // 卓聘
                if ("5".equals(type))  {

                    try {
                        wordToBeanService.toZhuoPin(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Response.error(file.getOriginalFilename()+ "  简历以及之后的文件解析失败！");
                    }

                }

            }
        }

        return Response.ok();
    }

}
