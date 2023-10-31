package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.*;
import java.nio.file.*;

@Service
public class AvatarService {


    @Value("${avatar.dir.path}")
    private String avatarsDir;

    private StudentService studentService;
    private AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public Avatar find(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow(EntityNotFoundException::new);
    }
    public Avatar findOrGreat(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    public void upload(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentService.findStudent(studentId);
        Path filePath = buildFilePath(student, avatarFile.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findOrGreat(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Path buildFilePath(Student student, String fileName) {
        return Path.of(avatarsDir, student.getId() + "_" + student.getName() + "." + getExtensions(fileName));
    }
}
