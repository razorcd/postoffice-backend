package com.postbox.controler.deserializer;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HttpRequestDeserializer {

    public String getUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    public String getMethod(HttpServletRequest request) {
        return request.getMethod();
    }

    public Map<String, String[]> getParams(HttpServletRequest request) {
        return request.getParameterMap();
    }

    public Map<String, String> getHeaders(HttpServletRequest request) {
        HashMap<String, String> headers = new HashMap<>();
        Enumeration<String> headersObj = request.getHeaderNames();
        while (headersObj.hasMoreElements()) {
            String headerKey = headersObj.nextElement();
            headers.put(headerKey, request.getHeader(headerKey));
        }
        return headers;
    }

    public String getBody(HttpServletRequest request) {
        String body = null;
        try {
            //read input buffer (only once)
            body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch(IOException ex) {
//            throw new UnprocessablePayloadException("runtime errr");
            ex.printStackTrace();
//            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(UncheckedIOException ex) {
            // handles MalformedInputException
            ex.printStackTrace();
//            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(IllegalStateException ex) {
            // software error | TEMP
            ex.printStackTrace();
//            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return body;
    }
}
