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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bus_station_ticket.project.ProjectConfig.LoggerConfig;
import com.bus_station_ticket.project.ProjectConfig.ResponseBoolAndMess;
import com.bus_station_ticket.project.ProjectConfig.ResponseObject;
import com.bus_station_ticket.project.ProjectDTO.AccountDTO;
import com.bus_station_ticket.project.ProjectService.AccountService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/accounts")
public class AccountController implements RestApiSimpleControllerInf<AccountDTO, String> {

       @Autowired
       private AccountService accountService;

       // Lấy tất cả các AccountEntity có
       // path: "/accounts" http:localhost:8080/accounts
       @GetMapping
       @Override
       public ResponseEntity<ResponseObject> getAll() {

              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // Lấy dữ liệu từ lớp Service
              List<AccountDTO> listAccountEnities = this.accountService.getAll_toDTO();

              // kiểm tra
              // Nếu mảng không rỗng
              if (listAccountEnities.isEmpty() == false) {

                     responseObject.setStatus(MESS_SUCCESS); // set status
                     responseObject.setData(listAccountEnities); // set data

                     responseObject.addMessage("mess", "Successfully retrieved data");
                     responseObject.addMessage("length", listAccountEnities.size());
                     responseObject.addMessage("info", responseObject.getPathBasicInfor("accounts", "{username-id}"));

                     // ghi logger

                     LoggerConfig.writeInfoLevel(AccountController.class, "/accounts", "Successfully retrieved data");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);

              }
              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(listAccountEnities);
              responseObject.addMessage("mess", "There is no data in the database");
              responseObject.addMessage("length", listAccountEnities.size());

              // ghi logger
              // ghi logger
              LoggerConfig.writeWarningLevel(AccountController.class, "/accounts", "There is no data in the database");

