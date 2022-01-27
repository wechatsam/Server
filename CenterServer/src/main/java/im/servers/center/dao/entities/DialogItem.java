package im.servers.center.dao.entities;

import lombok.Data;

@Data
public class DialogItem
{
	public String chatId = null;
	public String fid = null;
	public String uid = null;
	public String content = null;
	public String nickName = null;
	public String avatar = null;
	public String friendName = null;
}
