package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns URL that allows a user to upload a file to Blobstore.
 */
@WebServlet("/uploadImageUrl")
public class BlobstoreUploadUrlServlet extends HttpServlet {
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uploadUrl = blobstoreService.createUploadUrl("/comments");
        response.setContentType("text/plain");
        response.getWriter().println(uploadUrl);
    }
}