package src.config.swagger;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import src.config.dto.Pagination;
import src.config.dto.SuccessResponsePagedDto;

@Aspect
@Component
public class PaginatedApiResponseAspect {

    private final HttpServletRequest request;

    @Autowired
    public PaginatedApiResponseAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Around("@annotation(paginatedApiResponse)")
    public Object applyPagination(ProceedingJoinPoint joinPoint, PaginatedApiResponse paginatedApiResponse) throws Throwable {
        int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "1" : request.getParameter("pageSize"));
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber")== null ? "1" : request.getParameter("pageNumber"));
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Pagination pagination = new Pagination(10, pageSize, pageNumber);
        SuccessResponsePagedDto<Object> responseDto = new SuccessResponsePagedDto<>();
        responseDto.setSuccess(true);
        responseDto.setPagination(pagination);

        return ResponseEntity.ok(responseDto);
    }

}
