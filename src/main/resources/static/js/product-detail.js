function changeImage(src) {
    document.getElementById("mainImage").src = src;
    document.querySelectorAll(".thumbnail").forEach((thumb) => {
        thumb.classList.remove("active");
        if (thumb.src === src) {
            thumb.classList.add("active");
        }
    });
}

function increaseQuantity() {
    const input = document.getElementById("quantity");
    const max = parseInt(input.getAttribute("max"));
    const currentValue = parseInt(input.value);
    if (currentValue < max) {
        input.value = currentValue + 1;
    }
}

function decreaseQuantity() {
    const input = document.getElementById("quantity");
    if (parseInt(input.value) > 1) {
        input.value = parseInt(input.value) - 1;
    }
}
function checkComment() {
    let checkComment = document.getElementById("comment").value;
    if (checkComment == null || checkComment.trim() === "") {
        window.alert("Vui Lòng Nhập Bình Luận");
        return false;
    }
    return true;
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

