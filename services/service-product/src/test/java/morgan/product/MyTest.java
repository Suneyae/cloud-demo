package morgan.product;

import com.morgan.product.ProductMainApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@SpringBootTest(classes = ProductMainApplication.class)
public class MyTest {

    @Autowired
    DiscoveryClient discoveryClient;

    @Test
    void test(){
        for (String service : discoveryClient.getServices()) {
            System.out.println("service list as below :"+service);

            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(service);
            for(ServiceInstance instance:serviceInstances){
                System.out.println("host is:"+instance.getHost());
                System.out.println("port is:"+instance.getPort());
            }
        }

    }

}