              return ResponseEntity.status(HttpStatus.OK).body(responseObject);
       }

       // lấy đối tượng AccountEntity dựa vào username
       // path: "/account/{username-id}"
       @GetMapping("/{username-id}")
       public ResponseEntity<ResponseObject> getById(@PathVariable("username-id") String id) {

              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(AccountController.class, "/accounts/{username-id}",
                                   "Incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // Lấy đối tượng AccountEntity dựa vào username
              AccountDTO accountDTO = accountService.getById_toDTO(id);

              // kiểm tra
              if (accountDTO != null) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(accountDTO);
                     responseObject.addMessage("mess", "Found data with matching login name");

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("accounts", "{username-id}"));
                     LoggerConfig.writeInfoLevel(AccountController.class, "/accounts/{username-id}",
                                   "Found data with matching login name");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(accountDTO);
              responseObject.addMessage("mess", "No user found with matching login name");

              LoggerConfig.writeWarningLevel(AccountController.class, "/accounts/{username-id}",
                            "No user found with matching login name");

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);

       }

       // lưu một đối tượng account
       // path: "/insert"
       @PostMapping("/insert")
       @Override
       public ResponseEntity<ResponseObject> save(@Valid @RequestBody AccountDTO accountDTO) {

              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.accountService.save_toDTO(accountDTO);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(accountDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("accounts", "{username-id}"));

                     LoggerConfig.writeInfoLevel(AccountController.class, "/accounts/insert",
                                   responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(accountDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(AccountController.class, "/accounts/insert",
                            responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/delete/{username-id}")
       @Override
       public ResponseEntity<ResponseObject> delete(@PathVariable("username-id") String id) {

              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(AccountController.class, "/accounts/delete/{username-id}",
                                   "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);

              }

              // Lấy đối tượng AccountEntity dựa vào username
              AccountDTO accountDTO = accountService.getById_toDTO(id);

              // xóa đối tượng
              ResponseBoolAndMess responseBoolAndMess = accountService.delete(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(accountDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("accounts", "{username-id}"));

                     LoggerConfig.writeInfoLevel(AccountController.class, "/accounts/delete/{username-id}",
                                   "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(accountDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(AccountController.class, "/accounts/delete/{username-id}",
                            responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @DeleteMapping("/hidden/{username-id}")
       @Override
       public ResponseEntity<ResponseObject> hidden(@PathVariable("username-id") String id) {

              // Tạo một đối tượng phản hồi ResponseObject
              ResponseObject responseObject = new ResponseObject();

              // kiểm tra giá trị id
              if (isValidId(id) == false) {
                     responseObject.setStatus(MESS_FAILURE);
                     responseObject.addMessage("mess", "Missing path variable value or incorrect path variable value");
                     responseObject.setData(id);

                     LoggerConfig.writeErrorLevel(AccountController.class, "/accounts/hidden/{username-id}",
                                   "Missing path variable value or incorrect path variable value");

                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }

              // ẩn đối tượng
              ResponseBoolAndMess responseBoolAndMess = accountService.invisibleWithoutDelete(id);

              // Lấy đối tượng AccountEntity dựa vào username
              AccountDTO accountDTO = accountService.getById_toDTO(id);

              // kiểm tra
              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(accountDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("accounts", "{username-id}"));

                     LoggerConfig.writeInfoLevel(AccountController.class, "/accounts/hidden/{username-id}",
                                   responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(accountDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(AccountController.class, "/accounts/hidden/{username-id}",
                            responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PutMapping("/update")
       @Override
       public ResponseEntity<ResponseObject> update(@Valid @RequestBody AccountDTO accountDTO) {

              ResponseObject responseObject = new ResponseObject();

              ResponseBoolAndMess responseBoolAndMess = this.accountService.update_toDTO(accountDTO);

              if (responseBoolAndMess.getValueBool()) {
                     responseObject.setStatus(MESS_SUCCESS);
                     responseObject.setData(accountDTO);
                     responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

                     responseObject.addMessage("info", responseObject.getPathBasicInfor("accounts", "{username-id}"));

                     LoggerConfig.writeInfoLevel(AccountController.class, "/accounts/update",
                                   responseBoolAndMess.getValueMess());

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }

              responseObject.setStatus(MESS_FAILURE);
              responseObject.setData(accountDTO);
              responseObject.addMessage("mess", responseBoolAndMess.getValueMess());

              LoggerConfig.writeWarningLevel(AccountController.class, "/accounts/update",
                            responseBoolAndMess.getValueMess());

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);

       }

       @PostMapping("/info-login")
       public ResponseEntity<ResponseObject> getInfoLogin() {

              ResponseObject responseObject = new ResponseObject();

              AccountDTO accountDTO = this.accountService.geAccountDTOHasLogin();

              // kiem tra
              if (accountDTO != null) {
                     responseObject.setStatus(MESS_SUCCESS);

                     responseObject.addMessage("mess", "Below is the user's authentication information");
                     responseObject.addMessage("jwtToken", this.accountService.getTokenJwt());
                     responseObject.setData(accountDTO);

                     LoggerConfig.writeInfoLevel(AccountController.class, "/accounts/info-login",
                                   "Below is the user's authentication information");

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }
              responseObject.setStatus(MESS_FAILURE);
              responseObject.addMessage("mess", "Could not get user authentication information");

              responseObject.setData(null);

              LoggerConfig.writeWarningLevel(AccountController.class, "/accounts/info-login",
                            "Could not get user authentication information");

              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);

       }

       @PostMapping("/register")
       public ResponseEntity<ResponseObject> register(
                     @RequestParam("username") @NotBlank(message = "Cannot be left blank !") @Size(max = 20, min = 5, message = "Account username must be at least 5 characters and at most 20 characters") String username,
                     @RequestParam("pass") @NotBlank(message = "Cannot be left blank !") String pass,
                     @RequestParam("email") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@(gmail|outlook)\\.com$", message = "You need to enter your gmail or outlook address") String email,
                     @RequestParam("fullName") @NotBlank(message = "Cannot be left blank !") String fullName,
                     @RequestParam("phoneNumber") @Pattern(regexp = "^(03|05|07|08|09)[0-9]{8}$", message = "Your phone number is not valid") String phoneNumber) {

              ResponseObject responseObject = this.accountService.registerAccountUser(username, pass, email, fullName,
                            phoneNumber);

              if (responseObject.getStatus().equals(MESS_SUCCESS)) {
                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }

       @PostMapping("/update_user")
       public ResponseEntity<ResponseObject> updateForUser(
                     @NotBlank(message = "Cannot be left blank !") @Size(max = 20, min = 5, message = "Account username must be at least 5 characters and at most 20 characters") @RequestParam("username") String username,
                     @NotBlank(message = "Cannot be left blank !") @RequestParam("pass") String pass,
                     @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@(gmail|outlook)\\.com$", message = "You need to enter your gmail or outlook address") @RequestParam("email") String email,
                     @NotBlank(message = "Cannot be left blank !") @RequestParam("fullName") String fullName,
                     @Pattern(regexp = "^(03|05|07|08|09)[0-9]{8}$", message = "Your phone number is not valid") @RequestParam("phoneNumber") String phoneNumber) {

              ResponseObject responseObject = this.accountService.updateAccountForUser(username, pass, email, fullName,
                            phoneNumber);

              if (responseObject.getStatus().equals(MESS_SUCCESS)) {
                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              }
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
       }
}
