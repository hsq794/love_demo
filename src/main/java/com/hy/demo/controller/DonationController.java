package com.hy.demo.controller;


import com.hy.demo.service.impl.DonationServiceImpl;
import com.hy.demo.util.Result;
import com.hy.demo.vo.DonationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/donation")

public class DonationController {

    @Autowired
    private DonationServiceImpl donationService;

    @GetMapping("/donationListPage")
    public Result findUserList(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String uid,
                               @RequestParam String gid,
                               @RequestParam String dnumber) {

        Result result=donationService.donationListByPage(pageNum,pageSize,uid,gid,dnumber);

        return result;
    }

    @PostMapping("/addOrUpdate")
    public Result addOrUpdateUser(@RequestBody DonationDto donationDto) throws ParseException {
        Result result=donationService.addOrUpdateUser(donationDto);
        return result;
    }

    @DeleteMapping("/del/{id}")
    public Result delUser(@PathVariable Integer id){
        boolean b = donationService.removeById(id);
        if(b){
            return Result.success();
        }else{
            return Result.error("300","操作失败!");
        }

    }


}
