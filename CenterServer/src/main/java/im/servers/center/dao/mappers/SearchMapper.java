package im.servers.center.dao.mappers;

import im.servers.center.dao.entities.Account;
import im.servers.center.dao.entities.GroupItem;
import im.servers.center.dao.entities.User;

//@Mapper
public interface SearchMapper
{
	Account searchAccount(String account);
	
	User searchUser(String uid);
	
	GroupItem searchGroup(String gid);
 
}
