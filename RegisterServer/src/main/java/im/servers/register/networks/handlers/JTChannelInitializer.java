package im.servers.register.networks.handlers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JTChannelInitializer extends ChannelInitializer<SocketChannel>
{
    protected Logger logger = LoggerFactory.getLogger(JTChannelInitializer.class);
//    private int count = 0;

    @Resource
    private JTChannelMessageAdapter channelMessageAdapter;

    @Resource
    private JTReceiveChannelMessage receiveChannelMessage;
    @Resource
    private JTLoggingFilter loggingFilter;

//    private CorsHandler corsHandler = new CorsHandler(CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials().build());

    @Override
    protected void initChannel(SocketChannel channel) throws Exception
    {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
        CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials().build();
        pipeline.addLast(new CorsHandler(corsConfig));
        pipeline.addLast("logging", loggingFilter);
        pipeline.addLast("interceptor", channelMessageAdapter);
        pipeline.addLast("bizHandler", receiveChannelMessage);

//        logger.info("create channel" + ++count);
    }
}
