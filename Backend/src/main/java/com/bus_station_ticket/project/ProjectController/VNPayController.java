package com.bus_station_ticket.project.ProjectController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bus_station_ticket.project.ProjectDTO.TicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bus_station_ticket.project.ProjectConfig.ResponseObject;
import com.bus_station_ticket.project.ProjectService.TicketService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class VNPayController {

       @Autowired
       private TicketService ticketService;

       @PostMapping("/create_payment")
       public ResponseEntity<ResponseObject> creatPayment(
                     HttpServletRequest request,
                     @RequestParam("returnUrl") String returnUrl,
                     @RequestParam("seats") List<String> seats, // Danh sách ghế
                     @RequestParam("departureDate") LocalDate departureDate,
                     @RequestParam("scheduleId") Long scheduleId,
                     @RequestParam("discountId") Long discountId,
                     @RequestParam("token") String token) {

              try {
                     String baseUrl = request.getScheme() + "://" + request.getServerName() + ":"
                                   + request.getServerPort() + "/vnpay-payment-return";

                     ResponseObject responseObject = this.ticketService.createMultipleTicketsAndPayment(request, baseUrl, seats, departureDate, scheduleId, discountId, token);

                     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
              } catch (Exception e) {
                     e.printStackTrace();
                     ResponseObject responseObject = new ResponseObject();
                     responseObject.setStatus("success");
                     responseObject.addMessage("mess", "Exception '/create_payment' ");
                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
              }
       }

//       @GetMapping("/vnpay-payment-return")
//       public ResponseEntity<ResponseObject> resultPayment(HttpServletRequest request) {
//
//              ResponseObject responseObject = this.ticketService.returnFronVNPay(request);
//
//              if (responseObject.getStatus().equals("success")) {
//                     return  ResponseEntity.status(HttpStatus.OK).body(responseObject);
//              }
//              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
//
//       }

       @GetMapping("/vnpay-payment-return")
       public RedirectView resultPayment(HttpServletRequest request) {

              ResponseObject responseObject = this.ticketService.returnFronVNPay(request);

              List<TicketDTO> data = (List<TicketDTO>) responseObject.getData();

              String returnUrl = "http://localhost:5173/export?status=";

              if(responseObject.getStatus().equals("failure")) {
                     returnUrl += responseObject.getStatus();
              } else {
                     List<String> listTicketId = new ArrayList<>();
//                     List<String> listSeatNumber = new ArrayList<>();
//                     String departureDate = data.get(0).getDepartureDate().toString();
//                     String phoneNumber = data.get(0).getPhoneNumber();
//                     String bookingUser = data.get(0).getAccountEnity_Id();

                     data.forEach(ticketDTO ->  {
                            listTicketId.add(ticketDTO.getTicketId().toString());
//                            listSeatNumber.add(ticketDTO.getSeatNumber());
                     });

                     returnUrl += responseObject.getStatus() + "&ticketId=" + listTicketId.toString() ;
              }

           return new RedirectView(returnUrl);
       }
}
