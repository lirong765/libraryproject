import com.github.pagehelper.PageInfo;
import com.rong.pojo.ClassInfo;
import com.rong.service.ClassInfoService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//测试spring是否能够执行
public class TestDemo {

    @Test
    public void testSpring(){
        //获取Spring容器
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        //获取bean
        ClassInfoService classInfoService = context.getBean("classInfoService", ClassInfoService.class);

        PageInfo<ClassInfo> pageInfo = classInfoService.queryClassInfoAll("李荣", 1, 15);

        System.out.println(pageInfo.toString());

    }

    public static void main(String[] args) {
        System.out.println();

    }
}
