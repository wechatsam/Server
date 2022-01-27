package im.com.networks;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;

public interface JTIChannelWrite 
{

    /**
     * Shortcut for calling {@link #write(Object)} and {@link #flush()} and only act on
     * {@link Channel}s that are matched by the {@link ChannelMatcher}.
     */
    ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher);

    /**
     * Shortcut for calling {@link #write(Object, ChannelMatcher, boolean)} and {@link #flush()} and only act on
     * {@link Channel}s that are matched by the {@link ChannelMatcher}.
     */
    ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher, boolean voidPromise);
}
