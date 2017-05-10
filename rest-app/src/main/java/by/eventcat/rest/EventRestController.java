package by.eventcat.rest;

import by.eventcat.Event;
import by.eventcat.EventService;
import by.eventcat.TimePeriod;
import by.eventcat.TimePeriodService;
import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    private TimePeriodService timePeriodService;

    @Value("${program_error}")
    private String programError;

    @Value("${data_not_found}")
    private String dataNotFound;

    @Value("${incorrect_event_data}")
    private String incorrectEventInputData;

    @Value("${use_index_of_non_existing_category}")
    private String indexOfNonExistingCategory;

    @Value("${event_successfully_added}")
    private String eventSuccessfullyAdded;

    @Value("${incorrect_time_period_data}")
    private String incorrectTimePeriodInputData;

    @Value("${use_index_of_non_existing_event}")
    private String indexOfNonExistingEvent;

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

    //curl -H "Content-Type: application/json" -X POST -d '{ timePeriod[] }' -v localhost:8090/event
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody//returns new category id
    Map<String, Object> addEvent(@RequestBody List<TimePeriod> timePeriods) {
        LOGGER.debug("addEvent: event = {}", timePeriods.get(0).getEvent());
        Map<String, Object> responseObject = new HashMap<>();
        Event newEvent = timePeriods.get(0).getEvent();

        try{
            responseObject.put("data", eventService.addEvent(newEvent));
        } catch (ServiceException ex){
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INPUT_DATA)){
                responseObject.put("errorMessage", incorrectEventInputData);
                return responseObject;
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_CATEGORY)){
                responseObject.put("errorMessage", indexOfNonExistingCategory);
                return responseObject;
            } else {
                responseObject.put("errorMessage", programError);
                return responseObject;
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
            return responseObject;
        }

        try{
            timePeriodService.addTimePeriodList(timePeriods);
            responseObject.put("successMessage", eventSuccessfullyAdded);
        } catch (ServiceException ex){
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INPUT_DATA)){
                responseObject.put("errorMessage", incorrectTimePeriodInputData);
                return responseObject;
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_EVENT)){
                responseObject.put("errorMessage", indexOfNonExistingEvent);
                return responseObject;
            } else {
                responseObject.put("errorMessage", programError);
                return responseObject;
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
            return responseObject;
        }
        return responseObject;
    }



}
