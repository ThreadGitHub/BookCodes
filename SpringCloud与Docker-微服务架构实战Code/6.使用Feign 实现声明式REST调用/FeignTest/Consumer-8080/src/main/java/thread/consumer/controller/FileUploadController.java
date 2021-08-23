package thread.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import thread.consumer.feign.FileUploadClient;

@Controller
public class FileUploadController {
    @Autowired
    private FileUploadClient fileUploadClient;

    @GetMapping("/upload")
    public String upload(){
        return "upload";
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestPart MultipartFile file){
        String upload = fileUploadClient.upload(file);
        return upload;
    }
}
