package com.stylefeng.guns.modular.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stylefeng.guns.core.base.controller.BaseController;

/**
 * 远程数据库同步控制器
 *
 * @author fengshuonan
 * @Date 2017-10-23 17:17:26
 */
@Controller
@RequestMapping("/school/socket")
public class SocketController extends BaseController {

    private String PREFIX = "/school/socket/";

    /**
     * 跳转到远程数据库同步首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "socket.html";
    }
}
