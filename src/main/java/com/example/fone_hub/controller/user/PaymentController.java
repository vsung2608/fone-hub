package com.example.fone_hub.controller.user;

import com.example.fone_hub.enums.OrderStatus;
import com.example.fone_hub.service.OrderService;
import com.example.fone_hub.service.PaymentService;
import com.example.fone_hub.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showPaymentPage(@RequestParam("orderId") Long orderId,
                                  @RequestParam("addressId") Long addressId,
                                  Model model) {
        // Lấy thông tin đơn hàng
        model.addAttribute("order", orderService.getOrderById(orderId));
        model.addAttribute("addressId", addressId);
        return "user/payment";
    }

    @PostMapping("/process")
    public String processPayment(@RequestParam("orderId") Long orderId,
                                 @RequestParam("addressId") Long addressId,
                                 @RequestParam("paymentMethod") String paymentMethod,
                                 HttpServletRequest request,
                                 Model model) {
        try {
            // Cập nhật địa chỉ giao hàng cho đơn hàng
            userService.updateOrderAddress(orderId, addressId);

            if ("COD".equals(paymentMethod)) {
                // Cập nhật trạng thái đơn hàng là đã thanh toán COD
                orderService.updateStatusOrder(orderId, OrderStatus.PENDING);
                return "redirect:/user/history";
            } else if ("VNPAY".equals(paymentMethod)) {
                // Hoàn tất đơn hàng trước để có thông tin tổng tiền
                userService.checkout(orderId, addressId);

                // Lấy thông tin đơn hàng sau khi đã cập nhật
                Long amount = orderService.getOrderById(orderId).getTotal();
                if (amount == null || amount <= 0) {
                    throw new RuntimeException("Invalid order amount");
                }

                // Tạo URL thanh toán VNPay
                String orderInfo = "Thanh toan don hang " + orderId;
                String paymentUrl = paymentService.createPaymentUrl(orderId, amount, orderInfo, request);
                return "redirect:" + paymentUrl;
            }
            model.addAttribute("error", "Invalid payment method");
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", "Payment processing failed: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/vnpay-payment")
    public String vnpayPayment(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
            String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
            String vnp_TxnRef = request.getParameter("vnp_TxnRef");

            System.out.println("Processing VNPay callback:");
            System.out.println("Response Code: " + vnp_ResponseCode);
            System.out.println("Transaction Status: " + vnp_TransactionStatus);
            System.out.println("Transaction Reference: " + vnp_TxnRef);

            // Nếu người dùng hủy thanh toán
            if ("24".equals(vnp_ResponseCode)) {
                redirectAttributes.addFlashAttribute("message", "Thanh toán đã bị hủy");
                return "redirect:/user/history";
            }

            // Kiểm tra và xử lý callback
            boolean isValid = paymentService.validatePaymentResponse(request);
            System.out.println("Payment validation result: " + isValid);

            if (isValid) {
                try {
                    paymentService.processPaymentCallback(request);
                    redirectAttributes.addFlashAttribute("message", "Thanh toán thành công");
                    System.out.println("Payment processed successfully");
                } catch (Exception e) {
                    System.err.println("Error processing payment: " + e.getMessage());
                    redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra khi xử lý thanh toán");
                }
                return "redirect:/user/history";
            }

            // Nếu thanh toán thất bại
            System.out.println("Payment validation failed");
            redirectAttributes.addFlashAttribute("message", "Thanh toán thất bại");
            return "redirect:/user/history";

        } catch (Exception e) {
            System.err.println("Error in vnpayPayment: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra khi thanh toán");
            return "redirect:/user/history";
        }
    }

}
