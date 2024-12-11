// package com.bus_station_ticket.project.ProjectSecurity;

// import java.util.Arrays;
// import java.util.LinkedHashSet;
// import java.util.Set;

// public class temp {

//        private static String[] ADMIN_ACCESS_PATH = {
//                      "/accounts",
//                      "/accounts/{username-id}",
//                      "/accounts/insert",
//                      "/accounts/delete/{username-id}",
//                      "/accounts/hidden/{username-id}",
//                      "/accounts/update",
//                      "/accounts/info-login",
//                      "/accounts/register",
//                      "/accounts/update_user",

//                      "/buses",
//                      "/buses/{busId}",
//                      "/buses/delete/{busId}",
//                      "/buses/insert",
//                      "/buses/update",
//                      "/buses/hidden/{busId}",
//                      "/buses/check_seat",
//                      "/buses/list_empty_seat",

//                      "/bus_routes_schedule",
//                      "/bus_routes_schedule/{scheduleId}",
//                      "/bus_routes_schedule/insert",
//                      "/bus_routes_schedule/update",
//                      "/bus_routes_schedule/delete/{scheduleId}",
//                      "/bus_routes_schedule/hidden/{scheduleId}",
//                      "/bus_routes_schedule/departureLocation_destinationLocation",

//                      "/busroutes",
//                      "/busroutes/{routesId}",
//                      "/busroutes/delete/{routesId}",
//                      "/busroutes/hidden/{routesId}",
//                      "/busroutes/insert",
//                      "/busroutes/update",

//                      "/discounts",
//                      "/discounts/{discountId}",
//                      "/discounts/delete/{discountId}",
//                      "/discounts/hidden/{discountId}",
//                      "/discounts/insert",
//                      "/discounts/update",

//                      "/employees",
//                      "/employees/{driverId}",
//                      "/employees/delete/{driverId}",
//                      "/employees/hidden/{driverId}",
//                      "/employees/insert",
//                      "/employees/update",
//                      "/employees/update/bus_and_employee",
//                      "/employees/delete/bus_and_employee",
//                      "/employees/bus_and_employee",

//                      "/feedbacks",
//                      "/feedbacks/{feedbackId}",
//                      "/feedbacks/delete/{feedbackId}",
//                      "/feedbacks/hidden/{feedbackId}",
//                      "/feedbacks/insert",
//                      "/feedbacks/update",

//                      "/payments",
//                      "/payments/{paymentId}",
//                      "/payments/delete/{paymentId}",
//                      "/payments/hidden/{paymentId}",
//                      "/payments/insert",
//                      "/payments/update",

//                      "/penaltytickets",
//                      "/penaltytickets/{penaltyTicketId}",
//                      "/penaltytickets/delete/{penaltyticketId}",
//                      "/penaltytickets/hidden/{penaltyticketId}",
//                      "/penaltytickets/insert",
//                      "/penaltytickets/update",
//                      "/penaltytickets/statistics",

//                      "/tickets",
//                      "/tickets/{ticketId}",
//                      "/tickets/delete/{ticketId}",
//                      "/tickets/hidden/{ticketId}",
//                      "/tickets/insert",
//                      "/tickets/update",
//                      "/tickets/statistics",
//                      "/tickets/ticket_by_date_username",
//                      "/tickets/ticket_by_ticketId_username",
//                      "/tickets/ticket_full_info/{ticketId}",

//                      "/sendEmail",

//                      "/create_payment"
//        };

//        private static String[] MANAGER_ACCESS_PATH = {

//                      "/accounts",
//                      "/accounts/{username-id}",
//                      "/accounts/insert",
//                      "/accounts/update",
//                      "/accounts/info-login",
//                      "/accounts/register",
//                      "/accounts/update_user",

//                      "/buses",
//                      "/buses/{busId}",
//                      "/buses/update",
//                      "/buses/check_seat",
//                      "/buses/list_empty_seat",

//                      "/bus_routes_schedule",
//                      "/bus_routes_schedule/{scheduleId}",
//                      "/bus_routes_schedule/insert",
//                      "/bus_routes_schedule/update",
//                      "/bus_routes_schedule/delete/{scheduleId}",
//                      "/bus_routes_schedule/hidden/{scheduleId}",
//                      "/bus_routes_schedule/departureLocation_destinationLocation",

//                      "/busroutes",
//                      "/busroutes/{routesId}",
//                      "/busroutes/update",

//                      "/discounts",
//                      "/discounts/{discountId}",
//                      "/discounts/delete/{discountId}",
//                      "/discounts/hidden/{discountId}",
//                      "/discounts/insert",
//                      "/discounts/update",

//                      "/employees",
//                      "/employees/{driverId}",
//                      "/employees/delete/{driverId}",
//                      "/employees/hidden/{driverId}",
//                      "/employees/insert",
//                      "/employees/update",
//                      "/employees/update/bus_and_employee",
//                      "/employees/delete/bus_and_employee",
//                      "/employees/bus_and_employee",

//                      "/feedbacks",
//                      "/feedbacks/{feedbackId}",
//                      "/feedbacks/delete/{feedbackId}",
//                      "/feedbacks/hidden/{feedbackId}",
//                      "/feedbacks/insert",
//                      "/feedbacks/update",

