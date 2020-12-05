/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Controller
 *
 * @author limozhi on 2020/12/03
 */
@RestController
public class HelloWorldController {

    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String sayHello(){
        return "Hello World";
    }


}
