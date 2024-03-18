

merge into M_BORDER.P_SOLDIER@ASOOSD z1 using M_BORDER.P_SOLDIER z2 on (z1.SLD_ID = z2.SLD_ID and z1.ORG_ID = z2.ORG_ID)

when matched then
update set 
    SLD_NAME1 = z2.SLD_NAME1, 
    SLD_NAME2 = z2.SLD_NAME2, 
    SLD_NAME3 = z2.SLD_NAME3, 
    USER_ID = z2.USER_ID 

when not matched then
insert (
     SLD_ID, 
     ORG_ID, 
     SLD_NAME1, 
     SLD_NAME2, 
     SLD_NAME3, 
     USER_ID) 

values (
    z2.SLD_ID, 
    z2.ORG_ID, 
    z2.SLD_NAME1, 
    z2.SLD_NAME2, 
    z2.SLD_NAME3, 
    z2.USER_ID);

commit;

/*-----�� �� 4-�� ������ � �� 1-�� ������ --- ������� P_ATTACHED '������������������ � ��������������' ---*/

merge into M_BORDER.P_ATTACHED@ASOOSD z1 using M_BORDER.P_ATTACHED z2 on (z1.AT_ID = z2.AT_ID and z1.DEPARTMENT_ID = z2.DEPARTMENT_ID)

when matched then
update set 
    RANK_ID = z2.RANK_ID, 
    AT_BIRTHDAY = z2.AT_BIRTHDAY, 
    AT_DATE_IN = z2.AT_DATE_IN, 
    AT_NAME1 = z2.AT_NAME1, 
    AT_NAME2 = z2.AT_NAME2, 
    AT_NAME3 = z2.AT_NAME3, 
    EDUCATION_ID = z2.EDUCATION_ID,
    AT_DATE_OUT = z2.AT_DATE_OUT,
    AT_CALLUP = z2.AT_CALLUP,
    AT_WHEREFROM = z2.AT_WHEREFROM,
    AT_KIND = z2.AT_KIND,             
    DOA_ID = z2.DOA_ID,              
    AT_COMMENT = z2.AT_COMMENT,         
    AT_DPS_EXT = z2.AT_DPS_EXT,          
    AT_CONTRACT = z2.AT_CONTRACT,                       
    AT_PP_FOR_VISITING = z2.AT_PP_FOR_VISITING

when not matched then
insert (
      AT_ID,              
      RANK_ID,            
      DEPARTMENT_ID,       
      AT_BIRTHDAY,         
      AT_DATE_IN,                                
      AT_NAME1,            
      AT_NAME2,            
      AT_NAME3,            
      EDUCATION_ID,       
      AT_DATE_OUT,         
      AT_CALLUP,           
      AT_WHEREFROM ,       
      AT_KIND,              
      DOA_ID ,             
      AT_COMMENT,        
      AT_DPS_EXT,       
      AT_CONTRACT,         
      AT_PP_FOR_VISITING) 

values (
      z2.AT_ID,              
      z2.RANK_ID,            
      z2.DEPARTMENT_ID,       
      z2.AT_BIRTHDAY,         
      z2.AT_DATE_IN,                                
      z2.AT_NAME1,            
      z2.AT_NAME2,            
      z2.AT_NAME3,            
      z2.EDUCATION_ID,       
      z2.AT_DATE_OUT,         
      z2.AT_CALLUP,           
      z2.AT_WHEREFROM,       
      z2.AT_KIND,              
      z2.DOA_ID ,             
      z2.AT_COMMENT,        
      z2.AT_DPS_EXT,       
      z2.AT_CONTRACT,         
      z2.AT_PP_FOR_VISITING); 
commit;

/*--- ������� OSD_EQUIPMENT '���� ��������� �������������'-----*/

merge into M_BORDER.OSD_EQUIPMENT@ASOOSD z1 using M_BORDER.OSD_EQUIPMENT z2 on (z1.EQUIPMENT_ID = z2.EQUIPMENT_ID and z1.DEPARTMENT_ID = z2.DEPARTMENT_ID)

when matched then
update set 
  EQUIP_TYPE_ID = Z2.EQUIP_TYPE_ID,
  DEP_TYPE_ID = Z2.DEP_TYPE_ID,
  SERIAL_NUM = Z2.SERIAL_NUM,
  INVENTORY_NUM = Z2.INVENTORY_NUM,
  DESCRIPTION = Z2.DESCRIPTION,
  WAREHOUSE = Z2.WAREHOUSE,
  USED_SOLUTION = Z2.USED_SOLUTION,
  ADDL_INFO = Z2.ADDL_INFO,
  BEGIN_DATE = Z2.BEGIN_DATE,
  END_DATE = Z2.END_DATE,
  UPDATE_POSITION = Z2.UPDATE_POSITION,
  SIGNAL_CODE = Z2.SIGNAL_CODE,
  YEAR_OF_ISSUE = Z2.YEAR_OF_ISSUE 

when not matched then
INSERT (
  EQUIPMENT_ID, EQUIP_TYPE_ID, DEP_TYPE_ID, DEPARTMENT_ID, SERIAL_NUM, 
  INVENTORY_NUM, DESCRIPTION, WAREHOUSE, USED_SOLUTION, ADDL_INFO, 
  BEGIN_DATE, END_DATE, UPDATE_POSITION, SIGNAL_CODE, YEAR_OF_ISSUE)
