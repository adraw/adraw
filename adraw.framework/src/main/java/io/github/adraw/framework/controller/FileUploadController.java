package io.github.adraw.framework.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.adraw.framework.base.ResponseResult;
import io.github.adraw.framework.base.RestResultGenerator;
import io.github.adraw.framework.file.StorageFileNotFoundException;
import io.github.adraw.framework.file.StorageService;


@Controller
@RequestMapping("/common")
public class FileUploadController {

	@Autowired
    private StorageService storageService;


	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
    @GetMapping("/files/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long id) {

        Resource file = storageService.loadAsResource(id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                //.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseResult<String> handleFileUpload(@RequestParam MultiValueMap<String, Object> params) {
    	List<Long> ids = storageService.store(params);
    	String uid = UUID.randomUUID().toString();
    	redisTemplate.opsForValue().set(uid, ids,10,TimeUnit.MINUTES);
        return RestResultGenerator.genResult(uid,"保存成功!");
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(
            StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
