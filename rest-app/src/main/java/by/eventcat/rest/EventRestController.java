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
    @Autowired
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

    @Value("${use_incorrect_event_index}")
    private String incorrectEventIndex;

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

//    curl -v localhost:8090/event/1
    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET)
    //@ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody
    Map<String, Object> getFullEventById(@PathVariable(value = "eventId") int eventId) {
        LOGGER.debug("getFullEventById id={}", eventId);

        Map<String, Object> responseObject = new HashMap<>();
        try{
            responseObject.put("data", timePeriodService.getTimePeriodListOfCertainEventByEventId(new Event(eventId)));
        } catch (ServiceException ex){
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INDEX)){
                responseObject.put("errorMessage", incorrectEventIndex);
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND)){
                responseObject.put("errorMessage", dataNotFound);
            } else {
                responseObject.put("errorMessage", programError);
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
        }
        return responseObject;
    }

    //curl -H "Content-Type: application/json" -X POST -d '{ "timePeriods": [ { "timePeriodId": 0, "event":
    // { "eventId": -1, "category": { "categoryId": 1, "categoryName": "Театр" },
    // "eventName": "sdfasdf", "eventPlace": "asdfasdf" }, "beginning": 1494277260584, "end": 1494709260584 } ] }'
    // -v localhost:8090/event
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody//returns new category id
    Map<String, Object> addEvent(@RequestBody Map<String, List<TimePeriod>> requestBody) {
        List<TimePeriod> timePeriods = requestBody.get("timePeriods");
        LOGGER.debug("addEvent: event = {}", timePeriods.get(0).getEvent());
        Map<String, Object> responseObject = new HashMap<>();
        Event newEvent = timePeriods.get(0).getEvent();
        int newEventId;

        try{
            newEventId = eventService.addEvent(newEvent);
            responseObject.put("data", newEventId);
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
            for (TimePeriod timePeriod: timePeriods){
                timePeriod.getEvent().setEventId(newEventId);
            }
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
