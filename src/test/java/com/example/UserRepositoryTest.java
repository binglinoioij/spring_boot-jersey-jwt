package com.example;

import com.example.entity.mongo.User;
import com.example.repository.SequenceBuilder;
import com.example.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

/**
 * Created by binglin on 2016/10/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Inject
    private UserRepository userRepository;

    @Test
    public void testAddUser() {
        User user = new User(SequenceBuilder.builder("user"));
        user.setUserName("binglin");
        user.setPassword("123456");
        user.setRoles(new String[]{"user"});
        userRepository.save(user);
    }

    @Test
    public void TestFindUser() {
        User user = userRepository.findOne(20000L);
        user.setVersion(1);
        userRepository.save(user);
    }
}
