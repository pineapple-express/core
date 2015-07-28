package com.dotmarketing.db.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dotmarketing.db.DbConnectionFactory;

public abstract class DotCmsDb {
    public interface DotCmsTable {
        public List<String> getDummyValues();
    }

    public interface DotCmsColumn {
        public String getColumnName();
    }

    public enum Table implements DotCmsTable {
        FOLDER("folder", Folder.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);

                dummies.add("'TEMP_INODE'");
                dummies.add("'DUMMY_NAME'");
                dummies.add("'DUMMY_TITLE'");
                dummies.add(DbConnectionFactory.getDBFalse());
                dummies.add("'0'");
                dummies.add("''");
                dummies.add("'TEMP_IDENTIFIER'");
                dummies.add("?");

                if (DbConnectionFactory.isOracle()) {
                    dummies.add("to_date('1900-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS')");
                } else {
                    dummies.add("'1900-01-01 00:00:00.00'");
                }

                return dummies;
            }
        },
        FOLDER_IR("folder_ir", FolderIr.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);
                return dummies;
            }
        },
        STRUCTURE("structure", Structure.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);

                dummies.add("'TEMP_INODE'");
                dummies.add("'DUMMY_NAME'");
                dummies.add("'DUMMY_DESC'");
                dummies.add(DbConnectionFactory.getDBFalse());
                dummies.add("''");
                dummies.add("''");
                dummies.add("''");
                dummies.add("1");
                dummies.add(DbConnectionFactory.getDBTrue());
                dummies.add(DbConnectionFactory.getDBFalse());
                dummies.add("'DUMMY_VAR_NAME'");
                dummies.add("'DUMMY_PATERN'");
                dummies.add("?");
                dummies.add("?");
                dummies.add("'EXPIRE_DUMMY'");
                dummies.add("'PUBLISH_DUMMY'");

                if (DbConnectionFactory.isOracle()) {
                    dummies.add("to_date('1900-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS')");
                } else {
                    dummies.add("'1900-01-01 00:00:00.00'");
                }

                return dummies;
            }
        },
        STRUCTURES_IR("structures_ir", StructuresIr.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);
                return dummies;
            }
        },
        HTMLPAGE("htmlpage", HtmlPage.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);
                return dummies;
            }
        },
        HTMLPAGES_IR("htmlpages_ir", HtmlPagesIr.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);
                return dummies;
            }
        },
        WORKFLOW_SCHEME("workflow_scheme", WorkflowScheme.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);

                dummies.add("'TEMP_INODE'");
                dummies.add("'DUMMY_NAME'");
                dummies.add("'DUMMY_DESC'");
                dummies.add(DbConnectionFactory.getDBFalse());
                dummies.add(DbConnectionFactory.getDBFalse());
                dummies.add(DbConnectionFactory.getDBFalse());

                if (DbConnectionFactory.isOracle()) {
                    dummies.add("to_date('1900-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS')");
                } else {
                    dummies.add("'1900-01-01 00:00:00.00'");
                }

                return dummies;
            }
        },
        WORKFLOW_STEP("workflow_step", WorkflowStep.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);
                return dummies;
            }
        },
        IDENTIFIER("identifier", Identifier.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);
                return dummies;
            }
        },
        INODE("inode", Inode.Columns.values()) {
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);
                dummies.add("'TEMP_INODE'");
                dummies.add("'DUMMY_OWNER'");

                if (DbConnectionFactory.isOracle()) {
                    dummies.add("to_date('1900-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS')");
                } else {
                    dummies.add("'1900-01-01 00:00:00.00'");
                }

                dummies.add("'DUMMY_TYPE'");
                return dummies;
            }
        },
        CONTENTLET_VERSION_INFO("contentlet_version_info", ContentletVersionInfo.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);
                return dummies;
            }
        },
        CONTENTLET("contentlet", Contentlet.Columns.values()) {
            @Override
            public List<String> getDummyValues() {
                List<String> dummies = new ArrayList<String>(getColumns().length);
                return dummies;
            }
        };

        private final String tableName;
        private final DotCmsColumn[] columns;

        Table(String tableName, DotCmsColumn[] columns) {
            this.tableName = tableName;
            this.columns = columns;
        }

        public String getTableName() {
            return tableName;
        }

        public DotCmsColumn[] getColumns() {
            return columns;
        }

        public List<String> getColumnNames() {
            List<String> columnNames = new ArrayList<String>(columns.length);
            for (DotCmsColumn col : columns) {
                columnNames.add(col.getColumnName());
            }
            return columnNames;
        }

        public List<String> getParameterizedValues() {
            String[] parameterizedValues = new String[columns.length];
            Arrays.fill(parameterizedValues, "?");
            return Arrays.asList(parameterizedValues);
        }
    }

    /**
     * Table "folder" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class Folder implements DotCmsTable {
        public static enum Columns implements DotCmsColumn {
            INODE("inode"), NAME("name"), TITLE("title"), SHOW_ON_MENU("show_on_menu"),
            SORT_ORDER("sort_order"), FILES_MASKS("files_masks"), IDENTIFIER("identifier"),
            DEFAULT_FILE_TYPE("default_file_type"), MOD_DATE("mod_date");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "folder_ir" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class FolderIr implements DotCmsTable {
        public enum Columns implements DotCmsColumn {
            FOLDER("folder"), LOCAL_INODE("local_inode"), REMOTE_INODE("remote_inode"),
            LOCAL_IDENTIFIER("local_identifier"), REMOTE_IDENTIFIER("remote_identifier"),
            ENDPOINT_ID("endpoint_id");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "html_page" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class HtmlPage implements DotCmsTable {
        public enum Columns implements DotCmsColumn {
            INODE("inode"), SHOW_ON_MENU("show_on_menu"), TITLE("title"), MOD_DATE("mod_date"),
            MOD_USER("mod_user"), SORT_ORDER("sort_order"), FRIENDLY_NAME("friendly_name"),
            METADATA("metadata"), START_DATE("start_date"), END_DATE("end_date"),
            PAGE_URL("page_url"), HTTPS_REQUIRED("https_required"), REDIRECT("redirect"),
            IDENTIFIER("identifier"), SEO_DESCRIPTION("seo_description"),
            SEO_KEYWORDS("seo_keywords"), CACHE_TTL("cache_ttl"), TEMPLATE_ID("template_id");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "html_pages_ir" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class HtmlPagesIr implements DotCmsTable {
        public enum Columns implements DotCmsColumn {
            HTML_PAGE("html_page"), LOCAL_WORKING_INODE("local_working_inode"),
            LOCAL_LIVE_INODE("local_live_inode"), REMOTE_WORKING_INODE("remote_working_inode"),
            REMOTE_LIVE_INODE("remote_live_inode"), LOCAL_IDENTIFIER("local_identifier"),
            REMOTE_IDENTIFIER("remote_identifier"), ENDPOINT_ID("endpoint_id"),
            LANGUAGE_ID("language_id");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "workflow_scheme" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class WorkflowScheme implements DotCmsTable {
        public enum Columns implements DotCmsColumn {
            ID("id"), NAME("name"), DESCRIPTION("description"), ARCHIVED("archived"),
            MANDATORY("mandatory"), DEFAULT_SCHEME("default_scheme"),
            ENTRY_ACTION_ID("entry_action_id"), MOD_DATE("mod_date");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "workflow_step" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class WorkflowStep implements DotCmsTable {
        public enum Columns implements DotCmsColumn {
            ID("id"), NAME("name"), SCHEME_ID("scheme_id"), MY_ORDER("my_order"),
            RESOLVED("resolved"), ESCALATION_ENABLE("escalation_enable"),
            ESCALATION_ACTION("escalation_action"), ESCALATION_TIME("escalation_time");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "structure" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class Structure implements DotCmsTable {
        public enum Columns implements DotCmsColumn {
            INODE("inode"), NAME("name"), DESCRIPTION("description"),
            DEFAULT_STRUCTURE("default_structure"), REVIEW_INTERVAL("review_interval"),
            REVIEWER_ROLE("reviewer_role"), PAGE_DETAIL("page_detail"),
            STRUCTURETYPE("structuretype"), SYSTEM("system"), FIXED("fixed"),
            VELOCITY_VAR_NAME("velocity_var_name"), URL_MAP_PATTERN("url_map_pattern"),
            HOST("host"), FOLDER("folder"), EXPIRE_DATE_VAR("expire_date_var"),
            PUBLISH_DATE_VAR("publish_date_var"), MOD_DATE("mod_date");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "structures_ir" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class StructuresIr implements DotCmsTable {
        public enum Columns implements DotCmsColumn {
            VELOCITY_NAME("velocity_name"), LOCAL_INODE("local_inode"),
            REMOTE_INODE("remote_inode"), ENDPOINT_ID("endpoint_id");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "identifier" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class Identifier implements DotCmsTable {
        public enum Columns implements DotCmsColumn {
            ID("id"), PARENT_PATH("parent_path"), ASSET_NAME("asset_name"),
            HOST_INODE("host_inode"), ASSET_TYPE("asset_type"), SYSPUBLISH_DATE("syspublish_date"),
            SYSEXPIRE_DATE("sysexpire_date");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "inode" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class Inode implements DotCmsTable {
        public enum Columns implements DotCmsColumn {
            INODE("inode"), OWNER("owner"), IDATE("idate"), TYPE("type");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "contentlet_version_info" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class ContentletVersionInfo {
        public enum Columns implements DotCmsColumn {
            IDENTIFIER("identifier"), LANG("lang"), WORKING_INODE("working_inode"),
            LIVE_INODE("live_inode"), DELETED("deleted"), LOCKED_BY("locked_by"),
            LOCKED_ON("locked_on"), VERSION_TS("version_ts");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

    /**
     * Table "contentlet" definition
     * 
     * @author rogelioblanco
     *
     */
    public static abstract class Contentlet {
        public enum Columns implements DotCmsColumn {
            INODE("inode"), SHOW_ON_MENU("show_on_menu"), TITLE("title"), MOD_DATE("mod_date"),
            MOD_USER("mod_user"), SORT_ORDER("sort_order"), FRIENDLY_NAME("friendly_name"),
            STRUCTURE_INODE("structure_inode"), LAST_REVIEW("last_review"),
            NEXT_REVIEW("next_review"), REVIEW_INTERVAL("review_interval"),
            DISABLED_WYSIWYG("disabled_wysiwyg"), IDENTIFIER("identifier"),
            LANGUAGE_ID("language_id"), DATE1("date1"), DATE2("date2"), DATE3("date3"),
            DATE4("date4"), DATE5("date5"), DATE6("date6"), DATE7("date7"), DATE8("date8"),
            DATE9("date9"), DATE10("date10"), DATE11("date11"), DATE12("date12"), DATE13("date13"),
            DATE14("date14"), DATE15("date15"), DATE16("date16"), DATE17("date17"),
            DATE18("date18"), DATE19("date19"), DATE20("date20"), DATE21("date21"),
            DATE22("date22"), DATE23("date23"), DATE24("date24"), DATE25("date25"), TEXT1("text1"),
            TEXT2("text2"), TEXT3("text3"), TEXT4("text4"), TEXT5("text5"), TEXT6("text6"),
            TEXT7("text7"), TEXT8("text8"), TEXT9("text9"), TEXT10("text10"), TEXT11("text11"),
            TEXT12("text12"), TEXT13("text13"), TEXT14("text14"), TEXT15("text15"),
            TEXT16("text16"), TEXT17("text17"), TEXT18("text18"), TEXT19("text19"),
            TEXT20("text20"), TEXT21("text21"), TEXT22("text22"), TEXT23("text23"),
            TEXT24("text24"), TEXT25("text25"), TEXT_AREA1("text_area1"), TEXT_AREA2("text_area2"),
            TEXT_AREA3("text_area3"), TEXT_AREA4("text_area4"), TEXT_AREA5("text_area5"),
            TEXT_AREA6("text_area6"), TEXT_AREA7("text_area7"), TEXT_AREA8("text_area8"),
            TEXT_AREA9("text_area9"), TEXT_AREA10("text_area10"), TEXT_AREA11("text_area11"),
            TEXT_AREA12("text_area12"), TEXT_AREA13("text_area13"), TEXT_AREA14("text_area14"),
            TEXT_AREA15("text_area15"), TEXT_AREA16("text_area16"), TEXT_AREA17("text_area17"),
            TEXT_AREA18("text_area18"), TEXT_AREA19("text_area19"), TEXT_AREA20("text_area20"),
            TEXT_AREA21("text_area21"), TEXT_AREA22("text_area22"), TEXT_AREA23("text_area23"),
            TEXT_AREA24("text_area24"), TEXT_AREA25("text_area25"), INTEGER1("integer1"),
            INTEGER2("integer2"), INTEGER3("integer3"), INTEGER4("integer4"), INTEGER5("integer5"),
            INTEGER6("integer6"), INTEGER7("integer7"), INTEGER8("integer8"), INTEGER9("integer9"),
            INTEGER10("integer10"), INTEGER11("integer11"), INTEGER12("integer12"),
            INTEGER13("integer13"), INTEGER14("integer14"), INTEGER15("integer15"),
            INTEGER16("integer16"), INTEGER17("integer17"), INTEGER18("integer18"),
            INTEGER19("integer19"), INTEGER20("integer20"), INTEGER21("integer21"),
            INTEGER22("integer22"), INTEGER23("integer23"), INTEGER24("integer24"),
            INTEGER25("integer25"), FLOAT1("float1"), FLOAT2("float2"), FLOAT3("float3"),
            FLOAT4("float4"), FLOAT5("float5"), FLOAT6("float6"), FLOAT7("float7"),
            FLOAT8("float8"), FLOAT9("float9"), FLOAT10("float10"), FLOAT11("float11"),
            FLOAT12("float12"), FLOAT13("float13"), FLOAT14("float14"), FLOAT15("float15"),
            FLOAT16("float16"), FLOAT17("float17"), FLOAT18("float18"), FLOAT19("float19"),
            FLOAT20("float20"), FLOAT21("float21"), FLOAT22("float22"), FLOAT23("float23"),
            FLOAT24("float24"), FLOAT25("float25"), BOOL1("bool1"), BOOL2("bool2"), BOOL3("bool3"),
            BOOL4("bool4"), BOOL5("bool5"), BOOL6("bool6"), BOOL7("bool7"), BOOL8("bool8"),
            BOOL9("bool9"), BOOL10("bool10"), BOOL11("bool11"), BOOL12("bool12"), BOOL13("bool13"),
            BOOL14("bool14"), BOOL15("bool15"), BOOL16("bool16"), BOOL17("bool17"),
            BOOL18("bool18"), BOOL19("bool19"), BOOL20("bool20"), BOOL21("bool21"),
            BOOL22("bool22"), BOOL23("bool23"), BOOL24("bool24"), BOOL25("bool25");

            private final String columnName;

            Columns(String columnName) {
                this.columnName = columnName;
            }

            @Override
            public String getColumnName() {
                return columnName;
            }
        }
    }

}
