package im.servers.register.networks;


import im.servers.register.networks.handlers.JTChannelInitializer;
import im.servers.register.networks.handlers.JTChannelMessageAdapter;
import im.servers.register.networks.handlers.JTLoggingFilter;
import im.servers.register.networks.handlers.JTReceiveChannelMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;


@Configuration
public class NettyHttpServer implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(NettyHttpServer.class);

    @Value("${server.port}")
    private int port;

    @Autowired
    public JTChannelInitializer channelInitializer;

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {

        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childOption(NioChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(NioChannelOption.SO_REUSEADDR,true);
        bootstrap.childOption(NioChannelOption.SO_KEEPALIVE,false);
        bootstrap.childOption(NioChannelOption.SO_RCVBUF, 2048);
        bootstrap.childOption(NioChannelOption.SO_SNDBUF, 2048);
        bootstrap.childHandler(channelInitializer);
//        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            public void initChannel(SocketChannel channel)
//            {
//                ChannelPipeline pipeline = channel.pipeline();
//                pipeline.addLast("codec", new HttpServerCodec());
//                pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
//                CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials().build();
//                pipeline.addLast(new CorsHandler(corsConfig));
//                pipeline.addLast("logging", new JTLoggingFilter());
//                pipeline.addLast("interceptor", channelMessageAdapter);
//                pipeline.addLast("bizHandler", receiveChannelMessage);
//
//                logger.info("create channel" + ++count);
//            }
//        })
        ;
        ChannelFuture channelFuture = bootstrap.bind(port).syncUninterruptibly().addListener(future -> {
            String logBanner = "\n\n" +
                    "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                    "*                                                                                   *\n" +
                    "*                                                                                   *\n" +
                    "*                   Netty Http Server started on port {}.                         *\n" +
                    "*                                                                                   *\n" +
                    "*                                                                                   *\n" +
                    "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";
            logger.info(logBanner, port);
        });

        channelFuture.channel().closeFuture().addListener(future ->
        {
            logger.info("Netty Http Server Start Shutdown ............");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        });
    }

}