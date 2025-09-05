package com.sarankirthic.lms.service;

import com.sarankirthic.lms.model.StuDetails;
import com.sarankirthic.lms.repository.StuDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuDetailsService {

    @Autowired
    private StuDetailsRepository stuDetailsRepository;

    public List<StuDetails> getALlStudentDetails(){
        return stuDetailsRepository.findAll();
    }
    public StuDetails saveStudentDetails(StuDetails stuDetails){
       return stuDetailsRepository.save(stuDetails);

    }

    public StuDetails getStudent(Long id){
        return stuDetailsRepository.findById(id).orElse(null);
    }

    public void deleteStudent(Long id){
        stuDetailsRepository.deleteById(id);
    }
}
