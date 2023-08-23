CREATE OR REPLACE PROCEDURE grep (
    username IN VARCHAR2,
    pattern IN VARCHAR2
) IS
    m DBMS_SQL.VARCHAR2_TABLE;
BEGIN
    FOR t IN (SELECT table_name, column_name FROM all_tab_cols WHERE owner = username AND data_type = 'VARCHAR2') LOOP
        EXECUTE IMMEDIATE 'SELECT "' || t.column_name || '" FROM "' || t.table_name || '" WHERE "' || t.column_name || '" LIKE :1' BULK COLLECT INTO m USING pattern;
        FOR i IN 1 .. matches.COUNT LOOP
            DBMS_OUTPUT.PUT_LINE('"' || pattern || '","' || username || '","' || t.table_name || '","' || t.column_name || '","' || matches(i) || '"');
        END LOOP;
    END LOOP;
END grep;
