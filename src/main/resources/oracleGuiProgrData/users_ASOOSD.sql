MERGE INTO M_BORDER.A_USER a1 USING (SELECT * FROM M_BORDER.A_USER@ASOOSD where department_id = (select min (department_id) from m_border.a_user)) a2 ON (a1.USER_ID=a2.USER_ID)
when matched then
    update SET
               a1.DEPARTMENT_ID = a2.DEPARTMENT_ID,
               a1.USER_SURNAME = a2.USER_SURNAME,
               a1.USER_FIRSTNAME = a2.USER_FIRSTNAME,
               a1.USER_LASTNAME = a2.USER_LASTNAME,
               a1.USER_PROPERTY = a2.USER_PROPERTY,
               a1.ORG_ID = a2.ORG_ID,
               a1.ORG_ID_SUB = a2.ORG_ID_SUB,
               a1.LEVEL_ORG = a2.LEVEL_ORG,
               a1.TYPE_OPK = a2.TYPE_OPK,
               a1.DATE_PASSWORD = a2.DATE_PASSWORD,
               a1.FORCED_PASS_CHANGE = a2.FORCED_PASS_CHANGE,
               a1.DIRECT_USER = a2.DIRECT_USER,
               a1.USER_NAME = a2.USER_NAME
when not matched then
    insert (a1.USER_ID, a1.DEPARTMENT_ID, a1.USER_SURNAME, a1.USER_FIRSTNAME, a1.USER_LASTNAME, a1.USER_PASSWORD, a1.USER_PROPERTY, a1.PWD_HASH, a1.ORG_ID, a1.ORG_ID_SUB,
            a1.LEVEL_ORG, a1.TYPE_OPK, a1.DATE_PASSWORD, a1.FORCED_PASS_CHANGE, a1.DIRECT_USER, a1.USER_NAME)
    values
        (a2.USER_ID, a2.DEPARTMENT_ID, a2.USER_SURNAME, a2.USER_FIRSTNAME, a2.USER_LASTNAME, a2.USER_PASSWORD, a2.USER_PROPERTY, a2.PWD_HASH, a2.ORG_ID, a2.ORG_ID_SUB,
         a2.LEVEL_ORG, a2.TYPE_OPK, a2.DATE_PASSWORD, a2.FORCED_PASS_CHANGE, a2.DIRECT_USER, a2.USER_NAME);
commit;

delete from m_border.a_user_roles;
commit;

MERGE INTO M_BORDER.A_USER_ROLES t3 USING (SELECT * FROM M_BORDER.A_USER_ROLES@ASOOSD where USER_ID in ( select USER_ID FROM M_BORDER.A_USER@ASOOSD where department_id = (select min (department_id) from m_border.a_user))) t4 ON (t3.USER_ID=t4.USER_ID and t3.ROLE_ID = t4.ROLE_ID) WHEN NOT MATCHED THEN INSERT (t3.USER_ID, t3.ROLE_ID) values (t4.USER_ID, t4.ROLE_ID);
commit;


