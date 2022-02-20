package com.server.signalisation.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStorageImpl implements FileStorageInterface {

	private final Path root = Paths.get("uploads");
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
		  throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public void save(MultipartFile file) {
		// TODO Auto-generated method stub
		try {
		  Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			e.printStackTrace();
		  throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
		// TODO Auto-generated method stub
		try {
		  Path file = root.resolve(filename);
		  Resource resource = new UrlResource(file.toUri());
		
		  if (resource.exists() || resource.isReadable()) {
			  return resource;
		  } else {
			  throw new RuntimeException("Could not read the file!");
		  }
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

}
