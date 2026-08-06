package com.james.LMS.service.impl;

import com.james.LMS.dto.CompanyGroupDTO;
import com.james.LMS.entity.Group;
import com.james.LMS.repository.GroupRepository;
import com.james.LMS.service.GroupService;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final ExecutorService newVirtualThreadPerTaskExecutor;

  @Override
  public Slice<CompanyGroupDTO> findAllByCompanyIdAndUserAdminCompanyId(
      Long companyId, Long userAdminCompanyId, Pageable pageable) {
    return this.groupRepository.findAllByCompanyIdAndUserAdminCompanyId(
        companyId, userAdminCompanyId, pageable);
  }

  @Override
  public CompletableFuture<Integer> countMembersByGroupId(Long id) {
    return CompletableFuture.supplyAsync(
        () -> this.groupRepository.countById(id), this.newVirtualThreadPerTaskExecutor);
  }

  @Override
  public Boolean isGroupAdmin(Long userId, Long groupId) {
    return this.groupRepository.existsByIdAndUserAdminGroupId(groupId, userId);
  }

  @Override
  public Boolean isUserAdminGroupInCompanyAccessibleToGroup(
      Long userId, Long companyId, Long groupId) {
    return this.groupRepository.isUserAdminGroupInCompanyAccessibleToGroup(
        userId, companyId, groupId);
  }

  @Override
  public Boolean isUserAccessibleToGroup(Long userId, Long companyId, Long groupId) {
    return this.groupRepository.isUserAccessibleToGroup(userId, companyId, groupId);
  }

  @Override
  public Optional<Group> findById(Long id) {
    return this.groupRepository.findById(id);
  }
}
