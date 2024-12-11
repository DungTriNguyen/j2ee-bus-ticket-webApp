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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bus_station_ticket.project.ProjectConfig.LoggerConfig;
import com.bus_station_ticket.project.ProjectConfig.ResponseBoolAndMess;
import com.bus_station_ticket.project.ProjectConfig.ResponseObject;
import com.bus_station_ticket.project.ProjectDTO.BusRouteScheduleDTO;
import com.bus_station_ticket.project.ProjectService.BusRouteScheduleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bus_routes_schedule")
public class BusRouteScheduleController implements RestApiSimpleControllerInf<BusRouteScheduleDTO, Long> {

       @Autowired
       private BusRouteScheduleService busRouteScheduleService;

       @GetMapping
       @Override
       public ResponseEntity<ResponseObject> getAll() {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy dữ liệu từ lớp Service
              List<BusRouteScheduleDTO> listBusScheduleEntities = this.busRouteScheduleService.getAll_toDTO();

              // kiểm tra
              // Nếu mảng không rỗng
              if (listBusScheduleEntities.isEmpty() == false) {

                     responseObject.setStatus(MESS_SUCCESS); // set status
                     responseObject.setData(listBusScheduleEntities); // set data

                     responseObject.addMessage("mess", "Successfully retrieved data");
                     responseObject.addMessage("length", listBusScheduleEntities.size());
                     responseObject.addMessage("info",
                                   responseObject.getPathBasicInfor("bus_routes_schedule", "{scheduleId}"));

                     LoggerConfig.writeInfoLevel(BusController.class, "/bus_routes_schedule", "Successfully retrieved data");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);

              }
              responseObject.setStatus("failure");
              responseObject.setData(listBusScheduleEntities);
              responseObject.addMessage("mess", "There is no data in the database");
              responseObject.addMessage("length", listBusScheduleEntities.size());

              LoggerConfig.writeWarningLevel(BusController.class, "/bus_routes_schedule",
                            "There is no data in the database");

              return ResponseEntity.status(HttpStatus.OK).body(responseObject);
       }

       @GetMapping("/{scheduleId}")
       @Override
       public ResponseEntity<ResponseObject> getById(@Valid @PathVariable("scheduleId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(BusController.class, "/bus_routes_schedule/{scheduleId}",
                                   "Incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // Lấy đối tượng AccountEntity dựa vào username
              BusRouteScheduleDTO busRouteScheduleDTO = busRouteScheduleService.getById_toDTO(id);

              // kiểm tra
              if (busRouteScheduleDTO != null) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(busRouteScheduleDTO);
                     responseObject.addMessage("mess", "Found data with matching schedule id");

                     responseObject.addMessage("info",
                                   responseObject.getPathBasicInfor("bus_routes_schedule", "{scheduleId}"));

                     LoggerConfig.writeInfoLevel(BusController.class, "/bus_routes_schedule/{scheduleId}",
                                   "Found data with matching bus id");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(busRouteScheduleDTO);
              responseObject.addMessage("mess", "No bus routes schedule entity found with matching schedule id");

              LoggerConfig.writeWarningLevel(BusController.class, "/bus_routes_schedule/{scheduleId}",
                            "No bus route schedule entity found with matching bus id");

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PostMapping("/insert")
       @Override
       public ResponseEntity<ResponseObject> save(@Valid BusRouteScheduleDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.busRouteScheduleService.save_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info",
                                   responseObject.getPathBasicInfor("bus_routes_schedule", "{scheduleId}"));

                     LoggerConfig.writeInfoLevel(BusController.class, "/bus_routes_schedule/insert",
                                   responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(BusController.class, "/bus_routes_schedule/insert",
                            responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PutMapping("/update")
       @Override
       public ResponseEntity<ResponseObject> update(@Valid BusRouteScheduleDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.busRouteScheduleService.update_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("bus_routes_schedule", "{scheduleId}"));

                     LoggerConfig.writeInfoLevel(BusController.class, "/bus_routes_schedule/update",
                                   responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(BusController.class, "/bus_routes_schedule/update", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/delete/{scheduleId}")
       @Override
       public ResponseEntity<ResponseObject> delete(@PathVariable("scheduleId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(BusController.class, "/bus_routes_schedule/delete/{scheduleId}",
                                   "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // Lấy đối tượng AccountEntity dựa vào username
              BusRouteScheduleDTO busRouteScheduleDTO = busRouteScheduleService.getById_toDTO(id);

              // xóa đối tượng
              ResponseBoolAndMess responseBoolAndMess = busRouteScheduleService.delete(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(busRouteScheduleDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info",
                                   responseObject.getPathBasicInfor("bus_routes_schedule", "{scheduleId}"));

                     LoggerConfig.writeInfoLevel(BusController.class, "/bus_routes_schedule/delete/{scheduleId}",
                                   responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(busRouteScheduleDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(BusController.class, "/bus_routes_schedule/delete/{scheduleId}",
                            responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/hidden/{scheduleId}")
       @Override
       public ResponseEntity<ResponseObject> hidden(@PathVariable("scheduleId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(BusController.class, "/bus_routes_schedule/hidden/{scheduleId}",
                                   "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // ẩn đối tượng
              ResponseBoolAndMess responseBoolAndMess = busRouteScheduleService.invisibleWithoutDelete(id);

              BusRouteScheduleDTO busRouteScheduleDTO = busRouteScheduleService.getById_toDTO(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(busRouteScheduleDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("bus_routes_schedule", "{scheduleId}"));

                     LoggerConfig.writeInfoLevel(BusController.class, "/bus_routes_schedule/hidden/{scheduleId}",
                                   responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(busRouteScheduleDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(BusController.class, "/bus_routes_schedule/hidden/{scheduleId}",
                            responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @GetMapping("/departureLocation_destinationLocation")
       public ResponseEntity<ResponseObject> getScheduleByDepartureLocationAndDestinationLocation (@RequestParam("departureLocation") String departureLocation, @RequestParam("destinationLocation")
       String destinationLocation){

              ResponseObject responseObject = this.busRouteScheduleService.getScheduleByDepartureLocationAndDestinationLocation(departureLocation, destinationLocation);

              if(responseObject.getStatus().equals(MESS_SUCCESS)){
                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

}
