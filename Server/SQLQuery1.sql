use warau
go 

create table account
(
id int primary key,
cipher char(16),
nickName nvarchar(12),
head nvarchar(200),
lv int,
content nvarchar(30)
)

insert into account values(1070550684, '123456', 'warau','Image/Head/184_100.gif',10,'warau')
insert into account values(1070550685, '123456', '雨','Image/Head/185_100.gif',0,'更加努力吧')
insert into account values(1070550686, '123456', '理想','Image/Head/186_100.gif',0,'更加努力吧')
insert into account values(1070550687, '123456', '梦想','Image/Head/182_100.gif',0,'更加努力吧')
insert into account values(1070550688, '123456', '愿望','Image/Head/183_100.gif',0,'更加努力吧')
select * from account where id=1070550684 and cipher=123456
delete from account where id=1070550685
delete from account
drop table account

create table friendGroups
(
genusId int foreign key references account(id),
friendGroup nvarchar(10) primary key
)

insert into friendGroups values(1070550684, '组1')
insert into friendGroups values(1070550684, '组2')
insert into friendGroups values(1070550684, '组3')

insert into friendGroups values(1070550685, '我的好友')
insert into friendGroups values(1070550685, '恩')

select * from friendGroups
delete from friendGroups
delete from friendGroups
drop table friendGroups

create table friends
(
genusId int foreign key references account(id),
genusGroup nvarchar(10) foreign key references friendGroups(friendGroup),
friendId int foreign key references account(id),
remarks nvarchar(10),
hasUpdate int check(hasUpdate in(0,1)) 
)

insert into friends values(1070550684, '组1', 1070550685, '.....', 0)
insert into friends values(1070550684, '组1', 1070550686, 'milai', 0)
insert into friends values(1070550684, '组2', 1070550687, 'ss', 0)

insert into friends values(1070550685, '我的好友', 1070550684, 'zz', 1)
insert into friends values(1070550685, '我的好友', 1070550686, 'ff', 0)
insert into friends values(1070550685, '我的好友', 1070550687, 'gg', 0)
insert into friends values(1070550685, '恩', 1070550688, 'kk', 0)
select * from friends
delete from friends
delete from friends
drop table friends