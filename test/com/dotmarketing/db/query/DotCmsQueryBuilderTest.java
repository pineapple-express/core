package com.dotmarketing.db.query;

import java.sql.SQLException;
import java.util.Map;

import com.dotcms.repackage.junit.framework.Assert;
import com.dotcms.repackage.org.junit.BeforeClass;
import com.dotcms.repackage.org.junit.Test;
import com.dotmarketing.beans.Host;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.cache.StructureCache;
import com.dotmarketing.common.db.DotConnect;
import com.dotmarketing.db.query.DotCmsDb.Folder;
import com.dotmarketing.db.query.DotCmsDb.Identifier;
import com.dotmarketing.db.query.DotCmsDb.Inode;
import com.dotmarketing.db.query.DotCmsDb.Table;
import com.dotmarketing.db.query.DotCmsQueryBuilder.DotCmsCondition;
import com.dotmarketing.db.query.DotCmsQueryBuilder.InsertValueType;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.portlets.structure.model.Structure;
import com.dotmarketing.util.Logger;
import com.liferay.portal.model.User;

public class DotCmsQueryBuilderTest {
    static User user;
    static Host host;
    static Structure fileAssetSt;

    @BeforeClass
    public static void init() throws Exception {
        user = APILocator.getUserAPI().getSystemUser();
        host = APILocator.getHostAPI().findDefaultHost(user, false);
        fileAssetSt = StructureCache.getStructureByVelocityVarName("FileAsset");
    }

    @Test
    public void inodeTableQueryTest() {
        DotCmsQueryBuilder qb = new DotCmsQueryBuilder();

        // Expected queries
        final String insertQuery = "INSERT INTO inode (inode, owner, idate, type)";
        final String insertParameterized = insertQuery + " VALUES (?, ?, ?, ?)";
        final String insertValues = insertQuery
                + " VALUES ('00746d68-c2d2-4d2e-9b02-e78a5f29c8a9', 'dotcms.org.1', '2012-03-06 17:03:25.246', 'contentlet')";
        final String deleteFolderByInode = "DELETE FROM inode WHERE inode = '00746d68-c2d2-4d2e-9b02-e78a5f29c8a9'";

        // Using query builder
        final String insertParameterizedUsingQueryBuilder = qb.insert().from(Table.INODE)
                .values(InsertValueType.PARAMETERIZED).queryString();
        final String insertValuesUsingQueryBuilder = qb
                .insert()
                .from(Table.INODE)
                .values(InsertValueType.VALUES, "'00746d68-c2d2-4d2e-9b02-e78a5f29c8a9'",
                        "'dotcms.org.1'", "'2012-03-06 17:03:25.246'", "'contentlet'")
                .queryString();
        final String deleteFolderByInodeUsingQueryBuilder = qb
                .delete()
                .from(Table.INODE)
                .where(new DotCmsCondition().eq(Folder.Columns.INODE,
                        "'00746d68-c2d2-4d2e-9b02-e78a5f29c8a9'")).queryString();

        // Asserts using strings
        Assert.assertEquals(insertParameterized.replaceAll("\\s+", " "),
                insertParameterizedUsingQueryBuilder.replaceAll("\\s+", " "));
        Assert.assertEquals(insertValues.replaceAll("\\s+", " "),
                insertValuesUsingQueryBuilder.replaceAll("\\s+", " "));
        Assert.assertEquals(deleteFolderByInode.replaceAll("\\s+", " "),
                deleteFolderByInodeUsingQueryBuilder.replaceAll("\\s+", " "));
    }

    // @Test
    // public void identifierTableQueryTest() {
    // DotCmsQueryBuilder qb = new DotCmsQueryBuilder();
    //
    // // Expected queries
    // final String insertQuery = "INSERT INTO folder ("
    // +
    // "inode, name, title, show_on_menu, sort_order, files_masks, identifier, "
    // + "default_file_type, mod_date)";
    // final String insertParameterized = insertQuery +
    // " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    // final String insertValues = insertQuery
    // +
    // " VALUES ('0120e585-17b9-4fd7-b47b-fdca08ab3493', 'home', 'home', FALSE, 0, '', "
    // +
    // "'02e4730e-2e7d-47d5-8d3b-7c302c21d7b3', '33888b6f-7a8e-4069-b1b6-5c1aa9d0a48d', '2014-08-13 09:35:42.826')";
    // final String deleteFolderByInode =
    // "DELETE FROM folder WHERE inode = '0120e585-17b9-4fd7-b47b-fdca08ab3493'";
    // }

