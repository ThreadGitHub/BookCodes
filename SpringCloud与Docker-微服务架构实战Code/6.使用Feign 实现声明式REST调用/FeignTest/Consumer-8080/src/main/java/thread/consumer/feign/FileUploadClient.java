package thread.consumer.feign;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-upload", configuration = FileUploadClient.FileUploadConfiguration.class)
public interface FileUploadClient {
    //这里 consumes = MediaType.MULTIPART_FORM_DATA_VALUE 设置上是 formdata 的提交方式
    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file")MultipartFile file);

    //定义FeignUpload的配置
    class FileUploadConfiguration{
        /**
         * 加载用于Spring上传的 Encoder
         * @return
         */
        @Bean
        public Encoder encoder(){
            return new SpringFormEncoder();
        }
    }
}
