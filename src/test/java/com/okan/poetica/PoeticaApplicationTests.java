package com.okan.poetica;

import com.okan.poetica.model.User;
import com.okan.poetica.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoeticaApplicationTests {

    @Autowired
    private UserService userService;

    @Before
    public void initDb() {

    }

    @Test
    public void testCreateUser(){
        User newUser1 = new User("testUser", "123456","user@email.com","tester");
        userService.createUser(newUser1);
    }

   /* @Test
    public void testUser() {
        User user = userService.findOne("testUser@mail.com");
        assertNotNull(user);
        User admin = userService.findOne("testAdmin@mail.com");
        assertEquals(admin.getEmail(), "testAdmin@mail.com");
    }*/
}

