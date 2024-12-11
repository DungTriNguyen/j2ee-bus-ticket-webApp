package com.bus_station_ticket.project.ProjectDTO;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TicketDTO {

       @NotNull(message = "ticketId cannot be blank")
       private Long ticketId;

       @NotBlank(message = "accountEnity_Id cannot be blank")
       private String accountEnity_Id;

       @NotNull(message = "busRouteSchedule_Id cannot be blank")
       private Long busRouteSchedule_Id;

       @NotNull(message = "paymentEntity_Id cannot be blank")
       private Long paymentEntity_Id;

       // @NotNull(message = "discountEntity_Id cannot be blank")
       private Long discountEntity_Id;

       @NotBlank(message = "seatNumber cannot be blank")
       @Size(max = 5, message = "The seat number cannot exceed 3 characters")
       private String seatNumber;

       @NotNull(message = "departureDate cannot be null")
       private LocalDate departureDate;

       @NotNull(message = "price cannot be null")
       @Min(value = 0, message = "Price must be greater than or equal to 0")
       private float price;

       @NotNull(message = "phoneNumber cannot be null")
       @Pattern(regexp = "^(03|05|07|08|09)[0-9]{8}$", message = "Your phone number is not valid")
       private String phoneNumber;

       @NotBlank(message = "Status cannot be blank")
       @Pattern(regexp = "^(success|pending|failure)$", message = "There are only 3 status values: success, pending and failure")
       private String status;

       @NotNull(message = "List of ticket IDs cannot be null")
       private List<Long> listFeedbackEntities_Id;

       @NotNull(message = "Delete status cannot be null")
       private Boolean isDelete;

       public TicketDTO() {
       }

       public TicketDTO(@NotNull(message = "ticketId cannot be blank") Long ticketId,
                     @NotBlank(message = "accountEnity_Id cannot be blank") String accountEnity_Id,
                     @NotNull(message = "busRouteSchedule_Id cannot be blank") Long busRouteSchedule_Id,
                     @NotNull(message = "paymentEntity_Id cannot be blank") Long paymentEntity_Id,
                     Long discountEntity_Id,
                     @NotBlank(message = "seatNumber cannot be blank") @Size(max = 5, message = "The seat number cannot exceed 3 characters") String seatNumber,
                     @NotNull(message = "departureDate cannot be null") LocalDate departureDate,
                     @NotNull(message = "price cannot be null") @Min(value = 0, message = "Price must be greater than or equal to 0") float price,
                     @NotNull(message = "phoneNumber cannot be null") @Pattern(regexp = "^(03|05|07|08|09)[0-9]{8}$", message = "Your phone number is not valid") String phoneNumber,
                     @NotBlank(message = "Status cannot be blank") @Pattern(regexp = "^(success|pending|failure)$", message = "There are only 3 status values: success, pending and failure") String status,
                     @NotNull(message = "List of ticket IDs cannot be null") List<Long> listFeedbackEntities_Id,
                     @NotNull(message = "Delete status cannot be null") Boolean isDelete) {
              this.ticketId = ticketId;
              this.accountEnity_Id = accountEnity_Id;
              this.busRouteSchedule_Id = busRouteSchedule_Id;
              this.paymentEntity_Id = paymentEntity_Id;
              this.discountEntity_Id = discountEntity_Id;
              this.seatNumber = seatNumber;
              this.departureDate = departureDate;
              this.price = price;
              this.phoneNumber = phoneNumber;
              this.status = status;
              this.listFeedbackEntities_Id = listFeedbackEntities_Id;
              this.isDelete = isDelete;
       }

       public Long getTicketId() {
              return ticketId;
       }

       public void setTicketId(Long ticketId) {
              this.ticketId = ticketId;
       }

       public String getAccountEnity_Id() {
              return accountEnity_Id;
       }

       public void setAccountEnity_Id(String accountEnity_Id) {
              this.accountEnity_Id = accountEnity_Id;
       }

       

       public Long getPaymentEntity_Id() {
              return paymentEntity_Id;
       }

       public void setPaymentEntity_Id(Long paymentEntity_Id) {
              this.paymentEntity_Id = paymentEntity_Id;
       }

       public Long getDiscountEntity_Id() {
              return discountEntity_Id;
       }

       public void setDiscountEntity_Id(Long discountEntity_Id) {
              this.discountEntity_Id = discountEntity_Id;
       }

       public String getSeatNumber() {
              return seatNumber;
       }

       public void setSeatNumber(String seatNumber) {
              this.seatNumber = seatNumber;
       }

       public float getPrice() {
              return price;
       }

       public void setPrice(float price) {
              this.price = price;
       }

       public String getPhoneNumber() {
              return phoneNumber;
       }

       public void setPhoneNumber(String phoneNumber) {
              this.phoneNumber = phoneNumber;
       }

       public List<Long> getListFeedbackEntities_Id() {
              return listFeedbackEntities_Id;
       }

       public void setListFeedbackEntities_Id(List<Long> listFeedbackEntities_Id) {
              this.listFeedbackEntities_Id = listFeedbackEntities_Id;
       }

       public Boolean getIsDelete() {
              return isDelete;
       }

       public void setIsDelete(Boolean isDelete) {
              this.isDelete = isDelete;
       }

       public String getStatus() {
              return status;
       }

       public void setStatus(String status) {
              this.status = status;
       }

       public Long getBusRouteSchedule_Id() {
              return busRouteSchedule_Id;
       }

       public void setBusRouteSchedule_Id(Long busRouteSchedule_Id) {
              this.busRouteSchedule_Id = busRouteSchedule_Id;
       }

       public void setDepartureDate(LocalDate departureDate) {
              this.departureDate = departureDate;
       }

       public LocalDate getDepartureDate() {
              return departureDate;
       }

       

}
