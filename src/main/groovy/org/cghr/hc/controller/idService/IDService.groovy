package org.cghr.hc.controller.idService

import org.cghr.commons.db.DbAccess
import org.cghr.commons.db.DbStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by ravitej on 8/4/14.
 */

@RestController
@RequestMapping("/IDService/enum")
class IDService {

    @Autowired
    DbAccess dbAccess
    @Autowired
    DbStore dbStore

    def randomHouseNo = {
        Random r = new Random();
        int Low = 1000;
        int High = 2000;
        int R = r.nextInt(High - Low) + Low;
    };

    Map contextConfig = [
            house    : [id: 'houseId', table: 'house', parentId: 'areaId', nextId: randomHouseNo],
            household: [id: "householdId", table: 'household', parentId: 'houseId', nextId: "01"],
            member   : [id: 'memberId', table: 'member', parentId: 'householdId', nextId: "01"],
            hosp     : [id: 'id', table: 'householdHosp', parentId: 'householdId', nextId: "01"],
            death    : [id: 'deathId', table: 'death', parentId: 'householdId', nextId: "01"],
            visit    : [id: 'id', table: 'enumVisit', parentId: 'householdId', nextId: "1"]
    ]


    @RequestMapping("/area/{areaId}/house")
    Map getNextHouseId(@CookieValue("userid") String userid, @PathVariable("areaId") String areaId) {

        getNextId(areaId, userid, "house")

    }

    @RequestMapping("/area/{areaId}/house/{houseId}/household")
    Map getNextHouseholdId(@CookieValue("userid") String userid, @PathVariable("houseId") String houseId) {


        getNextId(houseId, userid, "household")


    }

    @RequestMapping("/area/{areaId}/house/{houseId}/household/{householdId}/visit")
    Map getNextVisit(
            @CookieValue("userid") String userid,
            @PathVariable("householdId") String householdId,
            @PathVariable("houseId") String houseId, @PathVariable("areaId") String areaId) {

        int visits = dbAccess.firstRow("select count(*) count from enumVisit where householdId=?", [householdId]).count
        if (visits == 0)
            dbStore.execute("INSERT INTO household(householdId,houseId,areaId) values(?,?,?)", [householdId, houseId, areaId])

        getNextId(householdId, userid, "visit")

    }

    @RequestMapping("/area/{areaId}/house/{houseId}/household/{householdId}/death")

    Map getNextMember(@CookieValue("userid") String userid,
                      @PathVariable("householdId") String householdId) {

        getNextId(householdId, userid, "death")

    }

    @RequestMapping("/area/{areaId}/house/{houseId}/household/{householdId}/member")
    Map getNextDeath(@CookieValue("userid") String userid,
                     @PathVariable("householdId") String householdId) {

        getNextId(householdId, userid, "member")

    }

    @RequestMapping("/area/{areaId}/house/{houseId}/household/{householdId}/hosp")
    Map getNextHosp(@CookieValue("userid") String userid,
                    @PathVariable("householdId") String householdId) {

        getNextId(householdId, userid, "hosp")
    }


    Map getNextId(String refId, String userid, String context) {

        Map config = contextConfig.get(context)
        String sql = "SELECT MAX($config.id) id FROM $config.table WHERE $config.parentId=? AND $config.id like '$userid%'"
        generateNextId(sql, refId, userid, context)

    }

    Map generateNextId(String sql, String refId, String userid, String context) {

        Map row = dbAccess.firstRow(sql, [refId])
        String nextId = resolveNextId(row, context, userid, refId)

        return [id: nextId]

    }

    String resolveNextId(Map row, String context, String userid, String refId) {

        if (!row.id)
            return (context == 'house') ? resolveNextIdForHouse(userid, refId) : resolveNextIdForRest(userid, refId, context)
        //String idPrefix = (context == 'house') ? (userid + refId) : refId
        //return idPrefix + (contextConfig."$context".nextId)
        else {
            Long id = (row.id).toLong()
            return (++id).toString()
        }
    }

    String resolveNextIdForHouse(String userid, String refId) {

        String idPrefix = userid + refId
        int randomNo = contextConfig.house.nextId()
        idPrefix + randomNo
    }

    String resolveNextIdForRest(String userid, String refId, String context) {

        refId + (contextConfig[context].nextId)
    }


}
