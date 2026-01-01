package com.stemlink.skillmentor.services.impl;


import com.stemlink.skillmentor.entities.Mentor;
import com.stemlink.skillmentor.exceptions.SkillMentorException;
import com.stemlink.skillmentor.repositories.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl {
    private final MentorRepository mentorRepository;
    private final ModelMapper modelMapper;

    public Mentor createNewMentor(Mentor mentor) {

        try {
            return mentorRepository.save(mentor);
        } catch (Exception exception) {
            System.err.println("Error creating a mentor" + exception.getMessage());
            throw new SkillMentorException("Fail to create a new mentor", HttpStatus.CONFLICT);

        }

    }

    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll(); // SELECT * FROM mentor
    }

    public Mentor getMentorById(Long id) {

        try{
            return mentorRepository.findById(id).get();
        }catch (Exception exception) {
            System.err.println("Error getting a mentor" + exception.getMessage());
            throw new SkillMentorException("Fail to get a mentor", HttpStatus.NOT_FOUND);

        }
    }

    public Mentor updateMentorById(Long id, Mentor updatedMentor) {
        Mentor mentor = mentorRepository.findById(id).get();
        modelMapper.map(updatedMentor, mentor);
        return mentorRepository.save(mentor);
    }

    public void deleteMentor(Long id) {
        mentorRepository.deleteById(id);
    }

}
