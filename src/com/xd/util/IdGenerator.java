package com.xd.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class IdGenerator {
	
	/**
	 * 生成id，用户呼叫id
	 * @param sender
	 * @return
	 */
	public static String getId(String sender){
		long time = System.currentTimeMillis();
		StringBuffer id = new StringBuffer();
	    id.append(sender)
	        .append(time);
	    return id.toString();
	}
	
	 /**
     * 生成新的Uuid,去掉了破折号
     * @return
     */
    public final static String nextUuid(){
        return StringUtils.remove(UUID.randomUUID().toString(), '-');
    }
    
    
    public static void main(String[] args) {
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
    	System.out.println(nextUuid());
	}

}
