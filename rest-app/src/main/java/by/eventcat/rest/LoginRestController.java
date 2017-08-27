package by.eventcat.rest;

import by.eventcat.LoginData;
import by.eventcat.User;
import by.eventcat.UserService;
import by.eventcat.rest.security.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * LoginRestController
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Transactional
public class LoginRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> authenticate(@RequestBody LoginData requestBody) {
        LOGGER.debug("authenticate()");
        Map<String, Object> responseObject = new HashMap<>();
        User user = userService.authenticateUser(requestBody.getUsername(), requestBody.getPassword());
        if (user != null){
            responseObject.put("token", JwtUtil.generateToken(user));
        } else {
            responseObject.put("token", null);
        }
        return responseObject;
    }

}
