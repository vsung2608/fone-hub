// Toggle between active and deleted brands
function toggleBrandView() {
    const selector = document.getElementById('brandViewSelect');
    const activeContainer = document.getElementById('activeBrandsContainer');
    const deletedContainer = document.getElementById('deletedBrandsContainer');

    if (selector.value === 'active') {
        activeContainer.classList.remove('hidden');
        deletedContainer.classList.add('hidden');
    } else {
        activeContainer.classList.add('hidden');
        deletedContainer.classList.remove('hidden');
    }
}

// Modal functions
function openCreateModal() {
    const modal = document.getElementById('createBrandModal');
    modal.style.display = 'flex';
    modal.classList.add('active');
    // Focus vào modal để dễ sử dụng
    setTimeout(() => {
        const firstInput = modal.querySelector('input[type="text"]');
        if (firstInput) firstInput.focus();
    }, 100);
}

function closeCreateModal() {
    const modal = document.getElementById('createBrandModal');
    modal.classList.remove('active');
    setTimeout(() => {
        modal.style.display = 'none';
        document.getElementById('createBrandForm').reset();
        document.getElementById('modalImagePreviewGrid').innerHTML = '';
    }, 300); // Delay để animation chạy xong
}

// Image preview
function previewModalImages(event) {
    const file = event.target.files[0];
    const previewGrid = document.getElementById('modalImagePreviewGrid');
    previewGrid.innerHTML = '';

    if (file) {
        const reader = new FileReader();

        reader.onload = function(e) {
            const imageContainer = document.createElement('div');
            imageContainer.className = 'modal-preview-item';

            const img = document.createElement('img');
            img.src = e.target.result;
            img.alt = 'Preview';

            const removeBtn = document.createElement('button');
            removeBtn.type = 'button';
            removeBtn.className = 'modal-remove-btn';
            removeBtn.innerHTML = '<i class="fas fa-times"></i>';
            removeBtn.onclick = function() {
                imageContainer.remove();
                document.getElementById('modalFiles').value = '';
            };

            imageContainer.appendChild(img);
            imageContainer.appendChild(removeBtn);
            previewGrid.appendChild(imageContainer);
        };

        reader.readAsDataURL(file);
    }
}

// Sorting functions
function sortTable(field) {
    const currentUrl = new URL(window.location);
    const currentSortField = currentUrl.searchParams.get('sortField') || 'id';
    const currentSortDir = currentUrl.searchParams.get('sortDir') || 'asc';

    let newSortDir = 'asc';
    if (currentSortField === field && currentSortDir === 'asc') {
        newSortDir = 'desc';
    }

    currentUrl.searchParams.set('sortField', field);
    currentUrl.searchParams.set('sortDir', newSortDir);
    currentUrl.searchParams.set('page', '0');

    window.location.href = currentUrl.toString();
}

function sortDeletedTable(field) {
    sortTable(field);
}

// CRUD Operations với jQuery AJAX
function softDeleteBrand(brandId) {
    if (confirm('Bạn có chắc chắn muốn xóa thương hiệu này?')) {
        $.ajax({
            url: `/admin/brand/delete/${brandId}`,
            type: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            success: function(response) {
                location.reload();
            },
            error: function(xhr, status, error) {
                alert('Có lỗi xảy ra khi xóa thương hiệu');
            }
        });
    }
}

function restoreBrand(brandId) {
    if (confirm('Bạn có chắc chắn muốn khôi phục thương hiệu này?')) {
        $.ajax({
            url: `/admin/brand/restore/${brandId}`,
            type: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            success: function(response) {
                location.reload();
            },
            error: function(xhr, status, error) {
                alert('Có lỗi xảy ra khi khôi phục thương hiệu');
            }
        });
    }
}

function deleteBrand(brandId) {
    if (confirm('Bạn có chắc chắn muốn xóa vĩnh viễn thương hiệu này? Hành động này không thể hoàn tác!')) {
        $.ajax({
            url: `/admin/brand/delete/${brandId}`,
            type: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            success: function(response) {
                location.reload();
            },
            error: function(xhr, status, error) {
                alert('Có lỗi xảy ra khi xóa thương hiệu');
            }
        });
    }
}

