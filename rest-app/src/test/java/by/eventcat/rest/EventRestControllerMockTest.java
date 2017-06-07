package by.eventcat.rest;

import by.eventcat.*;
import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import java.util.*;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Event Rest Controller Mock Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-rest-mock.xml"})
public class EventRestControllerMockTest {

    @Resource
    private EventRestController eventController;

    private MockMvc mockMvc;

    @Autowired
    private EventService mockEventService;

    @Autowired
    private TimePeriodService mockTimePeriodService;

    @Value("${event_successfully_added}")
    private String eventSuccessfullyAdded;

    @Value("${event_successfully_updated}")
    private String eventSuccessfullyUpdated;

    @Value("${event_fully_deleted}")
    private String eventSuccessfullyDeleted;

    private static final Event EVENT = new Event(1, new Category(1),
            "some_name", "some_place");
    private static final Event EVENT1 = new Event(2, new Category(1),
            "some_name1", "some_place1");
    private static final List<Event> EVENTS = new ArrayList<>();
    private static final TimePeriod TIME_PERIOD = new TimePeriod(new Event(1),
            123234234L, 1234566456L);
    private static final TimePeriod TIME_PERIOD_FULL = new TimePeriod(1, new Event(1,
            new Category(1), "name", "place"), 123234234L, 1234566456L);
    private static final List<TimePeriod> TIME_PERIODS = new ArrayList<>();
    private static final List<TimePeriod> TIME_PERIODS_FULL = new ArrayList<>();
    private static final int[] ROWS_AFFECTED = new int[]{1};
    private static final Map<String, List<TimePeriod>> REQUEST_BODY = new HashMap<>();

    static {
        EVENTS.add(EVENT);
        EVENTS.add(EVENT1);
        TIME_PERIODS.add(TIME_PERIOD);
        TIME_PERIODS_FULL.add(TIME_PERIOD_FULL);
        REQUEST_BODY.put("timePeriods", TIME_PERIODS);
    }

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(eventController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
        reset(mockEventService);
        reset(mockTimePeriodService);
    }

    @Test
    public void getEvents() throws Exception{
        expect(mockEventService.getAllEvents()).andReturn(EVENTS);
        replay(mockEventService);

        mockMvc.perform(
                get("/events")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
        verify(mockEventService);
    }

    @Test
    public void getEventsNoDataFound() throws Exception{
        expect(mockEventService.getAllEvents()).andThrow(new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND));
        replay(mockEventService);

        mockMvc.perform(
                get("/events")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
        verify(mockEventService);
    }

    @Test
    public void getEventsOfCertainCategoryOfInterval() throws Exception{
        expect(mockTimePeriodService.getAllTimePeriodsOfCertainCategoryInTimeInterval(anyObject(Category.class),
                eq(2345324523L), eq(23412341234L)))
                .andReturn(TIME_PERIODS);
        replay(mockTimePeriodService);

        Map<String, List<TimePeriod>> responseBody = new HashMap<>();
        responseBody.put("data", TIME_PERIODS);

        mockMvc.perform(
                get("/events/1/2345324523/23412341234")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseBody)));
        verify(mockTimePeriodService);
    }

    @Test
    public void getFullEventById() throws Exception{
        expect(mockTimePeriodService.getTimePeriodListOfCertainEventByEventId(anyObject(Event.class)))
                .andReturn(TIME_PERIODS_FULL);
        replay(mockTimePeriodService);

        Map<String, List<TimePeriod>> responseBody = new HashMap<>();
        responseBody.put("data", TIME_PERIODS_FULL);

        mockMvc.perform(
                get("/event/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseBody)));

        verify(mockTimePeriodService);
    }

    @Test
    public void addEvent() throws Exception{
        expect(mockEventService.addEvent(anyObject(Event.class))).andReturn(1);
        expect(mockTimePeriodService.addTimePeriodList(EasyMock.<List<TimePeriod>> anyObject())).andReturn(ROWS_AFFECTED);
        replay(mockEventService);
        replay(mockTimePeriodService);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("successMessage", eventSuccessfullyAdded);
        responseBody.put("data", 1);

        String timePeriods = new ObjectMapper().writeValueAsString(REQUEST_BODY);

        mockMvc.perform(
                post("/event")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(timePeriods)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseBody)));

        verify(mockTimePeriodService);
        verify(mockEventService);
    }

    @Test
    public void updateEvent() throws Exception{
        expect(mockEventService.updateEvent(anyObject(Event.class))).andReturn(1);
        replay(mockEventService);
        expect(mockTimePeriodService.deleteTimePeriodsByEventId(anyObject(Event.class))).andReturn(1);
        expect(mockTimePeriodService.addTimePeriodList(EasyMock.<List<TimePeriod>> anyObject())).andReturn(ROWS_AFFECTED);
        replay(mockTimePeriodService);

        String timePeriods = new ObjectMapper().writeValueAsString(REQUEST_BODY);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("successMessage", eventSuccessfullyUpdated);

        mockMvc.perform(
                put("/event/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timePeriods)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseBody)));

        verify(mockTimePeriodService);
        verify(mockEventService);
    }

    @Test
    public void deleteEventById() throws Exception{
        expect(mockTimePeriodService.deleteTimePeriodsByEventId(anyObject(Event.class))).andReturn(1);
        expect(mockEventService.deleteEvent(1)).andReturn(1);
        replay(mockEventService);
        replay(mockTimePeriodService);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("successMessage", eventSuccessfullyDeleted);

        mockMvc.perform(
                delete("/event/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseBody)));

        verify(mockTimePeriodService);
        verify(mockEventService);
    }

}
