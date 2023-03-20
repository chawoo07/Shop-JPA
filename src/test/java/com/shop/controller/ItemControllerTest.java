package com.shop.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("商品登録ペイジ権限テスト")
    @WithMockUser(username = "admin", roles = "ADMIN")  //現在nameがadminで、roleがAdminであるUserがログインされた状態でテストができるようにするアノテーション
    public void itemFormTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))  //商銀登録のペイジにget要請
                .andDo(print())     //メセージをコンソルに出力
                .andExpect(status().isOk());    //応答状態コードの確認
    }

    @Test
    @DisplayName("商品登録ペイジ一般会員接近テスト")
    @WithMockUser(username = "user", roles = "USER")    //現在nameがuserで、roleがUSERであるUserがログインされた状態でテストができるようにするアノテーション
    public void itemFormmNotAdminTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isForbidden()); //商品ペイジに入る時Forbidden例外が発生したらテスト成功
    }
}
