package by.lida.pogran.dbui.constants;

public class SQLConstants {
    public static final String users_asoosd = "MERGE INTO M_BORDER.A_USER a1 USING (SELECT * FROM M_BORDER.A_USER@ASOOSD WHERE department_id = (SELECT MIN(department_id) FROM m_border.a_user)) a2 ON (a1.USER_ID = a2.USER_ID) WHEN MATCHED THEN UPDATE SET a1.DEPARTMENT_ID = a2.DEPARTMENT_ID, a1.USER_SURNAME = a2.USER_SURNAME, a1.USER_FIRSTNAME = a2.USER_FIRSTNAME, a1.USER_LASTNAME = a2.USER_LASTNAME, a1.USER_PROPERTY = a2.USER_PROPERTY, a1.ORG_ID = a2.ORG_ID, a1.ORG_ID_SUB = a2.ORG_ID_SUB, a1.LEVEL_ORG = a2.LEVEL_ORG, a1.TYPE_OPK = a2.TYPE_OPK, a1.DATE_PASSWORD = a2.DATE_PASSWORD, a1.FORCED_PASS_CHANGE = a2.FORCED_PASS_CHANGE, a1.DIRECT_USER = a2.DIRECT_USER, a1.USER_NAME = a2.USER_NAME WHEN NOT MATCHED THEN INSERT (a1.USER_ID, a1.DEPARTMENT_ID, a1.USER_SURNAME, a1.USER_FIRSTNAME, a1.USER_LASTNAME, a1.USER_PASSWORD, a1.USER_PROPERTY, a1.PWD_HASH, a1.ORG_ID, a1.ORG_ID_SUB, a1.LEVEL_ORG, a1.TYPE_OPK, a1.DATE_PASSWORD, a1.FORCED_PASS_CHANGE, a1.DIRECT_USER, a1.USER_NAME) VALUES (a2.USER_ID, a2.DEPARTMENT_ID, a2.USER_SURNAME, a2.USER_FIRSTNAME, a2.USER_LASTNAME, a2.USER_PASSWORD, a2.USER_PROPERTY, a2.PWD_HASH, a2.ORG_ID, a2.ORG_ID_SUB, a2.LEVEL_ORG, a2.TYPE_OPK, a2.DATE_PASSWORD, a2.FORCED_PASS_CHANGE, a2.DIRECT_USER, a2.USER_NAME); DELETE FROM m_border.a_user_roles; MERGE INTO M_BORDER.A_USER_ROLES t3 USING (SELECT * FROM M_BORDER.A_USER_ROLES@ASOOSD WHERE USER_ID IN (SELECT USER_ID FROM M_BORDER.A_USER@ASOOSD WHERE department_id = (SELECT MIN(department_id) FROM m_border.a_user))) t4 ON (t3.USER_ID = t4.USER_ID AND t3.ROLE_ID = t4.ROLE_ID) WHEN NOT MATCHED THEN INSERT (t3.USER_ID, t3.ROLE_ID) VALUES (t4.USER_ID, t4.ROLE_ID);";
}
