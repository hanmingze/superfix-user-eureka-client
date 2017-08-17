package com.zgkj.user.repository;

import com.zgkj.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 韩明泽 on 2017/8/4.
 * user管理jpa模板
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return user
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
    User findByUsername(String username);

    /**
     * ajax校验手机号唯一
     * @param phone
     * @return
     */
    User findByPhone(String phone);
    /**
     * ajax校验邮箱账号唯一
     * @param email
     * @return
     */
    User findByEmail(String email);
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
     * email、username验证User
     * @param email
     * @param username
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
