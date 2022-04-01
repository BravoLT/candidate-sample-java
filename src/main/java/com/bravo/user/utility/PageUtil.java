package com.bravo.user.utility;

import javax.servlet.http.HttpServletResponse;
//learned that lombok is a Java library tool that generates code for minimizing boilerplate code
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@UtilityClass
public class PageUtil {
  //setting default page size to 20
  private static final int DEFAULT_SIZE = 20;

  //to create a new page, we need 2 arguments - an integer for the page number and an integer for the size
  public static PageRequest createPageRequest(final Integer page, final Integer size){
    return createPageRequest(page, size, DEFAULT_SIZE);
  }
  public static PageRequest createPageRequest(
      final Integer page,
      final Integer size,
      final Integer defaultSize
  ){
    //I don't quite understand what is going on here,
    final int pg = page != null && page > 0 ? page - 1 : 0;
    final int sz = size != null && size > 0 ? size : defaultSize;
    return PageRequest.of(pg, sz);
  }

  //updating page headers with
  public static void updatePageHeaders(
      final HttpServletResponse httpResponse,
      final Page<?> page,
      final PageRequest pageRequest
  ){
    if(httpResponse == null){
      return;
    }
    httpResponse.setIntHeader("page-count", page.getTotalPages());
    httpResponse.setIntHeader("page-number", pageRequest.getPageNumber() + 1);
    httpResponse.setIntHeader("page-size", pageRequest.getPageSize());
  }
}
