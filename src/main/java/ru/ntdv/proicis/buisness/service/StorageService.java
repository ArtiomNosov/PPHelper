package ru.ntdv.proicis.buisness.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.ntdv.proicis.crud.contract.FileAccessPolicy;
import ru.ntdv.proicis.crud.model.File;
import ru.ntdv.proicis.crud.model.User;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.util.stream.Stream;

public
interface StorageService {
Path getRootPath();

void init() throws FileNotFoundException;

File save(final MultipartFile file, final User owner, final FileAccessPolicy... fileAccessPolicy) throws FileSystemException;

void store(MultipartFile file, String fileName) throws FileSystemException;

void store(StringBuilder data, String originalFileName, String filename) throws FileSystemException;

Stream<Path> loadAll() throws FileSystemException;

Path load(String filename);

Resource loadAsResource(String filename) throws FileNotFoundException;

void deleteAll();

}