VALUES (
  Z2.EQUIPMENT_ID, Z2.EQUIP_TYPE_ID, Z2.DEP_TYPE_ID, Z2.DEPARTMENT_ID, Z2.SERIAL_NUM, 
  Z2.INVENTORY_NUM, Z2.DESCRIPTION, Z2.WAREHOUSE, Z2.USED_SOLUTION, Z2.ADDL_INFO, 
  Z2.BEGIN_DATE, Z2.END_DATE, Z2.UPDATE_POSITION, Z2.SIGNAL_CODE, Z2.YEAR_OF_ISSUE);

commit;

/*--- ������� Z_CARS '���������� �������������'-----*/
merge into M_BORDER.Z_CARS@ASOOSD z1 using M_BORDER.Z_CARS z2 on (z1.CR_ID = z2.CR_ID and z1.DEPARTMENT_ID = z2.DEPARTMENT_ID)

WHEN NOT MATCHED THEN 
INSERT (
  CR_ID, DEPARTMENT_ID, CR_NUMBER, CR_MARKA, CR_DATE, 
  CR_DATE_OUT, CR_COMMENT, CR_KIND, CR_ADD, NAVIGATION_ID, 
  ORG_ID, GPS_SERIAL, SPEEDOMETER, LIMIT_ON_YEAR, EQUIPMENT_ID)
VALUES (
  z2.CR_ID, z2.DEPARTMENT_ID, z2.CR_NUMBER, z2.CR_MARKA, z2.CR_DATE, 
  z2.CR_DATE_OUT, z2.CR_COMMENT, z2.CR_KIND, z2.CR_ADD, Z2.NAVIGATION_ID, 
  Z2.ORG_ID, Z2.GPS_SERIAL, Z2.SPEEDOMETER, Z2.LIMIT_ON_YEAR, Z2.EQUIPMENT_ID)
WHEN MATCHED THEN
UPDATE SET 
  CR_NUMBER = Z2.CR_NUMBER,
  CR_MARKA = Z2.CR_MARKA,
  CR_DATE = Z2.CR_DATE,
  CR_DATE_OUT = Z2.CR_DATE_OUT,
  CR_COMMENT = Z2.CR_COMMENT,
  CR_KIND = Z2.CR_KIND,
  CR_ADD = Z2.CR_ADD,
  NAVIGATION_ID = Z2.NAVIGATION_ID,
  ORG_ID = Z2.ORG_ID,
  GPS_SERIAL = Z2.GPS_SERIAL,
  SPEEDOMETER = Z2.SPEEDOMETER,
  LIMIT_ON_YEAR = Z2.LIMIT_ON_YEAR,
  EQUIPMENT_ID = Z2.EQUIPMENT_ID;

COMMIT;


/*--- ������� Z_MONITORING_ENV_R '����� ������������� ����'-----*/
merge into M_BORDER.Z_MONITORING_ENV_R@ASOOSD z1 using M_BORDER.Z_MONITORING_ENV_R z2 on (z1.ENV_R_ID = z2.ENV_R_ID and z1.DEPARTMENT_ID = z2.DEPARTMENT_ID)

WHEN NOT MATCHED THEN 
INSERT (
  ENV_R_ID, ENV_R_PLACE, SLD_ID, AT_ID, DEPARTMENT_ID, 
  ENV_R_VALUE, ADD_INFO, DETECTION_DATE, EQUIPMENT_ID, ENV_ADDING_DATE, 
  ENV_ADDING_USER)
VALUES (
  Z2.ENV_R_ID, Z2.ENV_R_PLACE, Z2.SLD_ID, Z2.AT_ID, Z2.DEPARTMENT_ID, 
  Z2.ENV_R_VALUE, Z2.ADD_INFO, Z2.DETECTION_DATE, Z2.EQUIPMENT_ID, Z2.ENV_ADDING_DATE, 
  Z2.ENV_ADDING_USER)
WHEN MATCHED THEN
UPDATE SET 
  ENV_R_PLACE = Z2.ENV_R_PLACE,
  SLD_ID = Z2.SLD_ID,
  AT_ID = Z2.AT_ID,
  ENV_R_VALUE = Z2.ENV_R_VALUE,
  ADD_INFO = Z2.ADD_INFO,
  DETECTION_DATE = Z2.DETECTION_DATE,
  EQUIPMENT_ID = Z2.EQUIPMENT_ID,
  ENV_ADDING_DATE = Z2.ENV_ADDING_DATE,
  ENV_ADDING_USER = Z2.ENV_ADDING_USER;

COMMIT;


/*--- ������� Z_MONITORING_ENV_T '����� �����������'-----*/
merge into M_BORDER.Z_MONITORING_ENV_T@ASOOSD z1 using M_BORDER.Z_MONITORING_ENV_T z2 on (z1.ENV_T_ID = z2.ENV_T_ID and z1.DEPARTMENT_ID = z2.DEPARTMENT_ID)

WHEN NOT MATCHED THEN 
INSERT (
  ENV_T_ID, DETECTION_DATE, SLD_ID, AT_ID, DEPARTMENT_ID, 
  ENV_T_VALUE_ST, ENV_T_VALUE_SL, ADD_INFO, ENV_T_VALUE_W, ENV_T_WEATHER_ID, 
  ENV_T_WIND_ID, ENV_ADDING_DATE, ENV_ADDING_USER)
VALUES (
  Z2.ENV_T_ID, Z2.DETECTION_DATE, Z2.SLD_ID, Z2.AT_ID, Z2.DEPARTMENT_ID, 
  Z2.ENV_T_VALUE_ST, Z2.ENV_T_VALUE_SL, Z2.ADD_INFO, Z2.ENV_T_VALUE_W, Z2.ENV_T_WEATHER_ID, 
  Z2.ENV_T_WIND_ID, Z2.ENV_ADDING_DATE, Z2.ENV_ADDING_USER)
