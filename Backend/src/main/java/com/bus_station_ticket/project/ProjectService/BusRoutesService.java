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
import com.bus_station_ticket.project.ProjectDTO.BusRoutesDTO;
import com.bus_station_ticket.project.ProjectEntity.BusRouteScheduleEntity;
import com.bus_station_ticket.project.ProjectEntity.BusRoutesEntity;
import com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice.BusRoutesMapping;
import com.bus_station_ticket.project.ProjectRepository.BusRouteScheduleRepo;
import com.bus_station_ticket.project.ProjectRepository.BusRoutesRepo;

@Service
public class BusRoutesService implements SimpleServiceInf<BusRoutesEntity, BusRoutesDTO, Long> {

       @Autowired
       private BusRoutesRepo repo;

       // @Autowired
       // private TicketRepo ticketRepo;

       @Autowired
       private BusRouteScheduleRepo busRouteScheduleRepo;

       // @Autowired
       // private BusRepo busRepo;

       @Autowired
       private BusRoutesMapping busRoutesMapping;

       // Lấy một đối tượng BusRoutesEntity theo giá trị routeId
       // Input: routesId (Long)
       // Output: BusRoutesEntity có giá trị routesId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public BusRoutesEntity getById(Long routesId) {

              return this.repo.findByRoutesId(routesId).orElse(null);

       }

       // Mapping đối tượng BusRoutesEntity --> BusRoutesDTO
       // Input: routesId (Long)
       // Output: BusRoutesDTO có giá trị routesId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public BusRoutesDTO getById_toDTO(Long routesId) {

              BusRoutesEntity busRoutesEntity = this.repo.findByRoutesId(routesId).orElse(null);

              if (busRoutesEntity != null) {
                     return this.busRoutesMapping.toDTO(busRoutesEntity);
              }
              return null;
       }

       // Lấy tất cả các đối tượng BusRoutesEntity
       // Input:
       // Output: List
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<BusRoutesEntity> getAll() {
              return this.repo.findAll();
       }

       // Mapping đối tượng List<BusRoutesEntity> --> List<BusRoutesDTO>
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<BusRoutesDTO> getAll_toDTO() {
              List<BusRoutesEntity> listBusRoutesEntities = this.repo.findAll();

              List<BusRoutesDTO> listBusRoutesDTOs = new ArrayList<>();

              if (listBusRoutesEntities.isEmpty() == false) {
                     for (BusRoutesEntity e : listBusRoutesEntities) {
                            listBusRoutesDTOs.add(busRoutesMapping.toDTO(e));
                     }
                     return listBusRoutesDTOs;
              }
              return listBusRoutesDTOs;
       }

       // Thêm một đối tượng BusRoutesEntity vào database
       // Input: BusRoutesEntity (object)
       // Output: ResponseBoolAndMess
       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save(BusRoutesEntity busRoutesEntity) {

              // // Kiểm tra
              // Optional<BusRoutesEntity> optional =
              // this.repo.findByRoutesId(busRoutesEntity.getRoutesId());

              busRoutesEntity.setRoutesId(-1l);
              if (isForeignKeyEmpty(busRoutesEntity) == false && isDuplicateLocations(busRoutesEntity) == false) {
                     busRoutesEntity.setRoutesId(null);
                     this.repo.save(busRoutesEntity);
                     return new ResponseBoolAndMess(true, MESS_SAVE_SUCCESS);
              }
              return new ResponseBoolAndMess(false, MESS_SAVE_FAILURE + " or " + MESS_FOREIGN_KEY_VIOLATION + " or "
                            + " Same arrival and departure points");
       }

       // Thêm một đối tượng BusRoutesEntity vào database
       // Input: BusRoutesDTO (object)
       // Output: ResponseBoolAndMess
       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save_toDTO(BusRoutesDTO busRoutesDTO) {

              // mapping
              BusRoutesEntity busRoutesEntity2 = this.busRoutesMapping.toEntity(busRoutesDTO);

              return save(busRoutesEntity2);

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess delete(Long id) {

              // kiem tra
              Optional<BusRoutesEntity> optional = this.repo.findByRoutesId(id);

              // neu ket qua co
              if (optional.isPresent()) {
                     // kierm tra khoa ngoai truoc khi xoa
                     Boolean check = foreignKeyViolationIfDelete(optional.get());

                     if (check == false) {
                            // xoa
                            this.repo.delete(optional.get());
                            return new ResponseBoolAndMess(true, MESS_DELETE_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_DELETE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }
              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update(BusRoutesEntity entityObj) {

              // Kiem tra
              Optional<BusRoutesEntity> optional = this.repo.findByRoutesId(entityObj.getRoutesId());

              if (optional.isPresent() && isForeignKeyEmpty(entityObj) == false
                            && isDuplicateLocations(entityObj) == false
                            && foreignKeyViolationIfHidden(entityObj) == false) {

                     // entityObj.setRoutesId(null);
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_UPDATE_SUCCESS);
              }

              return new ResponseBoolAndMess(false, MESS_UPDATE_FAILURE + "," + MESS_OBJECT_NOT_EXIST + " or "
                            + MESS_FOREIGN_KEY_VIOLATION + " or " + " Same arrival and departure points");
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update_toDTO(BusRoutesDTO dtoObj) {

              BusRoutesEntity busRoutesEntity = this.busRoutesMapping.toEntity(dtoObj);

              return update(busRoutesEntity);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess invisibleWithoutDelete(Long id) {

              Optional<BusRoutesEntity> optional = this.repo.findByRoutesId(id);

              if (optional.isPresent()) {
                     // kiem tra khoa ngoai
                     Boolean check = foreignKeyViolationIfHidden(optional.get());

                     if (check == false) {
                            BusRoutesEntity busRoutesEntity = optional.get();
                            busRoutesEntity.setIsDelete(true);
                            this.repo.save(busRoutesEntity);

                            return new ResponseBoolAndMess(true, MESS_HIDDEN_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_HIDDEN_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }
              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfDelete(BusRoutesEntity entityObj) {

              // BusRoutes foreign key bus

              // lay danh sach bus tham chieu den BusRoutes duoc xoa
              List<BusRouteScheduleEntity> listBusRouteScheduleEntities = this.busRouteScheduleRepo.findByBusRoutesEntity_Id(entityObj.getRoutesId());

              // kiem tra
              // neu co thuc the
              if (listBusRouteScheduleEntities.isEmpty() == false) {
                     return true;
              }

              return false;
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfHidden(BusRoutesEntity entityObj) {
              // BusRoutes foriegn key bus
              if (entityObj.getIsDelete()) {
                     // lay danh sach bus tham chieu den BusRoutes duoc xoa
                     List<BusRouteScheduleEntity> listBusRouteScheduleEntities = this.busRouteScheduleRepo.findByBusRoutesEntity_Id(entityObj.getRoutesId());

                     // kiem tra
                     // neu co thuc the
                     if (listBusRouteScheduleEntities.isEmpty() == false) {
                            for (BusRouteScheduleEntity e : listBusRouteScheduleEntities) {
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
       public Boolean isForeignKeyEmpty(BusRoutesEntity entityObj) {
              // BusRouter khong co thuoc tinh khoa ngoai
              return false;
       }

       // @Override
       // public Boolean isHasForeignKeyEntity(BusRoutesDTO dtoObj) {
       // // BusRouter khong co thuoc tinh khoa ngoai
       // return true;
       // }

       // kiem tra xem co trung diem denn diem di khong
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public Boolean isDuplicateLocations(BusRoutesEntity busRoutesEntity) {

              // Tìm tuyến có điểm đi và điểm đến giống trong database
              Optional<BusRoutesEntity> existingBusRoutesEntity = this.repo
                            .findByDepartureLocationAndDestinationLocation(
                                          busRoutesEntity.getDepartureLocation(),
                                          busRoutesEntity.getDestinationLocation());

              // Nếu không tìm thấy tuyến nào, không trùng
              if (existingBusRoutesEntity.isEmpty()) {
                     return false;
              }

              // Nếu tìm thấy tuyến nhưng là chính nó (cùng ID), không trùng
              if (existingBusRoutesEntity.get().getRoutesId().equals(busRoutesEntity.getRoutesId())) {
                     return false;
              }

              // Nếu tìm thấy tuyến khác với cùng điểm đi và điểm đến, trùng
              return true;

       }

       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public BusRoutesDTO getByDepartureLocationAndDestinationLocation(String departureLocation,
                     String destinationLocation) {
              BusRoutesEntity busRoutesEntity = this.repo
                            .findByDepartureLocationAndDestinationLocation(departureLocation, destinationLocation)
                            .orElse(null);

              if (busRoutesEntity != null) {
                     return this.busRoutesMapping.toDTO(busRoutesEntity);
              }
              return null;
       }
}
