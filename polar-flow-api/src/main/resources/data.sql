insert into device(id,name) values (1, 'Polar Vantage V');
insert into device(id,name) values (2, 'Polar V800');
insert into device(id,name) values (3, 'Polar RCX5');

-- source: https://support.polar.com/en/polar-flow-sport-profiles
insert into sport_type(id,name) values (1, 'General');
insert into sport_type(id,name) values (2, 'Cycling');
insert into sport_type(id,name) values (3, 'Dancing');
insert into sport_type(id,name) values (4, 'Group');
insert into sport_type(id,name) values (5, 'Running');
insert into sport_type(id,name) values (6, 'Skating');
insert into sport_type(id,name) values (7, 'Walking');
insert into sport_type(id,name) values (8, 'Swimming');
insert into sport_type(id,name) values (9, 'Yoga');

insert into sport(id,name,type_id) values (1, 'Basketball', 1);
insert into sport(id,name,type_id) values (2, 'Boxing', 1);
insert into sport(id,name,type_id) values (3, 'High-intensity interval training', 1);
insert into sport(id,name,type_id) values (4, 'Indoor cycling', 2);
insert into sport(id,name,type_id) values (5, 'Mountain biking', 2);
insert into sport(id,name,type_id) values (6, 'Ballet', 3);
insert into sport(id,name,type_id) values (7, 'Jazz', 3);
insert into sport(id,name,type_id) values (8, 'Jogging', 5);
insert into sport(id,name,type_id) values (9, 'Running', 5);
insert into sport(id,name,type_id) values (10, 'Treadmill running', 5);
insert into sport(id,name,type_id) values (11, 'Yoga', 9);
insert into sport(id,name,type_id) values (12, 'Pilates', 9);
insert into sport(id,name,type_id) values (13, 'Weight Lifting', 1);
insert into sport(id,name,type_id) values (14, 'Crossfit', 1);
insert into sport(id,name,type_id) values (15, 'Muay Thai', 1);

insert into user(id,email,handle,first_name,last_name,birth_date,gender,weight,height,measurement_unit,hr_max,hr_rest,vo2max,training_background,daily_activity_goal)
values (1, 'rabreu@redhat.com', '@aelkz', 'raphael', 'abreu', '1984-10-16', 0, 99.4, 182.0, 0, 192, 55, 42, 3, 0);

commit;
