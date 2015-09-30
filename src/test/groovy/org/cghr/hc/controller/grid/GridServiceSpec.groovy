package org.cghr.hc.controller.grid

import groovy.sql.Sql
import org.cghr.hc.controller.dbSetup.DbSetup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.GenericGroovyXmlContextLoader
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by ravitej on 16/11/14.
 */
@ContextConfiguration(locations = "classpath:appContextTest.groovy", loader = GenericGroovyXmlContextLoader)
class GridServiceSpec extends Specification {


    @Autowired
    GridService gridService
    @Autowired
    Sql sql

    MockMvc mockMvc

    def setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(gridService).build()
        new DbSetup(sql).setup()
    }

    def "should respond with status ok for  enum services"() {

        expect:
        mockMvc.perform(get('/GridService/enum/area'))
                .andExpect(status().isOk())

        mockMvc.perform(get('/GridService/enum/area/13/house'))
                .andExpect(status().isOk())

        mockMvc.perform(get('/GridService/enum/area/13/house/3813001/household'))
                .andExpect(status().isOk())

        mockMvc.perform(get('/GridService/enum/area/13/house/3813001/household/381300101/visit'))
                .andExpect(status().isOk())

//        mockMvc.perform(get('/GridService/enum/area/13/house/3813001/household/381300101/member'))
//                .andExpect(status().isOk())


    }

}