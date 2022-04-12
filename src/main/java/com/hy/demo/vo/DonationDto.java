package com.hy.demo.vo;

import com.hy.demo.entity.Donation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationDto extends Donation {

    private String gname;

    private String uname;



}