WHEN MATCHED THEN
UPDATE SET 
  DETECTION_DATE = Z2.DETECTION_DATE,
  SLD_ID = Z2.SLD_ID,
  AT_ID = Z2.AT_ID,
  ENV_T_VALUE_ST = Z2.ENV_T_VALUE_ST,
  ENV_T_VALUE_SL = Z2.ENV_T_VALUE_SL,
  ADD_INFO = Z2.ADD_INFO,
  ENV_T_VALUE_W = Z2.ENV_T_VALUE_W,
  ENV_T_WEATHER_ID = Z2.ENV_T_WEATHER_ID,
  ENV_T_WIND_ID = Z2.ENV_T_WIND_ID,
  ENV_ADDING_DATE = Z2.ENV_ADDING_DATE,
  ENV_ADDING_USER = Z2.ENV_ADDING_USER;

COMMIT;

/*-----�� �� 4-�� ������ � �� 1-�� ������ --- ������� Z_DUTIES_SHABLON '������������ �������� ������� �� ���' ---*/

merge into M_BORDER.Z_DUTIES_SHABLON@ASOOSD z1 using M_BORDER.Z_DUTIES_SHABLON z2 on (z1.DSH_ID = z2.DSH_ID and z1.DEPARTMENT_ID = z2.DEPARTMENT_ID)

when matched then
update set 
    DSH_NAME = z2.DSH_NAME, 
    DSH_PROPERTY = z2.DSH_PROPERTY 

when not matched then
insert (
     DSH_ID, 
     DSH_NAME, 
     DSH_PROPERTY, 
     DEPARTMENT_ID) 

values (
    z2.DSH_ID, 
    z2.DSH_NAME, 
    z2.DSH_PROPERTY, 
    z2.DEPARTMENT_ID);

commit;

merge into M_BORDER.Z_PLAN@ASOOSD z1 using M_BORDER.Z_PLAN z2 on (z1.pl_id = z2.pl_id and z1.department_id = z2.department_id)
/*when matched then
update set pl_month = z2.pl_month, ogg_fixed = z2.ogg_fixed, oom_fixed = z2.oom_fixed, ogg_fixed_user = z2.ogg_fixed_user, oom_fixed_user = z2.oom_fixed_user, ogg_signed = z2.ogg_signed, ogg_signed_user = z2.ogg_signed_user, oom_signed = z2.oom_signed, oom_signed_user = z2.oom_signed_user
*/when not matched then
insert (pl_id, department_id, pl_month, ogg_fixed, oom_fixed, ogg_fixed_user, oom_fixed_user, ogg_signed, ogg_signed_user, oom_signed, oom_signed_user) 
values (z2.pl_id, z2.department_id, z2.pl_month, z2.ogg_fixed, z2.oom_fixed, z2.ogg_fixed_user, z2.oom_fixed_user, z2.ogg_signed, z2.ogg_signed_user, z2.oom_signed, z2.oom_signed_user);

commit;

merge into M_BORDER.Z_PLAN_ACTION@ASOOSD z1 using M_BORDER.Z_PLAN_ACTION z2 on (z1.pa_id = z2.pa_id)
/*when matched then
update set pl_id = z2.pl_id, pa_index = z2.pa_index, ac_id = z2.ac_id, dk_id = z2.dk_id, pa_name = z2.pa_name, pa_period = z2.pa_period, osd_group = z2.osd_group*/
when not matched then
insert (pa_id, pl_id, pa_index, ac_id, dk_id, pa_name, pa_period, osd_group) 
values (z2.pa_id, z2.pl_id, z2.pa_index, z2.ac_id, z2.dk_id, z2.pa_name, z2.pa_period, z2.osd_group);

commit;


merge into M_BORDER.Z_PLANNED_EXECUTION@ASOOSD z1 using M_BORDER.Z_PLANNED_EXECUTION z2 on (z1.pa_id = z2.pa_id and z1.pe_date = z2.pe_date)
/*when matched then
update set sld_id = z2.sld_id, at_id = z2.at_id, pa_status = z2.pa_status, changed = z2.changed*/
when not matched then
insert (pa_id, pe_date, sld_id, at_id, pa_status, changed) 
values (z2.pa_id, z2.pe_date, z2.sld_id, z2.at_id, z2.pa_status, z2.changed);

commit;

/*--- ������� P_STAFF_LIST '������� ����������' (insert)---*/

MERGE INTO M_BORDER.P_STAFF_LIST@ASOOSD z1 USING M_BORDER.P_STAFF_LIST z2 ON
(z1.SL_ID = z2.SL_ID and z1.DEPARTMENT_ID = z2.DEPARTMENT_ID)
 
 /*
WHEN MATCHED THEN
UPDATE SET 
  SL_DATE = Z2.SL_DATE,
  SL_NAME = Z2.SL_NAME
*/

WHEN NOT MATCHED THEN
INSERT ( 
 SL_ID, DEPARTMENT_ID, SL_DATE, SL_NAME)
VALUES (
 Z2.SL_ID, Z2.DEPARTMENT_ID, Z2.SL_DATE, Z2.SL_NAME);

commit;

/*--- ������� P_STAFF_LIST_POSITION '������� ���������� � ���������������' (insert)---*/

