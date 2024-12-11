
-- Đây là file được dùng để test database, phải chạy bằng tay 

-- Mật khẩu admin là 123456Admin@
-- Mật khẩu manager là 123456Manager@
-- Mật khâu staff la 123456Staff@

-- user
-- Mật khâu user la 123456User@

-- Thêm dữ liệu vào account
INSERT INTO account (username, password, email, full_name, phone, ROLE, is_delete, is_block)
VALUES
('thinh','$2a$10$h1IlhlhelQn9vVtQUijs9eONe037IpghOwFawhGZHsHDBIu8/Yzuq', 'admin@gmail.com', 'Trần Khang Thịnh', '0987654321', 'ROLE_ADMIN', 0, 0),
('phuc','$2a$10$h1IlhlhelQn9vVtQUijs9eONe037IpghOwFawhGZHsHDBIu8/Yzuq', 'admin@gmail.com', 'Trương Công Phúc', '0823072871', 'ROLE_ADMIN', 0, 0),
('dung','$2y$10$rZ1fVKVZqA0e/N9mBDR6Qe2rpN1ATAhpCcFY0UZYM8Rh12RD.CW.K', 'manager@gmail.com', 'Nguyễn Trí Dũng', '0987654321', 'ROLE_MANAGER', 0, 0),
('quynh','$2y$10$mPchIPcw2PP6XW6r4KoQK./kmkr1zaBiCRXpQdGSIM9VRVRS5l2R6', 'user@gmail.com', 'Cao Bảo Quỳnh', '0823072871', 'ROLE_STAFF', 0, 0),
('toan','$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user@gmail.com', 'Nguyễn Quốc Toàn', '0823072871', 'ROLE_USER', 0, 0),
('an','$2y$10$Ge82QQtEWcexV69AJsSOq.j7AxmKmNoCI9IwusEyIIOeU3wHvaaBq', 'user@gmail.com', 'Lê Vạn An', '0823072871', 'ROLE_USER', 0, 0),
('hai','$2y$10$luF7URSbx7DiJikHSOnEBuFkM1mT4/ANzecbvp1wWW0.IgIlkMZuK', 'user@gmail.com', 'Lê Tiến Hải', '0823072871', 'ROLE_USER', 0, 0),
('linh', '$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user1@gmail.com', 'Trần Văn Linh', '0823011122', 'ROLE_USER', 0, 0),
('hieu', '$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user2@gmail.com', 'Nguyễn Văn Hiếu', '0823022233', 'ROLE_USER', 0, 0),
('minh', '$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user3@gmail.com', 'Đặng Quốc Minh', '0823033344', 'ROLE_USER', 0, 0),
('thao', '$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user4@gmail.com', 'Phạm Thị Thảo', '0823044455', 'ROLE_USER', 0, 0),
('giang', '$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user5@gmail.com', 'Lê Phương Giang', '0823055566', 'ROLE_USER', 0, 0),
('viet', '$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user6@gmail.com', 'Hoàng Văn Việt', '0823066677', 'ROLE_USER', 0, 0),
('phong', '$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user7@gmail.com', 'Ngô Thành Phong', '0823077788', 'ROLE_USER', 0, 0),
('nhung', '$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user8@gmail.com', 'Trần Thị Nhung', '0823088899', 'ROLE_USER', 0, 0),
('khanh', '$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user9@gmail.com', 'Phạm Văn Khánh', '0823099900', 'ROLE_USER', 0, 0),
('duy', '$2y$10$YsmlgKlBB76y6g9QvF004.Kloh6CEsmJntC428k5R3eFqauMUdcp2', 'user10@gmail.com', 'Nguyễn Duy Anh', '0823100011', 'ROLE_USER', 0, 0),
('dat','$2y$10$WQ4nZmE44PveXScIGt9ljeE/fFAKkUCDJRak0reMerttCLAO/xZ8a', 'user@gmail.com', 'Đỗ Tiến Đạt', '0823072871', 'ROLE_USER', 0, 0);


-- Thêm dữ liệu vào discount
INSERT INTO discount (discount_percentage, valid_from, valid_until, amount, is_delete) VALUES
(10.0, '2024-01-01 00:00:00', '2024-12-31 23:59:59', 100, 0),
(20.0, '2024-06-01 00:00:00', '2024-09-30 23:59:59', 50, 0),
(15.0, '2024-02-01 00:00:00', '2024-02-28 23:59:59', 75, 0),
(25.0, '2024-03-01 00:00:00', '2024-03-31 23:59:59', 100, 0),
(5.0,  '2024-04-01 00:00:00', '2024-04-15 23:59:59', 30, 0),
(30.0, '2024-07-01 00:00:00', '2024-07-31 23:59:59', 150, 0),
(40.0, '2024-08-01 00:00:00', '2024-08-31 23:59:59', 200, 0),
(10.0, '2024-09-15 00:00:00', '2024-10-15 23:59:59', 50, 0),
(50.0, '2024-11-01 00:00:00', '2024-11-30 23:59:59', 250, 0),
(60.0, '2024-12-01 00:00:00', '2024-12-31 23:59:59', 300, 0),
(35.0, '2024-01-15 00:00:00', '2024-03-15 23:59:59', 125, 0),
(20.0, '2024-05-01 00:00:00', '2024-06-30 23:59:59', 100, 0);

