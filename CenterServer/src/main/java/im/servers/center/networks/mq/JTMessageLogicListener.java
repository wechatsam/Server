package im.servers.center.networks.mq;


import im.com.events.JTFunctionManager;
import im.com.interfaces.JTIEventListener;
import im.mq.JTTopicType;
import im.redisson.caches.JTReadWriteLockerCacheMap;
import im.servers.center.dao.mappers.RegiserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
@Qualifier
public class JTMessageLogicListener
{
    protected Logger logger = LoggerFactory.getLogger(JTMessageLogicListener.class);

    @Autowired
    public RegiserMapper registerMapper;

    public JTReadWriteLockerCacheMap cacheUsers;

    public JTReadWriteLockerCacheMap cacheAccounts;

    @PostConstruct

    public void registers()
    {
        cacheUsers = JTReadWriteLockerCacheMap.instance("users_cache");
        cacheAccounts = JTReadWriteLockerCacheMap.instance("account_caches");
        JTFunctionManager.registerFunction(JTTopicType.TOPIC_REGISTER, this.registerAccountListener, false);
    }

    private JTIEventListener<String> registerAccountListener = new JTIEventListener()
    {

        @Override
        public void execute(Object result)
        {
            String account = String.valueOf(result);
            Map<String, Object> accountMap = (Map<String, Object>) cacheAccounts.getObject(account);
            if (accountMap == null)
            {
                logger.info("read cache map error!");
                return;
            }
            registerMapper.addAccount(accountMap);
            accountMap.remove("account");
            accountMap.remove("password");
            registerMapper.addUser(accountMap);
            logger.info("registered user and account succeed!");
        }
    };


    public JTMessageLogicListener logicListener()
    {
        return new JTMessageLogicListener();
    }

}
