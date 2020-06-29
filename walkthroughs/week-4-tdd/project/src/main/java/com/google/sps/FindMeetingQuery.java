// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.*;

public final class FindMeetingQuery {
  /**
   * Returns the times when {@code request} could happen on a day containing {@code events}
   * Time complexity of the algorithm is O(nlogn + mn), in which
   * n is the number of {@code events}, and m is the number of attendees in {@code request}
   */
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    if(isDurationInvalid(request)) {
      return Collections.emptyList();
    } else if (hasNoAttendees(request)) {
      return Collections.singletonList(TimeRange.WHOLE_DAY);
    }

    // Sort events based on event start time
    List<Event> eventList = new ArrayList<>(events);
    eventList.sort(Comparator.comparingInt(e -> e.getWhen().start()));

    // Locate gaps between events joined by any of the request attendees
    // and add valid timings to availableTimes
    Collection<TimeRange> availableTimes = new ArrayList<>();
    locateGaps(eventList, request, availableTimes);
    return availableTimes;
  }

  private boolean isDurationInvalid(MeetingRequest request) {
    return request.getDuration() > TimeRange.WHOLE_DAY.duration();
  }

  private boolean hasNoAttendees(MeetingRequest request) {
    return request.getAttendees().isEmpty();
  }

  private boolean attendeesOverlap(Event event, MeetingRequest request) {
    return !Collections.disjoint(event.getAttendees(), request.getAttendees());
  }

  /**
   * Check if gap can hold the duration of the request.
   * If valid, then add the gap timing to {@code availableTimes}
   */
  private void checkGap(int startTime, int prevEndTime, long duration, Collection<TimeRange> availableTimes) {
    int gap = startTime - prevEndTime;
    if(gap >= duration) {
      availableTimes.add(TimeRange.fromStartDuration(prevEndTime, gap));
    }
  }

  /**
   * Locate gaps between events joined by any of the request attendees
   * and add available gap timings to {@code availableTimes}
   * {@code eventList} should be sorted by start time.
   */
  private void locateGaps(List<Event> eventList, MeetingRequest request, Collection<TimeRange> availableTimes) {
    int prevEndTime = TimeRange.START_OF_DAY;
    for(Event e: eventList) {
      // Ignore if event not attended by any of the request attendees
      if(!attendeesOverlap(e, request)) {
        continue;
      }
      int eventStartTime = e.getWhen().start();
      int eventEndTime = e.getWhen().end();
      // Check if event overlaps with preceding events
      if(eventStartTime <= prevEndTime) {
        prevEndTime = Math.max(eventEndTime, prevEndTime);
      } else {
        // Check if gap between events can hold the meeting duration
        checkGap(eventStartTime, prevEndTime, request.getDuration(), availableTimes);
        prevEndTime = eventEndTime;
      }
    }
    // Check for the gap after the last event
    checkGap(TimeRange.END_OF_DAY + 1, prevEndTime, request.getDuration(), availableTimes);
  }
}
