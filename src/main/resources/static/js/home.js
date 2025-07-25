function toggleMenu() {
    const navPills = document.querySelector('.nav-pills');
    navPills.classList.toggle('show');
}

// Đóng menu khi click ra ngoài
document.addEventListener('click', function (event) {
    const navPills = document.querySelector('.nav-pills');
    const menuToggle = document.querySelector('.menu-toggle');

    if (!navPills.contains(event.target) && !menuToggle.contains(event.target)) {
        navPills.classList.remove('show');
    }
});

function changeImage(button, index) {
    // Lấy container cha của sản phẩm
    const productCard = button.closest(".product-card");

    // Xóa active class từ tất cả ảnh và nút
    productCard
        .querySelectorAll(".image-container img")
        .forEach((img) => img.classList.remove("active"));
    productCard
        .querySelectorAll(".image-nav-btn")
        .forEach((btn) => btn.classList.remove("active"));

    // Thêm active class cho ảnh và nút được chọn
    productCard
        .querySelectorAll(".image-container img")
        [index].classList.add("active");
    button.classList.add("active");
}

document.addEventListener("DOMContentLoaded", function () {
    // Lấy tất cả các link trong navigation
    const navLinks = document.querySelectorAll(".nav-link");

    navLinks.forEach((link) => {
        link.addEventListener("click", function (e) {
            // Chỉ xử lý cho các link có href bắt đầu bằng #
            if (this.getAttribute("href").startsWith("#")) {
                e.preventDefault();

                const targetId = this.getAttribute("href");

                // Xử lý đặc biệt cho scroll to top
                if (targetId === "#top") {
                    window.scrollTo({
                        top: 0,
                        behavior: "smooth",
                    });
                } else {
                    const targetElement = document.querySelector(targetId);
                    if (targetElement) {
                        targetElement.scrollIntoView({
                            behavior: "smooth",
                            block: "start",
                        });
                    }
                }

                // Thêm active class cho link được click
                navLinks.forEach((l) => l.classList.remove("active"));
                this.classList.add("active");
            }
        });
    });

    // Highlight navigation item khi scroll
    window.addEventListener("scroll", function () {
        let current = "";
        const sections = document.querySelectorAll("div[id]");
        const scrollPos = window.pageYOffset;

        // Thêm xử lý cho Trang Chủ active state
        if (scrollPos < 100) {
            // Nếu gần đầu trang
            navLinks.forEach((link) => {
                link.classList.remove("active");
                if (link.getAttribute("href") === "#top") {
                    link.classList.add("active");
                }
            });
            return;
        }

        sections.forEach((section) => {
            const sectionTop = section.offsetTop;
            if (scrollPos >= sectionTop - 60) {
                current = section.getAttribute("id");
            }
        });

        navLinks.forEach((link) => {
            link.classList.remove("active");
            if (link.getAttribute("href") === `#${current}`) {
                link.classList.add("active");
            }
        });
    });
});
document.addEventListener("DOMContentLoaded", function () {
    // Xử lý cho carousel sản phẩm mới
    const productCarousel = document.getElementById("productCarousel");
    if (productCarousel) {
        const productSlides =
            productCarousel.querySelectorAll(".carousel-item");
        const productControls = productCarousel.querySelectorAll(
            ".carousel-control-prev, .carousel-control-next"
        );

        if (productSlides.length <= 1) {
            productControls.forEach(
                (control) => (control.style.display = "none")
            );
        } else {
            productControls.forEach(
                (control) => (control.style.display = "flex")
            );
        }
    }

    // Xử lý cho carousel sản phẩm khuyến mãi
    const saleCarousel = document.getElementById("productSaleCarousel");
    if (saleCarousel) {
        const saleSlides = saleCarousel.querySelectorAll(".carousel-item");
        const saleControls = saleCarousel.querySelectorAll(
            ".carousel-control-prev, .carousel-control-next"
        );

        if (saleSlides.length <= 1) {
            saleControls.forEach((control) => (control.style.display = "none"));
        } else {
            saleControls.forEach((control) => (control.style.display = "flex"));
        }
    }
});

$(document).ready(function () {
    $(".product-price").each(function (index) {
        const $el = $(this);
        const price = parseInt($el.data("price"));
        const discount = parseInt($el.data("discount"));
        console.log(`→ Sản phẩm ${index + 1}: Giá=${price}, Giảm=${discount}`);

        if (isNaN(price) || isNaN(discount)) return;

        const oldPrice = Math.round(price / (1 - discount / 100));
        const downAmount = oldPrice - price;

        $el.find(".old-price").text(formatTienVN(oldPrice));
        $el.find(".new-price").text(formatTienVN(price));
        $el.find(".amount-down").text(`Giảm ${formatTienVN(downAmount)}`);
    });

    $("#bannerCarousel").carousel({
        interval: 3000, // Thời gian chuyển đổi (3 giây)
        wrap: true, // Lặp lại
        keyboard: true, // Cho phép điều khiển bằng bàn phím
    });

    // Dừng tự động chuyển khi hover
    $(".carousel").hover(
        function () {
            $(this).carousel("pause");
        },
        function () {
            $(this).carousel("cycle");
        }
    );
});

function checkSearch() {
    let checkSearch = document.getElementById("search").value;
    if (checkSearch == null || checkSearch.trim() === "") {
        window.alert("Vui Lòng Nhập Tìm Kiếm");
        return false;
    }
    return true;
}

function formatTienVN(amount) {
    if (isNaN(amount)) return "Giá trị không hợp lệ";
    return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " ₫";
}

