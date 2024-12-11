package com.bus_station_ticket.project.ProjectService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.bus_station_ticket.project.ProjectDTO.AccountDTO;
import com.bus_station_ticket.project.ProjectDTO.TicketDTO;
import com.bus_station_ticket.project.ProjectEntity.AccountEntity;
import com.bus_station_ticket.project.ProjectEntity.BusRouteScheduleEntity;
import com.bus_station_ticket.project.ProjectEntity.BusRoutesEntity;
import com.bus_station_ticket.project.ProjectEntity.DiscountEntity;
import com.bus_station_ticket.project.ProjectEntity.FeedbackEntity;
import com.bus_station_ticket.project.ProjectEntity.PaymentEntity;
import com.bus_station_ticket.project.ProjectEntity.TicketEntity;
import com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice.TicketMapping;
import com.bus_station_ticket.project.ProjectRepository.BusRouteScheduleRepo;
import com.bus_station_ticket.project.ProjectRepository.DiscountRepo;
import com.bus_station_ticket.project.ProjectRepository.FeedbackRepo;
import com.bus_station_ticket.project.ProjectRepository.PaymentRepo;
import com.bus_station_ticket.project.ProjectRepository.TicketRepo;
import com.bus_station_ticket.project.VNPay.VNPayService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TicketService implements SimpleServiceInf<TicketEntity, TicketDTO, Long> {

       @Autowired
       private TicketRepo repo;

       @Autowired
       private FeedbackRepo feedbackRepo;

       @Autowired
       private PaymentService paymentService;

       @Autowired
       private PaymentRepo paymentRepo;

       @Autowired
       private DiscountRepo discountRepo;

       @Autowired
       private BusRouteScheduleRepo busRouteScheduleRepo;

       @Autowired
       private BusRouteScheduleService busRouteScheduleService;

       @Autowired
       private BusService busService;

       @Autowired
       private AccountService accountService;

       @Autowired
       private TicketMapping ticketMapping;

       @Autowired
       private VNPayService vnPayService;

       // Lấy một đối tượng TicketEntity theo giá trị
       // Input: ticketId (Long)
       // Output: TicketEntity có giá trị ticketId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public TicketEntity getById(Long ticketId) {
              return this.repo.findByTicketId(ticketId).orElse(null);
       }

       // Mapping đối tượng TicketEntity --> TicketDTO
       // Input: ticketId (Long)
       // Output: TicketDTO có giá trị ticketId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public TicketDTO getById_toDTO(Long ticketId) {
              TicketEntity ticketEntity = this.repo.findByTicketId(ticketId).orElse(null);

              if (ticketEntity != null) {
                     return this.ticketMapping.toDTO(ticketEntity);
              }
              return null;
       }

       // Lấy tất cả các đối tượng TicketEntity
       // Input:
       // Output: List
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<TicketEntity> getAll() {
              return this.repo.findAll();
       }

       // Mapping đối tượng List<TicketEntity> --> List<TicketDTO>
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<TicketDTO> getAll_toDTO() {
              List<TicketEntity> listTicketEntities = this.repo.findAll();

              List<TicketDTO> listTicketDTOs = new ArrayList<>();

              if (listTicketEntities.isEmpty() == false) {
                     for (TicketEntity e : listTicketEntities) {
                            listTicketDTOs.add(this.ticketMapping.toDTO(e));
                     }
                     return listTicketDTOs;
              }
              return listTicketDTOs;
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess delete(Long id) {

              Optional<TicketEntity> optional = this.repo.findByTicketId(id);

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
       public ResponseBoolAndMess save(TicketEntity entityObj) {

              // Optional<TicketEntity> optional =
              // this.repo.findByTicketId(entityObj.getTicketId());

              if (isForeignKeyEmpty(entityObj) == false) {
                     entityObj.setTicketId(null);
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_SAVE_SUCCESS);
              }
              return new ResponseBoolAndMess(false, MESS_SAVE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save_toDTO(TicketDTO dtoObj) {

              // kiem tra thuoc tinnh khoa ngoai

              TicketEntity ticketEntity = this.ticketMapping.toEntity(dtoObj);

              return save(ticketEntity);

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update(TicketEntity entityObj) {

              Optional<TicketEntity> optional = this.repo.findByTicketId(entityObj.getTicketId());

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
       public ResponseBoolAndMess update_toDTO(TicketDTO dtoObj) {

              // kiem tra thuoc tinnh khoa ngoai

              TicketEntity ticketEntity = this.ticketMapping.toEntity(dtoObj);

              return update(ticketEntity);

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess invisibleWithoutDelete(Long id) {
              //
              Optional<TicketEntity> optional = this.repo.findByTicketId(id);

              if (optional.isPresent()) {
                     Boolean check = foreignKeyViolationIfHidden(optional.get());

                     if (check == false) {
                            TicketEntity ticketEntity = optional.get();
                            ticketEntity.setIsDelete(true);
                            this.repo.save(ticketEntity);
                            return new ResponseBoolAndMess(true, MESS_HIDDEN_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_HIDDEN_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }
              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfDelete(TicketEntity entityObj) {

              // Ticket foreign key Feedback
              List<FeedbackEntity> feedbackEntities = this.feedbackRepo.findByTicketEntity_Id(entityObj.getTicketId());

              if (feedbackEntities.isEmpty() == false) {
                     return true;
              }

              return false;
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfHidden(TicketEntity entityObj) {

              if (entityObj.getIsDelete()) {
                     // Ticket foreign key Feedback
                     List<FeedbackEntity> feedbackEntities = this.feedbackRepo
                                   .findByTicketEntity_Id(entityObj.getTicketId());

                     if (feedbackEntities.isEmpty() == false) {
                            for (FeedbackEntity e : feedbackEntities) {
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
       public Boolean isForeignKeyEmpty(TicketEntity entityObj) {
              // Ticket co 5 thuoc tinh khoa ngoai: account_name
              // payment_id, discount_id

              // kiem tra
              AccountEntity accountEntity = entityObj.getAccountEntity();
              BusRouteScheduleEntity busScheduleEntity = entityObj.getBusRouteSchedule();
              PaymentEntity paymentEntity = entityObj.getPaymentEntity();

              // Do discount co tthe null ne khong can kiem tra
              // DiscountEntity discountEntity = entityObj.getDiscountEntity();

              if (accountEntity != null
                            && busScheduleEntity != null
                            && paymentEntity != null) {

                     return false;
              }
              return true;
       }

       // @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation
       // = Isolation.READ_COMMITTED)
       // @Override
       // public Boolean isHasForeignKeyEntity(TicketDTO dtoObj) {
       // // Ticket co 5 thuoc tinh khoa ngoai: account_name, schedule,
       // // payment_id, discount_id

       // // kiem tra
       // AccountEntity accountEntity =
       // this.accountRepo.findByUserName(dtoObj.getAccountEnity_Id()).orElse(null);

       // BusRouteScheduleEntity busRouteScheduleEntity = this.busRouteScheduleRepo
       // .findByScheduleId(dtoObj.getBusRouteSchedule_Id()).orElse(null);

       // PaymentEntity paymentEntity =
       // this.paymentRepo.findByPaymentId(dtoObj.getPaymentEntity_Id()).orElse(null);

       // // discount cos the null nen khong can kiem t

       // if (accountEntity != null
       // && busRouteScheduleEntity != null
       // && paymentEntity != null) {

       // if (dtoObj.getDiscountEntity_Id() != null) {
       // DiscountEntity discountEntity = this.discountRepo
       // .findByDiscountId(dtoObj.getDiscountEntity_Id())
       // .orElse(null);
       // if (discountEntity != null) {
       // return true;
       // } else {
       // return false;
       // }
       // } else {
       // return true;
       // }
       // }

       // return false;
       // }

       // kiểm tra xem routes id có được phân cho bus chạy không
       // @Transactional
       // public Boolean isRoutesIdVal(TicketEntity ticketEntity) {
       // Long routesId = ticketEntity.getRoutes_Id();
       // BusEntity busEntity = ticketEntity.getBusEntity();

       // if (busEntity.getBusRoutesEntity().getRoutesId().equals(routesId)) {
       // return true;
       // }
       // return false;
       // }

       // thong kê
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public ResponseObject statisticTicketRangeDay(LocalDate dateA, LocalDate dateB) {

              ResponseObject responseObject = new ResponseObject();
              Map<String, Object> datas = new HashMap<>();

              // Lấy dữ liệu tất cả các vé trong phạm vi ngày A và ngày B
              List<TicketEntity> ticketEntities = this.repo.findTicketsWithinDateRange(dateA, dateB);

              if (ticketEntities.isEmpty() == false) {
                     responseObject.setStatus("success");
                     List<TicketDTO> ticketDTOs = new ArrayList<>();

                     // mapping
                     for (TicketEntity ticketEntity : ticketEntities) {
                            ticketDTOs.add(this.ticketMapping.toDTO(ticketEntity));
                     }

                     datas.put("tickets", ticketDTOs);

                     responseObject.addMessage("mess", "Statistics from day " + dateA + " to day " + dateB);
                     responseObject.addMessage("size", ticketEntities.size());

                     // doanh thu va so luong cac ve da duoc thanh toan thanh cong
                     int countTicketSuccess = 0;
                     float sumMoney = 0;

                     // so luong cac ve dang cho thanh toan (pending)
                     int countTicketPending = 0;

                     // so luong cac ve dang thanh toan that bai (failure)
                     int countTicketFailure = 0;

                     // mang dem cac chuyen co trong ve
                     List<Long> listBusRouteSchedule = new ArrayList<>();

                     for (TicketEntity e : ticketEntities) {

                            if (listBusRouteSchedule.contains(e.getBusRouteSchedule().getScheduleId()) == false) {
                                   listBusRouteSchedule.add(e.getBusRouteSchedule().getScheduleId());
                            }

                            if (e.getStatus().equals("success")) {
                                   countTicketSuccess++;
                                   sumMoney += e.getPrice();
                            }
                            if (e.getStatus().equals("pending")) {
                                   countTicketPending++;
                            }
                            if (e.getStatus().equals("failure")) {
                                   countTicketFailure++;
                            }
                     }

                     datas.put("numberTicketSuccess", countTicketSuccess);
                     datas.put("sumMoneyTicketSuccess", sumMoney);

                     datas.put("numberTicketPending", countTicketPending);

                     datas.put("numberTicketFailure", countTicketFailure);

                     // doanh thu cua cac chuyen
                     List<Object> list = new ArrayList<>();
                     Map<String, Float> routes = new HashMap<>();

                     for (Long id : listBusRouteSchedule) {

                            float sumMoneyRoutes = 0;

                            for (TicketEntity e : ticketEntities) {
                                   if (e.getBusRouteSchedule().getScheduleId().equals(id)
                                                 && e.getStatus().equals("success")) {
                                          sumMoneyRoutes += e.getPrice();
                                   }
                            }
                            // mapping de lay thong tin routes
                            BusRouteScheduleEntity busRouteScheduleEntity = this.busRouteScheduleRepo
                                          .findByScheduleId(id).orElse(null);

                            BusRoutesEntity busRoutesEntity = busRouteScheduleEntity.getBusRoutesEntity();

                            String inforRoutes = busRoutesEntity.getRoutesId() + "-"
                                          + busRoutesEntity.getDepartureLocation() + "-"
                                          + busRoutesEntity.getDestinationLocation();

                            routes.put(inforRoutes, sumMoneyRoutes);
                     }
                     list.add(routes);

                     // responseObject.addMessage("revenueOfRoutes", list);
                     datas.put("revenueOfRoutes", list);

                     responseObject.setData(datas);

                     return responseObject;
              }

              responseObject.setStatus("failure");
              responseObject.setData(ticketEntities);
              responseObject.addMessage("mess", "There are no statistics from day " + dateA + " to day " + dateB);

              return responseObject;
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       public ResponseObject createMultipleTicketsAndPayment(
                     HttpServletRequest request,
                     String returnUrl,
                     List<String> seats, // Danh sách ghế
                     LocalDate departureDate,
                     Long scheduleId,
                     Long discountId,
                     String token) throws Exception {

              ResponseObject responseObject = new ResponseObject();

              // Kiểm tra departureDate có thỏa không
              BusRouteScheduleEntity busRouteScheduleEntity = this.busRouteScheduleRepo.findByScheduleId(scheduleId)
                            .orElse(null);

              if (busRouteScheduleEntity == null) {
                     responseObject.setStatus("failure");
                     responseObject.addMessage("mess", "Not found schedule have id " + scheduleId);
                     Map<String, Object> datas = new HashMap<>();
                     datas.put("scheduleId", scheduleId);
                     responseObject.setData(datas);

                     return responseObject;
              }

              String dayOfWeek = busRouteScheduleEntity.getDayOfWeek();
              if (isValidDate(departureDate, dayOfWeek) == false) {
                     responseObject.setStatus("failure");
                     responseObject.addMessage("mess", "Departure date does not meet the schedule " + scheduleId
                                   + ". Departure day input is " + departureDate + "-" + departureDate.getDayOfWeek()
                                   + " but day of week in schedule is " + dayOfWeek);
                     Map<String, Object> datas = new HashMap<>();

                     datas.put("departureDate", departureDate);

                     responseObject.setData(datas);

                     return responseObject;
              }

              // Kiểm tra từng ghế trong danh sách
              for (String seat : seats) {
                     ResponseBoolAndMess check = this.busService.isValSeat(seat, scheduleId, departureDate);

                     if (!check.getValueBool()) {
                            responseObject.setStatus("failure");
                            responseObject.addMessage("mess",
                                          "One or more seats are already occupied. Please choose other seats.");
                            return responseObject;
                     }
              }

              // Phân giải token lấy username
              AccountDTO accountDTO = this.accountService.geAccountDTOHasLogin();

              // Lấy thông tin chuyến bus
              BusRoutesEntity busRoutesEntity = busRouteScheduleEntity.getBusRoutesEntity();

              // Lấy thông tin discount
              DiscountEntity discountEntity = discountId != null
                            ? this.discountRepo.findByDiscountId(discountId).orElse(null)
                            : null;
              float discountPercentage = (discountEntity != null) ? discountEntity.getDiscountPercentage() : 0;

              // Giảm số lượng discount (nếu có)
              if (discountEntity != null) {
                     discountEntity.setAmount(discountEntity.getAmount() - seats.size());
              }

              // Tính toán giá trị thanh toán
              BigDecimal originalPrice = BigDecimal.valueOf(busRoutesEntity.getPrice())
                            .multiply(BigDecimal.valueOf(seats.size()));
              BigDecimal discountAmount = originalPrice.multiply(BigDecimal.valueOf(discountPercentage))
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
              BigDecimal finalAmount = originalPrice.subtract(discountAmount);

              // Tạo payment duy nhất
              PaymentEntity paymentEntity = new PaymentEntity();
              paymentEntity.setPaymentTime(LocalDateTime.now());
              paymentEntity.setOriginalAmount(originalPrice.floatValue());
              paymentEntity.setDiscountAmount(discountAmount.floatValue());
              paymentEntity.setFinalAmount(finalAmount.floatValue());
              paymentEntity.setPaymentMethod("VNPay");
              paymentEntity.setStatus("pending");
              paymentEntity.setIsDelete(false);

              PaymentEntity savedPayment = this.paymentRepo.save(paymentEntity);
              if (savedPayment == null || savedPayment.getPaymentId() == null) {
                     throw new RuntimeException("Failed to save payment entity.");
              }

              // Tạo danh sách ticket
              List<TicketEntity> ticketEntities = new ArrayList<>();
              for (String seat : seats) {
                     TicketDTO ticketDTO = new TicketDTO();
                     ticketDTO.setAccountEnity_Id(accountDTO.getUserName());
                     ticketDTO.setBusRouteSchedule_Id(scheduleId);
                     ticketDTO.setPaymentEntity_Id(savedPayment.getPaymentId());
                     ticketDTO.setDiscountEntity_Id(discountEntity != null ? discountEntity.getDiscountId() : null);
                     ticketDTO.setSeatNumber(seat);
                     ticketDTO.setDepartureDate(departureDate);
                     ticketDTO.setPrice(finalAmount.floatValue() / seats.size()); // Giá mỗi vé
                     ticketDTO.setPhoneNumber(accountDTO.getPhoneNumber());
                     ticketDTO.setStatus("pending");
                     ticketDTO.setIsDelete(false);
                     ticketDTO.setListFeedbackEntities_Id(new ArrayList<>());

                     TicketEntity ticketEntity = this.ticketMapping.toEntity(ticketDTO);
                     ticketEntity.setTicketId(null);
                     ticketEntities.add(ticketEntity);
              }

              // Lưu tất cả ticket
              List<TicketEntity> savedTickets = this.repo.saveAll(ticketEntities);

              if (savedTickets.isEmpty()) {
                     throw new RuntimeException("Failed to save tickets.");
              }

              // Gọi VNPayService
              String ticketIds = savedTickets.stream()
                            .map(ticket -> String.valueOf(ticket.getTicketId()))
                            .reduce((a, b) -> a + "," + b).orElse("");

              String url = this.vnPayService.createOrder(request, finalAmount.intValue(),
                            String.valueOf(savedPayment.getPaymentId()), ticketIds, returnUrl);

              responseObject.setStatus("success");
              responseObject.setData(url);
              responseObject.addMessage("mess", "Link VNPay");
              return responseObject;
       }

       // Hàm kiểm tra xem một giá trị LocalDate có đúng là ngày thuộc thứ được chỉ
       // định và không nhỏ hơn ngày hiện tại (ngày hệ thống):
       @Transactional
       public boolean isValidDate(LocalDate date, String dayOfWeek) {
              // Lấy ngày hiện tại
              LocalDate today = LocalDate.now();

              // Kiểm tra nếu ngày truyền vào nhỏ hơn ngày hiện tại
              if (date.isBefore(today)) {
                     return false;
              }

              // Chuyển đổi `dayOfWeek` sang kiểu DayOfWeek
              DayOfWeek targetDayOfWeek;
              try {
                     targetDayOfWeek = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
              } catch (IllegalArgumentException e) {
                     throw new IllegalArgumentException("Invalid day of week: " + dayOfWeek);
              }

              // Kiểm tra nếu ngày truyền vào có đúng thứ như yêu cầu
              return date.getDayOfWeek().equals(targetDayOfWeek);
       }

       // Hàm xử lý giao dịch trả về
       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       public ResponseObject returnFronVNPay(HttpServletRequest request) {

              ResponseObject responseObject = new ResponseObject();

              Map<String, String> result = this.vnPayService.orderReturn(request);

              // Kiểm tra kết quả giao dịch
              String vnp_ResponseCode = result.get("vnp_ResponseCode");
              String vnp_TransactionStatus = result.get("vnp_TransactionStatus");

              // Lấy mã payment
              String[] temp = result.get("vnp_TxnRef").split("_");
              Long paymentId = Long.parseLong(temp[0]);

              // Nếu thành công
              if (vnp_ResponseCode.equals(vnp_TransactionStatus)) {

                     // Cập nhật trạng thái payment
                     PaymentEntity paymentEntity = this.paymentService.getById(paymentId);
                     paymentEntity.setStatus("success");

                     PaymentEntity paymentEntity2 = this.paymentRepo.save(paymentEntity);

                     // cap nhatj trang thai ticket
                     if (paymentEntity2.getStatus().equals("success")) {
                            List<TicketEntity> ticketEntities = this.repo.findByPaymentEntity_Id(paymentId);

                            for (TicketEntity e : ticketEntities) {
                                   e.setStatus("success");
                                   this.repo.save(e);
                            }

                            List<TicketEntity> ticketEntities2 = this.repo.findByPaymentEntity_Id(paymentId);
                            List<TicketDTO> ticketDTOs = new ArrayList<>();
                            for (TicketEntity e : ticketEntities2) {
                                   ticketDTOs.add(this.ticketMapping.toDTO(e));
                            }

                            responseObject.setStatus("success");
                            responseObject.setData(ticketDTOs);
                            responseObject.addMessage("mess", "Payment successful");

                            return responseObject;
                     }

              }

              // Cập nhật trạng thái payment
              PaymentEntity paymentEntity = this.paymentService.getById(paymentId);
              paymentEntity.setStatus("failure");
              PaymentEntity paymentEntity2 = this.paymentRepo.save(paymentEntity);

              // cap nhatj trang thai ticket
              if (paymentEntity2.getStatus().equals("failure")) {
                     List<TicketEntity> ticketEntities = this.repo.findByPaymentEntity_Id(paymentId);

                     for (TicketEntity e : ticketEntities) {
                            e.setStatus("failure");
                            this.repo.save(e);
                     }

                     List<TicketDTO> ticketDTOs = new ArrayList<>();
                     for (TicketEntity e : ticketEntities) {
                            ticketDTOs.add(this.ticketMapping.toDTO(e));
                     }

                     responseObject.setStatus("failure");
                     responseObject.setData(ticketDTOs);
                     responseObject.addMessage("mess", "Payment no successful");
                     responseObject.addMessage("size", ticketDTOs.size());
              }

              return responseObject;
       }

       // hàm tra cứu vé
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public ResponseObject getByTicketIdAndUserName(Long ticketId, String username) {

              AccountDTO accountEntity = this.accountService.geAccountDTOHasLogin();
              ResponseObject responseObject = new ResponseObject();

              if (accountEntity != null && accountEntity.getUserName().equals(username)) {
                     Optional<TicketEntity> optional = this.repo.findByTicketId(ticketId);
                     if (optional.isPresent()) {
                            if (optional.get().getAccountEntity().getUserName().equals(username)) {
                                   responseObject.setStatus("success");
                                   responseObject.addMessage("mess", "Found ticket");
                                   responseObject.setData(this.ticketMapping.toDTO(optional.get()));

                                   return responseObject;
                            }
                            responseObject.setStatus("failure");
                            responseObject.addMessage("mess", "Not found ticket");
                            responseObject.setData(null);

                            return responseObject;

                     }
                     responseObject.setStatus("failure");
                     responseObject.addMessage("mess", "Not found ticket");
                     responseObject.setData(null);

                     return responseObject;
              }
              responseObject.setStatus("failure");
              responseObject.addMessage("mess", "Ticket viewing is not allowed");
              responseObject.setData(null);

              return responseObject;
       }

       // Hàm tra cứu theo ngày dateA đến dateB và theo username
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public ResponseObject getByTicketIdAndUserNameAndDateRange(String username, LocalDate dateA,
                     LocalDate dateB) {

              AccountDTO accountEntity = this.accountService.geAccountDTOHasLogin();
              ResponseObject responseObject = new ResponseObject();

              if (accountEntity != null && accountEntity.getUserName().equals(username)) {

                     List<TicketDTO> ticketEntitiesReturn = new ArrayList<>();

                     if (dateA.isBefore(dateB)) {
                            List<TicketEntity> ticketEntities = this.repo.findTicketsWithinDateRange(dateA, dateB);

                            for (TicketEntity e : ticketEntities) {
                                   if (e.getAccountEntity().getUserName().equals(username)) {
                                          ticketEntitiesReturn.add(this.ticketMapping.toDTO(e));
                                   }
                            }

                            responseObject.setStatus("success");
                            responseObject.addMessage("mess", "Found tickets");
                            responseObject.setData(ticketEntitiesReturn);

                            return responseObject;

                     }

                     responseObject.setStatus("failure");
                     responseObject.addMessage("mess", "Not Found tickets");
                     responseObject.setData(ticketEntitiesReturn);

                     return responseObject;
              }

              responseObject.setStatus("failure");
              responseObject.addMessage("mess", "Ticket viewing is not allowed");
              responseObject.setData(null);

              return responseObject;

       }

       // Hàm trả về thông tin đầy đủ của vé cho người dùng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public ResponseObject getFullInfoTicket(Long ticketId) {

              ResponseObject responseObject = new ResponseObject();
              TicketEntity ticketEntity = this.repo.findByTicketId(ticketId).orElse(null);
              Map<String, Object> dataTicket = new HashMap<>();

              if (ticketEntity == null) {
                     responseObject.setStatus("failure");
                     responseObject.addMessage("mess", "Not found ticket have ticketId " + ticketId);
                     responseObject.setData(null);

                     return responseObject;
              }

              responseObject.setStatus("success");
              responseObject.addMessage("mess", "Found ticket have ticketId " + ticketId);

              // Lấy thông tin trên vé
              dataTicket.put("ticketId", ticketId);

              String seatNumber = ticketEntity.getSeatNumber();
              dataTicket.put("seatNumber", seatNumber);

              LocalDate departureDate = ticketEntity.getDepartureDate();
              dataTicket.put("departureDate", departureDate);

              String phoneNumber = ticketEntity.getPhoneNumber();
              dataTicket.put("phoneNumber", phoneNumber);

              // Lấy thông tin về người đặt vé
              AccountEntity accountEntity = ticketEntity.getAccountEntity();

              String accountUsername = accountEntity.getUserName();
              dataTicket.put("accountUsername", accountUsername);

              // Lấy thông tin về payment
              PaymentEntity paymentEntity = ticketEntity.getPaymentEntity();

              LocalDateTime paymentDate = paymentEntity.getPaymentTime();
              dataTicket.put("paymentDate", paymentDate);

              float originalAmount = paymentEntity.getOriginalAmount();
              dataTicket.put("originalAmount", originalAmount);

              float discountAmount = paymentEntity.getDiscountAmount();
              dataTicket.put("discountAmount", discountAmount);

              float finalAmount = paymentEntity.getFinalAmount();
              dataTicket.put("finalAmount", finalAmount);

              String paymentMethod = paymentEntity.getPaymentMethod();
              dataTicket.put("paymentMethod", paymentMethod);

              String status = paymentEntity.getStatus();
              dataTicket.put("status", status);

              // Lấy thôn tin schedule
              BusRouteScheduleEntity busRouteScheduleEntity = ticketEntity.getBusRouteSchedule();

              String dayOfWeek = busRouteScheduleEntity.getDayOfWeek();
              dataTicket.put("dayOfWeek", dayOfWeek);

              LocalTime departureTime = busRouteScheduleEntity.getDepartureTime();
              dataTicket.put("departureTime", departureTime);

              // Lấy thôn tin busRoute
              BusRoutesEntity busRoutesEntity = busRouteScheduleEntity.getBusRoutesEntity();

              String departureLocation = busRoutesEntity.getDepartureLocation();
              dataTicket.put("departureLocation", departureLocation);

              String destinationLocation = busRoutesEntity.getDestinationLocation();
              dataTicket.put("destinationLocation", destinationLocation);

              float distanceKm = busRoutesEntity.getDistanceKilometer();
              dataTicket.put("distanceKm", distanceKm);

              LocalTime tripTime = busRoutesEntity.getTripTime();
              dataTicket.put("tripTime", tripTime);

              LocalTime distinationTime = this.busRouteScheduleService.addLocalTimes(departureTime, tripTime);
              dataTicket.put("distinationTime", distinationTime);

              responseObject.setData(dataTicket);

              return responseObject;

       }

}
