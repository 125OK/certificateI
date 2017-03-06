package org.shdic.xfire.pojo;



import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.codehaus.xfire.client.Client;
import javax.xml.XMLConstants;

public class CopyOfHandleServiceTest {

	public static void main(String[] args) {
		try {
             // String newurl =  "http://172.168.1.100:8080/Server/services/PlaceService?wsdl";
			//String newurl ="http://172.168.1.100:8080/Server/services/OrderService?wsdl";
			//String newurl ="http://192.168.0.201/LoginService/LoginService.svc?wsdl";
			String newurl ="http://192.168.0.105:8080/newServices/AndroidServiceImpl.svc?wsdl";
		//String newurl ="http://192.168.0.85:8080/ServerAndroid/newServices/NewFoodService?wsdl";
//		String newurl ="http://192.168.0.85:8080/ServerAndroid/newServices/NewOrderService?wsdl";
			//String newurl ="http://172.168.1.100:8080/Server/services/UserService?wsdl";
			//String newurl ="http://172.168.1.100:8080/Server/services/VoucherService?wsdl";
	      //	String newurl ="http://172.168.1.100:8080/Server/services/AssessService?wsdl";
	//		String newurl ="http://192.168.0.82:8080/ServerAndroid/services/RealTimeServiceAndroid?wsdl";
			
			
			//Object[] results0 = client.invoke("insertVisitorBook",new Object[] {"{'tel':'13524108659','visitorperson':'Mr.Cong','content':'不知道写什么，" +"\r测试留言功能','isopen':'不公开'}"});
			//Object[] results0 = client.invoke("insertOrder",new Object[] {"{'ORDERTIME':'2013-08-31 17:17','PLACE':'日本厅','ORDERPERSON':'13524108659','TEL':'13','REMARKS':'备注'}"});
			Long a1=System.currentTimeMillis();
			Client client = new Client(new URL(newurl));
             // Object[] results0 = client.invoke("insertMessage",new Object[] {"{'手机号码':'13524108659','发送内容':'测试短信提醒'}"});
			//Object[] results0 = client.invoke("getSeatState",new Object[] {"{'shopname':'大连店' ,'ordertime':'2013-09-03'}"});
//			Object[] results0 = client.invoke("getCurSeatState",new Object[] {"{'shopname':'大连店'}"});
			//Object[] results0 = client.invoke("insertOrder",new Object[] {"{'ORDERTIME':'2013-09-11 18:01','PLACE':'意式厅','ORDERPERSON':'dfsdfa','TEL':'13524108659','REMARKS':''}"});
			//Object[] results0 = client.invoke("getOrdersList",new Object[] {"{'tel':'15001866053'}"});
			//Object[] results0 = client.invoke("deleteOrder",new Object[] {"{'orderseq':'15001866053'}"});
			//Object[] results0 = client.invoke("getOrderDetails",new Object[] {"{'tel':'13524108659'}"});
			//Object[] results0 = client.invoke("insertFavorite",new Object[] {"{'tel':'1111111','favorite':'[{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"*顾客基本信息*\",\"value\":\"\",\"edit\":\"\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"姓名：\",\"value\":\"未填写\",\"edit\":\"\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"性别：\",\"value\":\"未填写\",\"edit\":\"男/女\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"出生日期：\",\"value\":\"未填写\",\"edit\":\"\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"星座\",\"value\":\"未填写\",\"edit\":\"白羊座/金牛座/双子座/巨蟹座/狮子座/处女座/天秤座/天蝎座/射手座/摩羯座/水瓶座/双鱼座\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"联系方式\",\"value\":\"未填写\",\"edit\":\"\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"电子邮箱\",\"value\":\"未填写\",\"edit\":\"\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"职业\",\"value\":\"未填写\",\"edit\":\"学生/白领/公务员/企业老板/自由职业\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"*餐饮习惯*\",\"value\":\"\",\"edit\":\"\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"喜欢的咖啡口味\",\"value\":\"未填写\",\"edit\":\"偏苦的/偏酸的/偏甜的/偏奶味重的\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"平时饮用咖啡时的偏好\",\"value\":\"未填写\",\"edit\":\"不加糖和奶/只加糖/只加奶/糖奶都加\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"喜欢的咖啡类型\",\"value\":\"未填写\",\"edit\":\"意式咖啡/花式咖啡/纯品咖啡\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"最喜欢的咖啡：\",\"value\":\"未填写\",\"edit\":\"意大利浓缩/卡布基诺/拿铁/摩卡/美式咖啡/焦糖玛奇朵/纯品咖啡\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"喜欢的咖啡杯风格\",\"value\":\"未填写\",\"edit\":\"欧美风格/简约时尚风格/现代风格/古典风格\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"喜欢的饮品类型\",\"value\":\"未填写\",\"edit\":\"咖啡/茶/冰沙/奶茶/花草茶特调茶/果汁/碳酸饮料/鸡尾酒\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"喜欢的餐品口味\",\"value\":\"未填写\",\"edit\":\"辣口/甜口/咸口/偏酸口/清淡口/偏咸鲜口\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"常点的餐品：\",\"value\":\"未填写\",\"edit\":\"商务套餐/面类/牛排类/三文治/汤类/披萨/粥类/小吃\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"忌口\",\"value\":\"未填写\",\"edit\":\"葱/姜/蒜/香菜/辣\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"*其他兴趣爱好*\",\"value\":\"\",\"edit\":\"\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"最喜欢的颜色是\",\"value\":\"未填写\",\"edit\":\"黑色/白色/红色/绿色/蓝色/黄色/紫色\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"最喜欢的季节是\",\"value\":\"未填写\",\"edit\":\"春季/夏季/秋季/冬季\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"最喜欢的水果是\",\"value\":\"未填写\",\"edit\":\"苹果/梨/西瓜/柳橙/香蕉/奇异果\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"最喜欢的手机品牌是\",\"value\":\"未填写\",\"edit\":\"苹果/三星/索尼爱立信/诺基亚/国产品牌\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"最喜欢座的位置是\",\"value\":\"未填写\",\"edit\":\"包间/散台\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"喜欢的花 \",\"value\":\"未填写\",\"edit\":\"玫瑰/康乃馨/百合/郁金香\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"喜欢的音乐类型  \",\"value\":\"未填写\",\"edit\":\"欧美流行音乐/爵士音乐/中国流行音乐/钢琴曲/轻音乐\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"喜欢的杂志书籍类型\",\"value\":\"未填写\",\"edit\":\"时尚杂志/汽车杂志/美食杂志/历史书籍/文学书籍/小说\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"喜欢看的报纸\",\"value\":\"未填写\",\"edit\":\"大连晚报/大连日报/半岛晨报/新商报\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"*其他爱好*\",\"value\":\"\",\"edit\":\"\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"喜欢的娱乐方式\",\"value\":\"未填写\",\"edit\":\"打扑克/下棋/看书/看电视/打投影游戏机\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"在大连最常去的商圈\",\"value\":\"未填写\",\"edit\":\"青泥洼桥/西安路/和平广场/奥林匹克广场/万达广场\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"最常坐台位\",\"value\":\"未填写\",\"edit\":\"\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"特殊喜好或习惯\",\"value\":\"未填写\",\"edit\":\"\"},{\"section\":\"(null)\",\"row\":\"(null)\",\"type\":\"(null)\",\"key\":\"备注\",\"value\":\"未填写\",\"edit\":\"\"}]'}"});
			//Object[] results0 = client.invoke("checkUser",new Object[] {"{tel:13524108651}"});
			
//		service
		//  Object[] results0 = client.invoke("getAlluserInfo",new Object[] {});
//           	Object[] results0 = client.invoke("getEnvironment",new Object[] {""});
			
			//Object[] results0 = client.invoke("checkVerification",new Object[] {"{'tel':'13524108659','username':'CONGDONG','password':'123456'}"});
			//Object[] results0 = client.invoke("queryFavorite",new Object[] {"{'tel':'13524108659'}"});
		//	Object[] results0 = client.invoke("userUpdateOrder",new Object[] {"{'orderseq':'1311010001','zwmc':'店方取消'}"});//{'tel':'13524108659'}
	   //	Object[] results0 = client.invoke("getVoucherByVoc",new Object[] {"{'voucher':'100000000040'}"});//{'tel':'13524108659'}
			
			//Object[] results0 = client.invoke("getVisitorBook",new Object[] {null});
			//Object[] results0 = client.invoke("deleteVisitorBook",new Object[] {"{'id':'43'}"});
			//Object[] results0 = client.invoke("insertVisitorBook",new Object[] {"{'content':'你们的咖啡太甜，少放奶','tel':'13524108659','visitorperson':'李先生','isopen':'公开'}"});
			//Object[] results0 = client.invoke("getConsumesList",new Object[] {"{tel:13524108659}"});
			//Object[] results0 = client.invoke("getConsumeInfo",new Object[] {"{orderseq:1309010002}"});
			//Object[] results0 = client.invoke("insertUser",new Object[] {"{'username':'wwx','password':'123','tel':'13555555555'}"});
			
			//Object[] results0 = client.invoke("insertRequest",new Object[] {"{'username':'李先生','seat':'A002','tel':'13444444','content':'视频呼叫'}"});
//			Object[] results0 = client.invoke("getBigTypeFood",new Object[] {"{'state':'S','pid':'901'}"});
		//	Object[] results0 = client.invoke("insertOrders",new Object[] {"{'seat':'A5','content':'sss','ordertime':'2013-11-28 13:00','tel':'13555555555','status':'预约中'}"});
//			Object[] results0 = client.invoke("personOrder",new Object[] {"{'id':'1000171'}"});
			Object[] results0 = client.invoke("login",new Object[] {"{'pwd':'123456','usr':'123456'}"});
			
			Long a2=System.currentTimeMillis();
			System.out.println(a2-a1);
//			System.out.println(results0[0]);
//			System.out.println(results1[0]);
		/*	Object obj[] = new Object[3]; 
			obj[0]="2210";
			obj[1]="2234";
			obj[2]="36";*/
			
			    //String MASTER_CUSTOMS_CODE,String CUSTOMS_CODE,String ENTRY_COUNT_A
			//Object[] results0 = client.invoke("getWeatherbyCityName", new Object[]{"北京"});
			System.out.println("获取："+results0[0]);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(System.currentTimeMillis());
		}
	}

}
