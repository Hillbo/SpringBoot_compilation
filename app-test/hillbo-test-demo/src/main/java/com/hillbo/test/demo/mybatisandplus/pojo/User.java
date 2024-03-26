package com.hillbo.test.demo.mybatisandplus.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_sdata_user")
public class User {

    @TableField(exist = false)
    private Integer id;

    private Integer age;

    private String email;

    private String name;

}
