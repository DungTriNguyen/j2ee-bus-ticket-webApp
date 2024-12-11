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
import com.bus_station_ticket.project.ProjectDTO.BusRoutesDTO;
import com.bus_station_ticket.project.ProjectService.BusRoutesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/busroutes")
public class BusRoutesController implements RestApiSimpleControllerInf<BusRoutesDTO, Long> {

       @Autowired
       private BusRoutesService busRoutesService;

       // Lấy tất cả các BusRoutesEntity có
       // path: "/busroutes"
       @GetMapping
       @Override
       public ResponseEntity<ResponseObject> getAll() {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy dữ liệu từ lớp Service
              List<BusRoutesDTO> listBusRoutesEntities = this.busRoutesService.getAll_toDTO();

              // kiểm tra
              // Nếu mảng không rỗng
              if (listBusRoutesEntities.isEmpty() == false) {

                     responseObject.setStatus(MESS_SUCCESS); // set status
                     responseObject.setData(listBusRoutesEntities); // set data

                     responseObject.addMessage("mess", "Successfully retrieved data");
                     responseObject.addMessage("length", listBusRoutesEntities.size());
                     responseObject.addMessage("info", responseObject.getPathBasicInfor("busroutes", "{routesId}"));

                     LoggerConfig.writeInfoLevel(BusRoutesController.class, "/busroutes", "Successfully retrieved data");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);

              }
              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(listBusRoutesEntities);
              responseObject.addMessage("mess", "There is no data in the database");
              responseObject.addMessage("length", listBusRoutesEntities.size());

              LoggerConfig.writeWarningLevel(BusRoutesController.class, "/busroutes", "There is no data in the database");

              return ResponseEntity.status(HttpStatus.OK).body(responseObject);
       }

       // Lấy đối tượng BusRoutesEnity dựa vào routesId
       // path: "/busroutes/{routesId}"
       @GetMapping("/{routesId}")
       public ResponseEntity<ResponseObject> getById(@PathVariable("routesId") Long routesId) {

              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy đối tượng AccountEntity dựa vào username
              BusRoutesDTO busRoutesDTO = busRoutesService.getById_toDTO(routesId);

              // kiểm tra
              if (busRoutesDTO != null) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(busRoutesDTO);
                     responseObject.addMessage("mess", "Found data with matching bus routes id");

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("busroutes", "{routesId}"));

                     LoggerConfig.writeInfoLevel(BusRoutesController.class, "/busroutes/{routesId}", "Found data with matching bus routes id");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(busRoutesDTO);
              responseObject.addMessage("mess", "No bus routes entity found with matching routes id");

              LoggerConfig.writeWarningLevel(BusRoutesController.class, "/busroutes/{routesId}", "No bus routes entity found with matching routes id");

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/delete/{routesId}")
       @Override
       public ResponseEntity<ResponseObject> delete(@PathVariable("routesId") Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if(isValidId(id) == false){
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");     
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(BusRoutesController.class, "/busroutes/delete/{routesId}", "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // Lấy đối tượng AccountEntity dựa vào username
              BusRoutesDTO busRoutesDTO = busRoutesService.getById_toDTO(id);

              // xóa đối tượng
              ResponseBoolAndMess responseBoolAndMess = busRoutesService.delete(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(busRoutesDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("busroutes", "{routesId}"));

                     LoggerConfig.writeInfoLevel(BusRoutesController.class, "/busroutes/delete/{routesId}", responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(busRoutesDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(BusRoutesController.class, "/busroutes/delete/{routesId}", responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/hidden/{routesId}")
       @Override
       public ResponseEntity<ResponseObject> hidden(@PathVariable("routesId")  Long id) {
              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if(isValidId(id) == false){
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");     
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(BusRoutesController.class, "/busroutes/hidden/{routesId}","Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // ẩn đối tượng
              ResponseBoolAndMess responseBoolAndMess = busRoutesService.invisibleWithoutDelete(id);

              
              BusRoutesDTO busRoutesDTO = busRoutesService.getById_toDTO(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(busRoutesDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("busroutes", "{routesId}"));

                     LoggerConfig.writeInfoLevel(BusRoutesController.class, "/busroutes/hidden/{routesId}",responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(busRoutesDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(BusRoutesController.class, "/busroutes/hidden/{routesId}",responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PostMapping("/insert")
       @Override
       public ResponseEntity<ResponseObject> save(@Valid BusRoutesDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.busRoutesService.save_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());
                     
                     responseObject.addMessage("info", responseObject.getPathBasicInfor("busroutes", "{routesId}"));

                     LoggerConfig.writeInfoLevel(BusRoutesController.class, "/busroutes/insert",responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(BusRoutesController.class, "/busroutes/insert",responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PutMapping("/update")
       @Override
       public ResponseEntity<ResponseObject> update(@Valid BusRoutesDTO obj) {
              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.busRoutesService.update_toDTO(obj);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(obj);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());
                     
                     responseObject.addMessage("info", responseObject.getPathBasicInfor("busroutes", "{routesId}"));

                     LoggerConfig.writeInfoLevel(BusRoutesController.class, "/busroutes/update",responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(obj);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(BusRoutesController.class, "/busroutes/update",responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

}
