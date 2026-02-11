package com.james.LMS.service.impl;

import com.example.server.instructor.InstructorRequest;
import com.example.server.instructor.InstructorResponse;
import com.example.server.instructor.InstructorServiceGrpc;
import com.james.LMS.dto.InstructorDTO;
import com.james.LMS.service.InstructorService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
  @GrpcClient("instructor")
  private InstructorServiceGrpc.InstructorServiceBlockingStub instructorServiceBlockingStub;

  @Override
  public Optional<InstructorDTO> findByUserId(Long userId) {
    try {
      InstructorRequest instructorRequest =
          InstructorRequest.newBuilder().setUserId(userId).build();
      InstructorResponse instructorResponse =
          this.instructorServiceBlockingStub.findInstructorByUserId(instructorRequest);
      return Optional.of(
          InstructorDTO.builder()
              .userId(userId)
              .username(instructorResponse.getUsername())
              .avatar(instructorResponse.getAvatar())
              .instructorName(instructorResponse.getInstructorName())
              .build());
    } catch (Exception e) {
      log.warn("Instructor not found {}", userId);
      return Optional.empty();
    }
  }
}
