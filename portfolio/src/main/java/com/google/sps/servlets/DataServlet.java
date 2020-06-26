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

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that handles fetching and posting of comments */
@WebServlet("/comments")
public class DataServlet extends HttpServlet {

  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  ImagesService imagesService = ImagesServiceFactory.getImagesService();

  List<String> validFileTypes = Arrays.asList("image/jpeg", "image/png");

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("timestamp", Query.SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    List<Comment> comments = new ArrayList<>();
    for(Entity entity: results.asIterable()) {
        long id = entity.getKey().getId();
        String username = (String) entity.getProperty("username");
        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");
        String imageUrl = (String) entity.getProperty("imageUrl");

        Comment comment = new Comment(id, username, text, timestamp, imageUrl);
        comments.add(comment);
    }

    String json = convertToJsonUsingGson(comments);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Comment comment = getComment(request);
    if(comment.isValid()) {
        // Store comment entity in Datastore
        Entity commentEntity = new Entity("Comment");
        commentEntity.setProperty("username", comment.getUsername());
        commentEntity.setProperty("text", comment.getText());
        commentEntity.setProperty("timestamp", comment.getTimestamp());
        if(comment.getImageUrl() != null) {
            commentEntity.setProperty("imageUrl", comment.getImageUrl());
        }
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
      String imageUrl = getUploadedImageUrl(request);

      Comment comment = new Comment(usernameString, commentString, time, imageUrl);
      return comment;
  }

  /** Returns a URL that points to the uploaded image file, or null if the user didn't upload an image file. */
  private String getUploadedImageUrl(HttpServletRequest request) {
      Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
      List<BlobKey> blobKeys = blobs.get("image");

      // User did not submit a file
      if(blobKeys == null || blobKeys.isEmpty()) {
          return null;
      }

      // User can only submit one file from the form
      BlobKey blobKey = blobKeys.get(0);
      BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);

      if (blobInfo.getSize() == 0 || invalidFileType(blobInfo.getContentType())) {
          blobstoreService.delete(blobKey);
          return null;
      }

      ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
      String url = imagesService.getServingUrl(options);

      return url;
  }

  /** Check if file sent is an image file (jpeg or png). */
  private boolean invalidFileType(String type) {
      return !validFileTypes.contains(type);
  }
}



