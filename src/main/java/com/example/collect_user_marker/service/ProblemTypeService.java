package com.example.collect_user_marker.service;

import com.example.collect_user_marker.entity.ProblemTypeEntity;
import com.example.collect_user_marker.model.ResponseDTO;
import com.example.collect_user_marker.model.problemType.ProblemTypeDTO;

import java.util.List;
import java.util.Optional;

public interface ProblemTypeService {

    ProblemTypeEntity saveNewProblem(ProblemTypeDTO problemTypeDTO);

    List<ProblemTypeEntity> getAllProblems();

    Optional<ProblemTypeEntity> getProblemById(Integer id);

    ResponseDTO deleteProblemById(Integer id);
}
