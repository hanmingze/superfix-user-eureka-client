package com.zgkj.user.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


/**
 * Created by 韩明泽 on 2017/8/4.
 */
@Entity
@Data
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private Long createtime;
    private Integer activ;
}
