-- Drop existing tables if they exist
drop table if exists address;
drop table if exists name;
drop table if exists student;

-- Create the address table
create table address (
    address_id bigint not null auto_increment,
    address varchar(255),
    district varchar(255),
    pincode varchar(255),
    state varchar(255),
    primary key (address_id)
) engine=InnoDB;

-- Create the name table
create table name (
    name_id bigint not null auto_increment,
    fname varchar(255),
    lname varchar(255),
    mname varchar(255),
    primary key (name_id)
) engine=InnoDB;

-- Create the student table
create table student (
    standard integer,
    address_id bigint,
    name_id bigint,
    student_id bigint not null auto_increment,
    aadhar varchar(255),
    primary key (student_id)
) engine=InnoDB;

-- Add unique constraints
alter table student add constraint UKstudentaddressid unique (address_id);
alter table student add constraint UKstudentnameid unique (name_id);

-- Add foreign key constraints
alter table student add constraint FKstudentaddressid foreign key (address_id) references address (address_id);
alter table student add constraint FKstudentnameid foreign key (name_id) references name (name_id);
