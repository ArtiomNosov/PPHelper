package ru.ntdv.proicis.crud.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.crud.model.File;
import ru.ntdv.proicis.crud.repository.FileRepository;

import java.util.UUID;

@Service
public
class FileService {
@Autowired
private FileRepository fileRepository;

public
File getById(final UUID id) {
    return fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("File not found."));
}

public
File save(final File file) {
    return fileRepository.save(file);
}
}
