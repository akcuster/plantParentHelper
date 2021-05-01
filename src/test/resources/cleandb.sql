delete from plant;
delete from user;
delete from user_plant;


INSERT INTO user VALUES (1,'jcoyne','supersecret1','Joe','Coyne','1964-04-01', 750)
        ,(2,'fhensen','supersecret2','Fred','Hensen','1988-05-08', 25)
        ,(3,'bcurry','supersecret3','Barney','Curry','1947-11-11', 200)
        ,(4,'kmack','supersecret4','Karen','Mack','1986-07-08', 325)
        ,(5,'dklein','supersecret5','Dianne','Klein','1991-09-22', 75)
        ,(6,'dtillman','supersecret6','Dawn','Tillman','1979-08-30', 1000);

INSERT INTO plant VALUES (1,'Golden Pothos')
        ,(2,'Jade Plant')
        ,(3,'Monstera')
        ,(4,'Snake Plant')
        ,(5,'Satin Pothos')
        ,(6,'Goldfish Plant');

INSERT INTO user_plant VALUES (1, 1, 1, '2019-01-01')
        ,(2, 1, 2, '2019-05-26')
        ,(3, 2, 1, '2020-10-18')
        ,(4, 2, 5, '2020-12-25')
        ,(5, 3, 2, '2018-2-14')
        ,(6, 4, 5, '2012-07-04')
        ,(7, 5, 6, '2021-10-31');
