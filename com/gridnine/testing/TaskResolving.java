package com.gridnine.testing.com.gridnine.testing;

import java.util.List;

public interface TaskResolving {

    List<Flight> checkDepartureBeforeCurrentTime(List<Flight> flights);

    List<Flight> checkTimeSegments(List<Flight> flights);

    List<Flight> checkTotalTimeOnEarth(List<Flight> flights);

}
