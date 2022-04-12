package com.hy.demo.vo;

import com.hy.demo.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hsq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer uid;

    private String uname;

    private String upassword;

    private String newPassword;

    private String confirmPassword;

    private String token;

    private List<Menu> menuList;


}
