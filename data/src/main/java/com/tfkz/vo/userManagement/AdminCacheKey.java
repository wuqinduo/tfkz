package com.tfkz.vo.userManagement;
/**
 * 
 * @author Administrator
 *
 *	该类是封装了多个查询条件的一个VO类。
 *	(PO只能用在数据层，VO用在商业逻辑层和表示层。各层操作属于该层自己的数据对象，这样就可以降低各层之间的耦合，便于以后系统的维护和扩展。)
 *
 */
public class AdminCacheKey {
	 	private String username;
	    private String password;
	    private int start;
	    private int limit;
	    
	    public AdminCacheKey() {
	    }

	    public AdminCacheKey(String username, String password, int start, int limit) {
	        this.username = username;
	        this.password = password;
	        this.start = start;
	        this.limit = limit;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public int getStart() {
	        return start;
	    }

	    public void setStart(int start) {
	        this.start = start;
	    }

	    public int getLimit() {
	        return limit;
	    }

	    public void setLimit(int limit) {
	        this.limit = limit;
	    }
	    
	    @Override
	    public int hashCode(){
	    	 final int prime = 31;
	         int result = 1;
	         result = prime * result + limit;
	         result = prime * result
	                 + ((password == null) ? 0 : password.hashCode());
	         result = prime * result + start;
	         result = prime * result
	                 + ((username == null) ? 0 : username.hashCode());
	         return result;
	    }
	    
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj)
	            return true;
	        if (obj == null)
	            return false;
	        if (getClass() != obj.getClass())
	            return false;
	        AdminCacheKey other = (AdminCacheKey) obj;
	        if (limit != other.limit)
	            return false;
	        if (password == null) {
	            if (other.password != null)
	                return false;
	        } else if (!password.equals(other.password))
	            return false;
	        if (start != other.start)
	            return false;
	        if (username == null) {
	            if (other.username != null)
	                return false;
	        } else if (!username.equals(other.username))
	            return false;
	        return true;
	    }

}