MERGE INTO M_BORDER.P_STAFF_LIST_POSITION@ASOOSD z1 USING M_BORDER.P_STAFF_LIST_POSITION z2 ON
(z1.SL_ID = z2.SL_ID and z1.SL_NUMBER = z2.SL_NUMBER)
 
 /*
WHEN MATCHED THEN
UPDATE SET 

  RANK_ID_S = Z2.RANK_ID_S,
  SL_POSITION = Z2.SL_POSITION,
  SLD_ID = Z2.SLD_ID,
  SL_COMMENT = Z2.SL_COMMENT,
  SL_POSITION_ID = Z2.SL_POSITION_ID,
  EXISTS_CANDIDATE = Z2.EXISTS_CANDIDATE,
  PLAN_EXECUTOR = Z2.PLAN_EXECUTOR
*/

WHEN NOT MATCHED THEN 
INSERT (
  SL_ID, SL_NUMBER, RANK_ID_S, SL_POSITION, SLD_ID, 
  SL_COMMENT, SL_POSITION_ID, EXISTS_CANDIDATE, PLAN_EXECUTOR)
VALUES (
  Z2.SL_ID, Z2.SL_NUMBER, Z2.RANK_ID_S, Z2.SL_POSITION, Z2.SLD_ID, 
  Z2.SL_COMMENT, Z2.SL_POSITION_ID, Z2.EXISTS_CANDIDATE, Z2.PLAN_EXECUTOR);

commit;

/*--- ������� P_STAFF_LIST_POSITION_A '������� ���������� c ������������������' (insert)---*/

MERGE INTO M_BORDER.P_STAFF_LIST_POSITION_A@ASOOSD z1 USING M_BORDER.P_STAFF_LIST_POSITION_A z2 ON
(z1.SL_ID = z2.SL_ID and z1.SL_NUMBER = z2.SL_NUMBER)
 
 /*
WHEN MATCHED THEN
UPDATE SET 
  AT_ID = Z2.AT_ID,
  SL_POSITION = Z2.SL_POSITION,
  SL_COMMENT = Z2.SL_COMMENT,
  SL_POSITION_ID = Z2.SL_POSITION_ID,
  PLAN_EXECUTOR = Z2.PLAN_EXECUTOR
*/

WHEN NOT MATCHED THEN
INSERT (
  SL_ID, SL_NUMBER, AT_ID, SL_POSITION, SL_COMMENT, 
  SL_POSITION_ID, PLAN_EXECUTOR)
VALUES (
  Z2.SL_ID, Z2.SL_NUMBER, Z2.AT_ID, Z2.SL_POSITION, Z2.SL_COMMENT, 
  Z2.SL_POSITION_ID, Z2.PLAN_EXECUTOR);

commit;

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

insert into m_border.p_attached@ASOOSD 
select * from m_border.p_attached where at_id not in (select at_id from m_border.p_attached@ASOOSD);

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

/*-----�� �� 4-�� ������ � �� 1-�� ������ ---������� Z_DUTY_PLACE IS '����� ������ ������'----(��������� ����������� ������)*/

MERGE INTO M_BORDER.Z_DUTY_PLACE@ASOOSD z1 USING M_BORDER.Z_DUTY_PLACE z2 ON
(z1.DUTY_ID = z2.DUTY_ID 
and z1.OBJECTID = z2.OBJECTID)
 
 /*
WHEN MATCHED THEN
UPDATE SET 
  DP_INDEX = Z2.DP_INDEX
*/

WHEN NOT MATCHED THEN 
INSERT (
  DUTY_ID, DP_INDEX, OBJECTID)
VALUES (
  Z2.DUTY_ID, Z2.DP_INDEX, Z2.OBJECTID);

commit;

/*-----�� �� 4-�� ������ � �� 1-�� ������ ----'�������� ������� �������� ������'---*/

MERGE INTO M_BORDER.Z_DUTY_MEANS@ASOOSD A USING M_BORDER.Z_DUTY_MEANS B ON (A.DUTY_ID = B.DUTY_ID and A.DUTY_NUMBER = B.DUTY_NUMBER)
WHEN NOT MATCHED THEN 
INSERT (
  DUTY_ID, DUTY_NUMBER, SU_ID, EQUIPMENT_ID)
VALUES (
  B.DUTY_ID, B.DUTY_NUMBER, B.SU_ID, B.EQUIPMENT_ID)
WHEN MATCHED THEN
UPDATE SET 
  A.SU_ID = B.SU_ID,
  A.EQUIPMENT_ID = B.EQUIPMENT_ID;

COMMIT;


--------����� ������ ������� �Z_CAR_OUT (����� ����������� � �������)� �� 1-�� ������� �� �� �������������-----
merge into M_BORDER.Z_CAR_OUT@ASOOSD Z1 using M_BORDER.Z_CAR_OUT Z2 on (Z1.CO_ID = Z2.CO_ID and Z1.DEPARTMENT_ID = Z2.DEPARTMENT_ID)

WHEN NOT MATCHED THEN 
INSERT (
  CO_ID, CO_SYSTIME, DEPARTMENT_ID, CO_DRIVER, CO_LIST_NUMBER, 
  CO_MARKA, CO_NUMBER, OUT_CONDITION, OUT_SPEEDOMETER, CO_SM1, 
  CO_TIME, CO_OUT_REASON_ID, CO_AIM_PLACE, OUT_OWNER, RETURN_PERIOD, 
  CO_TIME_HOME, IN_CONDITION, IN_SPEEDOMETER, CO_SM2, IN_POLICE_VIOLATION, 
  CO_PASSANGERS, CO_COMMENT, CO_PROPERTY, CR_ID, DUTY_ID, 
  CO_KIND, ORG_ID, ORDER_CO_OUT, CO_DUTY_KIND, ENV_T_ID, 
  TRAILER_ID, CO_ADDING_DATE, CO_ADDING_USER)
