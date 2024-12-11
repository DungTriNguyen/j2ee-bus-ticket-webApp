package com.bus_station_ticket.project.ProjectService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bus_station_ticket.project.ProjectConfig.ResponseBoolAndMess;
import com.bus_station_ticket.project.ProjectConfig.ResponseObject;
import com.bus_station_ticket.project.ProjectDTO.BusRouteScheduleDTO;
import com.bus_station_ticket.project.ProjectEntity.BusEntity;
import com.bus_station_ticket.project.ProjectEntity.BusRouteScheduleEntity;
import com.bus_station_ticket.project.ProjectEntity.BusRoutesEntity;
import com.bus_station_ticket.project.ProjectEntity.TicketEntity;
import com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice.BusRouteScheduleMapping;
import com.bus_station_ticket.project.ProjectRepository.BusRouteScheduleRepo;
import com.bus_station_ticket.project.ProjectRepository.BusRoutesRepo;
import com.bus_station_ticket.project.ProjectRepository.TicketRepo;

@Service
public class BusRouteScheduleService implements SimpleServiceInf<BusRouteScheduleEntity, BusRouteScheduleDTO, Long> {

       @Autowired
       private BusRouteScheduleRepo repo;

       @Autowired
       private BusRoutesRepo busRoutesRepo;

       @Autowired
       private BusRouteScheduleMapping busRouteScheduleMapping;

