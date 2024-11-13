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

import com.bus_station_ticket.project.ProjectConfig.ResponseBoolAndMess;
import com.bus_station_ticket.project.ProjectConfig.ResponseObject;
import com.bus_station_ticket.project.ProjectDTO.PenaltyTicketDTO;
import com.bus_station_ticket.project.ProjectService.PenaltyTicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/penaltytickets")
public class PenaltyTicketController implements RestApiSimpleControllerInf<PenaltyTicketDTO,Long>{
       

       @Autowired
       private PenaltyTicketService penaltyTicketService;

       // Lấy tất cả các PenaltyTicketEntity có
       // path: "/penaltytickets"

       @GetMapping
       @Override
       public ResponseEntity<ResponseObject> getAll() {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy dữ liệu từ lớp Service
              List<PenaltyTicketDTO> listPenaltyTicketEntities = this.penaltyTicketService.getAll_toDTO();

              // kiểm tra
              // Nếu mảng không rỗng
              if (listPenaltyTicketEntities.isEmpty() == false) {

                     responseObject.setStatus("success"); // set status
                     responseObject.setData(listPenaltyTicketEntities); // set data

                     responseObject.addMessage("mess", "Successfully retrieved data");
                     responseObject.addMessage("length", listPenaltyTicketEntities.size());
                     responseObject.addMessage("info", responseObject.getPathBasicInfor("penaltytickets", "{penaltyTicketId}"));

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);

              }
              responseObject.setStatus("failure");
              responseObject.setData(listPenaltyTicketEntities);
              responseObject.addMessage("mess", "There is no data in the database");
              responseObject.addMessage("length", listPenaltyTicketEntities.size());

              return ResponseEntity.status(HttpStatus.OK).body(responseObject);
       }

       // Lấy đối tượng PenaltyTicketEntity dựa vào penaltyTicketId
       // path: "/penaltytickets/{penaltyTicketId}"

       @GetMapping("/{penaltyTicketId}")
       @Override
       public ResponseEntity<ResponseObject> getById(@PathVariable("penaltyTicketId") Long penaltyTicketId) {

              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy đối tượng AccountEntity dựa vào username
              PenaltyTicketDTO penaltyTicketDTO = this.penaltyTicketService.getById_toDTO(penaltyTicketId);

              // kiểm tra
              if (penaltyTicketDTO != null) {
                     responseObject.setStatus("success");
                     responseObject.setData(penaltyTicketDTO);
                     responseObject.addMessage("mess", "Found data with matching penalty ticket id");

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("penaltytickets", "{penaltyTicketId}"));
                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus("failure");
              responseObject.setData(penaltyTicketDTO);
              responseObject.addMessage("mess", "No penalty ticket entity found with matching penalty ticket id");

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/delete/{penaltyticketId}")
       @Override
       public ResponseEntity<ResponseObject> delete(@PathVariable("penaltyticketId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);
                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // Lấy đối tượng AccountEntity dựa vào username
              PenaltyTicketDTO penaltyTicketDTO = penaltyTicketService.getById_toDTO(id);

              // xóa đối tượng
              ResponseBoolAndMess responseBoolAndMess = penaltyTicketService.delete(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(penaltyTicketDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("penaltytickets", "{penaltyticketId}"));
                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(penaltyTicketDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/hidden/{penaltyticketId}")
       @Override
       public ResponseEntity<ResponseObject> hidden(@PathVariable("penaltyticketId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);
                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // ẩn đối tượng
              ResponseBoolAndMess responseBoolAndMess = penaltyTicketService.invisibleWithoutDelete(id);

              PenaltyTicketDTO penaltyTicketDTO = penaltyTicketService.getById_toDTO(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(penaltyTicketDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("penaltytickets", "{penaltyticketId}"));
                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(penaltyTicketDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PostMapping("/insert")
       @Override
       public ResponseEntity<ResponseObject> save(@Valid PenaltyTicketDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.penaltyTicketService.save_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("penaltytickets", "{penaltyticketId}"));

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PutMapping("/update")
       @Override
       public ResponseEntity<ResponseObject> update(@Valid PenaltyTicketDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.penaltyTicketService.update_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("penaltytickets", "{penaltyticketId}"));

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       
}
