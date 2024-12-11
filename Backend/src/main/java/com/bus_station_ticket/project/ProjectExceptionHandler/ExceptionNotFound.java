package com.bus_station_ticket.project.ProjectExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bus_station_ticket.project.ProjectConfig.ResponseObject;

@ControllerAdvice
public class ExceptionNotFound {
       @ExceptionHandler(NoHandlerFoundException.class)
       public ResponseEntity<ResponseObject> handle(NoHandlerFoundException exception) {
              ResponseObject responseObject = new ResponseObject();
              responseObject.setStatus("failure");
              responseObject.addMessage("mess", "Not found path: " + exception.getRequestURL());
              responseObject.setData(null);

              return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
       }
}
