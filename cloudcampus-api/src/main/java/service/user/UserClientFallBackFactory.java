package service.user;

import com.rh.cloudcampus.dto.TestDTO;
import com.rh.cloudcampus.enums.RspStatusEnum;
import com.rh.cloudcampus.response.ObjectResponse;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import service.takeout.ITakeOutService;

@Component
public class UserClientFallBackFactory implements FallbackFactory<IUserService> {
    public IUserService create(Throwable throwable) {
        return new IUserService() {

            /**
             * 插入数据熔断器
             *
             * @param testDTO
             * @return
             */
            public ObjectResponse<TestDTO> insertTest(TestDTO testDTO) {
                return new ObjectResponse<TestDTO>(RspStatusEnum.EXCEPTION.getCode(), RspStatusEnum.EXCEPTION.getMessage());
            }
        };
    }
}
