package im.servers.register.networks.utils;


import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class JTHttpRequestParser {

    public static Map<String, Object> readParams(FullHttpRequest request)
    {
        if(request.method() == HttpMethod.GET)
        {
            return readGetParams(request);
        }
        else if(request.method() == HttpMethod.POST)
        {
            return readPostParams(request);
        }
        else
        {
            return  null;
        }
    }
    /**
     * ???GET?????????
     * @param request
     * @return
     */
    public static Map<String, Object> readGetParams(FullHttpRequest request) {

        if (request.method() == HttpMethod.GET) {
            String contentType = request.headers().get("Content-Type").trim().toLowerCase();
            Map<String, Object> paramMap = new HashMap<>();
            QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
            decoder.parameters().entrySet().forEach(entry -> {
                paramMap.put(entry.getKey(), entry.getValue().get(0));
            });
            return paramMap;
        }

        return null;
    }


    /**
     * ???POST???????????
     *
     * @param request
     * @return ???????Map
     */
    public static Map<String, Object> readPostParams(FullHttpRequest request) {

        if (request.method() == HttpMethod.POST) {
            String contentType = request.headers().get("Content-Type").trim().toLowerCase();

            if (contentType.contains("x-www-form-urlencoded"))
            {
                Map<String, Object> paramMap = new HashMap<>();
                HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request);
                List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
                ByteBuf bytes = request.content();
                bytes.resetReaderIndex();
                String content = bytes.toString(Charset.forName("UTF-8"));
                return (Map<String, Object>) JSON.parse(content);
//                if (bytes.readableBytes() > 0)
//                {
//
//                }
//                for (InterfaceHttpData parm : parmList)
//                {
//                    if (parm.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute)
//                    {
//                        MemoryAttribute data = (MemoryAttribute) parm;
//                        paramMap.put(data.getName(), data.getValue());
//                    }
//                }
//                return paramMap;
            }
            else if(contentType.contains("json"))
            {
                ByteBuf content = request.content();
                String msg = content.toString(CharsetUtil.UTF_8);
                Map<String, Object> paramMap = JSON.parseObject(msg);
                return paramMap;

            }
        }

        return null;

    }

}

