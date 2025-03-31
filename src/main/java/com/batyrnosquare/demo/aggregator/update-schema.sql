ALTER TABLE act_ru_identitylink
DROP
CONSTRAINT act_fk_athrz_procedef;

ALTER TABLE act_ru_batch
DROP
CONSTRAINT act_fk_batch_job_def;

ALTER TABLE act_ru_batch
DROP
CONSTRAINT act_fk_batch_monitor_job_def;

ALTER TABLE act_ru_batch
DROP
CONSTRAINT act_fk_batch_seed_job_def;

ALTER TABLE act_ge_bytearray
DROP
CONSTRAINT act_fk_bytearr_depl;

ALTER TABLE act_ru_case_execution
DROP
CONSTRAINT act_fk_case_exe_case_def;

ALTER TABLE act_ru_case_execution
DROP
CONSTRAINT act_fk_case_exe_case_inst;

ALTER TABLE act_ru_case_execution
DROP
CONSTRAINT act_fk_case_exe_parent;

ALTER TABLE act_ru_case_sentry_part
DROP
CONSTRAINT act_fk_case_sentry_case_exec;

ALTER TABLE act_ru_case_sentry_part
DROP
CONSTRAINT act_fk_case_sentry_case_inst;

ALTER TABLE act_re_decision_def
DROP
CONSTRAINT act_fk_dec_req;

ALTER TABLE act_ru_event_subscr
DROP
CONSTRAINT act_fk_event_exec;

ALTER TABLE act_ru_execution
DROP
CONSTRAINT act_fk_exe_parent;

ALTER TABLE act_ru_execution
DROP
CONSTRAINT act_fk_exe_procdef;

ALTER TABLE act_ru_execution
DROP
CONSTRAINT act_fk_exe_procinst;

ALTER TABLE act_ru_execution
DROP
CONSTRAINT act_fk_exe_super;

ALTER TABLE act_ru_ext_task
DROP
CONSTRAINT act_fk_ext_task_error_details;

ALTER TABLE act_ru_ext_task
DROP
CONSTRAINT act_fk_ext_task_exe;

ALTER TABLE act_ru_incident
DROP
CONSTRAINT act_fk_inc_cause;

ALTER TABLE act_ru_incident
DROP
CONSTRAINT act_fk_inc_exe;

ALTER TABLE act_ru_incident
DROP
CONSTRAINT act_fk_inc_job_def;

ALTER TABLE act_ru_incident
DROP
CONSTRAINT act_fk_inc_procdef;

ALTER TABLE act_ru_incident
DROP
CONSTRAINT act_fk_inc_procinst;

ALTER TABLE act_ru_incident
DROP
CONSTRAINT act_fk_inc_rcause;

ALTER TABLE act_ru_job
DROP
CONSTRAINT act_fk_job_exception;

ALTER TABLE act_id_membership
DROP
CONSTRAINT act_fk_memb_group;

ALTER TABLE act_id_membership
DROP
CONSTRAINT act_fk_memb_user;

ALTER TABLE act_ru_task
DROP
CONSTRAINT act_fk_task_case_def;

ALTER TABLE act_ru_task
DROP
CONSTRAINT act_fk_task_case_exe;

ALTER TABLE act_ru_task
DROP
CONSTRAINT act_fk_task_exe;

ALTER TABLE act_ru_task
DROP
CONSTRAINT act_fk_task_procdef;

ALTER TABLE act_ru_task
DROP
CONSTRAINT act_fk_task_procinst;

ALTER TABLE act_id_tenant_member
DROP
CONSTRAINT act_fk_tenant_memb;

ALTER TABLE act_id_tenant_member
DROP
CONSTRAINT act_fk_tenant_memb_group;

ALTER TABLE act_id_tenant_member
DROP
CONSTRAINT act_fk_tenant_memb_user;

ALTER TABLE act_ru_identitylink
DROP
CONSTRAINT act_fk_tskass_task;

ALTER TABLE act_ru_variable
DROP
CONSTRAINT act_fk_var_batch;

ALTER TABLE act_ru_variable
DROP
CONSTRAINT act_fk_var_bytearray;

ALTER TABLE act_ru_variable
DROP
CONSTRAINT act_fk_var_case_exe;

ALTER TABLE act_ru_variable
DROP
CONSTRAINT act_fk_var_case_inst;

ALTER TABLE act_ru_variable
DROP
CONSTRAINT act_fk_var_exe;

ALTER TABLE act_ru_variable
DROP
CONSTRAINT act_fk_var_procinst;

CREATE TABLE analysis
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    patient_id BIGINT,
    hemoglobin INTEGER                                 NOT NULL,
    platelet   INTEGER                                 NOT NULL,
    glucose    DOUBLE PRECISION                        NOT NULL,
    CONSTRAINT pk_analysis PRIMARY KEY (id)
);

ALTER TABLE analysis
    ADD CONSTRAINT FK_ANALYSIS_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES patients (id);

DROP TABLE act_ge_bytearray CASCADE;

DROP TABLE act_ge_property CASCADE;

DROP TABLE act_ge_schema_log CASCADE;

DROP TABLE act_hi_actinst CASCADE;

DROP TABLE act_hi_attachment CASCADE;

DROP TABLE act_hi_batch CASCADE;

DROP TABLE act_hi_caseactinst CASCADE;

DROP TABLE act_hi_caseinst CASCADE;

DROP TABLE act_hi_comment CASCADE;

DROP TABLE act_hi_dec_in CASCADE;

DROP TABLE act_hi_dec_out CASCADE;

DROP TABLE act_hi_decinst CASCADE;

DROP TABLE act_hi_detail CASCADE;

DROP TABLE act_hi_ext_task_log CASCADE;

DROP TABLE act_hi_identitylink CASCADE;

DROP TABLE act_hi_incident CASCADE;

DROP TABLE act_hi_job_log CASCADE;

DROP TABLE act_hi_op_log CASCADE;

DROP TABLE act_hi_procinst CASCADE;

DROP TABLE act_hi_taskinst CASCADE;

DROP TABLE act_hi_varinst CASCADE;

DROP TABLE act_id_group CASCADE;

DROP TABLE act_id_info CASCADE;

DROP TABLE act_id_membership CASCADE;

DROP TABLE act_id_tenant CASCADE;

DROP TABLE act_id_tenant_member CASCADE;

DROP TABLE act_id_user CASCADE;

DROP TABLE act_re_camformdef CASCADE;

DROP TABLE act_re_case_def CASCADE;

DROP TABLE act_re_decision_def CASCADE;

DROP TABLE act_re_decision_req_def CASCADE;

DROP TABLE act_re_deployment CASCADE;

DROP TABLE act_re_procdef CASCADE;

DROP TABLE act_ru_authorization CASCADE;

DROP TABLE act_ru_batch CASCADE;

DROP TABLE act_ru_case_execution CASCADE;

DROP TABLE act_ru_case_sentry_part CASCADE;

DROP TABLE act_ru_event_subscr CASCADE;

DROP TABLE act_ru_execution CASCADE;

DROP TABLE act_ru_ext_task CASCADE;

DROP TABLE act_ru_filter CASCADE;

DROP TABLE act_ru_identitylink CASCADE;

DROP TABLE act_ru_incident CASCADE;

DROP TABLE act_ru_job CASCADE;

DROP TABLE act_ru_jobdef CASCADE;

DROP TABLE act_ru_meter_log CASCADE;

DROP TABLE act_ru_task CASCADE;

DROP TABLE act_ru_task_meter_log CASCADE;

DROP TABLE act_ru_variable CASCADE;