VALUES (
  Z2.CO_ID, Z2.CO_SYSTIME, Z2.DEPARTMENT_ID, Z2.CO_DRIVER, Z2.CO_LIST_NUMBER, 
  Z2.CO_MARKA, Z2.CO_NUMBER, Z2.OUT_CONDITION, Z2.OUT_SPEEDOMETER, Z2.CO_SM1, 
  Z2.CO_TIME, Z2.CO_OUT_REASON_ID, Z2.CO_AIM_PLACE, Z2.OUT_OWNER, Z2.RETURN_PERIOD, 
  Z2.CO_TIME_HOME, Z2.IN_CONDITION, Z2.IN_SPEEDOMETER, Z2.CO_SM2, Z2.IN_POLICE_VIOLATION, 
  Z2.CO_PASSANGERS, Z2.CO_COMMENT, Z2.CO_PROPERTY, Z2.CR_ID, Z2.DUTY_ID, 
  Z2.CO_KIND, Z2.ORG_ID, Z2.ORDER_CO_OUT, Z2.CO_DUTY_KIND, Z2.ENV_T_ID, 
  Z2.TRAILER_ID, Z2.CO_ADDING_DATE, Z2.CO_ADDING_USER)

WHEN MATCHED THEN
UPDATE SET 
  CO_SYSTIME = Z2.CO_SYSTIME,
  CO_DRIVER = Z2.CO_DRIVER,
  CO_LIST_NUMBER = Z2.CO_LIST_NUMBER,
  CO_MARKA = Z2.CO_MARKA,
  CO_NUMBER = Z2.CO_NUMBER,
  OUT_CONDITION = Z2.OUT_CONDITION,
  OUT_SPEEDOMETER = Z2.OUT_SPEEDOMETER,
  CO_SM1 = Z2.CO_SM1,
  CO_TIME = Z2.CO_TIME,
  CO_OUT_REASON_ID = Z2.CO_OUT_REASON_ID,
  CO_AIM_PLACE = Z2.CO_AIM_PLACE,
  OUT_OWNER = Z2.OUT_OWNER,
  RETURN_PERIOD = Z2.RETURN_PERIOD,
  CO_TIME_HOME = Z2.CO_TIME_HOME,
  IN_CONDITION = Z2.IN_CONDITION,
  IN_SPEEDOMETER = Z2.IN_SPEEDOMETER,
  CO_SM2 = Z2.CO_SM2,
  IN_POLICE_VIOLATION = Z2.IN_POLICE_VIOLATION,
  CO_PASSANGERS = Z2.CO_PASSANGERS,
  CO_COMMENT = Z2.CO_COMMENT,
  CO_PROPERTY = Z2.CO_PROPERTY,
  CR_ID = Z2.CR_ID,
  DUTY_ID = Z2.DUTY_ID,
  CO_KIND = Z2.CO_KIND,
  ORG_ID = Z2.ORG_ID,
  ORDER_CO_OUT = Z2.ORDER_CO_OUT,
  CO_DUTY_KIND = Z2.CO_DUTY_KIND,
  ENV_T_ID = Z2.ENV_T_ID,
  TRAILER_ID = Z2.TRAILER_ID,
  CO_ADDING_DATE = Z2.CO_ADDING_DATE,
  CO_ADDING_USER = Z2.CO_ADDING_USER;
COMMIT;

/*--- ������� Z_CARS_SPEEDOMETER             - ��������� ���������� ---*/
merge into M_BORDER.Z_CARS_SPEEDOMETER@ASOOSD Z1 using M_BORDER.Z_CARS_SPEEDOMETER Z2 on (Z1.CS_ID = Z2.CS_ID)

WHEN NOT MATCHED THEN 
INSERT (
  CS_ID, CR_ID, CS_DATE, CS_VALUE, CO_ID)
VALUES (
  Z2.CS_ID, Z2.CR_ID, Z2.CS_DATE, Z2.CS_VALUE, Z2.CO_ID)
WHEN MATCHED THEN
UPDATE SET 
  CR_ID = Z2.CR_ID,
  CS_DATE = Z2.CS_DATE,
  CS_VALUE = Z2.CS_VALUE,
  CO_ID = Z2.CO_ID;
COMMIT;

/*--- ������� Z_CAR_KM         - ��������� ���������� � ������ ������ - ���������� ---*/
merge into M_BORDER.Z_CAR_KM@ASOOSD z1 using M_BORDER.Z_CAR_KM z2 on (z1.CR_ID = z2.CR_ID and z1.CR_DATE_KM = z2.CR_DATE_KM)
WHEN NOT MATCHED THEN 
INSERT (
  CR_ID, CR_DATE_KM, CR_KM)
VALUES (
  z2.CR_ID, z2.CR_DATE_KM, z2.CR_KM)
WHEN MATCHED THEN
UPDATE SET 
  CR_KM = z2.CR_KM;

COMMIT;


--------����� ������ ������� �Z_CAR_OUT_PLACE (������.���� � �������, ���� ������� ��)� �� 1-�� ������� �� �� �������������-----

merge into M_BORDER.Z_CAR_OUT_PLACE@ASOOSD z1 using M_BORDER.Z_CAR_OUT_PLACE z2 on(z1.CO_ID = z2.CO_ID and z1.CO_INDEX = z2.CO_INDEX)
WHEN MATCHED THEN
UPDATE SET 
  OBJECTID = z2.OBJECTID,
  BS_NSING = z2.BS_NSING,
  BS_ID = z2.BS_ID
  
WHEN NOT MATCHED THEN 
INSERT (
  CO_ID, CO_INDEX, OBJECTID, BS_NSING, BS_ID)
