package org.cghr.hc.controller.dbSetup

import groovy.sql.Sql
import org.cghr.startupTasks.DbImport

/**
 * Created by ravitej on 16/11/14.
 */
class DbSetup {

    Sql gSql

    DbSetup(Sql sql) {

        this.gSql = sql
    }

    def setup() {

        DbImport dbImport = new DbImport("src/main/webapp/sqlImport", gSql)
        dbImport.importSqlScriptsWithoutDeleting()


    }
}
