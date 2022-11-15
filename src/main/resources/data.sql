drop table if exists address cascade;
drop table if exists payment cascade;
drop table if exists profile cascade;
drop table if exists user cascade;



create table address (
    id varchar(60) primary key,
    user_id varchar(60) not null,
    line1 varchar(100) not null,
    line2 varchar(100) null,
    city varchar(100) not null,
    state varchar(100) not null,
    zip varchar(10) not null,
    updated timestamp not null default current_timestamp()
);

create table payment (
    id varchar(60) primary key,
    user_id varchar(60) not null,
    card_number varchar(16) not null unique,
    expiry_month integer not null,
    expiry_year integer not null,
    updated timestamp not null default current_timestamp()
);

create table profile (
    profile_id varchar(60) primary key,
    user_id varchar(60) not null,
    username varchar(60) not null unique,
    password varchar(60) not null,
    picture_url varchar(100) not null,
    updated timestamp not null default current_timestamp()
);


create table user (
    id varchar(60) primary key,
    first_name varchar(100) not null,
    middle_name varchar(100) null,
    last_name varchar(100) not null,
    phone_number varchar(10) not null,
    updated timestamp not null default current_timestamp()
);


insert into user (id, first_name, middle_name, last_name, phone_number) values
('008a4215-0b1d-445e-b655-a964039cbb5a', 'Joyce', 'Lucas', 'Roberts', '6422107303'),
('00963d9b-f884-485e-9455-fcf30c6ac379', 'Cadie', 'Albert', 'Hall', '2072310564'),
('00bed3ac-5f3c-4a2d-a67b-80376ea9f941', 'Cadie', null, 'Foster', '2501424635'),
('0111d3ca-514b-4ae8-8f57-e85cca43fb1e', 'Reid', 'Hawkins', 'Edwards', '6262144035'),
('01316816-0cb7-41c4-8424-8367294aea27', 'Stella', null, 'Dixon', '3522332164'),
('01552e12-64ba-4bdc-8adf-a4fa0b9e70dd', 'Ellia', null, 'Chapman', '4712621122'),
('01e24e4e-1018-40fa-b92a-a7ad669e7805', 'Chelsea', 'Harper', 'Hamilton', '4132644404'),
('024b9a53-ae41-4342-9ee7-21bccd617252', 'Alexia', 'Lily', 'Payne', '2746761540'),
('028b13a3-f083-4e89-993a-e9a4f88d5e5f', 'Madaline', null, 'Johnston', '2156562646'),
('02e4bac8-5f28-4db3-b411-09dbfe68a009', 'Frederick', 'George', 'Cole', '4121305536'),
('039b0f6b-91cb-434d-8ad4-3fee5a025e11', 'Carina', null, 'Ross', '5337743527'),
('04c8c3a2-e126-45a1-b068-5da4972a8cb3', 'Emily', null, 'Clark', '4645411336'),
('05f923d0-d871-46b3-a180-cceda11aa7db', 'Amy', 'Chester', 'Casey', '3017524755'),
('06c70a5b-1950-4765-81b6-4bb2378e0991', 'Jack', null, 'Cole', '6066154071'),
('06f86959-0bf7-49ae-add8-f536452f7166', 'Agata', null, 'Williams', '2641235046'),
('0764c581-2387-4f45-9034-5a4d4d4eeae2', 'Naomi', null, 'Adams', '2667153424'),
('0770128d-206f-48b8-b370-6f4bd2db60f5', 'Aston', null, 'Hawkins', '4534321373'),
('07b53819-a95a-4d4e-908c-0b435e97bd5b', 'Amy', null, 'Carter', '5115123162'),
('0862620c-ef75-4034-a4ca-6b082f420310', 'Emily', null, 'Murphy', '4156445472'),
('0865dbbc-e4b9-4a22-af03-b4d67f56f07b', 'Aston', null, 'Russell', '3142112260'),
('0876efba-8f38-40c5-9022-01da55c2ede5', 'Lilianna', 'Alan', 'Elliott', '2325402732'),
('08947d7f-8ae6-4d60-bfc6-0f9bd8f4136b', 'Kate', 'Olivia', 'Grant', '2432622315'),
('09069452-fe49-422d-863f-e856c3399d0b', 'Emma', 'Gianna', 'Cunningham', '7311063034'),
('09377de6-e252-4cf7-93ad-453ff4d5d39c', 'Ashton', null, 'Brooks', '2432142632'),
('0938d977-62c0-4e1d-90fb-45cadbc24b1f', 'Charlotte', null, 'Anderson', '4532064510'),
('09d11075-b651-453e-85f1-ddaf9c5b7ac8', 'Vincent', null, 'Wilson', '5316264046'),
('0aaf13c5-c73f-457a-8892-67d094ef5959', 'Audrey', null, 'Ellis', '1764414422'),
('0acbe59f-b68c-422d-a925-35a5cd770e60', 'Roman', 'Vivian', 'Scott', '1542253157'),
('0b78b242-6469-4eaa-96cc-4df7648c3834', 'Valeria', 'Luke', 'Hall', '6743165471'),
('0bcabffe-3a42-4a39-a10b-cd780f050154', 'Vivian', 'Hill', 'Turner', '3141221435'),
('0c21b8a2-b4df-44c0-b6c8-3a0f5ceaf917', 'Clark', 'Hailey', 'Farrell', '4007457655'),
('0c59501d-9919-42a0-8647-c4c5180e2403', 'Darcy', 'Joyce', 'Hamilton', '5406366321'),
('0d67efcd-62fb-4d11-af50-bc197281d11c', 'Chelsea', null, 'Edwards', '6551117215'),
('0d6d21cc-7f6f-4cd2-8567-e347db5edf02', 'Aida', 'Maya', 'Spencer', '2164433707'),
('0d7a89ef-c959-48f4-b22d-304cf4bb0bcf', 'Byron', null, 'Clark', '4215630042'),
('0d7b8f3b-72fd-40f4-93e3-40224fa6fc87', 'Eddy', 'Williams', 'Brown', '6164124175'),
('0deb001b-07c1-4fbd-866b-a3f0a7721206', 'Alfred', 'Jack', 'Elliott', '3507665517'),
('0e7495f2-f83d-4739-aaa5-ae45e8d59a58', 'April', null, 'Perkins', '1221516423'),
('0ec7d87d-3196-439c-a9e3-dced9a6470d4', 'Edwin', 'Paul', 'Owens', '3212543633'),
('0f0c17b5-5fe6-45c2-a841-e819e2a56ec6', 'Wilson', 'Darcy', 'Ellis', '1466320540'),
('0fa26435-0d04-4afe-b7aa-9a86d71b33a4', 'Clark', null, 'West', '5221356551');
--('107f2920-9b79-4112-802c-47bc145acd56', 'Carlos', 'Lyndon', 'Perkins', '1136532503'),
--('10cfaadf-aeaa-425c-9a4f-93c866b2085f', 'Melissa', null, 'Walker', '5126136126'),
--('1112927c-29bc-436f-a4a4-40336b885ad8', 'Maximilian', null, 'Harris', '5145511233'),
--('12b9a975-59d8-4532-828f-5cdb69bc7af9', 'Ned', 'Fenton', 'Myers', '2365136376'),
--('12cc4be1-d2b0-42c5-9287-359a6da6c190', 'Audrey', null, 'Stevens', '6243055512'),
--('12e5be2f-369f-47ee-b0aa-53373964bb0b', 'Charlie', null, 'Bennett', '1416210330'),
--('13112a22-3217-4aff-98c2-382f5aa01bc0', 'Jordan', null, 'Myers', '3752353662'),
--('137d4c32-f60a-4007-aee8-a1177622d51d', 'Jack', null, 'Thomas', '1135433110'),
--('137f0f93-a5a2-4d3b-9c65-33be9382a13a', 'Sophia', 'Elise', 'Kelly', '5021401244'),
--('143fb599-800d-40c9-9119-8b2e6c5448d7', 'Charlotte', null, 'Douglas', '4266445245'),
--('14548bdc-bb36-48c5-8fd0-566b7077ea16', 'Alisa', null, 'Henderson', '5232332532'),
--('146ac8a8-0688-47a6-b9b5-ca4a5800f7da', 'Leonardo', null, 'Elliott', '2642241530'),
--('1693fe23-0122-48f9-8114-03e7aa961336', 'Vivian', 'Mason', 'Wilson', '6206525045'),
--('16b1fe90-c750-4c47-b693-9abb7d9b4d08', 'Paige', null, 'Alexander', '6431754156'),
--('16d8a3e2-d3d7-48fa-b693-5d1cea7af6dd', 'Sam', null, 'Cooper', '2523733264'),
--('17207515-223d-4573-80b9-f29643de0fe5', 'Tony', null, 'Hill', '4001175227'),
--('1744e840-b8d5-4f53-ad53-c8c3ba79f7b4', 'Tiana', null, 'Murphy', '5460111014'),
--('17df0b40-1e49-47b3-9051-77ccc36c8b6c', 'Byron', null, 'Cunningham', '4634333466'),
--('18227685-5345-46f7-96b0-c8428e0a2153', 'Arnold', 'Mike', 'Murphy', '6127511317'),
--('18bf879b-bcb4-411f-974c-df5dcdccbffe', 'Lucy', 'Wells', 'Hill', '1102313674'),
--('19fc541f-ed26-40da-b35e-577b83a62c29', 'Adele', 'Clark', 'Hill', '2630650533'),
--('1a1ff94b-4ed3-4dc6-adfb-0cbf3a145438', 'Spike', null, 'Henderson', '6431464247'),
--('1a2bead2-462b-4874-b77c-cae32ec8c97a', 'Patrick', 'Ellia', 'Davis', '4211635014'),
--('1a76c47c-583f-453c-b08f-25477e7f727e', 'Tyler', 'Melanie', 'Ryan', '2116152616'),
--('1a8e8c08-9235-443c-a0ca-eb9be921448a', 'Emily', null, 'Cameron', '6253766534'),
--('1b26f601-20f6-4529-bdfb-ee5f655d46f2', 'Brianna', null, 'Carter', '2541155167'),
--('1b2855e1-b916-401d-9dfd-f60a39c15a75', 'Audrey', 'Connie', 'Morris', '5633523411'),
--('1c3bd972-4fb6-4914-be72-e0538a529acb', 'Daryl', null, 'Bailey', '2110250243'),
--('1d0b9cd8-4e27-4152-98b9-38c9c864b746', 'Harold', null, 'Taylor', '1616567645'),
--('1ee7da1f-fdfa-498e-82b9-65d52a350dd8', 'Sofia', null, 'Spencer', '4052223765'),
--('1f41ae51-2099-4bf0-9721-38ece0decd35', 'Michelle', null, 'Craig', '5214551240'),
--('1f8be5f2-a472-4894-97a1-ca680f0113eb', 'Edgar', null, 'Morgan', '4615162736'),
--('2018c3ed-a8d4-4452-a68f-31ab7ca379c6', 'Justin', 'Julian', 'Douglas', '4127564124');

insert into address (id, user_id, line1, line2, city, state, zip) values
('42f33d30-f3f8-4743-a94e-4db11fdb747d', '008a4215-0b1d-445e-b655-a964039cbb5a', '412 Maple St', null, 'Dowagiac', 'Michigan', '49047'),
('579872ec-46f8-46b5-b809-d0724d965f0e', '00963d9b-f884-485e-9455-fcf30c6ac379', '237 Mountain Ter', 'Apt 10', 'Odenville', 'Alabama', '35120'),
('95a983d0-ba0e-4f30-afb6-667d4724b253', '00963d9b-f884-485e-9455-fcf30c6ac379', '107 Annettes Ct', null, 'Aydlett', 'North Carolina', '27916');




insert into payment (id, user_id, card_number, expiry_month, expiry_year) values
('001', '008a4215-0b1d-445e-b655-a964039cbb5a', '1234567890123456', 09, 2023),
('002', '008a4215-0b1d-445e-b655-a964039cbb5a', '1029384756123123', 09, 2023);


