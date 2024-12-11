package com.bus_station_ticket.project.ProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bus_station_ticket.project.ProjectConfig.ResponseBoolAndMess;
import com.bus_station_ticket.project.ProjectDTO.PaymentDTO;
import com.bus_station_ticket.project.ProjectEntity.PaymentEntity;
import com.bus_station_ticket.project.ProjectEntity.TicketEntity;
import com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice.PaymentMapping;
import com.bus_station_ticket.project.ProjectRepository.PaymentRepo;
import com.bus_station_ticket.project.ProjectRepository.TicketRepo;

@Service
public class PaymentService implements SimpleServiceInf<PaymentEntity, PaymentDTO, Long> {

       @Autowired
       private PaymentRepo repo;

       @Autowired
       private TicketRepo ticketRepo;

       @Autowired
       private PaymentMapping paymentMapping;

       // Lấy một đối tượng PaymentEntity theo giá trị
       // Input: paymentId (Long)
       // Output: PaymentEntity có giá trị paymentId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public PaymentEntity getById(Long paymentId) {
              return this.repo.findByPaymentId(paymentId).orElse(null);
       }

       // Mapping đối tượng PaymentEntity --> PaymentDTO
       // Input: paymentId (Long)
       // Output: PaymentDTO có giá trị paymentId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public PaymentDTO getById_toDTO(Long paymentId) {
              PaymentEntity paymentEntity = this.repo.findByPaymentId(paymentId).orElse(null);

              if (paymentEntity != null) {
                     return this.paymentMapping.toDTO(paymentEntity);
              }
              return null;
       }

       // Lấy tất cả các đối tượng PaymentEntity
       // Input:
       // Output: List
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<PaymentEntity> getAll() {
              return this.repo.findAll();
       }

       // Mapping đối tượng List<PaymentEntity> --> List<PaymentDTO>
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<PaymentDTO> getAll_toDTO() {
              List<PaymentEntity> listPaymentEntities = this.repo.findAll();

              List<PaymentDTO> listPaymentDTOs = new ArrayList<>();

              if (listPaymentEntities.isEmpty() == false) {
                     for (PaymentEntity e : listPaymentEntities) {
                            listPaymentDTOs.add(this.paymentMapping.toDTO(e));
                     }
                     return listPaymentDTOs;
              }
              return listPaymentDTOs;
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess delete(Long id) {

              Optional<PaymentEntity> optional = this.repo.findByPaymentId(id);

              if (optional.isPresent()) {
                     Boolean check = foreignKeyViolationIfDelete(optional.get());

                     if (check == false) {
                            this.repo.delete(optional.get());
                            return new ResponseBoolAndMess(true, MESS_DELETE_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_DELETE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }
              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save(PaymentEntity entityObj) {

              // Optional<PaymentEntity> optional =
              // this.repo.findByPaymentId(entityObj.getPaymentId());

              if (isForeignKeyEmpty(entityObj) == false) {
                     entityObj.setPaymentId(null);
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_SAVE_SUCCESS);
              }
              return new ResponseBoolAndMess(false, MESS_SAVE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save_toDTO(PaymentDTO dtoObj) {
              PaymentEntity paymentEntity = this.paymentMapping.toEntity(dtoObj);

              return save(paymentEntity);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update(PaymentEntity entityObj) {

              Optional<PaymentEntity> optional = this.repo.findByPaymentId(entityObj.getPaymentId());

              if (optional.isPresent() && isForeignKeyEmpty(entityObj) == false
                            && foreignKeyViolationIfHidden(entityObj) == false) {

                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_UPDATE_SUCCESS);
              }

              return new ResponseBoolAndMess(false,
                            MESS_UPDATE_FAILURE + "," + MESS_OBJECT_NOT_EXIST + " or " + MESS_FOREIGN_KEY_VIOLATION);

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update_toDTO(PaymentDTO dtoObj) {
              PaymentEntity paymentEntity = this.paymentMapping.toEntity(dtoObj);

              return update(paymentEntity);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess invisibleWithoutDelete(Long id) {

              Optional<PaymentEntity> optional = this.repo.findByPaymentId(id);

              if (optional.isPresent()) {
                     Boolean check = foreignKeyViolationIfHidden(optional.get());

                     if (check == false) {
                            PaymentEntity paymentEntity = optional.get();
                            paymentEntity.setIsDelete(true);
                            this.repo.save(paymentEntity);
                            return new ResponseBoolAndMess(true, MESS_HIDDEN_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_HIDDEN_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }
              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfDelete(PaymentEntity entityObj) {

              // Payment foreign key Ticket
              List<TicketEntity> ticketEntities = this.ticketRepo.findByPaymentEntity_Id(entityObj.getPaymentId());

              if (ticketEntities.isEmpty() == false) {
                     return true;
              }
              return false;
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfHidden(PaymentEntity entityObj) {

              if (entityObj.getIsDelete()) {
                     // Payment foreign key Ticket
                     List<TicketEntity> ticketEntities = this.ticketRepo
                                   .findByPaymentEntity_Id(entityObj.getPaymentId());

                     if (ticketEntities.isEmpty() == false) {
                            for (TicketEntity e : ticketEntities) {
                                   if (e.getIsDelete() == false) {
                                          return true;
                                   }
                            }
                            return false;
                     }
                     return false;
              }
              return false;

       }

       @Transactional
       @Override
       public Boolean isForeignKeyEmpty(PaymentEntity entityObj) {
              // payment khong co thuoc tinh khoa ngoai
              return false;
       }

       // @Transactional
       // @Override
       // public Boolean isHasForeignKeyEntity(PaymentDTO dtoObj) {
       // // payment khong co thuoc tinh khoa ngoai
       // return true;
       // }

}
