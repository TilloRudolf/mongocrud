package com.mongotest.service;

import com.mongotest.dao.UserDao;
import com.mongotest.model.User;
import com.mongotest.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Value("${spring.application.key}")
    private String key;

    @Override
    public List<User> getUsers() {
        try {
            List<User> userList = new ArrayList<>();
            for (UserEntity userEntity : userDao.findAll()) {
                Cipher cipher = generateCipher(false);
                User user = new User();
                user.setId(userEntity.getId());
                user.setPassword(new String(cipher.doFinal(userEntity.getPassword())));
                user.setLogin(userEntity.getLogin());
                userList.add(user);
            }
            return userList;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User getUser(String id) {
        try {
            Cipher cipher = generateCipher(false);
            UserEntity userEntity = userDao.findById(id);
            User user = new User();
            user.setId(userEntity.getId());
            user.setLogin(userEntity.getLogin());
            user.setPassword(new String(cipher.doFinal(userEntity.getPassword())));
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public String update(User user) {
        try {
            Cipher cipher = generateCipher(true);
            byte[] bytes = cipher.doFinal(user.getPassword().getBytes());
            UserEntity userEntity = new UserEntity();
            userEntity.setPassword(bytes);
            userEntity.setLogin(user.getLogin());
            userEntity.setId(user.getId());
            UserEntity savedUser = userDao.save(userEntity);
            return savedUser.getId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public String remove(String id) {
        if (userDao.findById(id) != null) {
            userDao.delete(id);
            return "success";
        } else return "user not found, create user to delete it, you, dump cockroach";
    }

    private Cipher generateCipher(boolean mode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        if(mode) {
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        }else{
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
        }
        return cipher;
    }
}