VALUES (
  z2.CO_ID, z2.CO_INDEX, z2.OBJECTID, z2.BS_NSING, z2.BS_ID);
COMMIT;

/*--- ������� Z_CAR_OUT_STAFF         - ��������, �������� �� ���� (������) ---*/
merge into M_BORDER.Z_CAR_OUT_STAFF@ASOOSD z1 using M_BORDER.Z_CAR_OUT_STAFF z2 on (z1.CO_ID = z2.CO_ID and z1.COS_INDEX = z2.COS_INDEX)

WHEN NOT MATCHED THEN 
INSERT (
  CO_ID, COS_INDEX, SLD_ID, AT_ID)
VALUES (
  z2.CO_ID, z2.COS_INDEX, z2.SLD_ID, z2.AT_ID)
WHEN MATCHED THEN
UPDATE SET 
  SLD_ID = z2.SLD_ID,
  AT_ID = z2.AT_ID;

COMMIT;



/*-----�� �� 4-�� ������ � �� 1-�� ������ ---- "������� ��������� ��������"---*/

MERGE INTO M_BORDER.OSD_RAZVEDKA_OBJECT@ASOOSD z1 USING M_BORDER.OSD_RAZVEDKA_OBJECT z2 ON
(z1.DEPARTMENT_ID = z2.DEPARTMENT_ID 
and z1.RO_ID = z2.RO_ID)
 
 /*
WHEN MATCHED THEN
UPDATE SET 
  RO_NAME = Z2.RO_NAME,
  RO_COORD = Z2.RO_COORD,
  RO_PRIN = Z2.RO_PRIN,
  RO_COMMENT = Z2.RO_COMMENT,
  RO_TEXT = Z2.RO_TEXT,
  RO_ARCHIVE = Z2.RO_ARCHIVE,
  LATITUDE = Z2.LATITUDE,
  LONGITUDE = Z2.LONGITUD
*/

WHEN NOT MATCHED THEN 
INSERT (
  RO_ID, DEPARTMENT_ID, RO_NAME, RO_COORD, RO_PRIN, 
  RO_COMMENT, RO_TEXT, RO_ARCHIVE, LATITUDE, LONGITUDE)
VALUES (
  Z2.RO_ID, Z2.DEPARTMENT_ID, Z2.RO_NAME, Z2.RO_COORD, Z2.RO_PRIN, 
  Z2.RO_COMMENT, Z2.RO_TEXT, Z2.RO_ARCHIVE, Z2.LATITUDE, Z2.LONGITUDE);

commit;
----������� "������ ����������"----

MERGE INTO M_BORDER.OSD_RAZVEDKA@ASOOSD z1 USING M_BORDER.OSD_RAZVEDKA z2 ON
(z1.DEPARTMENT_ID = z2.DEPARTMENT_ID and z1.RZ_TIME = z2.RZ_TIME)
 
 /*
WHEN MATCHED THEN
UPDATE SET 
  RZ_PLACE = Z2.RZ_PLACE,
  RZ_WHAT = Z2.RZ_WHAT,
  RZ_KOMU_DOLOZHENO = Z2.RZ_KOMU_DOLOZHENO,
  RZ_WHEN = Z2.RZ_WHEN,
  RZ_WHAT_DONE = Z2.RZ_WHAT_DONE,
  SLD_ID = Z2.SLD_ID,
  AT_ID = Z2.AT_ID,
  RZ_OTHER_SENDER = Z2.RZ_OTHER_SENDER,
  RZ_SYSTIME = Z2.RZ_SYSTIME,
  RO_ID = Z2.B.RO_ID,
  RZ_ADDING_USER = Z2.RZ_ADDING_USER
*/

WHEN NOT MATCHED THEN 
INSERT (
  DEPARTMENT_ID, RZ_TIME, RZ_PLACE, RZ_WHAT, RZ_KOMU_DOLOZHENO, 
  RZ_WHEN, RZ_WHAT_DONE, SLD_ID, AT_ID, RZ_OTHER_SENDER, 
  RZ_SYSTIME, RO_ID, RZ_ADDING_USER)
VALUES (
  Z2.DEPARTMENT_ID, Z2.RZ_TIME, Z2.RZ_PLACE, Z2.RZ_WHAT, Z2.RZ_KOMU_DOLOZHENO, 
  Z2.RZ_WHEN, Z2.RZ_WHAT_DONE, Z2.SLD_ID, Z2.AT_ID, Z2.RZ_OTHER_SENDER, 
  Z2.RZ_SYSTIME, Z2.RO_ID, Z2.RZ_ADDING_USER);

commit;

/*--- ������� Z_TG '�������� ��'-----*/

merge into M_BORDER.Z_TG@ASOOSD z1 using M_BORDER.Z_TG z2 on (z1.TG_ID = z2.TG_ID and z1.DEPARTMENT_ID = z2.DEPARTMENT_ID)

WHEN NOT MATCHED THEN 
INSERT (
  TG_ID, DEPARTMENT_ID, TG_TIME, TG_REASON, TG_TIME_PLACE, 
  TG_TIME_HOME, TG_MEANS, TG_COMMENT, DUTY_ID, TG_KIND, 
  BS_ID, SWITCH_ID, TG_MEASURE, TG_ADDING_DATE, TG_ADDING_USER)
VALUES (
  Z2.TG_ID, Z2.DEPARTMENT_ID, Z2.TG_TIME, Z2.TG_REASON, Z2.TG_TIME_PLACE, 
  Z2.TG_TIME_HOME, Z2.TG_MEANS, Z2.TG_COMMENT, Z2.DUTY_ID, Z2.TG_KIND, 
  Z2.BS_ID, Z2.SWITCH_ID, Z2.TG_MEASURE, Z2.TG_ADDING_DATE, Z2.TG_ADDING_USER)
