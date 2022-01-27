package im.servers.center.dao.mappers;

import java.util.List;

import im.servers.center.dao.entities.Account;
import im.servers.center.dao.entities.FriendItem;
import im.servers.center.dao.entities.GroupItem;
import im.servers.center.dao.entities.User;

public interface UpdateToRedisMapper
{
	List<GroupItem> getGroups();
	
	List<User> getUsers();
	
	List<Account> getAccounts();
	
	List<FriendItem> getFriends();
	
}
