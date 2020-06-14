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

/**
 * Updates job information based on selected job tab.
 */
function updateJob(index) {
    var tabs = document.getElementsByClassName("jobTab")
    for(var i = 0; i < tabs.length; i++) {
        tabs[i].style.color = "gray"
        tabs[i].style.borderColor = "gray"
    }

    switch(index) {
        case 0:
            document.getElementById("job-title").innerHTML = "Software Engineer Intern"
            document.getElementById("job-company").innerHTML = " @ Goldman Sachs"
            document.getElementById("job-duration").innerHTML = "July - Aug 2020"
            document.getElementById("job-description").innerHTML = "Yet to start."
            document.getElementById("job0").style.color = "black"
            document.getElementById("job0").style.borderColor = "black"
            break;
        case 1:
            document.getElementById("job-title").innerHTML = "Teaching Assistant"
            document.getElementById("job-company").innerHTML = " @ NUS"
            document.getElementById("job-duration").innerHTML = "Jan - May 2020"
            document.getElementById("job-description").innerHTML = "In charge of tutoring and grading 40 students for Data Structure and Algorithms (CS2040S and CS2040C) courses taught in Java and C++ respectively."
            document.getElementById("job1").style.borderColor = "black"
            document.getElementById("job1").style.color = "black"
            break;
        case 2:
            document.getElementById("job-title").innerHTML = "Software Engineer Intern"
            document.getElementById("job-company").innerHTML = " @ Sea Group"
            document.getElementById("job-duration").innerHTML = "May - Aug 2019"
            document.getElementById("job-description").innerHTML = "Worked as a Web Front End Software Engineer under the Order and Platform Team in Shopee. Implemented and optimised shopping cart, checkout and invoice generation features for Shopee desktop and mobile website. Led and built an internal course management website for Sea and Shopee employee."
            document.getElementById("job2").style.borderColor = "black"
            document.getElementById("job2").style.color = "black"
            break;
        case 3:
            document.getElementById("job-title").innerHTML = "Research Intern"
            document.getElementById("job-company").innerHTML = " @ Grab-NUS AI Lab"
            document.getElementById("job-duration").innerHTML = "Oct 2018 - April 2019"
            document.getElementById("job-description").innerHTML = "Optimised processing of massive amount of taxi data from Grab using parallel computation with Apache Flink and Java to conduct trajectory similarity search. Designed the Graphical User Interface to visualise processed spatio-temporal data such as taxi trajectories and snapshots with Swift."
            document.getElementById("job3").style.color = "black"
            document.getElementById("job3").style.borderColor = "black"
            break;
        case 4:
            document.getElementById("job-title").innerHTML = "Security Intern"
            document.getElementById("job-company").innerHTML = " @ Intel"
            document.getElementById("job-duration").innerHTML = "May - July 2018"
            document.getElementById("job-description").innerHTML = "Led a research team which analysed stack-based buffer overflow vulnerabilities and protections in Linux systems. Designed test exploitations and attacks using Python, C and Assembly and validate real time operating systems."
            document.getElementById("job4").style.color = "black"
            document.getElementById("job4").style.borderColor = "black"
            break;
        
        default:

    }
}

/**
 * Fetches comments from the servers and adds them to the DOM.
 */
async function fetchComments() {
    const response = await fetch('/data');
    const comments = await response.json();

    console.log(comments);

    const commentsContainer = document.getElementById('comments-container');
    commentsContainer.innerHTML = '';

    comments.forEach( comment => {
        const { username, text, timestamp } = comment;
        commentsContainer.appendChild(
            createListElement(username, text, timestamp)
        );
    });
}

/** Creates an <li> element containing text. */
function createListElement(username, comment, timestamp) {
  const liElement = document.createElement('li');

  const usernameElement = document.createElement('h5');
  const commentElement = document.createElement('p');
  const dateElement = document.createElement('h6');
  usernameElement.innerText = username;
  commentElement.innerText = comment;
  dateElement.innerText = convertTimestamp(timestamp);

  liElement.appendChild(usernameElement);
  liElement.appendChild(commentElement);
  liElement.appendChild(dateElement);

  return liElement;
}

/**
 * Convert timestamp to date string
 * Reference: https://stackoverflow.com/questions/24170933/convert-unix-timestamp-to-date-time-javascript
*/
function convertTimestamp(timestamp) {
    var d = new Date(timestamp),
        yyyy = d.getFullYear(),
        mm = ('0' + (d.getMonth() + 1)).slice(-2),  // Months are zero based. Add leading 0.
        dd = ('0' + d.getDate()).slice(-2),         // Add leading 0.
        hh = d.getHours(),
        h = hh,
        min = ('0' + d.getMinutes()).slice(-2),     // Add leading 0.
        ampm = 'AM',
        time;

    if (hh > 12) {
        h = hh - 12;
        ampm = 'PM';
    } else if (hh === 12) {
        h = 12;
        ampm = 'PM';
    } else if (hh == 0) {
        h = 12;
    }

    // ie: 2014-03-24, 3:00 PM
    time = yyyy + '-' + mm + '-' + dd + ', ' + h + ':' + min + ' ' + ampm;
    return time;
}
