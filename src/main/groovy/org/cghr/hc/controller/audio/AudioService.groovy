package org.cghr.hc.controller.audio

import groovy.util.logging.Log4j
import org.cghr.commons.db.DbStore
import org.cghr.va.audio.JavaSoundRecorder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by ravitej on 11/12/14.
 */

@Log4j
@RestController
@RequestMapping("/audio")
class AudioService {

    @Autowired
    JavaSoundRecorder javaSoundRecorder
    @Autowired
    @Qualifier("recordingsPath")
    String recordingsPath

    @Autowired
    DbStore dbStore

    boolean isRecording = false


    @RequestMapping("/start/{context}/{deathId}")
    void startRecording(@PathVariable("deathId") String deathId, @PathVariable("context") String context) {

        if (isRecording) {
            log.info 'Already running!'
            return;
        }


        log.info("Path to Save Audio " + recordingsPath + deathId + ".wav")
        try {


            Thread.start {
                File wavFile = new File(recordingsPath + deathId + "_" + context + ".wav")
                javaSoundRecorder.start(wavFile)
            }

        }
        catch (Exception e) {
            log.error "Error Starting Audio recording"
        }
        isRecording = true

        log.info "Started Audio recording"


    }

    @RequestMapping("/stop/{context}/{deathId}")
    void stopRecording(@PathVariable("deathId") String deathId, @PathVariable("context") String context) {


        try {
            javaSoundRecorder.finish()
            logFile(deathId, context)
            isRecording = false
            log.info "Stopped Audio recording successfully"
        }
        catch (Exception ex) {
            log.error("Error Stopping audio recording")
        }


    }
    //id,filename,filestore,category,status

    void logFile(String deathId, String context) {

        String filename = deathId + "_" + context + ".wav"
        dbStore.saveOrUpdate([filename: filename, filestore: 'narrativeSource', category: 'audio'], "filechangelog")

    }


}
