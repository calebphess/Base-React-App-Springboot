USE thingappdb;
INSERT INTO audit(url, user_id, roles, operation, audit_json, event_dtg, source_ip ) values( "/api/audit", "penn@nocomment.dev", "USER,PREMIUM_USER,ADMINISTRATOR", "RETRIEVE_ALL", "THIS IS A TEST", "2023-04-12 19:28:14", "0.0.0.0" );
INSERT INTO audit(url, user_id, roles, operation, audit_json, event_dtg, source_ip ) values( "/api/audit", "UNAUTHENTICATED_USER", "USER", "RETRIEVE_ALL", "THIS IS A TEST BAD GUY ATTACK", "2023-04-11 16:18:44", "0.0.0.0" );
INSERT INTO audit(url, user_id, roles, operation, audit_json, event_dtg, source_ip ) values( "/api/user", "penn@nocomment.dev", "USER,PREMIUM_USER,ADMINISTRATOR", "CREATE", "THIS IS A TEST CREATE", "2023-04-10 17:28:14", "0.0.0.0" );