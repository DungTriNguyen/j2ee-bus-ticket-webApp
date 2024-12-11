package com.bus_station_ticket.project.ProjectSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.bus_station_ticket.project.ProjectConfig.WebConfig;
import com.bus_station_ticket.project.ProjectExceptionHandler.ExceptionAccessDeniedHandler;
import com.bus_station_ticket.project.ProjectExceptionHandler.ExceptionAuthenticationEntryPoint;
import com.bus_station_ticket.project.ProjectRepository.AccountRepo;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

       @Autowired
       private JwtFilter jwtFilter;

       private final String[] TEMP = {
                     "/accounts",
                     "/accounts/{username-id}",
                     "/accounts/insert",
                     "/accounts/delete/{username-id}",
                     "/accounts/hidden/{username-id}",
                     "/accounts/update",
                     "/accounts/info-login",
                     "/accounts/register",
                     "/accounts/update_user",

                     "/buses",
                     "/buses/{busId}",
                     "/buses/delete/{busId}",
                     "/buses/insert",
                     "/buses/update",
                     "/buses/hidden/{busId}",
                     "/buses/check_seat",
                     "/buses/list_empty_seat",

                     "/bus_routes_schedule",
                     "/bus_routes_schedule/{scheduleId}",
                     "/bus_routes_schedule/insert",
                     "/bus_routes_schedule/update",
                     "/bus_routes_schedule/delete/{scheduleId}",
                     "/bus_routes_schedule/hidden/{scheduleId}",
                     "/bus_routes_schedule/departureLocation_destinationLocation",

                     "/busroutes",
                     "/busroutes/{routesId}",
                     "/busroutes/delete/{routesId}",
                     "/busroutes/hidden/{routesId}",
                     "/busroutes/insert",
                     "/busroutes/update",

                     "/discounts",
                     "/discounts/{discountId}",
                     "/discounts/delete/{discountId}",
                     "/discounts/hidden/{discountId}",
                     "/discounts/insert",
                     "/discounts/update",

                     "/employees",
                     "/employees/{driverId}",
                     "/employees/delete/{driverId}",
                     "/employees/hidden/{driverId}",
                     "/employees/insert",
                     "/employees/update",
                     "/employees/update/bus_and_employee",
                     "/employees/delete/bus_and_employee",
                     "/employees/bus_and_employee",

                     "/feedbacks",
                     "/feedbacks/{feedbackId}",
                     "/feedbacks/delete/{feedbackId}",
                     "/feedbacks/hidden/{feedbackId}",
                     "/feedbacks/insert",
                     "/feedbacks/update",

                     "/payments",
                     "/payments/{paymentId}",
                     "/payments/delete/{paymentId}",
                     "/payments/hidden/{paymentId}",
                     "/payments/insert",
                     "/payments/update",

                     "/penaltytickets",
                     "/penaltytickets/{penaltyTicketId}",
                     "/penaltytickets/delete/{penaltyticketId}",
                     "/penaltytickets/hidden/{penaltyticketId}",
                     "/penaltytickets/insert",
                     "/penaltytickets/update",
                     "/penaltytickets/statistics",

                     "/tickets",
                     "/tickets/{ticketId}",
                     "/tickets/delete/{ticketId}",
                     "/tickets/hidden/{ticketId}",
                     "/tickets/insert",
                     "/tickets/update",
                     "/tickets/statistics",
                     "/tickets/ticket_by_date_username",
                     "/tickets/ticket_by_ticketId_username",
                     "/tickets/ticket_full_info/{ticketId}",

                     "/sendEmail",

                     "/create_payment"
       };

       private final String[] ADMIN_ACCESS_PATH = {
                     "/accounts/delete/{username-id}",
                     "/accounts/hidden/{username-id}",

                     "/buses/delete/{busId}",
                     "/buses/insert",
                     "/buses/hidden/{busId}",

                     "/busroutes/delete/{routesId}",
                     "/busroutes/hidden/{routesId}",
                     "/busroutes/insert",

                     "/payments/delete/{paymentId}",
                     "/payments/hidden/{paymentId}"
       };

       private final String[] MANAGER_ACCESS_PATH = {

                     "/accounts/insert",

                     "/buses/update",

                     "/bus_routes_schedule/insert",
                     "/bus_routes_schedule/update",
                     "/bus_routes_schedule/delete/{scheduleId}",
                     "/bus_routes_schedule/hidden/{scheduleId}",
                     "/busroutes/update",

                     "/discounts/delete/{discountId}",
                     "/discounts/hidden/{discountId}",
                     "/discounts/insert",

                     "/employees/delete/{driverId}",
                     "/employees/hidden/{driverId}",
                     "/employees/insert",
                     "/employees/update",
                     "/employees/update/bus_and_employee",
                     "/employees/delete/bus_and_employee",

                     "/payments/insert",
                     "/payments/update",

                     "/penaltytickets/delete/{penaltyticketId}",
                     "/penaltytickets/hidden/{penaltyticketId}",
                     "/penaltytickets/insert",
                     "/penaltytickets/update"

       };

       private final String[] STAFF_ACCESS_PATH = {

                     "/accounts/update",

                     "/discounts/update",

                     "/feedbacks/delete/{feedbackId}",
                     "/feedbacks/hidden/{feedbackId}",
                     "/feedbacks/update",

                     "/penaltytickets/statistics",

                     "/tickets/delete/{ticketId}",
                     "/tickets/hidden/{ticketId}",
                     "/tickets/insert",
                     "/tickets/update",
                     "/tickets/statistics"

       };

       private final String[] USER_ACCESS_PATH = {
                     "/accounts",
                     "/accounts/{username-id}",
                     "/accounts/info-login",
                     "/accounts/update_user",

                     "/buses",
                     "/buses/{busId}",
                     "/buses/check_seat",
                     "/buses/list_empty_seat",

                     "/bus_routes_schedule",
                     "/bus_routes_schedule/{scheduleId}",
                     "/bus_routes_schedule/departureLocation_destinationLocation",

                     "/busroutes",
                     "/busroutes/{routesId}",

                     "/discounts",
                     "/discounts/{discountId}",

                     "/employees",
                     "/employees/{driverId}",
                     "/employees/bus_and_employee",

                     "/feedbacks",
                     "/feedbacks/{feedbackId}",
                     "/feedbacks/insert",

                     "/payments",
                     "/payments/{paymentId}",

                     "/penaltytickets",
                     "/penaltytickets/{penaltyTicketId}",

                     "/tickets",
                     "/tickets/{ticketId}",
                     "/tickets/ticket_by_date_username",
                     "/tickets/ticket_by_ticketId_username",
                     "/tickets/ticket_full_info/{ticketId}",

                     "/sendEmail",

                     "/create_payment"

       };

       // Cấu hình các bộ lọc Filter trong SecurityFilterChain cho việc bảo mật và xác
       // thực
       @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
              http
                            .cors(cors -> cors.configurationSource(new WebConfig()))
                            // .oauth2Login(oauth2 -> oauth2.loginPage("/sign-in-google")
                            // // .defaultSuccessUrl("/info-login-google", true)
                            // .successHandler(new AuthenticationSuccessHandlerCustom()))
                            // kích hoạt xác thực bằng HTTP Basic Filter
                            .httpBasic(request -> request
                                          .authenticationEntryPoint(new ExceptionAuthenticationEntryPoint())

                            )
                            // Cấu hình phân quyền
                            .authorizeHttpRequests(request -> request
                                          .requestMatchers("/vnpay-payment-return", "/accounts/register").permitAll()
                                          .requestMatchers(USER_ACCESS_PATH)
                                          .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF", "ROLE_USER")
                                          .requestMatchers(STAFF_ACCESS_PATH)
                                          .hasAnyAuthority("ROLE_STAFF", "ROLE_MANAGER", "ROLE_ADMIN")
                                          .requestMatchers(MANAGER_ACCESS_PATH)
                                          .hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                                          .requestMatchers(ADMIN_ACCESS_PATH).hasAnyAuthority("ROLE_ADMIN")
                                          .anyRequest().authenticated())
                            .exceptionHandling(
                                          request -> request.accessDeniedHandler(new ExceptionAccessDeniedHandler())
                                                        .authenticationEntryPoint(
                                                                      new ExceptionAuthenticationEntryPoint()))

                            // Thêm filter JWT trước Basic Authentication
                            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                            // .addFilterAfter(jwtFilter, OAuth2LoginAuthenticationFilter.class)
                            // vô hiệu hóa bảo mật bằng CSRF Filter
                            .csrf(request -> request.disable())
                            // vô hiệu hóa bảo mật bằng Form
                            .formLogin(request -> request.disable());

              return http.build();
       }

       // Khởi tạo một AuthenticationManager và đăng ký một AuthenticationProvider
       @Bean
       public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService)
                     throws Exception {
              return http
                            .getSharedObject(AuthenticationManagerBuilder.class)
                            .authenticationProvider(authenticationProvider(userDetailsService))
                            .build();

       }

       // Triển khai một AuthencticationProvider với xác thực bằng
       // DaoAuthenticationProvider
       @Bean
       public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
              DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
              dao.setUserDetailsService(userDetailsService);
              // Dùng mật khẩu mã hóa BCryptPasswordEncoder với mức độ mã hóa là 10
              dao.setPasswordEncoder(new BCryptPasswordEncoder(10));
              return dao;
       }

       // Khởi tạo một Bean UserDetailService liên kết tự động với
       // DaoAuthenticationProvider để xác thực
       @Bean
       public UserDetailsService userDetailsService(AccountRepo accountRepo) {
              return new UserDetailsServiceConfig(accountRepo);
       }

       @Bean
       public ExceptionAccessDeniedHandler exceptionAccessDeniedHandler() {
              return new ExceptionAccessDeniedHandler();
       }

       @Bean
       public ExceptionAuthenticationEntryPoint exceptionAuthenticationEntryPoint() {
              return new ExceptionAuthenticationEntryPoint();
       }

       @Bean
       public CorsConfigurationSource configurationSource() {
              return new WebConfig();
       }
}
