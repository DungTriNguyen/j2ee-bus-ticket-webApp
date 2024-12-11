package com.bus_station_ticket.project.ProjectController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bus_station_ticket.project.Email.MailService;
import com.bus_station_ticket.project.ProjectConfig.ResponseObject;

@RestController
public class EmailController {
       
       @Autowired
       private MailService mailService;


       @PostMapping("/sendEmail")
       public ResponseEntity<ResponseObject> sendEmail (@RequestParam("toEmail") String toEmail, @RequestParam("subject") String subject, @RequestParam("body") String body){

              ResponseObject responseObject = this.mailService.sendEmail(toEmail, subject, body);

              if(responseObject.getStatus().equals("success")){
                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }
}
