package org.cghr.hc.controller.resampling

import groovy.sql.Sql
import org.cghr.commons.db.DbAccess
import org.cghr.commons.db.DbStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.text.SimpleDateFormat

/**
 * Created by ravitej on 13/5/14.
 */
@RestController
@RequestMapping("/resampling")
class Resampling {

    @Autowired
    DbAccess dbAccess
    @Autowired
    DbStore dbStore
    @Autowired
    Sql gSql


    String getToday() {

        new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "%"
    }

    @RequestMapping("")
    Map[] report() {

        //String sql = "select a.memberId,name,houseno,CASEWHEN(b.memberId is null,'Pending','Completed') status from resampAssign a left join resampling b on a.memberId=b.memberId where assignedto=? and a.timelog like '$today'"
        //dbAccess.rows(sql, [userid])
        //String sql="select a.deathId,a.name from death a left join resampling b on a.deathId=b.deathId where a.timelog like '$today'"
        String sql = "SELECT a.deathId,NAME,b.camNo,a.sex,CONCAT(a.age_value,' ',a.age_unit) age,casewhen(c.memberId is null,'Pending','Completed') status  FROM death  a LEFT JOIN house b ON a.houseId=b.houseId LEFT JOIN resampling c ON a.deathId=c.memberId  WHERE a.timelog LIKE '$today'"
        dbAccess.rows(sql)
    }


}
