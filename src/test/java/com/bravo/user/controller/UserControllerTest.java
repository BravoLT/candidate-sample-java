package com.bravo.user.controller;

import com.bravo.user.App;
import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.UserReadDto;
import com.bravo.user.model.filter.UserFilter;
import com.bravo.user.service.UserService;
import com.bravo.user.utility.PageUtil;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserService userService;

  private List<UserReadDto> users;

  @BeforeEach
  public void beforeEach(){
    final List<Integer> ids = IntStream
        .range(1, 10)
        .boxed()
        .collect(Collectors.toList());

    this.users = ids.stream()
        .map(id -> createUserReadDto(Integer.toString(id)))
        .collect(Collectors.toList());
  }

  @Test
  void getRetrieveWithName() throws Exception {
    when(userService
        .retrieveByName(anyString(), any(PageRequest.class), any(HttpServletResponse.class)))
        .thenReturn(users);

    final ResultActions result = this.mockMvc
        .perform(get("/user/retrieve?name=lucy"))
        .andExpect(status().isOk());

    for(int i = 0; i < users.size(); i++){
      result.andExpect(jsonPath(String.format("$[%d].id", i)).value(users.get(i).getId()));
    }

    final PageRequest pageRequest = PageUtil.createPageRequest();
    verify(userService).retrieveByName(
        eq("lucy"), eq(pageRequest), any(HttpServletResponse.class)
    );
  }

  @Test
  void getRetrieveWithNameEmpty() throws Exception {
    this.mockMvc.perform(get("/user/retrieve?name="))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getRetrieveWithNameSpecialCharacters() throws Exception {
    this.mockMvc.perform(get("/user/retrieve?name=^"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getRetrieveWithNameSpace() throws Exception {
    this.mockMvc.perform(get("/user/retrieve?name=Sa w"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getRetrieveWithNameMissing() throws Exception {
    this.mockMvc.perform(get("/user/retrieve"))
        .andExpect(status().isBadRequest());
  }

  private UserReadDto createUserReadDto(final String id){
    final UserReadDto user = new UserReadDto();
    user.setId(id);
    return user;
  }
}