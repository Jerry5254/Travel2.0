package com.niit.travel.service;

import com.niit.travel.entity.tn;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface tnService {
    boolean addTravelNote(tn TN, MultipartFile fil, String fileName);
    tn GettravelNoteById(Integer tnId);
    List<tn> getTnByAuthorId(Integer id);
    List<tn> getAllTravelNote(String tn);
    List<tn> getShowTravelNote(String status);
    boolean updateTravelNote(tn Tn);
    boolean deleteTravelNote(Integer id);
}
