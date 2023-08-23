# DBGrep

Oracle PL/SQL stored procedure to search a regular expression across all text
columns of a database.

To import the `grep()` procedure in your current database, download the
file [grep.sql](grep.sql) and then run the following command in SQLPlus:

```text
SQL> @grep.sql
```

The `grep()` procedure takes two arguments: the name of the schema to
search and a pattern compatible with the SQL operator LIKE. For
example:

```text
SQL> grep('HR', '%John%');
```

The output of the procedure is in CSV format (without headers). Each
row is a match found in the database. For each match, the columns are
the searched pattern, the schema name, the table name, the column name
and the column value. For example, the output might look like this:

```text
"%John%","HR","employees","first_name","John"
"%John%","HR","employees","full_name","John Doe"
"%John%","HR","employees","email_address","john.doe@example.com"
```