WHEN MATCHED THEN
UPDATE SET 
  TG_TIME = Z2.TG_TIME,
  TG_REASON = Z2.TG_REASON,
  TG_TIME_PLACE = Z2.TG_TIME_PLACE,
  TG_TIME_HOME = Z2.TG_TIME_HOME,
  TG_MEANS = Z2.TG_MEANS,
  TG_COMMENT = Z2.TG_COMMENT,
  DUTY_ID = Z2.DUTY_ID,
  TG_KIND = Z2.TG_KIND,
  BS_ID = Z2.BS_ID,
  SWITCH_ID = Z2.SWITCH_ID,
  TG_MEASURE = Z2.TG_MEASURE,
  TG_ADDING_DATE = Z2.TG_ADDING_DATE,
  TG_ADDING_USER = Z2.TG_ADDING_USER;

COMMIT;


/*--- ������� Z_TG_STAFF '������ ��������� ������'-----*/

merge into M_BORDER.Z_TG_STAFF@ASOOSD z1 using M_BORDER.Z_TG_STAFF z2 on (z1.TG_ID = z2.TG_ID and z1.TG_INDEX = z2.TG_INDEX)

WHEN NOT MATCHED THEN 
INSERT (
  TG_ID, TG_INDEX, SLD_ID, AT_ID)
VALUES (
  z2.TG_ID, z2.TG_INDEX, z2.SLD_ID, z2.AT_ID)

WHEN MATCHED THEN
UPDATE SET 
  SLD_ID = z2.SLD_ID,
  AT_ID = z2.AT_ID;

COMMIT;

/*-------------------------------Z_IDEA-----------------------------------------------*/
merge into M_BORDER.Z_IDEA@ASOOSD z1 using M_BORDER.Z_IDEA z2 on (z1.DEPARTMENT_ID = z2.DEPARTMENT_ID and z1.DS_DATE = z2.DS_DATE)

when matched then
update set 
    ID_TEXT = z2.ID_TEXT, 
    ZADELKA_NA_KSP = z2.ZADELKA_NA_KSP 

when not matched then
insert (
     DEPARTMENT_ID, 
     DS_DATE, 
     ID_TEXT, 
     ZADELKA_NA_KSP) 

values (
    z2.DEPARTMENT_ID, 
    z2.DS_DATE, 
    z2.ID_TEXT, 
    z2.ZADELKA_NA_KSP);

commit;

/*---------------------------------OSD_PLAN_PDP---------------------------------------------*/

MERGE INTO M_BORDER.OSD_PLAN_PDP@ASOOSD A USING M_BORDER.OSD_PLAN_PDP B ON (A.PDP_ID = B.PDP_ID and A.DEPARTMENT_ID = B.DEPARTMENT_ID)
WHEN NOT MATCHED THEN 
INSERT (
  PDP_ID, DEPARTMENT_ID, PDP_START, PDP_STOP, SUBJECT_ID, 
  TOPIC, STATE, SLD_ID, AT_ID, COMMENTS, 
  CLASS_CATEGORY_ID, HOLDING_FORM_ID, ACTIVITY_TYPE, AGE_GROUP, PLACE, 
  CREATION_DATE)
VALUES (
  B.PDP_ID, B.DEPARTMENT_ID, B.PDP_START, B.PDP_STOP, B.SUBJECT_ID, 
  B.TOPIC, B.STATE, B.SLD_ID, B.AT_ID, B.COMMENTS, 
  B.CLASS_CATEGORY_ID, B.HOLDING_FORM_ID, B.ACTIVITY_TYPE, B.AGE_GROUP, B.PLACE, 
  B.CREATION_DATE)
WHEN MATCHED THEN
UPDATE SET 
  A.PDP_START = B.PDP_START,
  A.PDP_STOP = B.PDP_STOP,
  A.SUBJECT_ID = B.SUBJECT_ID,
  A.TOPIC = B.TOPIC,
  A.STATE = B.STATE,
  A.SLD_ID = B.SLD_ID,
  A.AT_ID = B.AT_ID,
  A.COMMENTS = B.COMMENTS,
  A.CLASS_CATEGORY_ID = B.CLASS_CATEGORY_ID,
  A.HOLDING_FORM_ID = B.HOLDING_FORM_ID,
  A.ACTIVITY_TYPE = B.ACTIVITY_TYPE,
  A.AGE_GROUP = B.AGE_GROUP,
  A.PLACE = B.PLACE,
  A.CREATION_DATE = B.CREATION_DATE;

COMMIT;

/*----------------OSD_WORK_WITH_PEOPLE---------*/

MERGE INTO M_BORDER.OSD_WORK_WITH_PEOPLE@ASOOSD A USING M_BORDER.OSD_WORK_WITH_PEOPLE B ON (A.WWP_ID = B.WWP_ID and A.DEPARTMENT_ID = B.DEPARTMENT_ID)
WHEN NOT MATCHED THEN 
INSERT (
  WWP_ID, DEPARTMENT_ID, WWP_DATE, WWP_Q, WWP_PLACE, 
  WWP_TEXT, SLD_ID, AT_ID, WWP_FORM, WWP_DATE_END, 
  WWP_KIND)
VALUES (
  B.WWP_ID, B.DEPARTMENT_ID, B.WWP_DATE, B.WWP_Q, B.WWP_PLACE, 
  B.WWP_TEXT, B.SLD_ID, B.AT_ID, B.WWP_FORM, B.WWP_DATE_END, 
  B.WWP_KIND)
