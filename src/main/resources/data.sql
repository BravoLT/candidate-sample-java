drop table if exists user;

create table user (
    id varchar(60) primary key,
    first_name varchar(100) not null,
    middle_name varchar(100) null,
    last_name varchar(100) not null,
    phone_number varchar(10) not null,
    email varchar(50) ,
    password varchar(100) ,
    user_role varchar(10) ,
    updated timestamp not null default current_timestamp()
);

insert into user (id, first_name, middle_name, last_name, phone_number,email,password,user_role) values
('008a4215-0b1d-445e-b655-a964039cbb5a', 'Joyce', 'Lucas', 'Roberts', '6422107303','mdhrashid@gmail.com','wItYU3/P5rwSW5HKKni7tQ==','admin'),
('008a4215-0b1d-445e-b656-a964039cbb5a', 'Md', 'Kabir', 'Humayun', '6422107303','harunor.md@gmail.com','m1AZoQxQifLdPjNMn/AVtw==','admin'),
('008a4215-0b1d-445e-b657-a964039cbb5a', 'Rashedul', 'Kabur', 'Habib', '6422107303','rased.kabir@gmail.com','GoZmcw22Sr908nHQYA+5uw==','admin'),
('008a4215-0b1d-445e-b658-a964039cbb5a', 'Moon', 'Rashid', 'Jarinur', '6422107303','asma.akhter@gmail.com','FHJ5mQCR7PZ/tJf7AUoriw==','admin'),
('008a4215-0b1d-445e-b659-a964039cbb5a', 'Aurin', 'Rashid', 'Habida', '6422107303','jihan.kabir@gmail.com','xKaNau/Cxv4n0NeMyUshfw==','admin'),
('008a4215-0b1d-445e-b651-a964039cbb5a', 'Malyka', 'Sarwat', 'H', '6422107303','mamunul.haque@gmail.com','mG7z057fW+NbEQRaA1QLZQ==','admin'),
('008a4215-0b1d-445e-b652-a964039cbb5a', 'Payel', 'Mahmud', 'Chowdury', '6422107303','payel.mahmud@gmail.com','FoJey7ZRFiEdFrIJyaaUUw==','admin'),
('008a4215-0b1d-445e-b653-a964039cbb5a', 'Mamun', 'Rashid', 'Al', '6422107303','nirav.padwala@gmail.com','f0JyLKVEnXxCuWzULmNAAw==','admin'),
('008a4215-0b1d-445e-b654-a964039cbb5a', 'Pinak', 'Gosh', 'Chowdhury', '6422107303','dhawal.satish@gmail.com','zvdcbDBkLDA7wev/nyngzQ==','admin'),
('008a4215-0b1d-445e-b665-a964039cbb5a', 'Joyce', 'Lucas', 'Roberts', '6422107303','sachin.kumar@gmail.com','FRdoys941A6UqpVqhDU6+A==','admin');


