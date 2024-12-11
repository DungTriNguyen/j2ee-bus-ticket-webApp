package com.bus_station_ticket.project.VNPay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vnpay")
public class VNPayDTO {
       private String payUrl;
       private String returnUrl;
       private String tmnCode;
       private String hashSecret;
       private String apiUrl;

       // Getters and Setters
       public String getPayUrl() {
              return payUrl;
       }

       public void setPayUrl(String payUrl) {
              this.payUrl = payUrl;
       }

       public String getReturnUrl() {
              return returnUrl;
       }

       public void setReturnUrl(String returnUrl) {
              this.returnUrl = returnUrl;
       }

       public String getTmnCode() {
              return tmnCode;
       }

       public void setTmnCode(String tmnCode) {
              this.tmnCode = tmnCode;
       }

       public String getHashSecret() {
              return hashSecret;
       }

       public void setHashSecret(String hashSecret) {
              this.hashSecret = hashSecret;
       }

       public String getApiUrl() {
              return apiUrl;
       }

       public void setApiUrl(String apiUrl) {
              this.apiUrl = apiUrl;
       }
}
