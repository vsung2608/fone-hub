function previewImage(event) {
    const preview = document.getElementById("preview");
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = function () {
        preview.src = reader.result;
        preview.style.display = "block";
        document.getElementById("submitBtn").style.display = "flex";
    };

    if (file) {
        reader.readAsDataURL(file);
    }
}

function deleteImage(imageId) {
    if (confirm("Bạn có chắc chắn muốn xóa ảnh này?")) {
        $.ajax({
            url: `/admin/product/image/${imageId}`,
            type: "DELETE",
            success: function () {
                // Thành công: Chuyển hướng hoặc reload trang
                location.reload();
            },
            error: function (xhr) {
                // Xử lý lỗi khi server trả về trạng thái khác 200/204
                alert("Có lỗi xảy ra khi xóa ảnh: " + xhr.status + " - " + xhr.statusText);
            },
        });
    }
}

document.querySelectorAll('.nav-tabs .nav-link').forEach(function(link) {
    link.addEventListener('click', function () {
        document.querySelectorAll('.nav-tabs .nav-link').forEach(function(el) {
            el.classList.remove('active');
        });
        this.classList.add('active');
    });
});

document.querySelectorAll('.sticky-tabs .nav-link').forEach(link => {
    link.addEventListener('click', () => {
        link.parentElement.scrollIntoView({
            behavior: 'smooth',
            block: 'nearest',
            inline: 'center'
        });
    });
});
