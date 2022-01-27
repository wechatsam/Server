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
package im.servers.register.networks.handlers;



import im.servers.register.networks.https.NettyHttpRequest;
import im.servers.register.networks.https.NettyRequest;
import im.servers.register.networks.exceptions.IllegalMethodNotAllowedException;
import im.servers.register.networks.exceptions.IllegalPathDuplicatedException;
import im.servers.register.networks.exceptions.IllegalPathNotFoundException;
import im.servers.register.networks.protocols.JTLogicListener;
import im.servers.register.networks.https.NettyHttpResponse;
import im.servers.register.networks.utils.Path;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Stream;

@ChannelHandler.Sharable
@Component
public class JTReceiveChannelMessage extends SimpleChannelInboundHandler<FullHttpRequest> implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(JTReceiveChannelMessage.class);

    private HashMap<Path, JTLogicListener> _logicListenerMap = new HashMap<>();

    private ExecutorService executor = Executors.newCachedThreadPool(runnable -> {
        Thread thread = Executors.defaultThreadFactory().newThread(runnable);
        thread.setName("NettyRequest-" + thread.getName());
        return thread;
    });

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        FullHttpRequest copyRequest = request.copy();
        executor.execute(() -> onReceiveMessage(ctx,new NettyHttpRequest(copyRequest)));
    }


    private void onReceiveMessage(ChannelHandlerContext context, NettyHttpRequest request){
        FullHttpResponse response = readMessage(request, context.channel());
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        ReferenceCountUtil.release(request);
    }

    private FullHttpResponse readMessage(NettyHttpRequest request, Channel channel) {

        JTLogicListener logicListener = null;
        try
        {
            logicListener = searchListener(request);
            String content =  logicListener.execute(request, channel);

            return NettyHttpResponse.ok(content);
        }
        catch (IllegalMethodNotAllowedException error)
        {
            return NettyHttpResponse.make(HttpResponseStatus.METHOD_NOT_ALLOWED);
        }
        catch (IllegalPathNotFoundException error)
        {
            return NettyHttpResponse.make(HttpResponseStatus.NOT_FOUND);
        }
        catch (Exception error)
        {
            LOGGER.error(logicListener.getClass().getSimpleName() + " Error",error);
            return NettyHttpResponse.makeError(error);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Map<String, Object> handlers =  applicationContext.getBeansWithAnnotation(NettyRequest.class);
        for (Map.Entry<String, Object> entry : handlers.entrySet())
        {
            Object handler = entry.getValue();
            Path path = Path.make(handler.getClass().getAnnotation(NettyRequest.class));
            if (_logicListenerMap.containsKey(path))
            {
                LOGGER.error("JTLogicListener has duplicated :" + path.toString(),new IllegalPathDuplicatedException());
                System.exit(0);
            }
            _logicListenerMap.put(path, (JTLogicListener) handler);
        }
    }

    private JTLogicListener searchListener(NettyHttpRequest request) throws IllegalPathNotFoundException, IllegalMethodNotAllowedException {

        AtomicBoolean matched = new AtomicBoolean(false);

        Stream<Path> stream = _logicListenerMap.keySet().stream()
                .filter(((Predicate<Path>) path ->
                {
                    if (request.matched(path.getUri(), path.isEqual()))
                    {
                        matched.set(true);
                        return matched.get();
                    }
                    return false;

                }).and(path ->
                {
                    return request.isAllowed(path.getMethod());
                }));
        Optional<Path> optional = stream.findFirst();
        stream.close();
        if (!optional.isPresent() && !matched.get()){
            throw  new IllegalPathNotFoundException();
        }
        if (!optional.isPresent() && matched.get()){
            throw  new IllegalMethodNotAllowedException();
        }
        return _logicListenerMap.get(optional.get());
    }

}
