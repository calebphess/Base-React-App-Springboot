package com.zetech.thingapp.thingapp.biz;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zetech.thingapp.thingapp.exceptions.ThingAppException;
import com.zetech.thingapp.thingapp.model.AuditVO;
import com.zetech.thingapp.thingapp.security.UserToken;

/*
 * This is the important service that handles our application audit
 * It should provide audit records for all user requested operations
 */

// TODO: This is different from source but I think I did it correctly

public interface AuditServiceInterface
{
  @Transactional(noRollbackFor = Throwable.class)
  void auditUnCaughtError(Throwable e, UserToken token, String url);

  @Transactional(rollbackFor = Throwable.class)
  void auditRetrieveAllSuccess(List<?> records, UserToken token, String url) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditRetrieveAllForSuccess(Object id, List<?> data, UserToken token, String extractUrl) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditRetrieveByIdSuccess(Object id, Object data, UserToken token, String extractUrl) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditCreateSuccess(Object data, UserToken token, String extractUrl) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditUpdateError(Object record, Throwable e, UserToken token, String extractUrl) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditDeleteSuccess(Object idOrRec, UserToken token, String extractUrl) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditRetrieveAllError(Throwable e, UserToken token, String url) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditRetrieveAllForError(Object id, Throwable e, UserToken token, String extractUrl) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditRetrieveByIdError(Object id, Throwable e, UserToken token, String extractUrl) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditCreateError(Object record, Throwable e, UserToken token, String extractUrl) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditDeleteError(Object idOrRec, Throwable e, UserToken token, String extractUrl) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditUpdateSuccess(Object data, UserToken token, String extractUrl) throws ThingAppException;

  @Transactional(readOnly = true)
  List<AuditVO> retrieveAll(UserToken token) throws ThingAppException;

  @Transactional(readOnly = true)
  AuditVO retrieveById(Long id, UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  AuditVO review(AuditVO record, UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  int reviewAll(UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  int deleteReviewed(UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  int deleteAll(UserToken token) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditSearchSuccess(Object criteria, List<?> results) throws ThingAppException;

  @Transactional(rollbackFor = Throwable.class)
  void auditSearchFail(Object criteria, Throwable e) throws ThingAppException;

}