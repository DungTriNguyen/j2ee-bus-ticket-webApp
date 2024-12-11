package com.bus_station_ticket.project.VNPay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class VNPayService {
       private final VNPayConfig config;

       public VNPayService(VNPayConfig config) {
              this.config = config;
       }

       @Transactional
       public String createOrder(HttpServletRequest request, int amount, String paymentId, String paymentInfor, String returnUrl)
                     throws UnsupportedEncodingException {

              LocalDateTime now = LocalDateTime.now();

              // VNPAY Parameters
              String vnpVersion = "2.1.0";
              String vnpCommand = "pay";
              String vnpTxnRef = paymentId + "_" + now.toString();
              String vnpIpAddr = getIpAddress(request);
              String orderType = "order-type";
              String locale = "vn";


              // Build VNPAY parameters
              Map<String, String> vnpParams = new HashMap<>();
              vnpParams.put("vnp_Version", vnpVersion);
              vnpParams.put("vnp_Command", vnpCommand);
              vnpParams.put("vnp_TmnCode", config.getVnpayDto().getTmnCode());
              vnpParams.put("vnp_Amount", String.valueOf(amount * 100)); // Multiply by 100 to convert to smallest unit
              vnpParams.put("vnp_CurrCode", "VND");
              vnpParams.put("vnp_TxnRef", vnpTxnRef);
              vnpParams.put("vnp_OrderInfo", paymentInfor);
              vnpParams.put("vnp_OrderType", orderType);
              vnpParams.put("vnp_Locale", locale);
              vnpParams.put("vnp_ReturnUrl", returnUrl);
              vnpParams.put("vnp_IpAddr", vnpIpAddr);

              // Add timestamps
              Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
              SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
              String createDate = formatter.format(cld.getTime());
              vnpParams.put("vnp_CreateDate", createDate);

              // thời gian hết giao dịch
              cld.add(Calendar.MINUTE, 3);
              String expireDate = formatter.format(cld.getTime());
              vnpParams.put("vnp_ExpireDate", expireDate);

              // Sort fields and build hashData and query string
              List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
              Collections.sort(fieldNames);

              StringBuilder hashData = new StringBuilder();
              StringBuilder query = new StringBuilder();
              Iterator<String> itr = fieldNames.iterator();

              while (itr.hasNext()) {
                     String fieldName = itr.next();
                     String fieldValue = vnpParams.get(fieldName);

                     if (fieldValue != null && !fieldValue.isEmpty()) {
                            // Build hash data
                            hashData.append(fieldName).append('=')
                                          .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                            // Build query
                            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                                          .append('=')
                                          .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                            if (itr.hasNext()) { // Add '&' if there are more fields
                                   hashData.append('&');
                                   query.append('&');
                            }
                     }
              }

              // Generate secure hash
              String vnpSecureHash = config.hmacSHA512(config.getVnpayDto().getHashSecret(), hashData.toString());
              query.append("&vnp_SecureHash=").append(vnpSecureHash);

              // Return full payment URL
              return config.getVnpayDto().getPayUrl() + "?" + query.toString();
       }

       private String getIpAddress(HttpServletRequest request) {
              String ipAddress = request.getHeader("X-FORWARDED-FOR");
              return (ipAddress != null && !ipAddress.isEmpty()) ? ipAddress : request.getRemoteAddr();
       }

       private String getRandomNumber(int length) {
              Random random = new Random();
              StringBuilder sb = new StringBuilder(length);
              for (int i = 0; i < length; i++) {
                     sb.append(random.nextInt(10));
              }
              return sb.toString();
       }

       public Map<String, String> orderReturn(HttpServletRequest request) {
              Map<String, String> fields = new HashMap<>();
              for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
                     String fieldName = params.nextElement();
                     String fieldValue = request.getParameter(fieldName);
                     if (fieldValue != null && !fieldValue.isEmpty()) {
                            fields.put(fieldName, fieldValue);
                     }
              }

              // Verify secure hash
              // String secureHash = request.getParameter("vnp_SecureHash");
              // fields.remove("vnp_SecureHashType");
              // fields.remove("vnp_SecureHash");

              // String signValue = config.hashAllFields(fields);
              // if (secureHash.equals(signValue)) {
              // return "00".equals(request.getParameter("vnp_TransactionStatus")) ? 1 : 0;
              // }
              // return -1;
              return fields;
       }

       public String checkTransactionStatus(String vnpTxnRef) throws UnsupportedEncodingException {
              // VNPAY Parameters
              String vnpVersion = "2.1.0";
              String vnpCommand = "querydr";
              String vnpIpAddr = "127.0.0.1"; // IP server thực hiện truy vấn

              // Build VNPAY parameters
              Map<String, String> vnpParams = new HashMap<>();
              vnpParams.put("vnp_Version", vnpVersion);
              vnpParams.put("vnp_Command", vnpCommand);
              vnpParams.put("vnp_TmnCode", config.getVnpayDto().getTmnCode());
              vnpParams.put("vnp_TxnRef", vnpTxnRef);
              vnpParams.put("vnp_IpAddr", vnpIpAddr);

              // Add timestamps
              Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
              SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
              String createDate = formatter.format(cld.getTime());
              vnpParams.put("vnp_CreateDate", createDate);

              // Sort fields and build hashData and query string
              List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
              Collections.sort(fieldNames);

              StringBuilder hashData = new StringBuilder();
              StringBuilder query = new StringBuilder();
              for (String fieldName : fieldNames) {
                     String fieldValue = vnpParams.get(fieldName);
                     if (fieldValue != null && !fieldValue.isEmpty()) {
                            hashData.append(fieldName).append('=')
                                          .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()))
                                          .append('&');
                            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=')
                                          .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()))
                                          .append('&');
                     }
              }

              if (hashData.length() > 0) {
                     hashData.setLength(hashData.length() - 1); // Remove trailing "&"
                     query.setLength(query.length() - 1); // Remove trailing "&"
              }

              // Generate secure hash
              String vnpSecureHash = config.hmacSHA512(config.getVnpayDto().getHashSecret(), hashData.toString());
              query.append("&vnp_SecureHash=").append(vnpSecureHash);

              // Send request to VNPay
              RestTemplate restTemplate = new RestTemplate();
              String requestUrl = config.getVnpayDto().getPayUrl() + "?" + query.toString();
              ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);

              return response.getBody(); // Trả về trạng thái từ VNPay
       }

       public void handleFailedPayment(String vnpTxnRef, long timeoutMs) {
              long startTime = System.currentTimeMillis();
              boolean isTransactionSuccessful = false;

              while (System.currentTimeMillis() - startTime < timeoutMs) {
                     try {
                            String response = checkTransactionStatus(vnpTxnRef);
                            // Parse response to determine status
                            if (response.contains("\"vnp_ResponseCode\":\"00\"")) { // "00" indicates success
                                   isTransactionSuccessful = true;
                                   break;
                            }
                     } catch (Exception e) {
                            // Log error but continue to retry
                            System.err.println("Error while checking transaction status: " + e.getMessage());
                     }

                     try {
                            Thread.sleep(5000); // Retry every 5 seconds
                     } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                     }
              }

              if (!isTransactionSuccessful) {
                     // Handle failed payment (e.g., update order status, notify user)
                     System.out.println("Payment failed for transaction: " + vnpTxnRef);
                     // Add your failure handling logic here
              }
       }

       // public void createOrderAndHandleFailure(HttpServletRequest request, int
       // amount, String orderInfo,
       // String returnUrl)
       // throws UnsupportedEncodingException {
       // String vnpTxnRef = getRandomNumber(8); // Tạo mã giao dịch

       // // Tạo đơn hàng
       // String paymentUrl = createOrder(request, amount, orderInfo, returnUrl);
       // System.out.println("Payment URL: " + paymentUrl);

       // // Xử lý thanh toán thất bại trong vòng 10 phút (600000 ms)
       // new Thread(() -> handleFailedPayment(vnpTxnRef, 600000)).start();
       // }
}
