package com.roadrescue.roadrescue.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/razorpay")
@CrossOrigin("*")
public class RazorpayController {

    private static final String KEY_ID = "rzp_test_T4we3XytkGofP5";
    private static final String KEY_SECRET = "Kzpyf4znLqbjXo2tEU78t7hp";

    @PostMapping("/create-order")
    public String createOrder(
            @RequestParam int amount
    ) throws Exception {

        RazorpayClient razorpay =
                new RazorpayClient(KEY_ID, KEY_SECRET);

        JSONObject options = new JSONObject();

        options.put("amount", amount * 100);
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");

        Order order = razorpay.orders.create(options);

        return order.toString();
    }
}