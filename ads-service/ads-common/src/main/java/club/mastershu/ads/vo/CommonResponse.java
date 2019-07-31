package club.mastershu.ads.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    private Integer errno;
    private String errMsg;
    private T result;

    public CommonResponse(Integer errno, String errMsg) {
        this.errno = errno;
        this.errMsg = errMsg;
    }
}
