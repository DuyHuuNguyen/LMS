package com.james.LMS.repository;

import com.james.LMS.dto.WishlistDTO;
import com.james.LMS.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
  Boolean existsWishlistByCurriculum_IdAndIsActiveIsTrue(Long curriculumId);

  @Query("""
    select count(w) >0
    from Wishlist w
    where w.isActive and w.curriculum.id =:curriculumId and w.userId =:userId
    """)
  Boolean existsWishlistByCurriculum_IdAndIsActiveIsTrueAndUserId(Long curriculumId, Long userId);

  @Query(value = """
    select new com.james.LMS.dto.WishlistDTO(
        wl.id,
            wl.userId,
                wl.curriculum.id
        )
    from Wishlist wl 
    where wl.isActive and wl.userId =:userId
    """)
  Page<WishlistDTO> findByUserId(Long userId, Pageable pageable);
}