-- bus_routes
-- Chèn dữ liệu cho bảng bus_routes
INSERT INTO bus_routes 
(departure_location, destination_location, distance_location, trip_time, price, is_delete) 
VALUES 
('Hà Nội', 'Hải Phòng', 102.5, '02:30:00', 150000, false),
('Hà Nội', 'Ninh Bình', 90.0, '02:00:00', 120000, false),
('Hải Phòng', 'Quảng Ninh', 75.5, '01:45:00', 100000, false),
('Hà Nội', 'Vinh', 298.0, '05:30:00', 250000, false),
('Đà Nẵng', 'Hội An', 30.5, '01:00:00', 50000, false),
('Hồ Chí Minh', 'Vũng Tàu', 125.0, '03:00:00', 180000, false),
('Cần Thơ', 'Sóc Trăng', 60.5, '01:30:00', 80000, false),
('Đà Lạt', 'Nha Trang', 132.0, '03:15:00', 200000, false),
('Hà Nội', 'Thanh Hóa', 180.5, '04:00:00', 220000, false),
('Hồ Chí Minh', 'Đà Lạt', 300.0, '06:30:00', 350000, false),
('Huế', 'Đà Nẵng', 105.0, '02:45:00', 150000, false),
('Hải Phòng', 'Hà Nội', 102.5, '02:30:00', 150000, false),
('Quảng Ninh', 'Hải Phòng', 75.5, '01:45:00', 100000, false),
('Vinh', 'Hà Nội', 298.0, '05:30:00', 250000, false),
('Hội An', 'Đà Nẵng', 30.5, '01:00:00', 50000, false),
('Vũng Tàu', 'Hồ Chí Minh', 125.0, '03:00:00', 180000, false),
('Sóc Trăng', 'Cần Thơ', 60.5, '01:30:00', 80000, false),
('Nha Trang', 'Đà Lạt', 132.0, '03:15:00', 200000, false),
('Thanh Hóa', 'Hà Nội', 180.5, '04:00:00', 220000, false),
('Đà Lạt', 'Hồ Chí Minh', 300.0, '06:30:00', 350000, false),
('Đà Nẵng', 'Huế', 105.0, '02:45:00', 150000, false);


-- employee
INSERT INTO employee (driver_id, driver_name, license_number, phone_number, is_driver, is_delete) VALUES
(1,'Nguyễn Văn A', '080203005137', '0901234567', 1, 0),
(2,'Trần Văn B', '090203001234', '0901234567', 1, 0),
(3, 'Phạm Thị C', '080104002345', '0934567890', 1, 0),
(4, 'Lê Quốc D', '070305003456', '0976543210', 1, 0),
(5, 'Ngô Thị E', '060406004567', '0812345678', 1, 0),
(6, 'Hoàng Văn F', '050507005678', '0834567891', 1, 0),
(7, 'Nguyễn Thị G', '040608006789', '0856789012', 1, 0),
(8, 'Đặng Quốc H', '030709007890', '0878901234', 1, 0),
(9, 'Phan Thị I', '020801008901', '0890123456', 1, 0),
(10, 'Đỗ Văn J', '010902009012', '0709876543', 1, 0),
(11, 'Lý Thị K', '110203000123', '0901112223', 1, 0);


-- bus
INSERT INTO bus (bus_id, brand, bus_number, capacity, is_delete) 
VALUES
(1, 'Toyota', '79A-12345', 45, 0),
(2, 'Hyundai', '30B-67890', 50, 0),
(3, 'Ford', '92C-54321', 30, 0),
(4, 'Mercedes', '29D-98765', 40, 0),
(5, 'Isuzu', '51E-45678', 55, 0),
(6, 'Hino', '60F-11223', 60, 0),
(7, 'Kia', '83G-44556', 25, 0),
(8, 'Daewoo', '84H-77889', 50, 0),
(9, 'Mitsubishi', '99K-66778', 35, 0),
(10, 'Volvo', '45L-12367', 40, 0),
(11, 'Toyota', '70M-11223', 40, 0),
(12, 'Hyundai', '72N-44556', 50, 0),
(13, 'Ford', '74P-77889', 35, 0),
(14, 'Mercedes', '75Q-66778', 45, 0),
(15, 'Isuzu', '77R-55667', 55, 0),
(16, 'Hino', '78S-33445', 60,  0),
(17, 'Kia', '79T-22334', 30,  0),
(18, 'Daewoo', '81U-88990', 50,  0),
(19, 'Mitsubishi', '82V-99887', 40,  0),
(20, 'Volvo', '83W-77665', 60,  0);


