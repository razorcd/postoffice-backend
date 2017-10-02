package com.postbox.controler;

import com.postbox.service.IncomingRequestService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MimeHeaders;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.MalformedInputException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BoxController {

    IncomingRequestService incomingRequestServiceImpl;

    @Autowired
    public BoxController(IncomingRequestService incomingRequestServiceImpl) {
        this.incomingRequestServiceImpl = incomingRequestServiceImpl;
    }

    @RequestMapping(value = "/**")
    public ResponseEntity<String> recordRequest(HttpServletRequest request, HttpServletResponse response) {

//        String fullRequest = payload; //TODO: add all request fields into an object

        String path = request.getRequestURL().toString();
        Map<String, String[]> queryParams = request.getParameterMap();

        String method = request.getMethod();

        //headers
        HashMap<String, String> headers = new HashMap<>();
        Enumeration<String> headersObj = request.getHeaderNames();
        while (headersObj.hasMoreElements()) {
            String headerKey = headersObj.nextElement();
            headers.put(headerKey, request.getHeader(headerKey));
        }

        Cookie[] cookies = request.getCookies();

        String body = null;
        try {
            //read input buffer (only once)
            body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch(IOException ex) {
//            throw new UnprocessablePayloadException("runtime errr");
            ex.printStackTrace();
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(UncheckedIOException ex) {
            // handles MalformedInputException
            ex.printStackTrace();
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(IllegalStateException ex) {
            // software error | TEMP
            ex.printStackTrace();
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }



        incomingRequestServiceImpl.save(body);

        return new ResponseEntity<String>(body, HttpStatus.NO_CONTENT);

    }
}
