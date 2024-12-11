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
import com.bus_station_ticket.project.ProjectDTO.DiscountDTO;
import com.bus_station_ticket.project.ProjectEntity.DiscountEntity;
import com.bus_station_ticket.project.ProjectEntity.TicketEntity;
import com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice.DiscountMapping;
import com.bus_station_ticket.project.ProjectRepository.DiscountRepo;
import com.bus_station_ticket.project.ProjectRepository.TicketRepo;

@Service
public class DiscountService implements SimpleServiceInf<DiscountEntity, DiscountDTO, Long> {

       @Autowired
       private DiscountRepo repo;

       @Autowired
       private TicketRepo ticketRepo;

       @Autowired
       private DiscountMapping discountMapping;

       // Lấy một đối tượng DiscountEntity theo giá trị discountId
       // Input: discountId (Long)
       // Output: DiscountEntity có giá trị discountId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public DiscountEntity getById(Long discountId) {
              return this.repo.findByDiscountId(discountId).orElse(null);
       }

       // Mapping đối tượng DiscountEntity --> DiscountDTO
       // Input: discountId (Long)
       // Output: DiscountDTO có giá trị discountId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public DiscountDTO getById_toDTO(Long discountId) {
              DiscountEntity discountEntity = this.repo.findByDiscountId(discountId).orElse(null);

              if (discountEntity != null) {
                     return this.discountMapping.toDTO(discountEntity);

              }
              return null;
       }

       // Lấy tất cả các đối tượng DiscountEntity
       // Input:
       // Output: List
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<DiscountEntity> getAll() {
              return this.repo.findAll();
       }

       // Mapping đối tượng List<DiscountEntity> --> List<DiscountDTO>
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<DiscountDTO> getAll_toDTO() {
              List<DiscountEntity> listDiscountEntities = this.repo.findAll();

              List<DiscountDTO> listDiscountDTOs = new ArrayList<>();

              if (listDiscountEntities.isEmpty() == false) {
                     for (DiscountEntity e : listDiscountEntities) {
                            listDiscountDTOs.add(this.discountMapping.toDTO(e));
                     }
                     return listDiscountDTOs;
              }
              return listDiscountDTOs;
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess delete(Long id) {

              Optional<DiscountEntity> optional = this.repo.findByDiscountId(id);

              if (optional.isPresent()) {
                     // Kiem tra khoa ngoai
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
       public ResponseBoolAndMess save(DiscountEntity entityObj) {
              // Optional<DiscountEntity> optional =
              // this.repo.findByDiscountId(entityObj.getDiscountId());

              if (isForeignKeyEmpty(entityObj) == false) {
                     entityObj.setDiscountId(null);
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_SAVE_SUCCESS);
              }
              return new ResponseBoolAndMess(false, MESS_SAVE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save_toDTO(DiscountDTO dtoObj) {
              DiscountEntity discountEntity = this.discountMapping.toEntity(dtoObj);

              return save(discountEntity);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update(DiscountEntity entityObj) {

              Optional<DiscountEntity> optional = this.repo.findByDiscountId(entityObj.getDiscountId());

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
       public ResponseBoolAndMess update_toDTO(DiscountDTO dtoObj) {

              DiscountEntity discountEntity = this.discountMapping.toEntity(dtoObj);

              return update(discountEntity);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess invisibleWithoutDelete(Long id) {

              Optional<DiscountEntity> optional = this.repo.findByDiscountId(id);

              if (optional.isPresent()) {
                     // kierm tra
                     Boolean check = foreignKeyViolationIfHidden(optional.get());

                     if (check == false) {
                            DiscountEntity discountEntity = optional.get();
                            discountEntity.setIsDelete(true);
                            this.repo.save(discountEntity);
                            return new ResponseBoolAndMess(true, MESS_HIDDEN_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_HIDDEN_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }

              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfDelete(DiscountEntity entityObj) {

              // Discount foreign key ticket
              List<TicketEntity> ticketEntities = this.ticketRepo.findByDiscountEntity_Id(entityObj.getDiscountId());

              if (ticketEntities.isEmpty() == false) {
                     return true;
              }
              return false;
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfHidden(DiscountEntity entityObj) {

              if (entityObj.getIsDelete()) {
                     // Discount foreign key ticket
                     List<TicketEntity> ticketEntities = this.ticketRepo
                                   .findByDiscountEntity_Id(entityObj.getDiscountId());

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
       public Boolean isForeignKeyEmpty(DiscountEntity entityObj) {
              // Discount khong co thuoc tinh khoa ngoai
              return false;
       }

       // @Override
       // public Boolean isHasForeignKeyEntity(DiscountDTO dtoObj) {
       // // Discount khong co thuoc tinh khoa ngoai
       // return true;
       // }

}
