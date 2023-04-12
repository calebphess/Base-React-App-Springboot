package com.zetech.thingapp.thingapp.dal.interfaces;

import java.util.List;

import com.zetech.thingapp.thingapp.model.AuditVO;

public interface AuditDAOInterface
{
  List<AuditVO> retrieveAll();

  AuditVO retrieveById(Long id);

  int create(AuditVO record);

  int updateReviewed(AuditVO record);

  int deleteReviewed();

  int deleteAll();
}