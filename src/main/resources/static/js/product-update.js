function toggleProductMenu(event) {
    console.log('Menu clicked!'); // Thêm dòng này để test
    event.preventDefault();
    const productMenu = document.querySelector('.product-menu');
    const submenu = document.getElementById('productSubmenu');

    productMenu.classList.toggle('active');
    submenu.classList.toggle('active');
}

// Close dropdown when clicking outside
document.addEventListener('click', function(event) {
    const productMenu = document.querySelector('.product-menu');
    const submenu = document.getElementById('productSubmenu');

    if (!productMenu.contains(event.target)) {
        productMenu.classList.remove('active');
        submenu.classList.remove('active');
    }
});