WHEN MATCHED THEN
UPDATE SET 
  A.WWP_DATE = B.WWP_DATE,
  A.WWP_Q = B.WWP_Q,
  A.WWP_PLACE = B.WWP_PLACE,
  A.WWP_TEXT = B.WWP_TEXT,
  A.SLD_ID = B.SLD_ID,
  A.AT_ID = B.AT_ID,
  A.WWP_FORM = B.WWP_FORM,
  A.WWP_DATE_END = B.WWP_DATE_END,
  A.WWP_KIND = B.WWP_KIND;
  
  COMMIT;

/*-----------Z_PASS_PEOPLE------������� ��� � ��/��/���--------------*/
MERGE INTO M_BORDER.Z_PASS_PEOPLE@ASOOSD A USING M_BORDER.Z_PASS_PEOPLE B
ON (A.PRP_ID = B.PRP_ID and A.DEPARTMENT_ID = B.DEPARTMENT_ID and A.PP_TIME_IN = B.PP_TIME_IN)
WHEN NOT MATCHED THEN 
INSERT (
  DEPARTMENT_ID, PP_TIME_IN, PRP_ID, PP_PLACE, DEP_PP_ID, 
  PP_TS_NUMBER, PP_TS_KIND, PP_TS_MARKA, PP_TS_PASS, PP_TIME_OUT, 
  PP_COMMENT, PP_ADDING_DATE, PP_ADDING_USER)
VALUES (
  B.DEPARTMENT_ID, B.PP_TIME_IN, B.PRP_ID, B.PP_PLACE, B.DEP_PP_ID, 
  B.PP_TS_NUMBER, B.PP_TS_KIND, B.PP_TS_MARKA, B.PP_TS_PASS, B.PP_TIME_OUT, 
  B.PP_COMMENT, B.PP_ADDING_DATE, B.PP_ADDING_USER)
WHEN MATCHED THEN
UPDATE SET 
  A.PP_PLACE = B.PP_PLACE,
  A.DEP_PP_ID = B.DEP_PP_ID,
  A.PP_TS_NUMBER = B.PP_TS_NUMBER,
  A.PP_TS_KIND = B.PP_TS_KIND,
  A.PP_TS_MARKA = B.PP_TS_MARKA,
  A.PP_TS_PASS = B.PP_TS_PASS,
  A.PP_TIME_OUT = B.PP_TIME_OUT,
  A.PP_COMMENT = B.PP_COMMENT,
  A.PP_ADDING_DATE = B.PP_ADDING_DATE,
  A.PP_ADDING_USER = B.PP_ADDING_USER;

COMMIT;

/*--- ������� OSD_RANK_HISTORY --- ������� ������ ---*/
MERGE INTO M_BORDER.OSD_RANK_HISTORY@ASOOSD A USING M_BORDER.OSD_RANK_HISTORY B ON (A.RH_ID = B.RH_ID and A.SLD_ID = B.SLD_ID)
/*WHEN MATCHED THEN
  UPDATE SET 
  A.RH_RANK_ID = B.RH_RANK_ID,
  A.RH_DATE = B.RH_DATE,
  A.RH_ORDER = B.RH_ORDER,
  A.RH_ORGANIZATION = B.RH_ORGANIZATION,
  A.ADD_INFO = B.ADD_INFO,
  A.BY_STAFF = B.BY_STAFF
*/
WHEN NOT MATCHED THEN 
INSERT (
  RH_ID, RH_RANK_ID, RH_DATE, RH_ORDER, RH_ORGANIZATION, 
  ADD_INFO, SLD_ID, BY_STAFF)
VALUES (
  B.RH_ID, B.RH_RANK_ID, B.RH_DATE, B.RH_ORDER, B.RH_ORGANIZATION, 
  B.ADD_INFO, B.SLD_ID, B.BY_STAFF);

COMMIT;

/*--- ������� OSD_PERSON_ON_DUTY_INFO ---- ������� ������ ��������� �� ������� ---*/
MERGE INTO M_BORDER.OSD_PERSON_ON_DUTY_INFO@ASOOSD A USING M_BORDER.OSD_PERSON_ON_DUTY_INFO B
ON (A.DEPARTMENT_ID = B.DEPARTMENT_ID and A.PDI_TIME = B.PDI_TIME)
WHEN NOT MATCHED THEN 
INSERT (
  DEPARTMENT_ID, PDI_TIME, PDI_REPORT_TIME, PDI_SENDER, PDI_RECEIVER, 
  PDI_TEXT, PDI_SYSTIME, PDI_DECISION, PDI_ADDING_DATE, PDI_ADDING_USER)
VALUES (
  B.DEPARTMENT_ID, B.PDI_TIME, B.PDI_REPORT_TIME, B.PDI_SENDER, B.PDI_RECEIVER, 
  B.PDI_TEXT, B.PDI_SYSTIME, B.PDI_DECISION, B.PDI_ADDING_DATE, B.PDI_ADDING_USER)
WHEN MATCHED THEN
UPDATE SET 
  A.PDI_REPORT_TIME = B.PDI_REPORT_TIME,
  A.PDI_SENDER = B.PDI_SENDER,
  A.PDI_RECEIVER = B.PDI_RECEIVER,
  A.PDI_TEXT = B.PDI_TEXT,
  A.PDI_SYSTIME = B.PDI_SYSTIME,
  A.PDI_DECISION = B.PDI_DECISION,
  A.PDI_ADDING_DATE = B.PDI_ADDING_DATE,
  A.PDI_ADDING_USER = B.PDI_ADDING_USER;
  
COMMIT;