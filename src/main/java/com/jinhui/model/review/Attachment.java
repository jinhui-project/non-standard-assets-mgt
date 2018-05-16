package com.jinhui.model.review;

import com.jinhui.model.Fileinfos;
import com.jinhui.model.Users;

import java.util.Date;

/**
 * Created by jinhui on 2017/7/6.
 */
public class Attachment {
    private Integer assetId;
    private String attachmentType;
    private Fileinfos fileinfo;
    private Users user;
    private Date gmtCreated;

    public Attachment(Integer assetId, Long fileId, Integer userId, String attachmentType) {
        this.assetId = assetId;
        fileinfo = new Fileinfos();
        fileinfo.setId(fileId);
        this.user = new Users();
        user.setId(userId);
        this.attachmentType = attachmentType;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public Fileinfos fileinfo() {
        return fileinfo;
    }

    public Users user() {
        return user;
    }

    Attachment() {
    }
}
