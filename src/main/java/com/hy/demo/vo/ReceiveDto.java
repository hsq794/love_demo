package com.hy.demo.vo;


import com.hy.demo.entity.Receive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveDto extends Receive {

    //获赠用户
    private String uname;

    //捐赠用户
    private String donationName;

    private String gname;

}
