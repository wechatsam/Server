package im.redisson.caches;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JTSerializeUtil 
{
	public static byte[] serialize(Object object) 
	{
		ObjectOutputStream buffer =  null ;
		ByteArrayOutputStream byteArray =  null ;
		try  
		{
			// ���л�
			byteArray =  new  ByteArrayOutputStream();
			buffer =  new  ObjectOutputStream(byteArray);
			buffer.writeObject(object);
			byte [] bytes = byteArray.toByteArray();
			return  bytes;
		}  
		catch(Exception e) 
		{

		}
		return  null ;
	}

	public static Object unserialize(byte [] bytes) 
	{
		ByteArrayInputStream byteArray =  null ;
		try  
		{
			byteArray =  new  ByteArrayInputStream(bytes);// �����л�
			ObjectInputStream buffer =  new  ObjectInputStream(byteArray);
			return  buffer.readObject();
		}  
		catch  (Exception e) 
		{

		}
		return   null ;
	}
}
