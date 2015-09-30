package org.cghr.hc.controller.dist

import org.cghr.commons.db.DbAccess
import org.cghr.commons.db.DbStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.text.SimpleDateFormat

/**
 * Created by ravitej on 1/6/15.
 */
@RestController
@RequestMapping("/distribute")
class TeamDistributor {


    @Autowired
    DbAccess dbAccess
    @Autowired
    DbStore dbStore
    List recipients = [31, 33, 34]

    @RequestMapping("")
    Map distributePhysicianData() {

        List<String> areas = dbAccess.rows("select distinct areaId from feedbackPhysician where timelog like '$today'")
                .collect { Map row -> row.areaId }

        distributeData(areas)

        [status: 'success']
    }

    void distributeData(List areas) {

        areas.each { String areaId ->
            recipients.each { Integer recipient ->
                dbStore.execute("insert into outbox(datastore,ref,refId,recipient) values(?,?,?,?)", ['feedbackPhysician', 'areaId', areaId, recipient])
            }

        }


    }

    String getToday() {

        new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "%"
    }


}