-- bus-employee
INSERT INTO bus_employee (bus_id, driver_id) 
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11), -- Thêm dữ liệu mới
(12, 1),
(13, 2),
(14, 3),
(15, 4),
(16, 5),
(17, 6),
(18, 7),
(19, 8),
(20, 9);


--penalty ticket
INSERT INTO penalty_ticket (penalty_ticket_id,bus_id,driver_id,penalty_day,description,responsibility,price,is_delete) VALUES
(1,1,1,'2024-01-05 14:30:00', 'Vi phạm quá tốc độ', TRUE,200.0, FALSE),
(2,1,2,'2024-01-05 14:30:00', 'Vi phạm quá tốc độ', TRUE,200.0, FALSE);


-- payment
INSERT INTO payment (payment_id, payment_time, original_amount, discount_amount, final_amount, payment_method, status, is_delete) 
VALUES
(1,'2024-11-15 10:00:00', 500000, 50000, 450000, 'VNPay', 'success', 0),
(2,'2024-11-16 12:30:00', 300000, 30000, 270000, 'VNPay', 'success', 0),
(3,'2024-11-17 09:45:00', 400000, 20000, 380000, 'VNPay', 'pending', 0),
(4,'2024-11-18 14:00:00', 600000, 60000, 540000, 'VNPay', 'success', 0),
(5,'2024-11-19 08:15:00', 700000, 70000, 630000, 'VNPay', 'failure', 0),
(6,'2024-11-20 16:45:00', 800000, 0, 800000, 'VNPay', 'success', 0),
(7,'2024-11-21 11:30:00', 550000, 50000, 500000, 'VNPay', 'pending', 0),
(8,'2024-11-22 15:10:00', 250000, 0, 250000, 'VNPay', 'success', 0),
(9,'2024-11-23 17:20:00', 450000, 45000, 405000, 'VNPay', 'failure', 0),
(10,'2024-11-24 10:50:00', 600000, 0, 600000, 'VNPay', 'success', 0);


-- Chèn dữ liệu cho bảng bus_route_schedule

-- Tuyến Hà Nội - Hải Phòng
-- Tuyến Hà Nội - Ninh Bình
-- Tuyến Hải Phòng - Quảng Ninh
-- Tuyến Hà Nội - Vinh
-- Tuyến Đà Nẵng - Hội An
-- Tuyến Hồ Chí Minh - Vũng Tàu
-- Tuyến Cần Thơ - Sóc Trăng
-- Tuyến Đà Lạt - Nha Trang
-- Tuyến Hà Nội - Thanh Hóa
-- Tuyến Hồ Chí Minh - Đà Lạt
-- Tuyến Huế - Đà Nẵng

