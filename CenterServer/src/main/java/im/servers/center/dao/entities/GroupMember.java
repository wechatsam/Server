package im.servers.center.dao.entities;

import lombok.Data;

@Data
public class GroupMember
{
	public int uid = 0;
	public String name = null;
	public String nickName = null;
	public int service = 0;
	public int managered = 0;
	public String avatar = null;
}
