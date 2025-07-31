package com.example.fone_hub.controller.user;


import com.example.fone_hub.dto.request.AddressRequest;
import com.example.fone_hub.dto.request.CreateUserRequest;
import com.example.fone_hub.dto.request.UpdateUserRequest;
import com.example.fone_hub.entity.User;
import com.example.fone_hub.enums.GenderEnum;
import com.example.fone_hub.service.CartService;
import com.example.fone_hub.service.OrderService;
import com.example.fone_hub.service.QRCodeService;
import com.example.fone_hub.service.UserService;
import com.example.fone_hub.utils.GetUserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class AccountController {   
    @Autowired
    private GetUserAuthentication getUserAuthentication;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private QRCodeService qrCodeService;
    @GetMapping("/info")
    public ModelAndView viewUser(@RequestParam(required = false) Long id) {
//        UserEntity user = getUserAuthentication.getUser();
//
//        String userData = "Họ tên: " + user.getFullName() + "\n" +
//                "Email: " + user.getEmail() + "\n" +
//                "SĐT: " + user.getPhone() + "\n" +
//                "Giới tính: " + user.getGender();
//
//        String qrCode = qrCodeService.generateQRCodeBase64(userData, 200, 200);


        User user = getUserAuthentication.getUser();
        String qrLink = "http://localhost:8080/user/qr-info/" + user.getUsername();
        String qrCode = qrCodeService.generateQRCodeBase64(qrLink, 200, 200);


        ModelAndView mav = new ModelAndView("/user/information")
                .addObject("genders", GenderEnum.values())
                .addObject("updateUser", new CreateUserRequest())
                .addObject("newAddress", new AddressRequest())
                .addObject("qrCode", qrCode);

        if (user != null) {
            mav.addObject("user", user);
        }

        return mav;
    }
    @GetMapping("/qr-info/{username}")
    public ModelAndView viewUserInfoFromQR(@PathVariable String username) {
        User user = userService.findByUserName(username);
        if (user == null) {
            return new ModelAndView("error").addObject("message", "Người dùng không tồn tại.");
        }

        return new ModelAndView("/user/qr-info").addObject("user", user);
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("updateUser") UpdateUserRequest request,
                             @RequestParam("file") MultipartFile file){

        User user = getUserAuthentication.getUser();

        userService.updateUser(request, user.getId(), file);

        return "redirect:/user/info";
    }

    @PostMapping("/add-address")
    public String updateUser(@ModelAttribute("newAddress") AddressRequest request){

        User user = getUserAuthentication.getUser();

        userService.addAddress(user.getId(), request);

        return "redirect:/user/info";
    }

    @GetMapping("/delete-address/{addressId}")
    public String deleteAddress(@PathVariable Long addressId){
        userService.deleteAddress(addressId);

        return "redirect:/user/info";
    }

    @GetMapping("/checkout/{addressId}")
    public String checkout(@PathVariable Long addressId){
        User user = getUserAuthentication.getUser();
        
        // Tạo đơn hàng mới
        Long orderId = orderService.AddOrder(user);
        
        // Chuyển đến trang thanh toán
        return "redirect:/payment?orderId=" + orderId + "&addressId=" + addressId;
    }

    @GetMapping("/history")
    public ModelAndView historyBuyProduct(){
        User user = getUserAuthentication.getUser();
        
        return new ModelAndView("/user/history")
                .addObject("orders", orderService.historyBuy(user.getId()));
    }
}
