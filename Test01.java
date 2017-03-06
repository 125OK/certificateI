import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import sun.misc.BASE64Encoder;

import com.shdic.szhg.newServices.AndroidService;
import com.shdic.szhg.util.JsonUtil;

public class Test01 {

	/**
	 * @param args
	 */
	private static String json="{'type':'2'}";
	
	public static void main(String[] args) {
		Service service = new ObjectServiceFactory().create(AndroidService.class);
		XFireProxyFactory fac = new XFireProxyFactory(XFireFactory.newInstance().getXFire());
		String url = "http://localhost:8080/WebPDA/newServices/AndroidService";
		try {
			AndroidService and = (AndroidService)fac.create(service,url);
			String imgFile = "d://MSI  Gaming.jpg";
			InputStream in = null;
			byte[] data = null;
			BASE64Encoder en = new BASE64Encoder();			
			try{
				in = new FileInputStream(imgFile);
				data = new byte[in.available()];
				in.read(data);
				in.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			int l = en.encode(data).length();
			
			Map ma = new HashMap();
			ma.put("yhm", "13701787173");
			ma.put("mm", "407536");

			String json = JsonUtil.map2json(ma);	
					
			System.out.println(and.spLogin(json));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
