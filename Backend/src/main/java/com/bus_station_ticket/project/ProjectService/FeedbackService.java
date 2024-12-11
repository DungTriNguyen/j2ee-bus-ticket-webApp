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
import com.bus_station_ticket.project.ProjectDTO.FeedbackDTO;
import com.bus_station_ticket.project.ProjectEntity.AccountEntity;
import com.bus_station_ticket.project.ProjectEntity.FeedbackEntity;
import com.bus_station_ticket.project.ProjectEntity.TicketEntity;
import com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice.FeedbackMapping;
import com.bus_station_ticket.project.ProjectRepository.FeedbackRepo;

@Service
public class FeedbackService implements SimpleServiceInf<FeedbackEntity, FeedbackDTO, Long> {

       @Autowired
       private FeedbackRepo repo;

       @Autowired
       private FeedbackMapping feedbackMapping;

       // Lấy một đối tượng FeedbackEntity theo giá trị
       // Input: feedbackId (Long)
       // Output: FeedbackEntity có giá trị feedbackId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public FeedbackEntity getById(Long feedbackId) {
              return this.repo.findByFeedbackId(feedbackId).orElse(null);

       }

       // Mapping đối tượng FeedbackEntity --> FeedbackDTO
       // Input: feedbackId (Long)
       // Output: FeedbackDTO có giá trị feedbackId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public FeedbackDTO getById_toDTO(Long feedbackId) {
              FeedbackEntity feedbackEntity = this.repo.findByFeedbackId(feedbackId).orElse(null);

              if (feedbackEntity != null) {
                     return this.feedbackMapping.toDTO(feedbackEntity);
              }
              return null;
       }

       // Lấy tất cả các đối tượng FeedbackEntity
       // Input:
       // Output: List
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<FeedbackEntity> getAll() {
              return this.repo.findAll();
       }

       // Mapping đối tượng List<FeedbackEntity> --> List<FeedbackDTO>
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<FeedbackDTO> getAll_toDTO() {

              List<FeedbackEntity> listFeedbackEntities = this.repo.findAll();

              List<FeedbackDTO> listFeedbackDTOs = new ArrayList<>();

              if (listFeedbackEntities.isEmpty() == false) {
                     for (FeedbackEntity e : listFeedbackEntities) {
                            listFeedbackDTOs.add(this.feedbackMapping.toDTO(e));
                     }
                     return listFeedbackDTOs;
              }
              return listFeedbackDTOs;
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess delete(Long id) {

              Optional<FeedbackEntity> optional = this.repo.findByFeedbackId(id);

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
       public ResponseBoolAndMess save(FeedbackEntity entityObj) {

              // Optional<FeedbackEntity> optional =
              // this.repo.findByFeedbackId(entityObj.getFeedbackId());

              if (isForeignKeyEmpty(entityObj) == false) {
                     entityObj.setFeedbackId(null);
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_SAVE_SUCCESS);
              }
              return new ResponseBoolAndMess(false, MESS_SAVE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save_toDTO(FeedbackDTO dtoObj) {

              /// kiem tra gia tri thuoc tinh khoa ngoai

              FeedbackEntity feedbackEntity = this.feedbackMapping.toEntity(dtoObj);

              return save(feedbackEntity);

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update(FeedbackEntity entityObj) {
              Optional<FeedbackEntity> optional = this.repo.findByFeedbackId(entityObj.getFeedbackId());

              if (optional.isPresent() && isForeignKeyEmpty(entityObj) == false && foreignKeyViolationIfHidden(entityObj) == false) {
                     
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_UPDATE_SUCCESS);
              }

              return new ResponseBoolAndMess(false,
                            MESS_UPDATE_FAILURE + "," + MESS_OBJECT_NOT_EXIST + " or " + MESS_FOREIGN_KEY_VIOLATION);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update_toDTO(FeedbackDTO dtoObj) {

              /// kiem tra gia tri thuoc tinh khoa ngoai

              FeedbackEntity feedbackEntity = this.feedbackMapping.toEntity(dtoObj);

              return update(feedbackEntity);

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess invisibleWithoutDelete(Long id) {

              Optional<FeedbackEntity> optional = this.repo.findByFeedbackId(id);

              if (optional.isPresent()) {
                     Boolean check = foreignKeyViolationIfHidden(optional.get());

                     if (check == false) {
                            FeedbackEntity feedbackEntity = optional.get();

                            feedbackEntity.setIsDelete(true);
                            this.repo.save(feedbackEntity);
                            return new ResponseBoolAndMess(true, MESS_HIDDEN_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_HIDDEN_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }

              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfDelete(FeedbackEntity entityObj) {

              // Feedback khong co tham chiu khoa ngoai
              return false;
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfHidden(FeedbackEntity entityObj) {
              return false;
       }

       @Transactional
       @Override
       public Boolean isForeignKeyEmpty(FeedbackEntity entityObj) {
              // Feedback co thuoc tinh khoa ngoai la username va ticketId
              // kiem tra
              AccountEntity accountEntity = entityObj.getAccountEntity();
              TicketEntity ticketEntity = entityObj.getTicketEntity();

              if (accountEntity != null && ticketEntity != null) {
                     return false;
              }

              return true;
       }

       // @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation
       // = Isolation.READ_COMMITTED)
       // @Override
       // public Boolean isHasForeignKeyEntity(FeedbackDTO dtoObj) {
       // // Feedback co thuoc tinh khoa ngoai la username va ticketId
       // // kiem tra

       // AccountEntity accountEntity =
       // this.accountRepo.findByUserName(dtoObj.getAccountEnity_userName())
       // .orElse(null);
       // TicketEntity ticketEntity =
       // this.ticketRepo.findByTicketId(dtoObj.getTicketEntity_Id()).orElse(null);

       // if (accountEntity != null && ticketEntity != null) {
       // return true;
       // }
       // return false;
       // }

}
