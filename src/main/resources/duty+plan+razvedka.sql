merge into M_BORDER.Z_DUTY@ASOOSD z1 using M_BORDER.Z_DUTY z2 on (z1.duty_id = z2.duty_id)
when not matched then
insert (z1.duty_id, z1.dk_id, z1.department_id, z1.dt_index, 
z1.dt_add, z1.dt_start, z1.dt_end, z1.dt_means, z1.dt_dd, 
z1.dt_for_dz, z1.dt_result, z1.dt_owner,
z1.dt_comment, z1.dt_str_unit, z1.dsh_id)
values
(z2.duty_id, z2.dk_id, z2.department_id, z2.dt_index, z2.dt_add, z2.dt_start, 
z2.dt_end, z2.dt_means, z2.dt_dd, z2.dt_for_dz, z2.dt_result, 
z2.dt_owner, z2.dt_comment, z2.dt_str_unit, z2.dsh_id);

commit;

insert into m_border.P_STAFF_LIST@ASOOSD 
select * from m_border.P_STAFF_LIST where sl_id not in (select sl_id from m_border.P_STAFF_LIST@ASOOSD);

commit;

insert into m_border.p_attached@ASOOSD 
select * from m_border.p_attached where at_id not in (select at_id from m_border.p_attached@ASOOSD);

commit;

insert into m_border.P_STAFF_LIST_POSITION@ASOOSD 
select * from m_border.P_STAFF_LIST_POSITION where sl_id not in (select sl_id from m_border.P_STAFF_LIST_POSITION@ASOOSD);

commit;

insert into m_border.P_STAFF_LIST_POSITION_A@ASOOSD 
select * from m_border.P_STAFF_LIST_POSITION_A where at_id not in (select at_id from m_border.P_STAFF_LIST_POSITION_A@ASOOSD);

commit;

merge into M_BORDER.Z_DUTY_STAFF@ASOOSD z1 using  M_BORDER.Z_DUTY_STAFF z2 on (z1.duty_id = z2.duty_id and z1.duty_number = z2.duty_number)
when not matched then
insert (duty_id, duty_number, sld_id, at_id, shift_type)
values
(z2.duty_id, z2.duty_number, z2.sld_id, z2.at_id, z2.shift_type)
when matched then
update set sld_id = z2.sld_id,
at_id = z2.at_id,
shift_type = z2.shift_type;

commit;

merge into M_BORDER.Z_LOAD@ASOOSD z1 using M_BORDER.Z_LOAD z2 on (z1.sld_id = z2.sld_id and z1.ld_date = z2.ld_date)
when matched then
update set ld_value = z2.ld_value, count_dk = z2.count_dk
when not matched then
insert (sld_id, ld_date, ld_value, count_dk) values (z2.sld_id, z2.ld_date, z2.ld_value, z2.count_dk);

commit;

merge into M_BORDER.Z_LOAD_DETAILED@ASOOSD z1 using M_BORDER.Z_LOAD_DETAILED z2 on (z1.load_date = z2.load_date and z1.sld_or_at_id = z2.sld_or_at_id and z1.duty_id = z2.duty_id)
when matched then
update set load_coeff = z2.load_coeff, department_id = z2.department_id, dt_curr_day_duration = z2.dt_curr_day_duration, is_at = z2.is_at
when not matched then
insert (load_date, duty_id, load_coeff, department_id, dt_curr_day_duration, sld_or_at_id, is_at)
values
(z2.load_date, z2.duty_id, z2.load_coeff, z2.department_id, z2.dt_curr_day_duration, z2.sld_or_at_id, z2.is_at);

commit;

merge into M_BORDER.Z_DUTIES_STATE@ASOOSD z1 using M_BORDER.Z_DUTIES_STATE z2 on (z1.department_id = z2.department_id and z1.ds_date = z2.ds_date)
when matched then
update set ds_state = z2.ds_state, ds_result = z2.ds_result, user_id = z2.user_id, ds_sign = z2.ds_sign
when not matched then
insert (department_id, ds_date, ds_state, ds_result, user_id, ds_sign) values (z2.department_id, z2.ds_date, z2.ds_state, z2.ds_result, z2.user_id, z2.ds_sign);

commit;


