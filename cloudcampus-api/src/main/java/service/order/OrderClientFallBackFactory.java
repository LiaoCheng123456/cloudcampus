package service.order;

import com.rh.cloudcampus.dto.TestDTO;
import com.rh.cloudcampus.enums.RspStatusEnum;
import com.rh.cloudcampus.response.ObjectResponse;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author liaocheng
 * @date 2020-1-15 17:20
 */
@Component
public class OrderClientFallBackFactory implements FallbackFactory<IOrderService> {
    public IOrderService create(Throwable throwable) {
        return new IOrderService() {

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
