package com.gridnine.testing.com.gridnine.testing;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        TaskResolving taskResolving = new TaskResolvingImpl();

        List<Flight> checkDeparture = taskResolving.checkDepartureBeforeCurrentTime(FlightBuilder.createFlights());
//        System.out.println(checkDeparture);

        List<Flight> checkTime = taskResolving.checkTimeSegments(FlightBuilder.createFlights());
//        System.out.println(checkTime);

        List<Flight> checkTotalTime = taskResolving.checkTotalTimeOnEarth(FlightBuilder.createFlights());
        System.out.println(checkTotalTime);
    }

}
