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
import com.bus_station_ticket.project.ProjectDTO.EmployeeDTO;
import com.bus_station_ticket.project.ProjectService.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController implements RestApiSimpleControllerInf<EmployeeDTO, Long> {

       @Autowired
       private EmployeeService employeeService;

       // Lấy tất cả các EmployeeEntity có
       // path: "/employees"

       @GetMapping
       @Override
       public ResponseEntity<ResponseObject> getAll() {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy dữ liệu từ lớp Service
              List<EmployeeDTO> listEmployeeEntities = this.employeeService.getAll_toDTO();

              // kiểm tra
              // Nếu mảng không rỗng
              if (listEmployeeEntities.isEmpty() == false) {

                     responseObject.setStatus("success"); // set status
                     responseObject.setData(listEmployeeEntities); // set data

                     responseObject.addMessage("mess", "Successfully retrieved data");
                     responseObject.addMessage("length", listEmployeeEntities.size());
                     responseObject.addMessage("info", responseObject.getPathBasicInfor("employees", "{driverId}"));

                     LoggerConfig.writeInfoLevel(EmployeeController.class, "/employees", "Successfully retrieved data");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);

              }
              responseObject.setStatus("failure");
              responseObject.setData(listEmployeeEntities);
              responseObject.addMessage("mess", "There is no data in the database");
              responseObject.addMessage("length", listEmployeeEntities.size());

              LoggerConfig.writeWarningLevel(EmployeeController.class, "/employees", "There is no data in the database");

              return ResponseEntity.status(HttpStatus.OK).body(responseObject);
       }

       // Lấy đối tượng EmployeeEntity dựa vào driverId
       // path: "/employees/{driverId}"

       @GetMapping("/{driverId}")
       public ResponseEntity<ResponseObject> getById(@PathVariable("driverId") Long driverId) {

              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy đối tượng AccountEntity dựa vào username
              EmployeeDTO employeeDTO = this.employeeService.getById_toDTO(driverId);

              // kiểm tra
              if (employeeDTO != null) {
                     responseObject.setStatus("success");
                     responseObject.setData(employeeDTO);
                     responseObject.addMessage("mess", "Found data with matching driver id");

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("employees", "{driverId}"));

                     LoggerConfig.writeInfoLevel(EmployeeController.class, "/employees/{driverId}", "Found data with matching driver id");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus("failure");
              responseObject.setData(employeeDTO);
              responseObject.addMessage("mess", "No employee entity found with matching driver id");

              LoggerConfig.writeWarningLevel(EmployeeController.class, "/employees/{driverId}", "No employee entity found with matching driver id");

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/delete/{driverId}")
       @Override
       public ResponseEntity<ResponseObject> delete(@PathVariable("driverId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(EmployeeController.class, "/employees/delete/{driverId}", "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // Lấy đối tượng AccountEntity dựa vào username
              EmployeeDTO employeeDTO = employeeService.getById_toDTO(id);

              // xóa đối tượng
              ResponseBoolAndMess responseBoolAndMess = employeeService.delete(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(employeeDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("employees", "{driverId}"));

                     LoggerConfig.writeInfoLevel(EmployeeController.class, "/employees/delete/{driverId}", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(employeeDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());
              
              LoggerConfig.writeWarningLevel(EmployeeController.class, "/employees/delete/{driverId}", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/hidden/{driverId}")
       @Override
       public ResponseEntity<ResponseObject> hidden(@PathVariable("driverId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(EmployeeController.class, "/employees/hidden/{driverId}", "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // ẩn đối tượng
              ResponseBoolAndMess responseBoolAndMess = employeeService.invisibleWithoutDelete(id);

              EmployeeDTO employeeDTO = employeeService.getById_toDTO(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(employeeDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("employees", "{driverId}"));

                     LoggerConfig.writeInfoLevel(EmployeeController.class, "/employees/hidden/{driverId}", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(employeeDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(EmployeeController.class, "/employees/hidden/{driverId}", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PostMapping("/insert")
       @Override
       public ResponseEntity<ResponseObject> save(@Valid EmployeeDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.employeeService.save_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("employees", "{driverId}"));

                     LoggerConfig.writeInfoLevel(EmployeeController.class, "/employees/insert", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(EmployeeController.class, "/employees/insert", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PutMapping("/update")
       @Override
       public ResponseEntity<ResponseObject> update(@Valid EmployeeDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.employeeService.update_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("employees", "{driverId}"));

                     LoggerConfig.writeInfoLevel(EmployeeController.class, "/employees/update", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(EmployeeController.class, "/employees/update", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PutMapping("/update/bus_and_employee")
       public ResponseEntity<ResponseObject> updateBusAndEmployeee(@Valid @RequestParam("busId") Long busId, @Valid @RequestParam("driverId") Long driverId) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.employeeService.addEmployeeDriverBus(driverId,busId);
              EmployeeDTO obj = this.employeeService.getById_toDTO(driverId);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("employees", "{driverId}"));

                     LoggerConfig.writeInfoLevel(EmployeeController.class, "/employees/update/bus_and_employee", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(EmployeeController.class, "/employees/update/bus_and_employee", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/delete/bus_and_employee")
       public ResponseEntity<ResponseObject> deleteBusAndEmployeee(@Valid @RequestParam("busId") Long busId, @Valid @RequestParam("driverId") Long driverId) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.employeeService.deleteEmployeeDriverBus(driverId,busId);
              EmployeeDTO obj = this.employeeService.getById_toDTO(driverId);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("employees", "{driverId}"));

                     LoggerConfig.writeInfoLevel(EmployeeController.class, "/employees/delete/bus_and_employee", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(EmployeeController.class, "/employees/delete/bus_and_employee", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @GetMapping("/bus_and_employee")
       public ResponseEntity<ResponseObject> getBusIdAndDriverId (){

              ResponseObject responseObject = this.employeeService.getAllDriverIdAndBusId();

              LoggerConfig.writeWarningLevel(EmployeeController.class, "/employees/bus_and_employee", MESS_SUCCESS);

              return ResponseEntity.status(HttpStatus.OK).body(responseObject);
       }

}
