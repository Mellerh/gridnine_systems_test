package com.gridnine.testing.com.gridnine.testing;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

@DisplayName("Класс с тестами для проверки работы методов фильтрации")
public class TaskResolvingTest {

    private static TaskResolving taskResolving;
    private LocalDateTime now;

    @BeforeAll
    public static void initTaskResolving() {
        taskResolving = new TaskResolvingImpl();
    }

    @BeforeEach
    public void initDateTimeNow() {
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Проверяем корректность работы метода по фильтрации полётов, в которых время вылета ДО текущего времени")
    public void shouldReturnCorrectFlightsWhereDepartureBeforeCurrentTime() {

        List<Segment> correctFlightSegment = List.of(new Segment(now.plusHours(3), now.plusHours(5)));
        List<Segment> incorrectFlightSegment = List.of(new Segment(now.minusHours(3), now.plusHours(6)));

        List<Flight> flights = List.of(
                new Flight(correctFlightSegment),
                new Flight(incorrectFlightSegment)
        );


        List<Flight> flightsAfterCheck = taskResolving.checkDepartureBeforeCurrentTime(flights);

        Assertions.assertEquals(1, flightsAfterCheck.size(), "Ошибка в checkDepartureBeforeCurrentTime");
        Assertions.assertEquals(correctFlightSegment, flights.get(0).getSegments(),
                "Ошибка в checkDepartureBeforeCurrentTime");

    }

    @Test
    @DisplayName("Проверяем корректность работы метода по фильтрации полётов, в которых время вылета " +
            "всегда ДО времени прилёта")
    public void shouldReturnCorrectFlightsWhereDepartureAlwaysAfterArrival() {
        List<Segment> correctFlightSegment = List.of(new Segment(now.plusHours(1), now.plusHours(5)));
        List<Segment> incorrectFlightSegment = List.of(new Segment(now.plusHours(3), now.plusHours(1)));

        List<Flight> flights = List.of(
                new Flight(correctFlightSegment),
                new Flight(incorrectFlightSegment)
        );

        List<Flight> flightsAfterCheck = taskResolving.checkTimeSegments(flights);

        Assertions.assertEquals(1, flightsAfterCheck.size(), "Ошибка в checkTimeSegments");
        Assertions.assertEquals(correctFlightSegment, flights.get(0).getSegments(),
                "Ошибка в checkTimeSegments");
    }

    @Test
    @DisplayName("Проверяем корректность работы метода по фильтрации полётов, в которых общее время на земеле менее 121м.")
    public void shouldReturnCorrectFlightsWhereTotalTimeOnEarthLessThen121Min() {
        List<Segment> correctFlightSegment = List.of(
                new Segment(now.plusHours(1), now.plusHours(2)),
                new Segment(now.plusHours(3), now.plusHours(4))
        );

        List<Segment> incorrectFlightSegment = List.of(
                new Segment(now.plusHours(1), now.plusHours(2)),
                new Segment(now.plusHours(5), now.plusHours(10))
        );

        List<Flight> flights = List.of(
                new Flight(correctFlightSegment),
                new Flight(incorrectFlightSegment)
        );

        List<Flight> flightsAfterCheck = taskResolving.checkTotalTimeOnEarth(flights);

        Assertions.assertEquals(1, flightsAfterCheck.size(), "ошибка в checkTotalTimeOnEarth");
        Assertions.assertEquals(correctFlightSegment, flights.get(0).getSegments(),
                "Ошибка в checkTotalTimeOnEarth");
    }

}
