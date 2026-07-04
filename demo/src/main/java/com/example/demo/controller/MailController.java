package com.example.demo.controller;

import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

/**
 * @author 
 * @date 
 */
@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @Value("${mail.from:your_email@qq.com}")
    private String mailFrom;

    @Value("${mail.to:your_email@foxmail.com}")
    private String mailTo;

    /**
     * 发送简单邮件
     */
    @GetMapping("/sendSimpleMail")
    public void sendSimpleMail() {
        mailService.sendSimpleMail(mailFrom,
                mailTo,
                "测试邮件",
                "这是一封测试邮件这是一封很好的测试邮件");
    }

    /**
     * 发送复杂邮件（文本+图片+附件）
     */
    @GetMapping("/sendMimeMail")
    public ResponseEntity<String> sendMimeMail() {
        return mailService.sendMimeMail(mailFrom,
                mailTo,
                "测试邮件",
                "<h3>这是一封测试邮件</h3><br>" +
                        "这是一封很好的测试邮件<br>" +
                        "<img src='cid:logo'>");
    }

    /**
     * 发送模板邮件
     *
     * @param
     * @return
     */
    @GetMapping("/sendTemplateMail")
    public ResponseEntity<String> sendTemplateMail() {
        Context context = new Context();
        context.setVariable("username", "liu-jie-liang");
        return mailService.sendTemplateMail(mailFrom,
                mailTo,
                "测试邮件",
                context);
    }
}
