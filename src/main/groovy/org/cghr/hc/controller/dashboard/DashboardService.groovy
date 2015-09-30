package org.cghr.hc.controller.dashboard

import org.cghr.commons.db.DbAccess
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.text.SimpleDateFormat

/**
 * Created by ravitej on 12/5/14.
 */
@RestController
@RequestMapping("/dashboard")
class DashboardService {

    @Autowired
    DbAccess dbAccess


    @RequestMapping("/downloads")
    List getPendingDownloads() {

        getData("SELECT  username,COUNT(*)  downloads FROM user  a  JOIN   outbox b ON a.id=b.recipient  WHERE role='user'  AND dwnStatus IS NULL GROUP BY  b.recipient")
    }

    @RequestMapping("/va")
    List getTotalProgressEnum() {

        getData("select username name,count(*) surveys from user a join feedback b on a.id=b.surveyor where b.timelog like '$today' and surveytype='va' group by b.surveyor")

    }

    @RequestMapping("/esl")
    List getTodayProgressHHQ() {

        getData("select username name,count(*) surveys from user a join feedback b on a.id=b.surveyor where b.timelog like '$today' and surveytype is null group by b.surveyor")
    }

    List getData(String sql, List params = []) {

        dbAccess.rows(sql, params)
    }

    String getToday() {

        new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "%"
    }
}
