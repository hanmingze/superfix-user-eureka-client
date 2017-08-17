package com.zgkj.user.service.impl;

import com.zgkj.user.pojo.User;
import com.zgkj.user.repository.UserRepository;
import com.zgkj.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 韩明泽 on 2017/8/4.
 * service层实现类
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;
    }
    /**
     * 登陆
     * @param email 邮箱
     * @param password 密码
     * @return
     */
    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user=userRepository.findByEmailAndPassword(email, password);
        return user;
    }
    /**
     * 登陆
     * @param phone 手机号
     * @param password 密码
     * @return
     */
    @Override
    public User findByPhoneAndPassword(String phone, String password) {
        User user=userRepository.findByPhoneAndPassword(phone, password);
        return user;
    }
    /**
     * ajax校验用户名唯一
     * @param username
     * @return
     */
    @Override
    public String findByUsername(String username) {
        User user=userRepository.findByUsername(username);
        if(user==null){
           return "1";
        }
        return "2";
    }
    /**
     * ajax校验手机号唯一
     * @param phone
     * @return
     */
    @Override
    public String findByPhone(String phone) {
        User user=userRepository.findByPhone(phone);
        if(user==null){
            return "1";
        }
        return "2";
    }
    /**
     * ajax校验邮箱账号唯一
     * @param email
     * @return
     */
    @Override
    public String findByEmail(String email) {
        User user=userRepository.findByEmail(email);
        if(user==null){
            return "1";
        }
        return "2";
    }

    /**
     * 注册
     * @param user
     */
    @Override
    public void save(User user) {
     userRepository.save(user);
    }

    /**
     * 查询用户的uuid
     * @param uuid
     * @return
     */
    @Override
    public User findByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    /**
     * 激活账号
     * @param uuid
     * @param id
     * @return
     */
    @Override
    public User findByUuidAndId(String uuid, Integer id) {
        return userRepository.findByUuidAndId(uuid, id);
    }

    /**
     * 根据id查寻User
     * @param id
     * @return
     */
    @Override
    public User findById(Integer id) {
        return userRepository.findOne(id);
    }
    /**
     * 根据id删除用户
     * @param id
     */
    @Override
    public void deleteById(Integer id) {userRepository.delete(id);
    }
    /**
     * 根据username和email验证用户
     * @param username
     * @param email
     * @return
     */

    @Override
    public User findByEmailAndUsername(String email,String username) {
        return userRepository.findByEmailAndUsername(email,username);
    }
    /**
     * 根据email查询
     * @param email
     * @return
     */
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
