package com.bus_station_ticket.project.ProjectController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bus_station_ticket.project.ProjectConfig.LoggerConfig;
import com.bus_station_ticket.project.ProjectConfig.ResponseBoolAndMess;
import com.bus_station_ticket.project.ProjectConfig.ResponseObject;
import com.bus_station_ticket.project.ProjectDTO.FeedbackDTO;

import com.bus_station_ticket.project.ProjectService.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController implements RestApiSimpleControllerInf<FeedbackDTO, Long> {

       @Autowired
       private FeedbackService feedbackService;

       // Lấy tất cả các FeedbackEntity có
       // path: "/feedbacks"

       @GetMapping
       @Override
       public ResponseEntity<ResponseObject> getAll() {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy dữ liệu từ lớp Service
              List<FeedbackDTO> listFeedbackentities = this.feedbackService.getAll_toDTO();

              // kiểm tra
              // Nếu mảng không rỗng
              if (listFeedbackentities.isEmpty() == false) {

                     responseObject.setStatus("success"); // set status
                     responseObject.setData(listFeedbackentities); // set data

                     responseObject.addMessage("mess", "Successfully retrieved data");
                     responseObject.addMessage("length", listFeedbackentities.size());
                     responseObject.addMessage("info", responseObject.getPathBasicInfor("feedbacks", "{feedbackId}"));

                     LoggerConfig.writeInfoLevel(FeedbackController.class, "/feedbacks", "Successfully retrieved data");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);

              }
              responseObject.setStatus("failure");
              responseObject.setData(listFeedbackentities);
              responseObject.addMessage("mess", "There is no data in the database");
              responseObject.addMessage("length", listFeedbackentities.size());

              LoggerConfig.writeWarningLevel(FeedbackController.class, "/feedbacks", "There is no data in the database");

              return ResponseEntity.status(HttpStatus.OK).body(responseObject);
       }

       // Lấy đối tượng FeedbackEntity dựa vào feedbackId
       // path: "/feedbacks/{feedbackId}"

       @GetMapping("/{feedbackId}")
       @Override
       public ResponseEntity<ResponseObject> getById(@PathVariable("feedbackId") Long feedbackId) {

              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy đối tượng AccountEntity dựa vào username
              FeedbackDTO feedbackDTO = this.feedbackService.getById_toDTO(feedbackId);

              // kiểm tra
              if (feedbackDTO != null) {
                     responseObject.setStatus("success");
                     responseObject.setData(feedbackDTO);
                     responseObject.addMessage("mess", "Found data with matching feedback id");

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("feedbacks", "{feedbackId}"));

                     LoggerConfig.writeInfoLevel(FeedbackController.class, "/feedbacks/{feedbackId}", "Found data with matching feedback id");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus("failure");
              responseObject.setData(feedbackDTO);
              responseObject.addMessage("mess", "No feedback entity found with matching feedback id");

              LoggerConfig.writeWarningLevel(FeedbackController.class, "/feedbacks/{feedbackId}", "No feedback entity found with matching feedback id");

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/delete/{feedbackId}")
       @Override
       public ResponseEntity<ResponseObject> delete(@PathVariable("feedbackId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(FeedbackController.class, "/feedbacks/delete/{feedbackId}", "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // Lấy đối tượng AccountEntity dựa vào username
              FeedbackDTO feedbackDTO = feedbackService.getById_toDTO(id);

              // xóa đối tượng
              ResponseBoolAndMess responseBoolAndMess = feedbackService.delete(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(feedbackDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("feedbacks", "{feedbackId}"));

                     LoggerConfig.writeInfoLevel(FeedbackController.class, "/feedbacks/delete/{feedbackId}", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(feedbackDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(FeedbackController.class, "/feedbacks/delete/{feedbackId}", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/hidden/{feedbackId}")
       @Override
       public ResponseEntity<ResponseObject> hidden(@PathVariable("feedbackId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(FeedbackController.class, "/feedbacks/hidden/{feedbackId}","Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // ẩn đối tượng
              ResponseBoolAndMess responseBoolAndMess = feedbackService.invisibleWithoutDelete(id);

              FeedbackDTO feedbackDTO = feedbackService.getById_toDTO(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(feedbackDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("feedbacks", "{feedbackId}"));

                     LoggerConfig.writeInfoLevel(FeedbackController.class, "/feedbacks/hidden/{feedbackId}",responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(feedbackDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(FeedbackController.class, "/feedbacks/hidden/{feedbackId}",responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PostMapping("/insert")
       @Override
       public ResponseEntity<ResponseObject> save(@Valid FeedbackDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.feedbackService.save_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("feedbacks", "{feedbackId}"));

                     LoggerConfig.writeInfoLevel(FeedbackController.class, "/feedbacks/insert",responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(FeedbackController.class, "/feedbacks/insert",responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PutMapping("/update")
       @Override
       public ResponseEntity<ResponseObject> update(@Valid FeedbackDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.feedbackService.update_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("feedbacks", "{feedbackId}"));

                     LoggerConfig.writeInfoLevel(FeedbackController.class, "/feedbacks/update",responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(FeedbackController.class, "/feedbacks/update",responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

}
