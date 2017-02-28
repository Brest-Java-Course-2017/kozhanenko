package com.epam.test.client.rest;

import com.epam.test.client.api.UsersConsumer;
import com.epam.test.client.exception.ServerDataAccessException;
import com.epam.test.dao.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Users Consumer REST implementation.
 */
public class UsersConsumerRest implements UsersConsumer {

    private String hostUrl;
    private String urlUsers;
    private String urlUser;

    RestTemplate restTemplate;


    public UsersConsumerRest(String hostUrl, String urlUsers, String urlUser) {
        this.hostUrl = hostUrl;
        this.urlUsers = urlUsers;
        this.urlUser = urlUser;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getAllUsers() throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.getForEntity(hostUrl + "/" + urlUsers, List.class);
        List<User> users = (List<User>) responseEntity.getBody();
        return users;
    }

    @Override
    public User getUserById(Integer userId) throws ServerDataAccessException {
        return null;
    }

    @Override
    public User getUserByLogin(String login) throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.getForEntity(hostUrl + "/" + urlUser + "/" + login, User.class);
        Object user = responseEntity.getBody();
        return (User) user;
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"login":"xyz","password":"xyz"}' -v localhost:8080/user
    @Override
    public Integer addUser(User user) throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.postForEntity(hostUrl + "/" + urlUser, user, Integer.class);
        Object userId = responseEntity.getBody();
        return (Integer) userId;
    }

    //curl -X PUT -v localhost:8088/user/2/l1/p1/d1
    @Override
    public int updateUser(User user) throws ServerDataAccessException {
        return 0;
    }

    //curl -X DELETE -v localhost:8080/user/userId     -v???
    @Override
    public int deleteUser(Integer userId) throws ServerDataAccessException {
        return 0;
    }
}