INSERT INTO bus_routes_schedule 
(day_of_week, bus_id, routes_id, departure_time, is_delete) 
VALUES 
('Monday', 1, 1, '06:00:00', false),
('Wednesday', 1, 1, '06:00:00', false),
('Friday', 1, 1, '06:00:00', false),
('Tuesday', 2, 2, '07:30:00', false),
('Thursday', 2, 2, '07:30:00', false),
('Saturday', 2, 2, '07:30:00', false),
('Monday', 3, 3, '08:15:00', false),
('Wednesday', 3, 3, '08:15:00', false),
('Friday', 3, 3, '08:15:00', false),
('Tuesday', 4, 4, '05:00:00', false),
('Thursday', 4, 4, '05:00:00', false),
('Saturday', 4, 4, '05:00:00', false),
('Monday', 5, 5, '10:00:00', false),
('Wednesday', 5, 5, '10:00:00', false),
('Friday', 5, 5, '10:00:00', false),
('Tuesday', 6, 6, '06:30:00', false),
('Thursday', 6, 6, '06:30:00', false),
('Saturday', 6, 6, '06:30:00', false),
('Monday', 7, 7, '09:00:00', false),
('Wednesday', 7, 7, '09:00:00', false),
('Friday', 7, 7, '09:00:00', false),
('Tuesday', 8, 8, '07:00:00', false),
('Thursday', 8, 8, '07:00:00', false),
('Saturday', 8, 8, '07:00:00', false),
('Monday', 9, 9, '05:30:00', false),
('Wednesday', 9, 9, '05:30:00', false),
('Friday', 9, 9, '05:30:00', false),
('Tuesday', 10, 10, '04:00:00', false),
('Thursday', 10, 10, '04:00:00', false),
('Saturday', 10, 10, '04:00:00', false),
('Monday', 11, 11, '11:00:00', false),
('Wednesday', 11, 11, '11:00:00', false),
('Friday', 11, 11, '11:00:00', false),
('Tuesday', 12, 12, '06:15:00', false),
('Thursday', 12, 12, '06:15:00', false),
('Saturday', 12, 12, '06:15:00', false),
('Tuesday', 13, 13, '08:30:00', false),
('Thursday', 13, 13, '08:30:00', false),
('Saturday', 13, 13, '08:30:00', false),
('Monday', 14, 14, '05:15:00', false),
('Wednesday', 14, 14, '05:15:00', false),
('Friday', 14, 14, '05:15:00', false),
('Tuesday', 15, 15, '10:15:00', false),
('Thursday', 15, 15, '10:15:00', false),
('Saturday', 15, 15, '10:15:00', false),
('Monday', 16, 16, '06:45:00', false),
('Wednesday', 16, 16, '06:45:00', false),
('Friday', 16, 16, '06:45:00', false),
('Tuesday', 17, 17, '09:15:00', false),
('Thursday', 17, 17, '09:15:00', false),
('Saturday', 17, 17, '09:15:00', false),
('Monday', 18, 18, '07:15:00', false),
('Wednesday', 18, 18, '07:15:00', false),
('Friday', 18, 18, '07:15:00', false),
('Tuesday', 19, 19, '05:45:00', false),
('Thursday', 19, 19, '05:45:00', false),
('Saturday', 19, 19, '05:45:00', false);



-- ticket
-- Chèn dữ liệu mẫu vào bảng ticket
INSERT INTO ticket 
(username_id, schedule_id, payment_id, discount_id, seat_number, departure_date, price, phone, status, is_delete) 
VALUES 
('toan', 1, 1, NULL, '1_A01', '2024-03-20', 150000, '0823072871', 'success', 0),
('toan', 2, 2, NULL, '1_A02', '2024-03-22', 120000, '0823072871', 'success', 0),
('an', 3, 3, 1, '1_A03', '2024-03-21', 100000, '0823072871', 'success', 0),
('an', 4, 4, NULL, '2_A02', '2024-03-23', 250000, '0823072871', 'pending', 0),
('hai', 5, 5, NULL, '2_A04', '2024-03-20', 50000, '0823072871', 'success', 0),
('hai', 6, 6, 2, '2_A07', '2024-03-22', 180000, '0823072871', 'success', 0),
('linh', 7, 7, NULL, '3_A01', '2024-03-21', 80000, '0823011122', 'success', 0),
('linh', 8, 8, NULL, '3_A05', '2024-03-23', 200000, '0823011122', 'pending', 0),
('hieu', 9, 9, 3, '3_A09', '2024-03-20', 220000, '0823022233', 'success', 0),
('hieu', 10, 10, NULL, '4_A02', '2024-03-22', 350000, '0823022233', 'success', 0);






-- feedback
INSERT INTO feedback (feedback_id, username, content, ticket_id, date_comment, rating, is_delete) VALUES
(1, 'toan', 'Dịch vụ rất tốt, tài xế thân thiện.', 1, '2024-11-10', 5, 0),
(2, 'an', 'Xe buýt sạch sẽ và đúng giờ.', 2, '2024-11-11', 4, 0),
(3, 'hai', 'Tài xế lái xe an toàn nhưng hơi chậm.', 3, '2024-11-12', 3, 0),
(4, 'linh', 'Hành trình không như mong đợi, xe trễ giờ.', 4, '2024-11-13', 2, 0),
(5, 'linh', 'Ghế ngồi không thoải mái, cần cải thiện.', 5, '2024-11-14', 3, 0),
(6, 'hieu', 'Tài xế không lịch sự, không hài lòng.', 6, '2024-11-15', 1, 1),
(7, 'minh', 'Giá vé hợp lý, dịch vụ ổn.', 7, '2024-11-16', 4, 0),
(8, 'thao', 'Điểm đến không đúng giờ, xe cũ.', 8, '2024-11-17', 2, 0),
(9, 'giang', 'Xe mới, thoải mái, tài xế nhiệt tình.', 9, '2024-11-18', 5, 0),
(10, 'viet', 'Quá đông, không đủ ghế ngồi.', 10, '2024-11-19', 1, 0);















