package com.hy.demo.controller;

import com.hy.demo.config.SystemControllerLog;
import com.hy.demo.service.ReceiveService;
import com.hy.demo.util.Result;
import com.hy.demo.vo.ReceiveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/receive")
public class ReceiveController {

    @Autowired
    private ReceiveService receiveService;


    @GetMapping("/receiveListPage")
    @SystemControllerLog(operate = "获赠信息查询",module = "获赠管理")
    public Result findUserList(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String uid,
                               @RequestParam String gid,
                               @RequestParam String rnumber) {

        Result result=receiveService.donationListByPage(pageNum,pageSize,uid,gid,rnumber);

        return result;
    }

    @PostMapping("/addOrUpdate")
    @SystemControllerLog(operate = "获赠信息添加或者修改",module = "获赠管理")
    public Result addOrUpdateUser(@RequestBody ReceiveDto receiveDto) throws Exception {
        try {
            Result result=receiveService.addOrUpdateUser(receiveDto);

            return result;
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("300","操作失败");
        }


    }

    @DeleteMapping("/del/{id}")
    @SystemControllerLog(operate = "获赠信息删除",module = "获赠管理")
    public Result delUser(@PathVariable Integer id) throws Exception{
        boolean b = receiveService.removeById(id);
        if(b){
            return Result.success();
        }else{
            return Result.error("300","操作失败!");
        }

    }


}
