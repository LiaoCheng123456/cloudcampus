package service.order;


import com.rh.cloudcampus.dto.TestDTO;
import com.rh.cloudcampus.response.ObjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author liaocheng
 * @date 2020-1-15 17:20
 */
@FeignClient(value = "cloudcampus-order",fallbackFactory = OrderClientFallBackFactory.class)
public interface IOrderService {

    @PostMapping("test/insert")
    ObjectResponse<TestDTO> insertTest(@RequestBody TestDTO testDTO);
}
