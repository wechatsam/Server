package im.servers.center.caches;

import com.alibaba.fastjson.JSON;
import im.redisson.caches.JTICacheMap;
import im.redisson.caches.JTLockerCacheMap;
import im.redisson.caches.JTReadWriteLockerCacheMap;
import im.servers.center.dao.entities.Account;
import im.servers.center.dao.entities.FriendItem;
import im.servers.center.dao.entities.GroupItem;
import im.servers.center.dao.entities.User;
import im.servers.center.dao.mappers.UpdateToRedisMapper;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class JTUpdateCaches 
{

    @Autowired
    public UpdateToRedisMapper updateToRedisMapper;

    public static JTLockerCacheMap cacheReceiveAddFriends = null;

    public static JTLockerCacheMap cacheFriends = null;
    public static JTReadWriteLockerCacheMap cacheUsers = null;
    public static JTLockerCacheMap cacheGroups = null;
    public static JTReadWriteLockerCacheMap cacheAccounts = null;
    public static JTLockerCacheMap cacheUserOnline = null;

    @PostConstruct
    public void initalize()
    {
        cacheReceiveAddFriends = JTLockerCacheMap.instance("receive_add_friend_cache");
        cacheFriends = JTLockerCacheMap.instance("friends_cache");
        cacheUsers = JTReadWriteLockerCacheMap.instance("users_cache");
        cacheGroups = JTLockerCacheMap.instance("groups_caches");
        cacheAccounts  = JTReadWriteLockerCacheMap.instance("account_caches");
        cacheUserOnline  = JTLockerCacheMap.instance("user_online_caches");


//        UpdateToRedisMapper updateToRedisMapper = JTSpringUtils.getBean(UpdateToRedisMapper.class);
        updateUsers(updateToRedisMapper, cacheUsers.getMap());
        updateAccounts(updateToRedisMapper, cacheAccounts.getMap());
        updateGroups(updateToRedisMapper, cacheGroups.getMap());
        updateFriends(updateToRedisMapper, cacheFriends.getMap());
        updateAddFriends(updateToRedisMapper, cacheReceiveAddFriends.getMap());

    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getAccount(String account)
    {
        return (Map<String, Object>) cacheAccounts.getObject(account);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getUser(String uid)
    {
        return (Map<String, Object>) cacheUsers.getObject(uid);
    }

    @SuppressWarnings("unchecked")
    public List<Object> getFriends(String uid)
    {
        return (List<Object>) cacheFriends.getObject(uid);
    }

    private void updateAddFriends(UpdateToRedisMapper update, RMap cacheMap)
    {

    }

    private void updateFriends(UpdateToRedisMapper update, RMap cacheMap)
    {
        cacheMap.clear();
        List<FriendItem> friends = update.getFriends();
        Map<String, List<FriendItem>> friendMap = new HashMap<>();
        for (int i = 0; i < friends.size(); i++)
        {
            FriendItem item = friends.get(i);
            String id = item.uid;
            List<FriendItem> list = friendMap.get(id);
            if (list == null)
            {
                list = new ArrayList<>();
                friendMap.put(id, list);
            }
            list.add(item);
        }
        for (Map.Entry<String, List<FriendItem>> entry : friendMap.entrySet())
        {
            cacheMap.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
        }
    }

    private void updateGroups(UpdateToRedisMapper update, RMap cacheMap)
    {
        cacheMap.clear();
        List<GroupItem> groups = update.getGroups();
        Map<String, List<GroupItem>> _dataMap = new HashMap<>();
        for (int i = 0; i < groups.size(); i++)
        {
            GroupItem item = groups.get(i);
            String id = item.uid;
            List<GroupItem> list = _dataMap.get(id);
            if (list == null)
            {
                list = new ArrayList<>();
                _dataMap.put(id, list);
            }
            list.add(item);
        }
        for (Map.Entry<String, List<GroupItem>> entry : _dataMap.entrySet())
        {
            cacheMap.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
        }

    }

    private void updateUsers(UpdateToRedisMapper update, RMap cacheMap)
    {
        cacheMap.clear();
        List<User> usersInfo = update.getUsers();
        for (int i = 0; i < usersInfo.size(); i++)
        {
            User user = usersInfo.get(i);
            cacheMap.put(user.uid, JSON.toJSONString(user));
        }
    }

    private void updateAccounts(UpdateToRedisMapper update, RMap cacheMap)
    {
        cacheMap.clear();
        List<Account> accountsInfo = update.getAccounts();
        for (int i = 0; i < accountsInfo.size(); i++)
        {
            Account accountInfo = accountsInfo.get(i);
            cacheMap.put(accountInfo.account, JSON.toJSONString(accountInfo));
        }
    }
}