//                      "/payments",
//                      "/payments/{paymentId}",
//                      "/payments/insert",
//                      "/payments/update",

//                      "/penaltytickets",
//                      "/penaltytickets/{penaltyTicketId}",
//                      "/penaltytickets/delete/{penaltyticketId}",
//                      "/penaltytickets/hidden/{penaltyticketId}",
//                      "/penaltytickets/insert",
//                      "/penaltytickets/update",
//                      "/penaltytickets/statistics",

//                      "/tickets",
//                      "/tickets/{ticketId}",
//                      "/tickets/delete/{ticketId}",
//                      "/tickets/hidden/{ticketId}",
//                      "/tickets/insert",
//                      "/tickets/update",
//                      "/tickets/statistics",
//                      "/tickets/ticket_by_date_username",
//                      "/tickets/ticket_by_ticketId_username",
//                      "/tickets/ticket_full_info/{ticketId}",

//                      "/sendEmail",

//                      "/create_payment"
//        };

//        private static String[] STAFF_ACCESS_PATH = {

//                      "/accounts",
//                      "/accounts/{username-id}",
//                      "/accounts/update",
//                      "/accounts/info-login",
//                      "/accounts/register",
//                      "/accounts/update_user",

//                      "/buses",
//                      "/buses/{busId}",
//                      "/buses/check_seat",
//                      "/buses/list_empty_seat",

//                      "/bus_routes_schedule",
//                      "/bus_routes_schedule/{scheduleId}",
//                      "/bus_routes_schedule/departureLocation_destinationLocation",

//                      "/busroutes",
//                      "/busroutes/{routesId}",
//                      "/discounts",
//                      "/discounts/{discountId}",
//                      "/discounts/update",

//                      "/employees",
//                      "/employees/{driverId}",
//                      "/employees/bus_and_employee",

//                      "/feedbacks",
//                      "/feedbacks/{feedbackId}",
//                      "/feedbacks/delete/{feedbackId}",
//                      "/feedbacks/hidden/{feedbackId}",
//                      "/feedbacks/insert",
//                      "/feedbacks/update",

//                      "/payments",
//                      "/payments/{paymentId}",

//                      "/penaltytickets",
//                      "/penaltytickets/{penaltyTicketId}",
//                      "/penaltytickets/statistics",

//                      "/tickets",
//                      "/tickets/{ticketId}",
//                      "/tickets/delete/{ticketId}",
//                      "/tickets/hidden/{ticketId}",
//                      "/tickets/insert",
//                      "/tickets/update",
//                      "/tickets/statistics",
//                      "/tickets/ticket_by_date_username",
//                      "/tickets/ticket_by_ticketId_username",
//                      "/tickets/ticket_full_info/{ticketId}",

//                      "/sendEmail",

//                      "/create_payment"
//        };

//        private static String[] USER_ACCESS_PATH = {
//                      "/accounts",
//                      "/accounts/{username-id}",
//                      "/accounts/info-login",
//                      "/accounts/register",
//                      "/accounts/update_user",

//                      "/buses",
//                      "/buses/{busId}",
//                      "/buses/check_seat",
//                      "/buses/list_empty_seat",

//                      "/bus_routes_schedule",
//                      "/bus_routes_schedule/{scheduleId}",
//                      "/bus_routes_schedule/departureLocation_destinationLocation",

//                      "/busroutes",
//                      "/busroutes/{routesId}",

//                      "/discounts",
//                      "/discounts/{discountId}",

//                      "/employees",
//                      "/employees/{driverId}",
//                      "/employees/bus_and_employee",

//                      "/feedbacks",
//                      "/feedbacks/{feedbackId}",
//                      "/feedbacks/insert",

//                      "/payments",
//                      "/payments/{paymentId}",

//                      "/penaltytickets",
//                      "/penaltytickets/{penaltyTicketId}",

//                      "/tickets",
//                      "/tickets/{ticketId}",
//                      "/tickets/ticket_by_date_username",
//                      "/tickets/ticket_by_ticketId_username",
//                      "/tickets/ticket_full_info/{ticketId}",

//                      "/sendEmail",

//                      "/create_payment"

//        };

//        public static void main(String[] args) {
//               // Chuyển mảng thành Set để loại bỏ trùng lặp
//               Set<String> userPaths = new LinkedHashSet<>(Arrays.asList(USER_ACCESS_PATH));
//               Set<String> staffPaths = new LinkedHashSet<>(Arrays.asList(STAFF_ACCESS_PATH));
//               Set<String> managerPaths = new LinkedHashSet<>(Arrays.asList(MANAGER_ACCESS_PATH));
//               Set<String> adminPaths = new LinkedHashSet<>(Arrays.asList(ADMIN_ACCESS_PATH));

//               // Loại bỏ các path của cấp thấp hơn khỏi cấp cao hơn
//               staffPaths.removeAll(userPaths);
//               managerPaths.removeAll(userPaths);
//               managerPaths.removeAll(staffPaths);
//               adminPaths.removeAll(userPaths);
//               adminPaths.removeAll(staffPaths);
//               adminPaths.removeAll(managerPaths);

//               System.out.println(adminPaths);
//        }
// }
