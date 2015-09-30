package org.cghr.hc.controller.image

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by ravitej on 15/5/14.
 */
@RestController
@RequestMapping("/image")
class ImageService {

    @Autowired
    HashMap fileStoreFactory

    @RequestMapping(value = "/{memberId}/{filestore}/{category}/{type}", produces = MediaType.IMAGE_PNG_VALUE)
    byte[] getImage(
            @PathVariable("memberId") String memberId,
            @PathVariable("filestore") String filestore,
            @PathVariable("category") String category,
            @PathVariable("type") String type) throws IOException {

        File file = new File(getImagePath(memberId, filestore, category, type))
        file.getBytes()
    }

    String getImagePath(String memberId, String filestore, String category, String type) {

        fileStoreFactory."$filestore"."$category" + '/' + memberId + '_' + type

    }

}
