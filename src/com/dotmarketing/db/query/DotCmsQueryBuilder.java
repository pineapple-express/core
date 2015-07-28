package com.dotmarketing.db.query;

import com.dotmarketing.exception.DotRuntimeException;
import com.dotmarketing.util.Logger;

public class DotCmsQueryBuilder {
    public final static String SQL_WORD_VALUES = " VALUES ";
    public final static String SQL_WORD_WHERE = " WHERE ";
    public final static String SQL_WORD_AND = " AND ";
    public final static String SQL_WORD_OR = " OR ";

    public static enum QueryType {
        INSERT("INSERT INTO $table "), UPDATE("UPDATE $table SET "), DELETE("DELETE FROM $table ");

        private final String prefixQuery;

        QueryType(final String prefixQuery) {
            this.prefixQuery = prefixQuery;
        }

        public String getInitQuery(DotCmsDb.Table table) {
            return prefixQuery.replace("$table", table.getTableName());
        }
    }

    public static enum InsertValueType {
        VALUES, INTO_SELECT, PARAMETERIZED, DUMMY
    }

    public InsertStatement insert() {
        return new InsertStatement();
    }

    public DeleteStatement delete() {
        return new DeleteStatement();
    }

    public class InsertStatement {
        private DotCmsQuery query;

        /**
         * Method that creates the INSERT syntax using the table object
         * 
         * @param table
         *            name of the table to generate the insert query
         * @return InsertValues object
         */
        public InsertValues from(DotCmsDb.Table table) {
            query = new DotCmsQuery(QueryType.INSERT, table);

            // Columns
            query.append("(").append(String.join(", ", table.getColumnNames())).append(")");

            return new InsertValues(table);
        }

        public class InsertValues {
            private final DotCmsDb.Table table;

            InsertValues(DotCmsDb.Table table) {
                this.table = table;
            }

            /**
             * Return query using "SQL INSERT INTO" statement using dummy values
             * 
             * @return insert statement with dummy values
             */
            public DotCmsQuery values(InsertValueType insertType) {
                if (insertType == InsertValueType.DUMMY) {
                    return valuesDummy();
                }

                if (insertType == InsertValueType.PARAMETERIZED) {
                    return valuesParameterized();
                }

                // Throw error if insertType not supported for this method
                final String errorMsg = "Error insert type is not supported.";
                Logger.error(DotCmsQueryBuilder.class, errorMsg);
                throw new DotRuntimeException(errorMsg);
            }

            /**
             * Return query using "SQL INSERT INTO" statement using dummy values
             * 
             * @return insert statement with dummy values
             */
            public DotCmsQuery values(InsertValueType insertType, String... values) {
                if (insertType == InsertValueType.INTO_SELECT) {
                    return valuesSelect(values[0]);
                }

                if (insertType == InsertValueType.VALUES) {
                    return values(values);
                }

                // Throw error if insertType not supported for this method
                final String errorMsg = "Error insert type is not supported.";
                Logger.error(DotCmsQueryBuilder.class, errorMsg);
                throw new DotRuntimeException(errorMsg);
            }

            /**
             * Return query using "SQL INSERT INTO" statement using dummy values
             */
            private DotCmsQuery valuesDummy() {
                query.append(SQL_WORD_VALUES + "(")
                        .append(String.join(", ", table.getDummyValues())).append(")");
                return query;
            }

            /**
             * Return query using "INSERT INTO SELECT" statement
             * <p>
             * Example: INSERT INTO table2 (column_name(s)) SELECT
             * column_name(s) FROM table1;
             * </p>
             * 
             * @param selectQuery
             *            query the gather values to be inserted
             */
            private DotCmsQuery valuesSelect(String selectQuery) {
                query.append(selectQuery);
                return query;
            }

            /**
             * Return query using "SQL INSERT INTO" statement
             * <p>
             * Example: INSERT INTO table_name (column1,column2,column3,...)
             * VALUES (value1,value2,value3,...);
             * </p>
             * 
             * @param values
             *            column value for the query
             */
            private DotCmsQuery values(String... values) {
                query.append(SQL_WORD_VALUES + "(").append(String.join(", ", values)).append(")");
                return query;
            }

            /**
             * Return query using "SQL INSERT INTO" statement with parameterized
             * values
             * <p>
             * Example: INSERT INTO table_name (column1,column2,column3,...)
             * VALUES (?,?,?,...);
             * </p>
             * 
             */
            private DotCmsQuery valuesParameterized() {
                query.append(SQL_WORD_VALUES + "(")
                        .append(String.join(", ", table.getParameterizedValues())).append(")");
                return query;
            }
        }
    }

    public class DeleteStatement {
        private DotCmsQuery query;

        /**
         * Method that creates the DELETE syntax using the table object
         * 
         * @param table
         *            name of the table to generate the insert query
         * @return InsertValues object
         */
        public DeleteWhere from(DotCmsDb.Table table) {
            query = new DotCmsQuery(QueryType.DELETE, table);

            return new DeleteWhere();
        }

        public class DeleteWhere {
            public DotCmsQuery where(DotCmsCondition condition) {
                query.append(SQL_WORD_WHERE).append(condition.getConditionString());
                return query;
            }
        }
    }

    public static class DotCmsCondition {
        private StringBuilder condition = new StringBuilder();

        public DotCmsCondition and() {
            condition.append(SQL_WORD_AND);
            return this;
        }

        public DotCmsCondition or() {
            condition.append(SQL_WORD_OR);
            return this;
        }

        public DotCmsCondition eq(DotCmsDb.DotCmsColumn column, String value) {
            condition.append(column.getColumnName()).append(" = ").append(value);
            return this;
        }

        public String getConditionString() {
            return condition.toString();
        }
    }

    public class DotCmsQuery {
        private StringBuilder query;

        public DotCmsQuery(QueryType queryType, DotCmsDb.Table table) {
            query = new StringBuilder(queryType.getInitQuery(table));
        }

        public DotCmsQuery append(String param) {
            query.append(param);
            return this;
        }

        public String queryString() {
            return query.toString();
        }
    }
}
