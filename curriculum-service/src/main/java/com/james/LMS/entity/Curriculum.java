package com.james.LMS.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "curriculums")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Curriculum extends BaseEntity {

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "head_line", nullable = false)
  private String headLine;

  @Column(name = "cost", nullable = false)
  private BigDecimal cost;

  @Column(name = "description")
  private String description;

  @Column(name = "requirement")
  private String requirement;

  @Column(name = "thumbnail")
  private String thumbnail;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "channel_id")
  private Channel channel;

  @OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY)
  @Builder.Default
  private List<CurriculumTopic> curriculumTopics = new ArrayList<>();

  @OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY)
  @Builder.Default
  private List<Session> sessions = new ArrayList<>();

  @OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY)
  @Builder.Default
  private List<UserCurriculum> userCurricula = new ArrayList<>();

  @OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY)
  @Builder.Default
  private List<UserGiven> userGivens = new ArrayList<>();

  @OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY)
  @Builder.Default
  private List<Wishlist> wishlist = new ArrayList<>();

  @OneToOne(mappedBy = "curriculum", fetch = FetchType.LAZY)
  private CurriculumAudit curriculumAudit;

  @OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY)
  @Builder.Default
  private List<CompanyPossessCurriculum> companyPossessCurriculums = new ArrayList<>();

  public Long getChanelUserId() {
    return this.channel.getUserId();
  }
}
