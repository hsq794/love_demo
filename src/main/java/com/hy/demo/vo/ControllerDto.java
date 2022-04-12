package com.hy.demo.vo;

import com.hy.demo.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author
 * 请求前端参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerDto {

    private Integer cid;

    private String cname;

    private String cpassword;

    private String newPassword;

    private String confirmPassword;

    private String token;

    private List<Menu> menuList;
}
