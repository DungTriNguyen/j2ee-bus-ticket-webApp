package com.bus_station_ticket.project.ProjectController;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bus_station_ticket.project.ProjectConfig.LoggerConfig;
import com.bus_station_ticket.project.ProjectConfig.ResponseBoolAndMess;
import com.bus_station_ticket.project.ProjectConfig.ResponseObject;
import com.bus_station_ticket.project.ProjectDTO.TicketDTO;
// import com.bus_station_ticket.project.ProjectService.AccountService;
import com.bus_station_ticket.project.ProjectService.TicketService;

// import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tickets")
public class TicketController implements RestApiSimpleControllerInf<TicketDTO, Long> {

       @Autowired
       private TicketService ticketService;

       // @Autowired
       // private AccountService accountService;

       @GetMapping
       @Override
       public ResponseEntity<ResponseObject> getAll() {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy dữ liệu từ lớp Service
              List<TicketDTO> listTicketEntities = this.ticketService.getAll_toDTO();

              // kiểm tra
              // Nếu mảng không rỗng
              if (listTicketEntities.isEmpty() == false) {

                     responseObject.setStatus("success"); // set status
                     responseObject.setData(listTicketEntities); // set data

                     responseObject.addMessage("mess", "Successfully retrieved data");
                     responseObject.addMessage("length", listTicketEntities.size());
                     responseObject.addMessage("info", responseObject.getPathBasicInfor("tickets", "{ticketId}"));

                     LoggerConfig.writeInfoLevel(TicketController.class, "/tickets", "Successfully retrieved data");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);

              }
              responseObject.setStatus("failure");
              responseObject.setData(listTicketEntities);
              responseObject.addMessage("mess", "There is no data in the database");
              responseObject.addMessage("length", listTicketEntities.size());

              LoggerConfig.writeWarningLevel(TicketController.class, "/tickets", "There is no data in the database");

              return ResponseEntity.status(HttpStatus.OK).body(responseObject);
       }

       // Lấy đối tượng TicketEntity dựa vào ticketId
       // path: "/tickets/{ticketId}"

       @GetMapping("/{ticketId}")
       @Override
       public ResponseEntity<ResponseObject> getById(@PathVariable("ticketId") Long ticketId) {

              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy đối tượng AccountEntity dựa vào username
              TicketDTO ticketDTO = this.ticketService.getById_toDTO(ticketId);

              // kiểm tra
              if (ticketDTO != null) {
                     responseObject.setStatus("success");
                     responseObject.setData(ticketDTO);
                     responseObject.addMessage("mess", "Found data with matching ticket id");

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("tickets", "{ticketId}"));

                     LoggerConfig.writeInfoLevel(TicketController.class, "/tickets/{ticketId}", "Found data with matching ticket id");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus("failure");
              responseObject.setData(ticketDTO);
              responseObject.addMessage("mess", "No ticket entity found with matching ticket id");

              LoggerConfig.writeWarningLevel(TicketController.class, "/tickets/{ticketId}", "No ticket entity found with matching ticket id");

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/delete/{ticketId}")
       @Override
       public ResponseEntity<ResponseObject> delete(@PathVariable("ticketId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(TicketController.class, "/tickets/delete/{ticketId}", "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // Lấy đối tượng AccountEntity dựa vào username
              TicketDTO ticketDTO = ticketService.getById_toDTO(id);

              // xóa đối tượng
              ResponseBoolAndMess responseBoolAndMess = ticketService.delete(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(ticketDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("tickets", "{ticketId}"));

                     LoggerConfig.writeInfoLevel(TicketController.class, "/tickets/delete/{ticketId}", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(ticketDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(TicketController.class, "/tickets/delete/{ticketId}", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/hidden/{ticketId}")
       @Override
       public ResponseEntity<ResponseObject> hidden(@PathVariable("ticketId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(TicketController.class, "/tickets/hidden/{ticketId}", "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // ẩn đối tượng
              ResponseBoolAndMess responseBoolAndMess = ticketService.invisibleWithoutDelete(id);

              TicketDTO ticketDTO = ticketService.getById_toDTO(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(ticketDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("tickets", "{ticketId}"));

                     LoggerConfig.writeInfoLevel(TicketController.class, "/tickets/hidden/{ticketId}", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(ticketDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(TicketController.class, "/tickets/hidden/{ticketId}", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PostMapping("/insert")
       @Override
       public ResponseEntity<ResponseObject> save(@Valid TicketDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.ticketService.save_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("tickets", "{ticketId}"));

                     LoggerConfig.writeInfoLevel(TicketController.class, "/tickets/insert", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(TicketController.class, "/tickets/insert", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PutMapping("/update")
       @Override
       public ResponseEntity<ResponseObject> update(@Valid TicketDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.ticketService.update_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("tickets", "{ticketId}"));

                     LoggerConfig.writeInfoLevel(TicketController.class, "/tickets/update", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(TicketController.class, "/tickets/update", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       // thong ke
       @GetMapping("/statistics")
       public ResponseEntity<ResponseObject> getStatisticsTickets(@RequestParam("dateA") LocalDate dateA,
                     @RequestParam("dateB") LocalDate dateB) {

              ResponseObject responseObject = new ResponseObject();

              if (dateA.isBefore(dateB)) {
                     responseObject = this.ticketService.statisticTicketRangeDay(dateA, dateB);

                     LoggerConfig.writeInfoLevel(TicketController.class, "/tickets/statistics", MESS_SUCCESS);

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.addMessage("mess", "The statistical date entered is incorrect");
              responseObject.setData(null);

              LoggerConfig.writeWarningLevel(TicketController.class, "/tickets/statistics", "The statistical date entered is incorrect");

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @GetMapping("/ticket_by_ticketId_username")
       public ResponseEntity<ResponseObject> getByTicketIdAndUserName(@RequestParam("ticketId") Long ticketId, @RequestParam("username") String username) {

              ResponseObject responseObject = this.ticketService.getByTicketIdAndUserName(ticketId,username);

              return ResponseEntity.status(HttpStatus.OK).body(responseObject);
       }

       @GetMapping("/ticket_by_date_username")
       public ResponseEntity<ResponseObject> getByTicketIdAndUserNameAndDateRange(@RequestParam("username") String username, @RequestParam("dateA") LocalDate dateA, @RequestParam("dateB") LocalDate dateB) {

              ResponseObject responseObject = this.ticketService.getByTicketIdAndUserNameAndDateRange(username, dateA, dateB);

              return ResponseEntity.status(HttpStatus.OK).body(responseObject);
       }

       @GetMapping("/ticket_full_info/{ticketId}")
       public ResponseEntity<ResponseObject> getFullInfoTicket (@PathVariable("ticketId") Long ticketId){

              ResponseObject responseObject = this.ticketService.getFullInfoTicket(ticketId);

              if(responseObject.getStatus().equals(MESS_SUCCESS)){
                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }
}
