package com.dotmarketing.portlets.contentlet.business;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.dotcms.LicenseTestUtil;
import com.dotcms.repackage.org.junit.*;

import org.quartz.SimpleTrigger;

import com.dotcms.enterprise.HostAssetsJobProxy;
import com.dotmarketing.beans.Host;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.db.HibernateUtil;
import com.dotmarketing.quartz.QuartzUtils;
import com.dotmarketing.quartz.SimpleScheduledTask;
import com.dotmarketing.quartz.job.HostCopyOptions;
import com.dotmarketing.util.Logger;
import com.liferay.portal.model.User;

public class HostAPITest {
	
	@Before
    public void prepare() throws Exception {
        LicenseTestUtil.getLicense();
    }
    
    @Test
    public void makeDefault() throws Exception {
    	User user=APILocator.getUserAPI().getSystemUser();
    	/*
    	 * Get the current Default host
    	 */
    	Host hdef = APILocator.getHostAPI().findDefaultHost(user, false);
    	
    	/*
    	 * Create a new Host and make it default
    	 */
    	Host host=new Host();
        host.setHostname("test"+System.currentTimeMillis()+".demo.dotcms.com");
        host.setDefault(false);
        try{
        	HibernateUtil.startTransaction();
        	host=APILocator.getHostAPI().save(host, user, false);
        	HibernateUtil.commitTransaction();
        }catch(Exception e){
        	HibernateUtil.rollbackTransaction();
        	Logger.error(HostAPITest.class, e.getMessage());
        }
        APILocator.getHostAPI().publish(host, user, false);
        APILocator.getHostAPI().makeDefault(host, user, false);
        
        host = APILocator.getHostAPI().find(host.getIdentifier(), user, false);
        APILocator.getContentletAPI().isInodeIndexed(host.getInode());
        APILocator.getContentletAPI().isInodeIndexed(host.getInode(),true);
        hdef = APILocator.getHostAPI().find(hdef.getIdentifier(), user, false);
        APILocator.getContentletAPI().isInodeIndexed(hdef.getInode());
        APILocator.getContentletAPI().isInodeIndexed(hdef.getInode(),true);
        
        /*
         * Validate if the previous default host. Is live and not default
         */
        Assert.assertTrue(hdef.isLive());
        Assert.assertFalse(hdef.isDefault());
        
        /*
         * get Back to default the previous host
         */
        APILocator.getHostAPI().makeDefault(hdef, user, false);
        
        host = APILocator.getHostAPI().find(host.getIdentifier(), user, false);
        APILocator.getContentletAPI().isInodeIndexed(host.getInode());
        APILocator.getContentletAPI().isInodeIndexed(host.getInode(),true);
        hdef = APILocator.getHostAPI().find(hdef.getIdentifier(), user, false);
        APILocator.getContentletAPI().isInodeIndexed(hdef.getInode());
        APILocator.getContentletAPI().isInodeIndexed(hdef.getInode(),true);
        
        /*
         * Validate if the new host is not default anymore and if its live
         */
        Assert.assertFalse(host.isDefault());
        Assert.assertTrue(host.isLive());
        
        Assert.assertTrue(hdef.isLive());
        Assert.assertTrue(hdef.isDefault());
        
        /*
         * Delete the new test host
         */
        Thread.sleep(600); // wait a bit for the index
        try{
        	HibernateUtil.startTransaction();
        	APILocator.getHostAPI().archive(host, user, false);
        	APILocator.getHostAPI().delete(host, user, false);
        	HibernateUtil.commitTransaction();
        }catch(Exception e){
        	HibernateUtil.rollbackTransaction();
        	Logger.error(HostAPITest.class, e.getMessage());
        }
        Thread.sleep(600); // wait a bit for the index
        /*
         * Validate if the current Original default host is the current default one
         */
        host = APILocator.getHostAPI().findDefaultHost(user, false);
        Assert.assertEquals(hdef.getIdentifier(), host.getIdentifier());
    }
}
