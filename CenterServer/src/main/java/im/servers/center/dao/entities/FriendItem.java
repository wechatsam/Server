package im.servers.center.dao.entities;

import lombok.Data;

@Data

public class FriendItem
{
	public String fid = null;
	public String nickName = null;
	public String avatar = null;
	public String uid = null;
	
	public String birthday = null;
	public int phone = 0;
	public String email = null;
	public String sgin = null;
	public int male = 0;
	
	public String remark = null;
	public int block = 0;
	public String loginTime = null;
}
