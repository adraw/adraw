package io.github.adraw.framework.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.util.IOUtils;

import io.github.adraw.framework.mapper.ArchiveMapper;
import io.github.adraw.framework.model.Archive;


@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    
    @Autowired
    private ArchiveMapper archiveMapper;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(Long id) {
        Archive archive = archiveMapper.selectByPrimaryKey(id);
        String filename = archive.getPath();
		try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

	@Override
	public List<Long> store(MultiValueMap<String, Object> params) {
		List<Object> base64List = params.get("base64");
		List<Object> typeList = params.get("type");
		List<Long> ids = new ArrayList<Long>();
		int size = base64List.size();
		Date now = new Date();
		
		for (int i = 0;i<size;i++) {
			String base64String = (String) base64List.get(i);
			base64String = base64String.split("base64,")[1];
			byte[] buffer = IOUtils.decodeBase64(base64String);
			String uuid = UUID.randomUUID().toString();
			Archive archive = new Archive();
			String type = (String) typeList.get(i);
			archive.setPath(uuid);
			archive.setType(type);
			archive.setCreateAt(now);
			archiveMapper.insert(archive);
			Long id = archive.getId();
			ids.add(id);
			try {
				Files.copy(new ByteArrayInputStream(buffer), this.rootLocation.resolve(uuid));
			} catch (IOException e) {
				 throw new StorageException("Failed to store file ", e);
			}
		}
		
		return ids;
		
	}
}
