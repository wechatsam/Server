package im.servers.center.dao.mappers;

import java.util.Map;

//@Mapper
//@Qualifier("friendMapper")
public interface FriendMapper
{
	void addFriend(Object data);
	
	void deleteFriend(Map<String, Object> data);
}
