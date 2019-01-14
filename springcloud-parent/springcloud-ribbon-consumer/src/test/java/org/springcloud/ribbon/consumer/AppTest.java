package org.springcloud.ribbon.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springcloud.ribbon.consumer.dto.User;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public static void main(String[] args) {
    	String userIds = "1,2,";
    	String[] userIdArray = userIds.split(",");
		List<User> list = new ArrayList<>();
		for(String str : userIdArray) {
			User user = new User();
			user.setId(Long.valueOf(str));
			user.setUserName("kaiyun");
			list.add(user);
		}
		int count = 0;
		System.out.println(list.get(count++));
	}
}
