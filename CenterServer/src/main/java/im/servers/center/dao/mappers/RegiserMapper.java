package im.servers.center.dao.mappers;

import im.servers.center.dao.entities.Account;

import java.util.Map;

//@Mapper
public interface RegiserMapper
{
	Account getAccount(String account);
	
	void addAccount(Map<String, Object> dataMap);
	
	void addUser(Map<String, Object> dataMap);
}
