package by.eventcat.rest;

import by.eventcat.EventService;
import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Event REST controller
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EventRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private EventService eventService;

    @Value("${program_error}")
    private String programError;

    @Value("${data_not_found}")
    private String dataNotFound;

    //curl -v localhost:8090/events
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getEvents() {
        LOGGER.debug("getEvents()");
        Map<String, Object> responseObject = new HashMap<>();

        try{
            responseObject.put("data", eventService.getAllEvents());
        } catch (ServiceException ex){
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND)){
                responseObject.put("errorMessage", dataNotFound);
            } else {
                responseObject.put("errorMessage", programError);
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
        }
        return responseObject;
    }



}
