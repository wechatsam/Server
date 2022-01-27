/**
 * Copyright 2013-2033 Xia Jun(3979434@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.farsunset.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package im.servers.register.networks.protocols;


import com.alibaba.fastjson.JSON;
import im.com.common.JTStringUtils;
import im.com.common.JTTimeUtils;
import im.com.networks.JTProtocolErrorCode;
import im.com.networks.JTSendPackage;
import im.mq.JTMessageSender;
import im.redisson.caches.JTReadWriteLockerCacheMap;
import im.servers.register.networks.https.NettyHttpRequest;
import im.servers.register.networks.https.NettyRequest;
import im.servers.register.networks.https.Response;
import im.servers.register.networks.utils.JTHttpRequestParser;
import io.netty.channel.Channel;

import javax.annotation.PostConstruct;
import java.util.Map;

@NettyRequest(path = "/imChat/register", method = "POST")
public class JTRegisterLisenter implements JTLogicListener<String>
{

    private JTReadWriteLockerCacheMap localcache = null;

    @PostConstruct
    public void init()
    {
        this.localcache = JTReadWriteLockerCacheMap.instance("account_caches");
    }

    @Override
    public String execute(NettyHttpRequest request, Channel channel)
    {
        Map<String, Object> _dataMap = JTHttpRequestParser.readParams(request);
        String protocol = (String) _dataMap.get("protocol");
        Map<String, Object> _contentMap = (Map<String, Object>) _dataMap.get("content");
        String account = (String) _contentMap.get("account");
        String pwd = (String) _contentMap.get("password");
        if (account.length() < 6 || account.length() > 20 || pwd.length() < 6 || pwd.length() > 20)
        {
            return null;
        }
        Object AccountInfo = localcache.get(account);
        if (AccountInfo != null)
        {
                //该账户已经注册了
            return JTSendPackage.sendErrorPackage(protocol, JTProtocolErrorCode.ACCOUNT_REGISTERED, channel);
        }
        String uid = JTStringUtils.stringToMD5(account);
        _contentMap.put("uid", uid);
        _contentMap.put("loginTime", JTTimeUtils.getLocalTime());
        String content = JSON.toJSONString(_contentMap);
        localcache.set(account, content);
        JTMessageSender.send("register", account);

        return Response.ok("ok").toJSONString();
    }
}
