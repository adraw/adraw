package io.github.adraw.framework.file;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    void store(MultipartFile file);
    
    List<Long> store(MultiValueMap<String, Object> params);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(Long id);

    void deleteAll();

}
