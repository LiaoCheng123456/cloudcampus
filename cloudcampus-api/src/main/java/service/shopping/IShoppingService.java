package service.shopping;

import com.rh.cloudcampus.dto.TestDTO;
import com.rh.cloudcampus.response.ObjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author liaocheng
 * @date 2020-1-15 17:20
 */
@FeignClient(value = "cloudcampus-shopping",fallbackFactory = ShoppingClientFallBackFactory.class)
public interface IShoppingService {

    @PostMapping("test/insert")
    ObjectResponse<TestDTO> insertTest(@RequestBody TestDTO testDTO);
}
