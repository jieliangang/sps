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

    Collection<TimeRange> availableTimes = new ArrayList<>();
    List<Event> eventList = new ArrayList<>(events);
    eventList.sort(Comparator.comparingInt(e -> e.getWhen().start()));

    // Locate gaps between events joined by any of the request attendees
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
        int timeGap = eventStartTime - prevEndTime;
        // Check if gap between events can hold the meeting duration
        if(timeGap >= request.getDuration()) {
          availableTimes.add(TimeRange.fromStartDuration(prevEndTime, timeGap));
        }
        prevEndTime = eventEndTime;
      }
    }

    // Check for the gap after the last event
    int lastGap = TimeRange.END_OF_DAY + 1 - prevEndTime;
    if(lastGap >= request.getDuration()) {
      availableTimes.add(TimeRange.fromStartDuration(prevEndTime, lastGap));
    }

    return availableTimes;
  }

  /**
   * Check if {@code event} and {@code request} share any same attendee.
   * Time complexity of the algorithm is O(n), in which n is the size of attendees in {@code request}
   * Returns true if the attendees overlap.
   */
  private boolean attendeesOverlap(Event event, MeetingRequest request) {
    for(String attendee: request.getAttendees()) {
      if(event.getAttendees().contains(attendee)) {
        return true;
      }
    }
    return false;
  }
}
