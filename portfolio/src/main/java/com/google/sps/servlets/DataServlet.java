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

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private List<Comment> comments = new ArrayList<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertToJsonUsingGson(comments);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Comment comment = getComment(request);
    if(isValidComment(comment)) {
        comments.add(comment);

        // Store comment entity in Datastore
        Entity commentEntity = new Entity("Comment");
        commentEntity.setProperty("username", comment.getUsername());
        commentEntity.setProperty("text", comment.getText());
        commentEntity.setProperty("timestamp", comment.getTimestamp());

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);
    }
    response.sendRedirect("/#comments");
  }

  private String convertToJsonUsingGson(List<Comment> comments) {
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    return json;
  }

  private Comment getComment(HttpServletRequest request) {
      String usernameString = request.getParameter("username");
      String commentString = request.getParameter("comment");

      if(usernameString.trim().length() <= 0) {
          usernameString = "Anonymous";
      }

      Date date = new Date();
      long time = date.getTime();

      Comment comment = new Comment(usernameString, commentString, time);
      return comment;
  }

  private Boolean isValidComment(Comment comment) {
      // Check if username and text is not empty or contains only whitespace
      return comment.getText().trim().length() > 0
              && comment.getUsername().trim().length() > 0;
  } 
}



