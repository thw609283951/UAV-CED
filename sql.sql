create table user (name varchar(10) primary key not null,password varchar(10) not null);
--insert into user (name,password)values('hyc','hyc');
create table uavonline (
ipaddress varchar(15) not null,
port int not null,
primary key(ipaddress,port),
 longitude double(9,6) not null,
latitude double (8,6) not null,
elevation double(4,2) not null ,
height double(6,2) not null ,
velocity double(6,2) not null,
powerconsumption double(6,2) not null,
remainingpower int(2) not null,
version varchar(5) not null
);
--insert into uavonline(ipaddress,port,longitude,latitude,elevation ,height ,velocity ,powerconsumption,remainingpower ,version) 
values('192.163.146.34',5000,126.633344,45.745019,30.12,1245.43,1234.43,12.45,34,'00001');

create table res (version varchar(5) not null primary key,restrictheight double(6,2),restrictremainingpower  int(2));
--insert into res values (5000,6000.00,10);

create table historicalpath(inde int auto_increment primary key,ipaddress varchar(15) not null,port int not null,longitude double(9,6) not null,latitude double (8,6) not null);



create table banarea(longitude double(9,6) not null,latitude double (8,6) not null,radius double(4,2) not null,northeastlongitude double(9,6) not null, 
northeastlatitude double (8,6) not null,southwestlongitude double(9,6) not null,southwestlatitude double (8,6) not null,primary key(longitude,latitude));

--insert into banarea values(126.633000,45.710123,10.56);
create table zone(inde int auto_increment primary key,longitude double(9,6)not null,latitude double(8,6)not null,unique(longitude,latitude));


create table record(ipaddress varchar(15) not null ,port int not null,indate date not null,intime time not null,outdate date,outtime time);
--insert into record values('192.163.146.34',5001,'2016-05-11','14:35:34','2016-05-11','14:55:34');
select * from record;




create table uavinbase (
id int auto_increment,
ipaddress varchar(15) not null,
port int not null,
primary key(id),
unique(ipaddress,port),
 longitude double(9,6) not null,
latitude double (8,6) not null,
elevation double(4,2) not null ,
height double(6,2) not null ,
velocity double(6,2) not null,
powerconsumption double(6,2) not null,
sumpower double(6,2) not null,
remainingpower int(2) not null,
version varchar(5) not null
);