INSERT INTO house(houseId,areaId,address,houseno) values(10999001,999,'address1','101');
INSERT INTO household(householdId,houseId,areaId) values(1099900101,10999001,999);
INSERT INTO death(deathId,householdId,houseId,areaId,age_value,age_unit,sex,name,surveytype) values(109990010101,1099900101,10999001,999,40,'Years','Male','Adult','va');
INSERT INTO death(deathId,householdId,houseId,areaId,age_value,age_unit,sex,name,surveytype) values(109990010102,1099900101,10999001,999,10,'Months','Male','Child','esl');
INSERT INTO death(deathId,householdId,houseId,areaId,age_value,age_unit,sex,name,surveytype) values(109990010103,1099900101,10999001,999,5,'Days','Male','Neonatal','va');
