package com.hy.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.entity.User;
import com.hy.demo.util.Result;
import com.hy.demo.vo.UserDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface UserService extends IService<User> {


    Result login(UserDto userDto);

    Result register(UserDto userDto);

    Result updatePassword(UserDto userDto);

    Result userImportExcel(MultipartFile file) throws IOException;

    Result userExportExcel(HttpServletResponse response);
}