       @Autowired
       private TicketRepo ticketRepo;

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess delete(Long id) {

              Optional<BusRouteScheduleEntity> optional = this.repo.findByScheduleId(id);

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

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfDelete(BusRouteScheduleEntity entityObj) {
              // Bus schedule co khoa ticket tham chiu
              List<TicketEntity> listTicketEntities = this.ticketRepo
                            .findByBusRouteScheduleEntity_Id(entityObj.getScheduleId());

              // kiem tra
              if (listTicketEntities.isEmpty() == true) {
                     return false;
              }

              return true;
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfHidden(BusRouteScheduleEntity entityObj) {
              // Bus schedule co khoa ticket tham chiu
              List<TicketEntity> listTicketEntities = this.ticketRepo
                            .findByBusRouteScheduleEntity_Id(entityObj.getScheduleId());

              // kiem tra
              if (listTicketEntities.isEmpty() == true) {
                     return false;
              }

              for (TicketEntity ticketEntity : listTicketEntities) {
                     if (ticketEntity.getIsDelete() == false) {
                            return true;
                     }
              }

              return false;
       }

       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<BusRouteScheduleEntity> getAll() {

              return this.repo.findAll();
       }

       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<BusRouteScheduleDTO> getAll_toDTO() {

              List<BusRouteScheduleEntity> listBusRouteScheduleEntities = this.repo.findAll();

              List<BusRouteScheduleDTO> listBusRouteScheduleDTOs = new ArrayList<>();

              if (listBusRouteScheduleEntities.isEmpty() == false) {
                     for (BusRouteScheduleEntity busRouteScheduleEntity : listBusRouteScheduleEntities) {
                            listBusRouteScheduleDTOs.add(this.busRouteScheduleMapping.toDTO(busRouteScheduleEntity));
                     }
                     return listBusRouteScheduleDTOs;
              }

              return listBusRouteScheduleDTOs;
       }

       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public BusRouteScheduleEntity getById(Long id) {
              return this.repo.findByScheduleId(id).orElse(null);
       }

       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public BusRouteScheduleDTO getById_toDTO(Long id) {

              BusRouteScheduleEntity busRouteScheduleEntity = this.repo.findByScheduleId(id).orElse(null);

              if (busRouteScheduleEntity != null) {
                     return this.busRouteScheduleMapping.toDTO(busRouteScheduleEntity);
              }
              return null;
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess invisibleWithoutDelete(Long id) {

              Optional<BusRouteScheduleEntity> optional = this.repo.findByScheduleId(id);

              if (optional.isPresent()) {
                     // kiem tra
                     Boolean check = foreignKeyViolationIfHidden(optional.get());

                     if (check == false) {
                            BusRouteScheduleEntity busRouteScheduleEntity = optional.get();
                            busRouteScheduleEntity.setIsDelete(true);

                            this.repo.save(busRouteScheduleEntity);

                            this.repo.save(busRouteScheduleEntity);
                            return new ResponseBoolAndMess(true, MESS_HIDDEN_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_HIDDEN_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }

              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional
       @Override
       public Boolean isForeignKeyEmpty(BusRouteScheduleEntity entityObj) {

              // co 2 thuoc tinh khoa ngoai la bus va routes
              BusEntity busEntity = entityObj.getBusEntity();
              BusRoutesEntity busRoutesEntity = entityObj.getBusRoutesEntity();

              if (busEntity != null && busRoutesEntity != null) {
                     return false;
              }
              return true;
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       public ResponseBoolAndMess save(BusRouteScheduleEntity entityObj) {

              entityObj.setScheduleId(-1l);
              if (isForeignKeyEmpty(entityObj) == false && isDuplicateSchedule(entityObj) == false) {
                     entityObj.setScheduleId(null);
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_SAVE_SUCCESS);
              }

              return new ResponseBoolAndMess(false, MESS_SAVE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION + " or "
                            + " Matching schedule");
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save_toDTO(BusRouteScheduleDTO dtoObj) {
              BusRouteScheduleEntity busRouteScheduleEntity = this.busRouteScheduleMapping.toEntity(dtoObj);
              return save(busRouteScheduleEntity);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update(BusRouteScheduleEntity entityObj) {

              Optional<BusRouteScheduleEntity> optional = this.repo.findByScheduleId(entityObj.getScheduleId());

              if (optional.isPresent() && isForeignKeyEmpty(entityObj) == false
                            && isDuplicateSchedule(entityObj) == false) {
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_UPDATE_SUCCESS);
              }

              return new ResponseBoolAndMess(false,
                            MESS_UPDATE_FAILURE + "," + MESS_OBJECT_NOT_EXIST + " or " + MESS_FOREIGN_KEY_VIOLATION
                                          + " or " + " Matching schedule");
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update_toDTO(BusRouteScheduleDTO dtoObj) {
              BusRouteScheduleEntity busRouteScheduleEntity = this.busRouteScheduleMapping.toEntity(dtoObj);
              return update(busRouteScheduleEntity);
       }

       // kiểm tra xem thông tin các thuộc tính schedule có bị trùng không
       @Transactional
       public Boolean isDuplicateSchedule(BusRouteScheduleEntity entityObj) {
              // Lấy danh sách tất cả các thực thể từ repo
              List<BusRouteScheduleEntity> list = this.repo.findAll();

              // Nếu danh sách không rỗng, kiểm tra trùng lặp
              if (!list.isEmpty()) {
                     for (BusRouteScheduleEntity e : list) {
                            // Bỏ qua chính thực thể đang được cập nhật (cùng id)
                            if (!e.getScheduleId().equals(entityObj.getScheduleId())) {
                                   // Kiểm tra các thuộc tính khác có trùng khớp hay không
                                   if (e.getBusEntity().equals(entityObj.getBusEntity()) &&
                                                 e.getBusRoutesEntity().equals(entityObj.getBusRoutesEntity()) &&
                                                 e.getDepartureTime().equals(entityObj.getDepartureTime()) &&
                                                 e.getDayOfWeek().equals(entityObj.getDayOfWeek())) {
                                          return true; // Trùng lặp
                                   }
                            }
                     }
              }
              return false; // Không trùng lặp
       }

       // Hàm lọc các schedule dựa theo điểm đi và điểm đến
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public ResponseObject getScheduleByDepartureLocationAndDestinationLocation(String departureLocation,
                     String destinationLocation) {

              ResponseObject responseObject = new ResponseObject();

              // Lấy thông tin route dựa vào điểm đến điểm đi
              BusRoutesEntity busRoutesEntity = this.busRoutesRepo
                            .findByDepartureLocationAndDestinationLocation(departureLocation, destinationLocation)
                            .orElse(null);

              if (busRoutesEntity == null) {
                     responseObject.setStatus("failure");
                     responseObject.addMessage("mess",
                                   "Not found routes between departureLocation and destinationLocation");
                     Map<String, Object> datas = new HashMap<>();
                     datas.put("departureLocation", departureLocation);
                     datas.put("destinationLocation", destinationLocation);
                     responseObject.setData(datas);

                     return responseObject;
              }

              Long routeId = busRoutesEntity.getRoutesId();
              float distanceKm = busRoutesEntity.getDistanceKilometer();
              LocalTime tripTime = busRoutesEntity.getTripTime();
              float price = busRoutesEntity.getPrice();

              // Lấy danh sách các schedule dựa theo routeId
              List<BusRouteScheduleEntity> listBusRouteScheduleEntities = this.repo.findByBusRoutesEntity_Id(routeId);

              if (listBusRouteScheduleEntities.isEmpty()) {
                     responseObject.setStatus("failure");
                     responseObject.addMessage("mess", "Not found schedule have routeId " + routeId);
                     responseObject.setData(null);

                     return responseObject;
              }

              List<Object> dataSchedules = new ArrayList<>();

              for (BusRouteScheduleEntity e : listBusRouteScheduleEntities) {

                     // Lấy các thông tin
                     Long scheduleId = e.getScheduleId();
                     Long busId = e.getBusEntity().getBusId();
                     String dayOfWeek = e.getDayOfWeek();
                     LocalTime departureTime = e.getDepartureTime();

                     // Lấy danh sách các n ngày departureDate thuộc dayOfWeek
                     List<LocalDate> listDeparturDates = getNextNDays(dayOfWeek, 5);

                     for (LocalDate day : listDeparturDates) {

                            Map<String, Object> scheduleForm = formScheduleInfo(scheduleId, busId, routeId,
                                          departureLocation, destinationLocation, dayOfWeek, day, departureTime,
                                          tripTime, distanceKm, price);

                            dataSchedules.add(scheduleForm);
                     }
              }

              responseObject.setStatus("success");
              responseObject.addMessage("mess", "All schedule information");
              responseObject.setData(dataSchedules);

              return responseObject;
       }

       @Transactional
       public Map<String, Object> formScheduleInfo(Long scheduleId, Long busId, Long routeId, String departureLocation,
                     String destinationLocation, String dayOfWeek, LocalDate departurnDate, LocalTime departurnTime,
                     LocalTime tripTime, float distanceKm, float price) {

              Map<String, Object> data = new HashMap<>();

              data.put("scheduleId", scheduleId);
              data.put("busId", busId);
              data.put("routeId", routeId);
              data.put("departureLocation", departureLocation);
              data.put("destinationLocation", destinationLocation);
              data.put("dayOfWeek", dayOfWeek);
              data.put("departurnDate", departurnDate);
              data.put("departurnTime", departurnTime);
              data.put("tripTime", tripTime);
              data.put("destinationTime", addLocalTimes(departurnTime, tripTime));
              data.put("disdistanceKm", distanceKm);
              data.put("price", price);

              return data;
       }

       // Hàm cộng thời gian
       @Transactional
       public LocalTime addLocalTimes(LocalTime time1, LocalTime time2) {
              // Lấy các thành phần giờ, phút, giây từ `time2`
              int hours = time2.getHour();
              int minutes = time2.getMinute();
              int seconds = time2.getSecond();
              int nanos = time2.getNano();

              // Cộng vào `time1`
              LocalTime result = time1
                            .plusHours(hours)
                            .plusMinutes(minutes)
                            .plusSeconds(seconds)
                            .plusNanos(nanos);

              return result;
       }

       // Hàm sẽ nhận đầu vào là một ngày trong tuần (dayOfWeek) và số nguyên n, sau đó
       // trả về danh sách n ngày tiếp theo rơi vào ngày trong tuần đó
       @Transactional
       public List<LocalDate> getNextNDays(String dayOfWeek, int n) {
              List<LocalDate> result = new ArrayList<>();

              // Lấy ngày hiện tại
              LocalDate today = LocalDate.now();

              // Chuyển đổi `dayOfWeek` thành DayOfWeek enum
              DayOfWeek targetDayOfWeek;
              try {
                     targetDayOfWeek = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
              } catch (IllegalArgumentException e) {
                     throw new IllegalArgumentException("Invalid day of week: " + dayOfWeek);
              }

              // Tìm ngày đầu tiên khớp với `dayOfWeek` lớn hơn ngày hôm nay
              int daysUntilTarget = targetDayOfWeek.getValue() - today.getDayOfWeek().getValue();
              if (daysUntilTarget <= 0) {
                     daysUntilTarget += 7; // Nếu ngày đã qua hoặc là hôm nay, nhảy tới tuần sau
              }

              LocalDate nextTargetDate = today.plusDays(daysUntilTarget);

              // Lấy `n` ngày kế tiếp
              for (int i = 0; i < n; i++) {
                     result.add(nextTargetDate);
                     nextTargetDate = nextTargetDate.plusWeeks(1); // Thêm 1 tuần
              }

              return result;
       }

}
