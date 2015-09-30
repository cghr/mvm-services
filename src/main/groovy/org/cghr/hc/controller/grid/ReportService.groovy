package org.cghr.hc.controller.grid

import org.cghr.commons.db.DbAccess
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by ravitej on 8/4/14.
 */
@RestController
@RequestMapping("/GridService/report")
class ReportService {


    @Autowired
    DbAccess dbAccess

    Map reports = [
            "11": "select * from area",
            "12": "select * from house",
            "13": "select  * from household",
            "15": "select deathId,areaId,name,age_value,age_unit,sex,surveytype,cast(timelog as char) time,surveyor from death",
            "16": "select  a.deathId,b.areaId,c.username,cast(summary as char)narrative,cast(b.timelog as char) time,b.name,b.surveyor  FROM narrative a JOIN death b ON a.deathId=b.deathId  LEFT JOIN user c  ON b.surveyor=c.id",
            //"16": "select  a.deathId,b.areaId,c.username,cast(summary as char)narrative,cast(b.timelog as char) time,a.surveyor  FROM narrativePhysician a LEFT JOIN death b ON a.deathId=b.deathId  LEFT JOIN user c  ON a.surveyor=c.id",
            "31": "select id,username,role from user"]

    @RequestMapping("/{reportId}")
    List getReport(@PathVariable("reportId") String reportId) {


        constructJsonResponse(reports[reportId], [])
    }

    List constructJsonResponse(String sql, List params = []) {

        dbAccess.rows(sql, params)

    }


}