merge into M_BORDER.Z_PLAN@ASOOSD z1 using M_BORDER.Z_PLAN z2 on (z1.pl_id = z2.pl_id)
when matched then
update SET
z1.DEPARTMENT_ID = z2.DEPARTMENT_ID,
z1.PL_MONTH = z2.PL_MONTH,
z1.OGG_FIXED = z2.OGG_FIXED, 
z1.OOM_FIXED = z2.OOM_FIXED,
z1.OGG_FIXED_USER = z2.OGG_FIXED_USER, 
z1.OOM_FIXED_USER = z2.OOM_FIXED_USER, 
z1.OGG_SIGNED = z2.OGG_SIGNED, 
z1.OGG_SIGNED_USER = z2.OGG_SIGNED_USER, 
z1.OOM_SIGNED = z2.OOM_SIGNED, 
z1.OOM_SIGNED_USER = z2.OOM_SIGNED_USER
when not matched then
insert (z1.PL_ID, z1.DEPARTMENT_ID, z1.PL_MONTH, z1.OGG_FIXED, 
z1.OOM_FIXED, z1.OGG_FIXED_USER, z1.OOM_FIXED_USER, z1.OGG_SIGNED, z1.OGG_SIGNED_USER, 
z1.OOM_SIGNED, z1.OOM_SIGNED_USER)
values
(z2.PL_ID, z2.DEPARTMENT_ID, z2.PL_MONTH, z2.OGG_FIXED, 
z2.OOM_FIXED, z2.OGG_FIXED_USER, z2.OOM_FIXED_USER, z2.OGG_SIGNED, z2.OGG_SIGNED_USER, 
z2.OOM_SIGNED, z2.OOM_SIGNED_USER);


commit;

MERGE INTO M_BORDER.OSD_PLAN_PDP@ASOOSD z1 USING M_BORDER.OSD_PLAN_PDP z2 on (z1.PDP_ID = z2.PDP_ID)
 
WHEN NOT MATCHED THEN 
INSERT (
  z1.PDP_ID, z1.DEPARTMENT_ID, z1.PDP_START, z1.PDP_STOP, z1.SUBJECT_ID, 
  z1.TOPIC, z1.STATE, z1.SLD_ID, z1.AT_ID, z1.COMMENTS, 
  z1.CLASS_CATEGORY_ID, z1.HOLDING_FORM_ID, z1.ACTIVITY_TYPE, z1.AGE_GROUP, z1.PLACE, 
  z1.CREATION_DATE)
VALUES (
  z2.PDP_ID, z2.DEPARTMENT_ID, z2.PDP_START, z2.PDP_STOP, z2.SUBJECT_ID, 
  z2.TOPIC, z2.STATE, z2.SLD_ID, z2.AT_ID, z2.COMMENTS, 
  z2.CLASS_CATEGORY_ID, z2.HOLDING_FORM_ID, z2.ACTIVITY_TYPE, z2.AGE_GROUP, z2.PLACE, 
  z2.CREATION_DATE)
WHEN MATCHED THEN
UPDATE SET 
  z1.DEPARTMENT_ID = z2.DEPARTMENT_ID,
  z1.PDP_START = z2.PDP_START,
  z1.PDP_STOP = z2.PDP_STOP,
  z1.SUBJECT_ID = z2.SUBJECT_ID,
  z1.TOPIC = z2.TOPIC,
  z1.STATE = z2.STATE,
  z1.SLD_ID = z2.SLD_ID,
  z1.AT_ID = z2.AT_ID,
  z1.COMMENTS = z2.COMMENTS,
  z1.CLASS_CATEGORY_ID = z2.CLASS_CATEGORY_ID,
  z1.HOLDING_FORM_ID = z2.HOLDING_FORM_ID,
  z1.ACTIVITY_TYPE = z2.ACTIVITY_TYPE,
  z1.AGE_GROUP = z2.AGE_GROUP,
  z1.PLACE = z2.PLACE,
  z1.CREATION_DATE = z2.CREATION_DATE;

COMMIT;




/*MERGE INTO M_BORDER.OG_ACTION@ASOOSD z1 A USING M_BORDER.OG_ACTION z2 on (z1.AC_ID = z2.AC_ID)
 
WHEN NOT MATCHED THEN 
INSERT (
  z1.AC_ID, z1.AC_INDEX, z1.DC_ID, z1.AC_NAME, z1.AC_DEADLINE, 
  z1.AC_PERIOD)
VALUES (
  z2.AC_ID, z2.AC_INDEX, z2.DC_ID, z2.AC_NAME, z2.AC_DEADLINE, 
  z2.AC_PERIOD)
WHEN MATCHED THEN
UPDATE SET 
  z1.AC_INDEX = z2.AC_INDEX,
  z1.DC_ID = z2.DC_ID,
  z1.AC_NAME = z2.AC_NAME,
  z1.AC_DEADLINE = z2.AC_DEADLINE,
  z1.AC_PERIOD = z2.AC_PERIOD;

COMMIT;*/






