package club.mastershu.ads.advice;

import club.mastershu.ads.exception.AdsException;
import club.mastershu.ads.vo.CommonResponse;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = AdsException.class)
    public CommonResponse<String> handlerAdsException(HttpServletRequest request, AdsException exception) {
        CommonResponse<String> response = new CommonResponse<>(-1, "Has error");
        response.setResult(exception.getMessage());
        return response;
    }
}
