package com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bus_station_ticket.project.ProjectDTO.TicketDTO;
import com.bus_station_ticket.project.ProjectEntity.FeedbackEntity;
import com.bus_station_ticket.project.ProjectEntity.TicketEntity;
import com.bus_station_ticket.project.ProjectRepository.AccountRepo;
import com.bus_station_ticket.project.ProjectRepository.BusRouteScheduleRepo;
import com.bus_station_ticket.project.ProjectRepository.DiscountRepo;
import com.bus_station_ticket.project.ProjectRepository.FeedbackRepo;
import com.bus_station_ticket.project.ProjectRepository.PaymentRepo;

@Component
public class TicketMapping implements MappingInterface<TicketEntity, TicketDTO> {

       @Autowired
       private AccountRepo accountRepo;

       @Autowired
       private BusRouteScheduleRepo busRouteScheduleRepo;

       @Autowired
       private PaymentRepo paymentRepo;

       @Autowired
       private DiscountRepo discountRepo;

       @Autowired
       private FeedbackRepo feedbackRepo;

       @Override
       public TicketDTO toDTO(TicketEntity entity) {

              TicketDTO ticketDTO = new TicketDTO();
              // Mapping cac thuoc tinh co ban
              ticketDTO.setTicketId(entity.getTicketId());
              ticketDTO.setAccountEnity_Id(
                            (entity.getAccountEntity() != null) ? entity.getAccountEntity().getUserName() : null);

              ticketDTO.setBusRouteSchedule_Id(
                            entity.getBusRouteSchedule() != null ? entity.getBusRouteSchedule().getScheduleId() : null);

              ticketDTO.setPaymentEntity_Id(
                            (entity.getPaymentEntity() != null) ? entity.getPaymentEntity().getPaymentId() : null);
              ticketDTO.setDiscountEntity_Id(
                            (entity.getDiscountEntity() != null) ? entity.getDiscountEntity().getDiscountId() : null);
              ticketDTO.setSeatNumber(entity.getSeatNumber());
              ticketDTO.setDepartureDate(entity.getDepartureDate());
              ticketDTO.setPrice(entity.getPrice());
              ticketDTO.setPhoneNumber(entity.getPhoneNumber());
              ticketDTO.setStatus(entity.getStatus());
              ticketDTO.setIsDelete(entity.getIsDelete());

              // Mapping cac thuoc tinh list
              List<Long> listFeedbackEntities_Id = new ArrayList<>();

              if (entity.getListFeedbackEntities() != null) {
                     for (FeedbackEntity e : entity.getListFeedbackEntities()) {
                            listFeedbackEntities_Id.add(e.getFeedbackId());
                     }
              }

              ticketDTO.setListFeedbackEntities_Id(listFeedbackEntities_Id);

              return ticketDTO;
       }

       @Override
       public TicketEntity toEntity(TicketDTO dto) {

              TicketEntity ticketEntity = new TicketEntity();
              // Mapping cac thuoc tinh co ban
              ticketEntity.setTicketId(dto.getTicketId());

              ticketEntity.setAccountEntity(this.accountRepo.findByUserName(dto.getAccountEnity_Id()).orElse(null));

              ticketEntity.setBusRouteSchedule(
                            this.busRouteScheduleRepo.findByScheduleId(dto.getBusRouteSchedule_Id()).orElse(null));

              ticketEntity.setPaymentEntity(this.paymentRepo.findByPaymentId(dto.getPaymentEntity_Id()).orElse(null));

              ticketEntity.setDiscountEntity(
                            this.discountRepo.findByDiscountId(dto.getDiscountEntity_Id()).orElse(null));

              ticketEntity.setSeatNumber(dto.getSeatNumber());

              ticketEntity.setDepartureDate(dto.getDepartureDate());

              ticketEntity.setPrice(dto.getPrice());

              ticketEntity.setPhoneNumber(dto.getPhoneNumber());

              ticketEntity.setStatus(dto.getStatus());

              ticketEntity.setIsDelete(dto.getIsDelete());

              // Mapping cac thuoc tinh List

              List<FeedbackEntity> listFeedbackEntities = new ArrayList<>();
              for (Long value : dto.getListFeedbackEntities_Id()) {

                     FeedbackEntity feedbackEntity = this.feedbackRepo.findByFeedbackId(value).orElse(null);

                     listFeedbackEntities.add(feedbackEntity);

              }
              ticketEntity.setListFeedbackEntities(listFeedbackEntities);

              return ticketEntity;
       }
}
