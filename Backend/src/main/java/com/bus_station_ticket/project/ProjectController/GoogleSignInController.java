// package com.bus_station_ticket.project.ProjectController;

// import java.util.Map;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
// import org.springframework.security.oauth2.client.registration.ClientRegistration;
// import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
// import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.servlet.view.RedirectView;

// import com.bus_station_ticket.project.ProjectConfig.ResponseObject;
// import com.bus_station_ticket.project.ProjectEntity.AccountEntity;
// import com.bus_station_ticket.project.ProjectSecurity.UserDetailsConfig;

// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;

// @RestController
// public class GoogleSignInController {

//        private final ClientRegistrationRepository clientRegistrationRepository;

//        public GoogleSignInController(ClientRegistrationRepository clientRegistrationRepository) {
//               this.clientRegistrationRepository = clientRegistrationRepository;
//        }

//        @GetMapping("/sign-in-google")
//        public String signInGoogle() {
//               ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("google");

//               String authorizationUri = clientRegistration.getProviderDetails().getAuthorizationUri();
//               String redirectUri = "http://localhost:8080/info-login-google"; // Đảm bảo cấu hình URL chính xác

//               String url = authorizationUri + "?response_type=code&client_id=" + clientRegistration.getClientId()
//                             + "&redirect_uri=" + redirectUri + "&scope=openid%20profile%20email";

//               return url;
//        }

//        // @GetMapping("/info-login-google")
//        // public ResponseEntity<Object> getGoogleUserInfo(HttpServletRequest request) {
//        //        // In ra toàn bộ thông tin authentication
//        //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        //        System.out.println("Authentication: " + authentication);
//        //        System.out.println("Principal: " + authentication.getPrincipal());
//        //        System.out.println("Credentials: " + authentication.getCredentials());
//        //        System.out.println("Details: " + authentication.getDetails());

//        //        return ResponseEntity.ok(authentication);
//        // }

//        // @GetMapping("/sign-in-google")
//        // public String signInGoogle() {
//        // return "http://localhost:8080/oauth2/authorize/google";
//        // }

//        @GetMapping("/info-login-google")
//        public ResponseEntity<Object> getGoogleUserInfo() {
//               // Lấy thông tin authentication từ SecurityContext
//               Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//               // Kiểm tra xem authentication có phải là OAuth2Authentication không
//               if (authentication instanceof OAuth2AuthenticationToken) {
//                      OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

//                      // Trích xuất thông tin người dùng
//                      Map<String, Object> userAttributes = oauth2User.getAttributes();

//                      return ResponseEntity.status(HttpStatus.OK).body(userAttributes);
//               }

//               // Nếu không phải OAuth2 authentication
//               return ResponseEntity.status(HttpStatus.OK).body(authentication);
//        }

// }
