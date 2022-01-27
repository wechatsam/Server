package im.servers.center.dao.mappers;


import im.servers.center.dao.entities.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@Mapper
public interface LoginMapper
{
	User login(String uid);
	
	List<FriendItem> getFriends(String uid);
	
	List<GroupItem> getGroups(String uid);
	
	List<DialogItem> getDialogs(String uid);
	
	List<Account> getUsers();
	
	void updateLoginTime(@Param("uid") String uid, @Param("loginTime") String loginTime);
}
