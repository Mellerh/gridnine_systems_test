package com.gridnine.testing.com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class TaskResolvingImpl implements TaskResolving {
    @Override
    public List<Flight> checkDepartureBeforeCurrentTime(List<Flight> flights) {

//        List<Flight> finalListOfFlights = new ArrayList<>();
//
//        for (int i = 0; i < flights.size(); i++) {
//            List<Segment> segments = flights.get(i).getSegments();
//
//            boolean isTimeBefore = true;
//
//            for (int k = 0; k < segments.size(); k++) {
//                if (segments.get(k).getDepartureDate().isAfter(LocalDateTime.now())) {
//                    isTimeBefore = false;
//                    break;
//                }
//            }
//
//            if (isTimeBefore) {
//                finalListOfFlights.add(flights.get(i));
//            }
//        }
//        return finalListOfFlights;

        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());


    }

    @Override
    public List<Flight> checkTimeSegments(List<Flight> flights) {

//        List<Flight> finalListOfFlights = new ArrayList<>();
//
//        for (int i = 0; i < flights.size(); i++) {
//            List<Segment> segments = flights.get(i).getSegments();
//
//            boolean isTimeBefore = true;
//
//            for (int k = 0; k < segments.size(); k++) {
//                LocalDateTime dep = segments.get(k).getDepartureDate();
//                LocalDateTime arr = segments.get(k).getArrivalDate();
//
//                if (dep.isAfter(arr)) {
//                    isTimeBefore = false;
//                    break;
//                }
//            }
//
//            if (isTimeBefore) {
//                finalListOfFlights.add(flights.get(i));
//            }
//        }

//        return finalListOfFlights;

        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> checkTotalTimeOnEarth(List<Flight> flights) {

        List<Flight> finalListOfFlights = new ArrayList<>();

        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            long totalTimeOnEarth = 0;

            if (segments.size() < 2) {
                finalListOfFlights.add(flight);
                continue;
            }


            for (int i = 0; i < segments.size() - 1; i++) {
                Duration duration = Duration.between(
                        segments.get(i).getArrivalDate(),
                        segments.get(i + 1).getDepartureDate()
                );

                totalTimeOnEarth += duration.toMinutes();
            }

            if (totalTimeOnEarth <= 120) {
                finalListOfFlights.add(flight);
            }

        }

        return finalListOfFlights;
    }
}


