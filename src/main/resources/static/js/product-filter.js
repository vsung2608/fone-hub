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