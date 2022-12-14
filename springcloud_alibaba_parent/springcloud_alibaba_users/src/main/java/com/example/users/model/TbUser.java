package com.example.users.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@TableName("tb_user")
@Data
@ToString
public class TbUser {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;

    private String userName;

    private String userPass;

    private int gender;

    private String nickName;

    private String createTime;

}
