Create Table [User] (
	ID int Identity(1,1) Primary Key,
	email nvarchar(100) not null unique,
	username nvarchar(50) not null unique,
	fullname nvarchar(50) not null,
	password nvarchar(100) not null,
	avatar nvarchar(200) null,
	roleid int not null default 5,
	phone nvarchar(40) not null unique,
	createddate date not null
)

Insert into [User] (email, username, fullname, password, avatar, roleid, phone, createddate)
values
	('user1@example.com', 'user1', 'Nguyen Van A', 'password123', NULL, 5, '0123456789', GETDATE()),
	('user2@example.com', 'user2', 'Tran Thi B', 'password456', NULL, 5, '0987654321', GETDATE())