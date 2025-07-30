package com.example.fone_hub.controller;

import com.example.fone_hub.service.OrderDetailService;
import com.example.fone_hub.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/{orderId}")
    public ModelAndView viewOrder(@PathVariable Long orderId) {
        return new ModelAndView("/order/view")
                .addObject("order", orderService.getOrderById(orderId))
                .addObject("items", orderDetailService.getItemByOrderId(orderId));
    }
}