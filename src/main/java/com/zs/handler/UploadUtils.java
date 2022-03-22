package com.zs.handler;

import com.zs.config.Const;
import com.zs.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 上传文件处理器
 * @Created by zs on 2022/3/6.
 */
public class UploadUtils {

    // TODO 处理图片时，如果是编辑的情况，则需删除以前保存的图片，以减少空间占用

    /**
     * 上传图片处理器
     * @param uploadFile 上传文件对象
     * @param userName 用户名
     * @param picSize 限定文件大小
     * @return serverSaveFileName == null  表示图片大小、格式有误
     *         serverSaveFileName == ""    表示未上传图片
     *         serverSaveFileName == xxx   表示图片处理成功，返回在本地保存的文件名
     * @throws Exception
     */
    public static String uploadPictureHandler(MultipartFile uploadFile, String userName, Long picSize) throws Exception {
        String serverSaveFileName = null;
        // 上传文件不为 null / 大小不为 0
        if(uploadFile != null && uploadFile.getSize() != 0) {
            // 文件是否超过规定大小
            if (uploadFile.getSize() < picSize) {
                String originalFilename = uploadFile.getOriginalFilename();
                String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
                if (Const.PICTURE_SUPPORT_FORMAT.contains(ext)) {
                        // 文件处理
                        serverSaveFileName = userName + System.currentTimeMillis() + ext;
                        String parentDir = System.getProperty("user.dir");
                        String childDir = Const.BLOG_FIRST_PICTURE_SAVE_DIR + serverSaveFileName;
                        uploadFile.transferTo(new File(parentDir + childDir));
                } else {
                    serverSaveFileName = null;
                    throw new UniversalException("上传图片格式有误");
                }
            } else {
                serverSaveFileName = null;
                throw new UniversalException("上传图片大小有误");
            }
        } else {
            serverSaveFileName = "";
        }
        return serverSaveFileName;
    }

}