// Initialize sorting icons
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const sortField = urlParams.get('sortField') || 'id';
    const sortDir = urlParams.get('sortDir') || 'asc';

    const iconElement = document.getElementById(`sort-icon-${sortField}`);
    if (iconElement) {
        iconElement.innerHTML = sortDir === 'asc' ? '↑' : '↓';
    }
});

window.onclick = function(event) {
    const createModal = document.getElementById('createBrandModal');
    const updateModal = document.getElementById('updateBrandModal');
    if (event.target === createModal) closeCreateModal();
    if (event.target === updateModal) closeUpdateModal();
};


// Sidebar functions
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
function openUpdateModalFromData(button) {
    const id = button.getAttribute('data-id');
    const name = button.getAttribute('data-name');
    const image = button.getAttribute('data-image');
    openUpdateModal(id, name, image);
}

// Update Modal functions - Thêm vào phần script
function openUpdateModal(brandId, brandName, brandImage) {
    const modal = document.getElementById('updateBrandModal');
    const form = document.getElementById('updateBrandForm');
    const nameInput = document.getElementById('updateBrandName');
    const previewGrid = document.getElementById('updateModalImagePreviewGrid');

    // Set form action
    form.action = `/admin/brand/update/${brandId}`;

    // Set current brand name
    nameInput.value = brandName;

    // Clear previous preview
    previewGrid.innerHTML = '';

    // Show current image if exists
    if (brandImage && brandImage !== 'null') {
        const imageContainer = document.createElement('div');
        imageContainer.className = 'modal-preview-item current-image';

        const img = document.createElement('img');
        img.src = brandImage;
        img.alt = 'Current Image';

        const label = document.createElement('div');
        label.className = 'current-image-label';
        label.innerHTML = '<i class="fas fa-image"></i> Ảnh hiện tại';

        imageContainer.appendChild(img);
        imageContainer.appendChild(label);
        previewGrid.appendChild(imageContainer);
    }

    // Show modal
    modal.style.display = 'flex';
    modal.classList.add('active');

    // Focus vào modal
    setTimeout(() => {
        nameInput.focus();
        nameInput.select();
    }, 100);
}

function closeUpdateModal() {
    const modal = document.getElementById('updateBrandModal');
    modal.classList.remove('active');
    setTimeout(() => {
        modal.style.display = 'none';
        document.getElementById('updateBrandForm').reset();
        document.getElementById('updateModalImagePreviewGrid').innerHTML = '';
        document.getElementById('updateModalFiles').value = '';
    }, 300);
}

// Image preview for update modal
function previewUpdateModalImages(event) {
    const file = event.target.files[0];
    const previewGrid = document.getElementById('updateModalImagePreviewGrid');

    // Remove any previous new image preview, but keep current image
    const newImagePreviews = previewGrid.querySelectorAll('.modal-preview-item:not(.current-image)');
    newImagePreviews.forEach(item => item.remove());

    if (file) {
        const reader = new FileReader();

        reader.onload = function(e) {
            const imageContainer = document.createElement('div');
            imageContainer.className = 'modal-preview-item new-image';

            const img = document.createElement('img');
            img.src = e.target.result;
            img.alt = 'New Image Preview';

            const removeBtn = document.createElement('button');
            removeBtn.type = 'button';
            removeBtn.className = 'modal-remove-btn';
            removeBtn.innerHTML = '<i class="fas fa-times"></i>';
            removeBtn.onclick = function() {
                imageContainer.remove();
                document.getElementById('updateModalFiles').value = '';
            };

            const label = document.createElement('div');
            label.className = 'new-image-label';
            label.innerHTML = '<i class="fas fa-upload"></i> Ảnh mới';

            imageContainer.appendChild(img);
            imageContainer.appendChild(removeBtn);
            imageContainer.appendChild(label);
            previewGrid.appendChild(imageContainer);
        };

        reader.readAsDataURL(file);
    }
}

// Close update modal when clicking outside
window.onclick = function(event) {
    const createModal = document.getElementById('createBrandModal');
    const updateModal = document.getElementById('updateBrandModal');

    if (event.target === createModal) {
        closeCreateModal();
    }
    if (event.target === updateModal) {
        closeUpdateModal();
    }
}