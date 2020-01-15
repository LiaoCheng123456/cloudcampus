package service.user;

import com.rh.cloudcampus.dto.TestDTO;
import com.rh.cloudcampus.response.ObjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import service.takeout.TakeOutClientFallBackFactory;

@FeignClient(value = "cloudcampus-user",fallbackFactory = UserClientFallBackFactory.class)
public interface IUserService {

    @PostMapping("test/insert")
    ObjectResponse<TestDTO> insertTest(@RequestBody TestDTO testDTO);
}
