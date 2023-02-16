package com.shop.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "名前は必須です")
    private String name;

    @NotBlank(message = "E-mailは必須です")
    @Email(message = "E-mailの形式で入力してください")
    private String email;

    @NotBlank(message = "パスワードは必須です")
    @Length(min=8, max =16,message="パスワードは８字以上、１６字以下で入力してください")
    private String password;

    @NotEmpty(message = "アドレスは必須です")
    private String address;

}
