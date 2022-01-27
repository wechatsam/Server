package im.servers.center.dao.entities;

import lombok.Data;

@Data
public class User
{
	public String uid = null;
	public String avatar = null;
	public int birthday = 0;
	public int phone = 0;
	public String email = null;
	public String nickName = null;
	public String sgin = null;
	public int male = 0;
	public String rqcode = null;
	public String loginTime = null;
	public String offTime = null;
}
