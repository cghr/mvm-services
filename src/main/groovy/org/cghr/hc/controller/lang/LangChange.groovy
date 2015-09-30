package org.cghr.hc.controller.lang

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import java.awt.*
import java.awt.event.KeyEvent

/**
 * Created by ravitej on 31/8/15.
 */
@RestController
@RequestMapping("/langToggle")
class LangChange {

    @RequestMapping(value = "", method = RequestMethod.GET)
    Map toggleLang() {

        Robot robot = new Robot();

        // Simulate a key press
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_ALT);

        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_SHIFT);

        println("Language Switched")

        [status: "success"]
    }


}
