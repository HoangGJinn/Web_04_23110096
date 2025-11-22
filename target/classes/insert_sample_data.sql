

DELETE FROM Videos;
DELETE FROM Category;
-- DELETE FROM Users;
-- ALTER TABLE Users AUTO_INCREMENT = 1;
ALTER TABLE Category AUTO_INCREMENT = 1;
ALTER TABLE Videos AUTO_INCREMENT = 1;


INSERT INTO Users (Username, Password, Email, Fullname, Phone, Admin, Active, Images) VALUES
('admin', 'admin123', 'admin@giapweb.com', 'Nguyễn Hoàng Giáp', '0123456789', 1, 1, NULL),
('user1', 'user123', 'user1@giapweb.com', 'Nguyễn Văn A', '0987654321', 0, 1, NULL),
('user2', 'user123', 'user2@giapweb.com', 'Trần Thị B', '0912345678', 0, 1, NULL),
('user3', 'user123', 'user3@giapweb.com', 'Lê Văn C', '0923456789', 0, 1, NULL),
('user4', 'user123', 'user4@giapweb.com', 'Phạm Thị D', '0934567890', 0, 1, NULL);


SET @admin_id = (SELECT Id FROM Users WHERE Username = 'admin' LIMIT 1);
SET @user1_id = (SELECT Id FROM Users WHERE Username = 'user1' LIMIT 1);
SET @user2_id = (SELECT Id FROM Users WHERE Username = 'user2' LIMIT 1);

INSERT INTO Category (Categoryname, Categorycode, Images, Status, UserId) VALUES
('Công nghệ thông tin', 'CNTT', NULL, 1, @admin_id),
('Khoa học máy tính', 'KHTT', NULL, 1, @admin_id),
('Lập trình Web', 'LTW', NULL, 1, @user1_id),
('Lập trình Java', 'LTJ', NULL, 1, @user1_id),
('Cơ sở dữ liệu', 'CSDL', NULL, 1, @user2_id),
('Mạng máy tính', 'MMT', NULL, 1, @user2_id),
('Trí tuệ nhân tạo', 'TTNT', NULL, 1, @admin_id),
('An toàn thông tin', 'ATTT', NULL, 1, @admin_id);


SET @cntt_id = (SELECT Categoryld FROM Category WHERE Categoryname = 'Công nghệ thông tin' LIMIT 1);
SET @lw_id = (SELECT Categoryld FROM Category WHERE Categoryname = 'Lập trình Web' LIMIT 1);
SET @lj_id = (SELECT Categoryld FROM Category WHERE Categoryname = 'Lập trình Java' LIMIT 1);
SET @csdl_id = (SELECT Categoryld FROM Category WHERE Categoryname = 'Cơ sở dữ liệu' LIMIT 1);
SET @mmt_id = (SELECT Categoryld FROM Category WHERE Categoryname = 'Mạng máy tính' LIMIT 1);
SET @ttnt_id = (SELECT Categoryld FROM Category WHERE Categoryname = 'Trí tuệ nhân tạo' LIMIT 1);
SET @attt_id = (SELECT Categoryld FROM Category WHERE Categoryname = 'An toàn thông tin' LIMIT 1);

INSERT INTO Videos (Title, Poster, Views, Description, Active, Categoryld) VALUES
('Giới thiệu về Java Programming', NULL, 150, 'Video giới thiệu về ngôn ngữ lập trình Java, các khái niệm cơ bản và ứng dụng thực tế.', 1, @lj_id),
('HTML và CSS cơ bản', NULL, 200, 'Hướng dẫn học HTML và CSS từ cơ bản đến nâng cao cho người mới bắt đầu.', 1, @lw_id),
('JavaScript ES6+', NULL, 180, 'Tìm hiểu các tính năng mới của JavaScript ES6 và các phiên bản sau đó.', 1, @lw_id),
('Spring Framework Tutorial', NULL, 120, 'Hướng dẫn sử dụng Spring Framework để xây dựng ứng dụng Java Enterprise.', 1, @lj_id),
('MySQL Database Design', NULL, 100, 'Thiết kế và quản lý cơ sở dữ liệu MySQL một cách hiệu quả.', 1, @csdl_id),
('Network Security Basics', NULL, 90, 'Các khái niệm cơ bản về bảo mật mạng và cách bảo vệ hệ thống.', 1, @mmt_id),
('Machine Learning Introduction', NULL, 250, 'Giới thiệu về Machine Learning và các ứng dụng trong thực tế.', 1, @ttnt_id),
('Cybersecurity Fundamentals', NULL, 110, 'Những kiến thức cơ bản về an toàn thông tin và bảo mật hệ thống.', 1, @attt_id),
('JPA và Hibernate', NULL, 140, 'Học cách sử dụng JPA và Hibernate để làm việc với cơ sở dữ liệu trong Java.', 1, @lj_id),
('React.js Tutorial', NULL, 300, 'Hướng dẫn xây dựng ứng dụng web với React.js framework.', 1, @lw_id),
('Python for Data Science', NULL, 220, 'Sử dụng Python để phân tích dữ liệu và khoa học dữ liệu.', 1, @cntt_id),
('Cloud Computing Overview', NULL, 130, 'Tổng quan về điện toán đám mây và các dịch vụ cloud phổ biến.', 1, @cntt_id);

