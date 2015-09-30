package org.cghr.hc.controller.idService
import org.cghr.commons.db.DbAccess
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
/**
 * Created by ravitej on 2/2/15.
 */

@RestController
@RequestMapping("/IDService/hc")
class HcIDService {



    @Autowired
    DbAccess dbAccess

    Map contextConfig = [
            visit    : [id: 'id', table: 'householdDeath', parentId: 'householdId', nextId: "1"]
    ]


    @RequestMapping("/area/{areaId}/house/{houseId}/household/{householdId}/visit")
    Map getNextVisit(
            @CookieValue("userid") String userid,
            @PathVariable("householdId") String householdId,
            @PathVariable("houseId") String houseId, @PathVariable("areaId") String areaId) {

        int visits = dbAccess.firstRow("select count(*) count from hcVisit where householdId=?", [householdId]).count

        getNextId(householdId, userid, "visit")

    }

    Map getNextId(String refId, String userid, String context) {

        Map config = contextConfig.get(context)
        String sql = "SELECT MAX($config.id) id FROM $config.table WHERE $config.parentId=?"
        generateNextId(sql, refId, userid, context)

    }

    Map generateNextId(String sql, String refId, String userid, String context) {

        Map row = dbAccess.firstRow(sql, [refId])
        String nextId = resolveNextId(row, context, userid, refId)

        return [id: nextId]

    }

    String resolveNextId(Map row, String context, String userid, String refId) {

        if (!row.id) {
            String idPrefix = (context == 'house') ? (userid + refId) : refId
            return idPrefix + (contextConfig."$context".nextId)
        } else {
            Long id = (row.id).toLong()
            return (++id).toString()
        }
    }



}
