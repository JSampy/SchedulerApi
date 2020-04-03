package ru.jsamkt.jocdoc.schedulerapi.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SchedulerRestControllerTest {

    private static final String ROOT_URL = "/scheduler/";

    @RunWith(SpringRunner.class)
    @SpringBootTest
    @AutoConfigureMockMvc
    public static class GetTests extends BaseInitTest {

        @Autowired
        private ObjectMapper mapper;

        @Autowired
        private MockMvc mvc;

        @Test
        public void testGetOne() throws Exception {

            mvc.perform(get(ROOT_URL + "/{id}",
                    SchedulerKeyConverter.getSchedulerKeyString(
                            clinicID,
                            doctorID,
                            month
                    )).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            "{" +
                                    "  'workDays' : {" +
                                    "    '2020-02-04' : {" +
                                    "      'from' : '13:00:00'," +
                                    "      'to' : '19:00:00'" +
                                    "    }," +
                                    "    '2020-02-03' : {" +
                                    "      'from' : '09:00:00'," +
                                    "      'to' : '21:00:00'" +
                                    "    }," +
                                    "    '2020-02-02' : {" +
                                    "      'from' : '08:00:00'," +
                                    "      'to' : '20:00:00'" +
                                    "    }," +
                                    "    '2020-02-01' : {" +
                                    "      'from' : '08:00:00'," +
                                    "      'to' : '20:00:00'" +
                                    "    }" +
                                    "  }," +
                                    "  'lunchTime' : {" +
                                    "    'from' : '13:00:00'," +
                                    "    'to' : '14:00:00'" +
                                    "  }," +
                                    "  '_links' : {" +
                                    "    'self' : {" +
                                    "      'href' : 'http://localhost/scheduler/123_321_2020-02-01'" +
                                    "    }," +
                                    "    'scheduler' : {" +
                                    "      'href' : 'http://localhost/scheduler/123_321_2020-02-01'" +
                                    "    }" +
                                    "  }" +
                                    "}"));
        }

        @Test
        public void testGetAll() throws Exception {
            mvc.perform(get(ROOT_URL).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("" +
                            "{" +
                            "  '_embedded' : {" +
                            "    'scheduler' : [ {" +
                            "      'workDays' : {" +
                            "        '2020-02-04' : {" +
                            "          'from' : '13:00:00'," +
                            "          'to' : '19:00:00'" +
                            "        }," +
                            "        '2020-02-03' : {" +
                            "          'from' : '09:00:00'," +
                            "          'to' : '21:00:00'" +
                            "        }," +
                            "        '2020-02-02' : {" +
                            "          'from' : '08:00:00'," +
                            "          'to' : '20:00:00'" +
                            "        }," +
                            "        '2020-02-01' : {" +
                            "          'from' : '08:00:00'," +
                            "          'to' : '20:00:00'" +
                            "        }" +
                            "      }," +
                            "      'lunchTime' : {" +
                            "        'from' : '13:00:00'," +
                            "        'to' : '14:00:00'" +
                            "      }," +
                            "      '_links' : {" +
                            "        'self' : {" +
                            "          'href' : 'http://localhost/scheduler/123_321_2020-02-01'" +
                            "        }," +
                            "        'scheduler' : {" +
                            "          'href' : 'http://localhost/scheduler/123_321_2020-02-01'" +
                            "        }" +
                            "      }" +
                            "    } ]" +
                            "  }," +
                            "  '_links' : {" +
                            "    'self' : {" +
                            "      'href' : 'http://localhost/scheduler{?page,size,sort}'," +
                            "      'templated' : true" +
                            "    }," +
                            "    'profile' : {" +
                            "      'href' : 'http://localhost/profile/scheduler'" +
                            "    }" +
                            "  }," +
                            "  'page' : {" +
                            "    'size' : 20," +
                            "    'totalElements' : 1," +
                            "    'totalPages' : 1," +
                            "    'number' : 0" +
                            "  }" +
                            "}"));
        }


    }

    @RunWith(SpringRunner.class)
    @SpringBootTest
    @AutoConfigureMockMvc
    public static class ModifyTests extends BaseInitTest {

        @Autowired
        private ObjectMapper mapper;

        @Autowired
        private MockMvc mvc;


        @Test
        public void testCreateOne() throws Exception {

            Long newClinicId = 111L;
            Long newDoctorId = 222L;
            LocalDate newMonth = LocalDate.parse("2020-02-01");

            Scheduler scheduler = Scheduler.builder()
                    .key(Scheduler.SchedulerKey.builder()
                            .clinicId(newClinicId)
                            .doctorId(newDoctorId)
                            .month(newMonth)
                            .build())
                    .workDays(new HashMap<LocalDate, Scheduler.TimeRange>() {{
                        put(newMonth, Scheduler.TimeRange.builder().from(LocalTime.parse("09:00")).to(LocalTime.parse("18:00")).build());
                    }})
                    .lunchTime(Scheduler.TimeRange.builder().from(LocalTime.parse("12:00")).to(LocalTime.parse("13:00")).build())
                    .build();

            String body = mapper.writeValueAsString(scheduler);

            mvc.perform(post(ROOT_URL).content(body))
                    .andExpect(status().isCreated());

            mvc.perform(get(ROOT_URL + "{id}",
                    SchedulerKeyConverter.getSchedulerKeyString(
                            newClinicId,
                            newDoctorId,
                            newMonth
                    )
            ).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("" +
                            "{" +
                            "  'workDays' : {" +
                            "    '2020-02-01' : {" +
                            "      'from' : '09:00:00'," +
                            "      'to' : '18:00:00'" +
                            "    }" +
                            "  }," +
                            "  'lunchTime' : {" +
                            "    'from' : '12:00:00'," +
                            "    'to' : '13:00:00'" +
                            "  }," +
                            "  '_links' : {" +
                            "    'self' : {" +
                            "      'href' : 'http://localhost/scheduler/111_222_2020-02-01'" +
                            "    }," +
                            "    'scheduler' : {" +
                            "      'href' : 'http://localhost/scheduler/111_222_2020-02-01'" +
                            "    }" +
                            "  }" +
                            "}"));
        }

        @Test
        public void testUpdate() throws Exception {

            Scheduler scheduler = Scheduler.builder()
                    .workDays(new HashMap<LocalDate, Scheduler.TimeRange>() {{
                        put(month, Scheduler.TimeRange.builder().from(LocalTime.parse("10:00")).to(LocalTime.parse("18:30")).build());
                    }})
                    .lunchTime(Scheduler.TimeRange.builder().from(LocalTime.parse("13:00")).to(LocalTime.parse("14:00")).build())
                    .build();

            String body = mapper.writeValueAsString(scheduler);

            mvc.perform(put(ROOT_URL + SchedulerKeyConverter.getSchedulerKeyString(clinicID, doctorID, month))
                    .accept(MediaType.APPLICATION_JSON)
                    .content(body)
            )
                    .andExpect(status().isOk())
                    .andExpect(content().json("" +
                            "{" +
                            "  'workDays' : {" +
                            "    '2020-02-01' : {" +
                            "      'from' : '10:00:00'," +
                            "      'to' : '18:30:00'" +
                            "    }" +
                            "  }," +
                            "  'lunchTime' : {" +
                            "    'from' : '13:00:00'," +
                            "    'to' : '14:00:00'" +
                            "  }," +
                            "  '_links' : {" +
                            "    'self' : {" +
                            "      'href' : 'http://localhost/scheduler/123_321_2020-02-01'" +
                            "    }," +
                            "    'scheduler' : {" +
                            "      'href' : 'http://localhost/scheduler/123_321_2020-02-01'" +
                            "    }" +
                            "  }" +
                            "}"));


            mvc.perform(get(ROOT_URL + SchedulerKeyConverter.getSchedulerKeyString(clinicID, doctorID, month)).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json("" +
                            "{" +
                            "  'workDays' : {" +
                            "    '2020-02-01' : {" +
                            "      'from' : '10:00:00'," +
                            "      'to' : '18:30:00'" +
                            "    }" +
                            "  }," +
                            "  'lunchTime' : {" +
                            "    'from' : '13:00:00'," +
                            "    'to' : '14:00:00'" +
                            "  }," +
                            "  '_links' : {" +
                            "    'self' : {" +
                            "      'href' : 'http://localhost/scheduler/123_321_2020-02-01'" +
                            "    }," +
                            "    'scheduler' : {" +
                            "      'href' : 'http://localhost/scheduler/123_321_2020-02-01'" +
                            "    }" +
                            "  }" +
                            "}"));

        }

        @Test
        public void testDelete() throws Exception {
            mvc.perform(get(ROOT_URL + SchedulerKeyConverter.getSchedulerKeyString(clinicID, doctorID, month)).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            mvc.perform(delete(ROOT_URL + SchedulerKeyConverter.getSchedulerKeyString(clinicID, doctorID, month)))
                    .andExpect(status().isNoContent());

            mvc.perform(get(ROOT_URL + SchedulerKeyConverter.getSchedulerKeyString(clinicID, doctorID, month)).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

        }

    }


    @RunWith(SpringRunner.class)
    @SpringBootTest
    @AutoConfigureMockMvc
    public static class ValidationTests extends BaseInitTest {

        @Autowired
        private ObjectMapper mapper;

        @Autowired
        private MockMvc mvc;

        @Test
        public void testInvalidDataSave() throws Exception {
            Long newClinicId = 111L;
            Long newDoctorId = 222L;
            LocalDate newMonth = LocalDate.parse("2020-02-01");

            Scheduler scheduler = Scheduler.builder()
                    .key(Scheduler.SchedulerKey.builder()
                            .clinicId(newClinicId)
                            .doctorId(newDoctorId)
                            .month(newMonth)
                            .build())
                    .workDays(new HashMap<LocalDate, Scheduler.TimeRange>() {{
                        put(newMonth.withMonth(3), Scheduler.TimeRange.builder().from(LocalTime.parse("09:00")).to(LocalTime.parse("18:00")).build());
                    }})
                    .lunchTime(Scheduler.TimeRange.builder().from(LocalTime.parse("12:00")).to(LocalTime.parse("13:00")).build())
                    .build();

            String body = mapper.writeValueAsString(scheduler);

            mvc.perform(post(ROOT_URL).content(body))
                    .andExpect(status().isBadRequest());

        }

    }

}