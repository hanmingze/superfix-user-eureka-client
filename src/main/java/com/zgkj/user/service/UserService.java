package com.zgkj.user.service;

import com.zgkj.user.pojo.User;

/**
 * Created by 韩明泽 on 2017/8/4.
 * service层
 */
public interface UserService {
    /**
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User findByUsernameAndPassword(String username, String password);
    /**
     * 登陆
     * @param email 邮箱
     * @param password 密码
     * @return
     */
    User findByEmailAndPassword(String email, String password);

    /**
     * 登陆
     * @param phone 手机号
     * @param password 密码
     * @return
     */
    User findByPhoneAndPassword(String phone, String password);
    /**
     * ajax校验用户名唯一
     * @param username
     * @return
     */
    String findByUsername(String username);
    /**
     * ajax校验手机号唯一
     * @param phone
     * @return
     */
    String findByPhone(String phone);
    /**
     * ajax校验邮箱账号唯一
     * @param email
     * @return
     */
    String findByEmail(String email);

    /**
     * 添加
     * @param user
     */
    void save(User user);
    /**
     * 根据uuid查询user
     * @param uuid
     * @return
     */
    User findByUuid(String uuid);
    /**
     * 根据uuid和id查询用户
     * @param uuid
     * @param id
     * @return
     */
    User findByUuidAndId(String uuid,Integer id);

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    User findById(Integer id);
    /**
     * 根据id删除用户
     * @param id
     */
    void deleteById(Integer id);
    /**
     * 根据username和email验证用户
     * @param username
     * @param email
     * @return
     */
    User findByEmailAndUsername(String email,String username);
    /**
     * 根据email查询user
     * @param email
     * @return
     */
    User findUserByEmail(String email);
}
