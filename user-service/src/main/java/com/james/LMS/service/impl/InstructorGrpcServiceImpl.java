package com.james.LMS.service.impl;

import com.example.server.instructor.InstructorRequest;
import com.example.server.instructor.InstructorResponse;
import com.example.server.instructor.InstructorServiceGrpc;
import com.james.LMS.entity.User;
import com.james.LMS.repository.UserRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class InstructorGrpcServiceImpl extends InstructorServiceGrpc.InstructorServiceImplBase {
  private final UserRepository userRepository;

  @Override
  public void findInstructorByUserId(
      InstructorRequest request, StreamObserver<InstructorResponse> responseObserver) {
    long userId = request.getUserId();
    Optional<User> userOptional = this.userRepository.findUserAndInstructorById(userId);
    if (userOptional.isEmpty()) {
      log.warn("Instructor not found {}", request);
      responseObserver.onError(
          Status.INTERNAL.withDescription("Instructor not found").asRuntimeException());
      responseObserver.onCompleted();
    }

    User user = userOptional.get();
    InstructorResponse instructorResponse =
        InstructorResponse.newBuilder()
            .setUserId(user.getId())
            .setUsername(user.getUsername())
            .setAvatar(user.getAvatarUrl())
            .setInstructorName(user.getInstructor().getName())
            .build();
    responseObserver.onNext(instructorResponse);
    responseObserver.onCompleted();
  }
}
