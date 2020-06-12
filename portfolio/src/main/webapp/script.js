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
 * Fetches hello quote from the server and adds it to the DOM.
 */
async function getHelloQuote() {
    const response = await fetch('/data');
    const quote = await response.text();
    document.getElementById("hello-container").innerHTML = quote;
}