merge into M_BORDER.Z_PLAN_ACTION@ASOOSD z1 using M_BORDER.Z_PLAN_ACTION z2 on (z1.pa_id = z2.pa_id)
when matched then
update SET
z1.PL_ID = z2.PL_ID,
z1.PA_INDEX = z2.PA_INDEX,
z1.AC_ID = z2.AC_ID, 
z1.DK_ID = z2.DK_ID,
z1.PA_NAME = z2.PA_NAME, 
z1.PA_PERIOD = z2.PA_PERIOD, 
z1.OSD_GROUP = z2.OSD_GROUP 
when not matched then
insert (z1.PA_ID, z1.PL_ID, z1.PA_INDEX, z1.AC_ID, 
z1.DK_ID, z1.PA_NAME, z1.PA_PERIOD, z1.OSD_GROUP)
values
(z2.PA_ID, z2.PL_ID, z2.PA_INDEX, z2.AC_ID, 
z2.DK_ID, z2.PA_NAME, z2.PA_PERIOD, z2.OSD_GROUP);


commit;


MERGE INTO M_BORDER.Z_PATTERN@ASOOSD z1 USING M_BORDER.Z_PATTERN z2 on (z1.PATTERN_ID = z2.PATTERN_ID)
 
WHEN NOT MATCHED THEN 
INSERT (
  z1.PATTERN_ID, z1.PATTERN_NAME, z1.PATTERN_KIND, z1.DEPARTMENT_ID)
VALUES (
  z2.PATTERN_ID, z2.PATTERN_NAME, z2.PATTERN_KIND, z2.DEPARTMENT_ID)
WHEN MATCHED THEN
UPDATE SET 
  z1.PATTERN_NAME = z2.PATTERN_NAME,
  z1.PATTERN_KIND = z2.PATTERN_KIND,
  z1.DEPARTMENT_ID = z2.DEPARTMENT_ID;




merge into M_BORDER.Z_PLAN_ACTION_PATTERN@ASOOSD z1 using M_BORDER.Z_PLAN_ACTION_PATTERN z2 on (z1.PA_ID = z2.PA_ID)
when matched then
update SET
z1.PATTERN_ID = z2.PATTERN_ID,
z1.PA_INDEX = z2.PA_INDEX,
z1.AC_ID = z2.AC_ID, 
z1.DK_ID = z2.DK_ID,
z1.PA_NAME = z2.PA_NAME, 
z1.PA_PERIOD = z2.PA_PERIOD, 
z1.OSD_GROUP = z2.OSD_GROUP 
when not matched then
insert (z1.PA_ID, z1.PATTERN_ID, z1.PA_INDEX, z1.AC_ID, 
z1.DK_ID, z1.PA_NAME, z1.PA_PERIOD, z1.OSD_GROUP)
values
(z2.PA_ID, z2.PATTERN_ID, z2.PA_INDEX, z2.AC_ID, 
z2.DK_ID, z2.PA_NAME, z2.PA_PERIOD, z2.OSD_GROUP);


commit;



merge into M_BORDER.Z_PLANNED_EXECUTION@ASOOSD z1 using M_BORDER.Z_PLANNED_EXECUTION z2 on (z1.PA_ID = z2.PA_ID and z1.PE_DATE = z2.PE_DATE)
when matched then
update SET
z1.SLD_ID = z2.SLD_ID, 
z1.AT_ID = z2.AT_ID,
z1.PA_STATUS = z2.PA_STATUS, 
z1.CHANGED = z2.CHANGED 
when not matched then
insert (z1.PA_ID, z1.PE_DATE, z1.SLD_ID, z1.AT_ID, 
z1.PA_STATUS, z1.CHANGED)
values
(z2.PA_ID, z2.PE_DATE, z2.SLD_ID, z2.AT_ID, 
z2.PA_STATUS, z2.CHANGED);


commit;



