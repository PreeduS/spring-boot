package com.example.demo.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;

public class HttpResponseUtil {
    public static void writeExceptionToResponse(HttpServletResponse response, String message, String path, int responseStatus, String contentType)
    throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        //data.put("status",HttpStatus.FORBIDDEN.value());
        data.put("status",responseStatus);
        data.put("message", message);
        data.put("path", path);

        response.setContentType(contentType);
        response.setStatus(responseStatus);
        //response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, data);
        out.flush();
    }
    public static void writeExceptionToResponse(HttpServletResponse response, String message, String path)
        throws IOException {
            writeExceptionToResponse(response, message, path, HttpServletResponse.SC_UNAUTHORIZED, "application/json");

    }
}