    @Test
    public void folderTableQueryTest() {
        DotCmsQueryBuilder qb = new DotCmsQueryBuilder();

        // Expected queries
        final String insertQuery = "INSERT INTO folder ("
                + "inode, name, title, show_on_menu, sort_order, files_masks, identifier, "
                + "default_file_type, mod_date)";
        final String insertParameterized = insertQuery + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        final String insertValues = insertQuery
                + " VALUES ('0120e585-17b9-4fd7-b47b-fdca08ab3493', 'home', 'home', FALSE, 0, '', "
                + "'02e4730e-2e7d-47d5-8d3b-7c302c21d7b3', '33888b6f-7a8e-4069-b1b6-5c1aa9d0a48d', '2014-08-13 09:35:42.826')";
        final String deleteFolderByInode = "DELETE FROM folder WHERE inode = '0120e585-17b9-4fd7-b47b-fdca08ab3493'";

        // Using query builder
        final String insertParameterizedUsingQueryBuilder = qb.insert().from(Table.FOLDER)
                .values(InsertValueType.PARAMETERIZED).queryString();
        final String insertValuesUsingQueryBuilder = qb
                .insert()
                .from(Table.FOLDER)
                .values(InsertValueType.VALUES, "'0120e585-17b9-4fd7-b47b-fdca08ab3493'", "'home'",
                        "'home'", "FALSE", "0", "''", "'02e4730e-2e7d-47d5-8d3b-7c302c21d7b3'",
                        "'33888b6f-7a8e-4069-b1b6-5c1aa9d0a48d'", "'2014-08-13 09:35:42.826'")
                .queryString();
        final String deleteFolderByInodeUsingQueryBuilder = qb
                .delete()
                .from(Table.FOLDER)
                .where(new DotCmsCondition().eq(Folder.Columns.INODE,
                        "'0120e585-17b9-4fd7-b47b-fdca08ab3493'")).queryString();

        // Asserts using strings
        Assert.assertEquals(insertParameterized.replaceAll("\\s+", " "),
                insertParameterizedUsingQueryBuilder.replaceAll("\\s+", " "));
        Assert.assertEquals(insertValues.replaceAll("\\s+", " "),
                insertValuesUsingQueryBuilder.replaceAll("\\s+", " "));
        Assert.assertEquals(deleteFolderByInode.replaceAll("\\s+", " "),
                deleteFolderByInodeUsingQueryBuilder.replaceAll("\\s+", " "));
    }

    @Test
    public void dummyInserts() {
        DotCmsQueryBuilder qb = new DotCmsQueryBuilder();

        // Assert using database
        DotConnect dc = new DotConnect();
        try {
            dc.

            dc.setSQL(qb.insert().from(Table.FOLDER).values(InsertValueType.DUMMY).queryString());
            dc.addParam(fileAssetSt.getInode());
            dc.loadResult();

            dc.setSQL("select * from folder where inode = 'TEMP_INODE'");
            Map<String, Object> dummyFolderRow = dc.loadObjectResults().get(0);
            Assert.assertTrue(dummyFolderRow.size() > 0);

            dc.setSQL("select * from folder where inode = 'TEMP_INODE'");
            dummyFolderRow = dc.loadObjectResults().get(0);
            Assert.assertTrue(dummyFolderRow.size() < 1);
        } catch (SQLException | DotDataException e) {
            Logger.error(this, "Fail ", e);
            Assert.fail(e.getMessage());
        } finally {
            dc.executeStatement(qb.delete().from(Table.INODE)
                    .where(new DotCmsCondition().eq(Inode.Columns.INODE, "'TEMP_INODE'"))
                    .queryString());
            
            dc.executeStatement(qb.delete().from(Table.IDENTIFIER)
                    .where(new DotCmsCondition().eq(Identifier.Columns.ID, "'TEMP_INODE'"))
                    .queryString());

            dc.executeStatement(qb.delete().from(Table.FOLDER)
                    .where(new DotCmsCondition().eq(Folder.Columns.INODE, "'TEMP_INODE'"))
                    .queryString());
        }
    }
}
