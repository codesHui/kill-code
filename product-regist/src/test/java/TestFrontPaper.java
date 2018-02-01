


import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.imi.dao.products.IProductDao;
import com.imi.dao.security.IUserDao;
import com.imi.entity.security.User;
/**
 * 测试前端试卷。
 * 
 * @author josh
 * @since 2014年10月20日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class TestFrontPaper {
	//private static final Logger logger = Logger.getLogger(TestFrontPaper.class);
@Resource
private 	IProductDao productDao;

@Resource
private 	IUserDao userDao;
	/*@Test
	public void  test(){
		for(int i=0;i<30;i++){
		Product data=new Product();
	
		data.setChineseName("产品"+i);
		data.setCreatedTime(new Date());
		data.setModifiedTime(new Date());
		data.setDescription("产品描述"+i);
		data.setKeywords("产品关键字"+i);
		data.setOrderNo(i);
		data.setProductLanguage("中文");
		data.setProductNO("");
		MemberUser user=new MemberUser();
		user.setId(5l);
		data.setCreator(user.getUser());
		data.setModifier(user.getUser());
		user.setRegisteredCode(String.format("%05d", 100+i));
		data.setUser(user);
		data.setRiskName("CAIT");
		data.setRiskType("CA");
		productDao.save(data); 
		}
	}*/


public void test2(){
	
	for(int i=6;i<17;i++){
		User data=new User();
		
		userDao.save(data);
	}
}
	
}