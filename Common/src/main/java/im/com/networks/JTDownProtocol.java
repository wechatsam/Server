package im.com.networks;

public class JTDownProtocol 
{
	public static final int USER_LOGIN = 10000;
	
	public static final int USER_REGISTER = 10001;
	
	/***
	 * ��������
	 * */
	public static final int SEARCH_FRIEND = 10002;
	/***
	 * ������Ӻ���
	 * */
	public static final int ADD_FRIEND_REQUEST = 10003;
	/***
	 * �Ƿ�ͬ���������
	 * */
	public static final int AGREE_WITH_FRIENDS = 10005;
	
	
	
	
	//
	/***
	 * ������������� �յ���������
	 * */
	public static final int NOTIFY_RECEIVE_FRIEND_REQUEST = 10004; 
	/***
	 * �յ���������ظ�
	 * */
	public static final int NOTIFY_RECEIV_FRIEND_ANSWER = 10006;
	/***
	 * �յ��µĺ���
	 * */
	public static final int NOTIFY_RECEIV_NEW_FRIEND = 10007;
}
