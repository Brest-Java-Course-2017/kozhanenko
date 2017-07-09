package by.eventcat.rest;

import by.eventcat.*;
import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event REST controller
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Transactional
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

    @Value("${incorrect_time_period_data_deleting}")
    private String incorrectTimePeriodInputDataWhenDeleting;

    @Value("${use_index_of_non_existing_event}")
    private String indexOfNonExistingEvent;

    @Value("${use_incorrect_event_index}")
    private String incorrectEventIndex;

    @Value("${no_actions_made}")
    private String noActionsMade;

    @Value("${time_periods_not_added}")
    private String timePeriodsNotAdded;

    @Value("${event_successfully_updated}")
    private String eventSuccessfullyUpdated;

    @Value("${event_not_deleted}")
    private String noActionsMadeWhenDeletingEvent;

    @Value("${event_fully_deleted}")
    private String eventSuccessfullyDeleted;

    @Value("${time_periods_not_deleted}")
    private String timePeriodsNotDeleted;

    @Value("${event_to_delete_in_use}")
    private String deletingEventIsInUse;

    @Value("${incorrect_category_or_interval}")
    private String incorrectCategoryOrInterval;


    //curl -v localhost:8080/rest-app-1.0-SNAPSHOT/events
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

    //curl -v localhost:8080/rest-app-1.0-SNAPSHOT/events/1/1489438800/1489525199
    @RequestMapping(value = "/events/{categoryId}/{beginOfInterval}/{endOfInterval}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Map<String, Object> getEventsOfCertainCategoryOfInterval(
            @PathVariable(value = "categoryId") int categoryId,
            @PathVariable(value = "beginOfInterval") long beginOfInterval,
            @PathVariable(value = "endOfInterval") long endOfInterval
            ) {
        LOGGER.debug("getEventsOfCertainCategoryOfInterval()");
        Map<String, Object> responseObject = new HashMap<>();

        try{
            responseObject.put("data", timePeriodService.getAllTimePeriodsOfCertainCategoryInTimeInterval(
                    new Category(categoryId), beginOfInterval, endOfInterval
            ));
        } catch (ServiceException ex){
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INPUT_DATA)){
                responseObject.put("errorMessage", incorrectCategoryOrInterval);
            } else if (CustomErrorCodes.NO_CALLING_DATA_FOUND.equals(ex.getCustomErrorCode())){
                responseObject.put("errorMessage", dataNotFound);
            } else {
                responseObject.put("errorMessage", programError);
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
        }
        return responseObject;
    }

//    curl -v localhost:8080/rest-app-1.0-SNAPSHOT/event/1
    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
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
    // -v localhost:8080/rest-app-1.0-SNAPSHOT/event
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody//returns new category id
    Map<String, Object> addEvent(@RequestBody Map<String, List<TimePeriod>> requestBody) {
        List<TimePeriod> timePeriods = requestBody.get("timePeriods");
        LOGGER.debug("addEvent: event = {}", timePeriods.get(0).getEvent());
        LOGGER.debug("timePeriods = {}", timePeriods);
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
            if (CustomErrorCodes.INCORRECT_INPUT_DATA.equals(ex.getCustomErrorCode())){
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

    //curl -X PUT -H "Content-Type: application/json" -d '{ "timePeriods": [ { "timePeriodId": 0, "event":
    // { "eventId": 6, "category": { "categoryId": 5, "categoryName": "Музеи" }, "eventName": "\"Камни Беларуси\"",
    // "eventPlace": "\"Краеведческий музей\"" }, "beginning": 1494536460231, "end": 1494709260231 } ] }'
    // localhost:8080/rest-app-1.0-SNAPSHOT/event/4
    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Map<String, Object> updateEvent(@PathVariable(value = "eventId") int eventId,
                                    @RequestBody Map<String, List<TimePeriod>> requestBody) {
        LOGGER.debug("updateEvent id={}", eventId);
        List<TimePeriod> timePeriods = requestBody.get("timePeriods");
        Event updatingEvent = timePeriods.get(0).getEvent();
        Map<String, Object> responseObject = new HashMap<>();

        try{
            eventService.updateEvent(updatingEvent);
        } catch (ServiceException ex){
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INDEX)){
                responseObject.put("errorMessage", incorrectEventIndex);
                return responseObject;
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INPUT_DATA)){
                responseObject.put("errorMessage", incorrectEventInputData);
                return responseObject;
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.NO_ACTIONS_MADE)){
                responseObject.put("errorMessage", noActionsMade);
                return responseObject;
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.ACTIONS_ERROR)){
                responseObject.put("errorMessage", programError);
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
            timePeriodService.deleteTimePeriodsByEventId(updatingEvent);
        } catch (ServiceException ex) {
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INDEX)) {
                responseObject.put("errorMessage", incorrectTimePeriodInputData);
                return responseObject;
            } else if ( ! ex.getCustomErrorCode().equals(CustomErrorCodes.NO_ACTIONS_MADE)) {
                responseObject.put("errorMessage", programError);
                return responseObject;
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
            return responseObject;
        }

        try{
            timePeriodService.addTimePeriodList(timePeriods);
            responseObject.put("successMessage", eventSuccessfullyUpdated);
        } catch (ServiceException ex){
            if (CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_EVENT.equals(ex.getCustomErrorCode())){
                responseObject.put("errorMessage", indexOfNonExistingEvent);
            } else if (CustomErrorCodes.INCORRECT_INPUT_DATA.equals(ex.getCustomErrorCode())){
                responseObject.put("errorMessage", incorrectTimePeriodInputData);
            }   else {
                responseObject.put("errorMessage", programError);
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
        }
        return responseObject;
    }

    //curl -X DELETE localhost:8080/rest-app-1.0-SNAPSHOT/event/1
    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Map<String, Object> deleteEventById(@PathVariable(value = "eventId") int eventId) {
        LOGGER.debug("deleteEventById id={}", eventId);
        Map<String, Object> responseObject = new HashMap<>();

        try{
            timePeriodService.deleteTimePeriodsByEventId(new Event(eventId));
        } catch (ServiceException ex){
            if (CustomErrorCodes.INCORRECT_INDEX.equals(ex.getCustomErrorCode())){
                responseObject.put("errorMessage", incorrectTimePeriodInputDataWhenDeleting);
                return responseObject;
            } else if( ! CustomErrorCodes.NO_ACTIONS_MADE.equals(ex.getCustomErrorCode())){
                responseObject.put("errorMessage", programError);
                return responseObject;
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
            return responseObject;
        }

        try{
            eventService.deleteEvent(eventId);
            responseObject.put("successMessage", eventSuccessfullyDeleted);
        } catch (ServiceException ex){
            if (CustomErrorCodes.INCORRECT_INDEX.equals(ex.getCustomErrorCode())){
                responseObject.put("errorMessage", incorrectEventIndex);
                return responseObject;
            } else if(CustomErrorCodes.DELETING_DATA_IS_IN_USE.equals(ex.getCustomErrorCode())){
                responseObject.put("errorMessage", deletingEventIsInUse);
                return responseObject;
            } else if(CustomErrorCodes.NO_ACTIONS_MADE.equals(ex.getCustomErrorCode())){
                responseObject.put("errorMessage", noActionsMadeWhenDeletingEvent);
                return responseObject;
            } else if (CustomErrorCodes.ACTIONS_ERROR.equals(ex.getCustomErrorCode())){
                responseObject.put("errorMessage", programError);
                return responseObject;
            } else {
                responseObject.put("errorMessage", programError);
                return responseObject;
            }
        } catch (DataAccessException ex){
            return responseObject;
        }
        return responseObject;
    }



}