merge into M_BORDER.OSD_RAZVEDKA_OBJECT@ASOOSD z1 using M_BORDER.OSD_RAZVEDKA_OBJECT z2 on (z1.RO_ID = z2.RO_ID)
when matched then
update SET
z1.DEPARTMENT_ID = z2.DEPARTMENT_ID,			
z1.RO_NAME = z2.RO_NAME, 							
z1.RO_COORD = z2.RO_COORD,				
z1.RO_PRIN = z2.RO_PRIN,					
z1.RO_COMMENT = z2.RO_COMMENT,				
z1.RO_TEXT = z2.RO_TEXT, 						
z1.RO_ARCHIVE = z2.RO_ARCHIVE,				
z1.LATITUDE = z2.LATITUDE,				
z1.LONGITUDE = z2.LONGITUDE,				
z1.LAT_DEGREES = z2.LAT_DEGREES,			
z1.LAT_MINUTES = z2.LAT_MINUTES,				
z1.LAT_SECONDS = z2.LAT_SECONDS,				
z1.LONG_DEGREES = z2.LONG_DEGREES,			
z1.LONG_MINUTES	= z2.LONG_MINUTES,	
z1.LONG_SECONDS	= z2.LONG_SECONDS 
when not matched then
insert (z1.RO_ID, z1.DEPARTMENT_ID, z1.RO_NAME, z1.RO_COORD, z1.RO_PRIN, z1.RO_COMMENT, z1.RO_TEXT, z1.RO_ARCHIVE, 				
z1.LATITUDE, z1.LONGITUDE, z1.LAT_DEGREES, z1.LAT_MINUTES, z1.LAT_SECONDS, z1.LONG_DEGREES, z1.LONG_MINUTES, z1.LONG_SECONDS)
values
(z2.RO_ID, z2.DEPARTMENT_ID, z2.RO_NAME, z2.RO_COORD, z2.RO_PRIN, z2.RO_COMMENT, z2.RO_TEXT, z2.RO_ARCHIVE, 				
z2.LATITUDE, z2.LONGITUDE, z2.LAT_DEGREES, z2.LAT_MINUTES, z2.LAT_SECONDS, z2.LONG_DEGREES, z2.LONG_MINUTES, z2.LONG_SECONDS);


commit;


merge into M_BORDER.OSD_RAZVEDKA_OBJECT_INFO@ASOOSD z1 using M_BORDER.OSD_RAZVEDKA_OBJECT_INFO z2 on (z1.RO_ID = z2.RO_ID and z1.ROI_DATE= z2.ROI_DATE)
when matched then
update SET
z1.ROI_SENDER = z2.ROI_SENDER,			
z1.ROI_COMMENT = z2.ROI_COMMENT, 							
z1.ROI_TEXT = z2.ROI_TEXT,				
z1.ROI_REPORT_DATE = z2.ROI_REPORT_DATE,					
z1.ROI_REPORT_TO = z2.ROI_REPORT_TO
when not matched then
insert (z1.RO_ID, z1.ROI_DATE, z1.ROI_SENDER, z1.ROI_COMMENT, z1.ROI_TEXT, z1.ROI_REPORT_DATE, z1.ROI_REPORT_TO)
values
(z2.RO_ID, z2.ROI_DATE, z2.ROI_SENDER, z2.ROI_COMMENT, z2.ROI_TEXT, z2.ROI_REPORT_DATE, z2.ROI_REPORT_TO);


commit;

merge into M_BORDER.OSD_RAZVEDKA@ASOOSD z1 using M_BORDER.OSD_RAZVEDKA z2 on (z1.DEPARTMENT_ID = z2.DEPARTMENT_ID and z1.RZ_TIME = z2.RZ_TIME)
when matched then
update SET
z1.RZ_PLACE = z2.RZ_PLACE,			
z1.RZ_WHAT = z2.RZ_WHAT,		
z1.RZ_KOMU_DOLOZHENO = z2.RZ_KOMU_DOLOZHENO,			
z1.RZ_WHEN = z2.RZ_WHEN,			
z1.RZ_WHAT_DONE = z2.RZ_WHAT_DONE,		
z1.SLD_ID = z2.SLD_ID,		
z1.AT_ID = z2.AT_ID,			
z1.RZ_OTHER_SENDER = z2.RZ_OTHER_SENDER,			
z1.RZ_SYSTIME = z2.RZ_SYSTIME,		
z1.RO_ID = z2.RO_ID,			
z1.RZ_ADDING_USER = z2.RZ_ADDING_USER
when not matched then
insert (z1.DEPARTMENT_ID, z1.RZ_TIME, z1.RZ_PLACE, z1.RZ_WHAT, z1.RZ_KOMU_DOLOZHENO, z1.RZ_WHEN, z1.RZ_WHAT_DONE, z1.SLD_ID, z1.AT_ID,			
z1.RZ_OTHER_SENDER, z1.RZ_SYSTIME, z1.RO_ID, z1.RZ_ADDING_USER)
values
(z2.DEPARTMENT_ID, z2.RZ_TIME, z2.RZ_PLACE, z2.RZ_WHAT, z2.RZ_KOMU_DOLOZHENO, z2.RZ_WHEN, z2.RZ_WHAT_DONE, z2.SLD_ID, z2.AT_ID,			
z2.RZ_OTHER_SENDER, z2.RZ_SYSTIME, z2.RO_ID, z2.RZ_ADDING_USER);


commit;



