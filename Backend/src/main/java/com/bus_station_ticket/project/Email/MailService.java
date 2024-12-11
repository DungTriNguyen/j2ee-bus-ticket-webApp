package com.bus_station_ticket.project.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.bus_station_ticket.project.ProjectConfig.ResponseObject;

import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

       @Autowired
       private JavaMailSender javaMailSender;

       // Gửi email
       public ResponseObject sendEmail(String toEmail, String subject, String body) {
              try {
                     ResponseObject responseObject = new ResponseObject();
                     Map<String, String> data = new HashMap<>();

                     SimpleMailMessage mail = new SimpleMailMessage();
                     mail.setFrom("truongphuc056@gmail.com"); // Gmail yêu cầu email hợp lệ
                     mail.setTo(toEmail);
                     mail.setSubject(subject);
                     mail.setText(body);

                     javaMailSender.send(mail);

                     responseObject.setStatus("success");
                     responseObject.addMessage("mess", "Email sent successfully.");
                     data.put("toEmail", toEmail);
                     data.put("subject", subject);
                     data.put("body", body);
                     responseObject.setData(data);

                     return responseObject;

              } catch (Exception e) {
                     e.printStackTrace(); // In lỗi ra console để debug
                     ResponseObject responseObject = new ResponseObject();
                     Map<String, String> data = new HashMap<>();

                     responseObject.setStatus("failure");
                     responseObject.addMessage("mess", "Failed to send email: " + e.getMessage());
                     data.put("toEmail", toEmail);
                     data.put("subject", subject);
                     data.put("body", body);
                     responseObject.setData(data);

                     return responseObject;
              }
       }
}
