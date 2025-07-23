// Image preview function
function previewImage(input) {
    const preview = document.getElementById('imagePreview');
    
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        
        reader.onload = function(e) {
            preview.innerHTML = `<img src="${e.target.result}" alt="Preview" style="max-width: 200px; max-height: 200px; border-radius: 10px; box-shadow: 0 3px 10px rgba(0,0,0,0.2);">`;
        };
        
        reader.readAsDataURL(input.files[0]);
    } else {
        preview.innerHTML = '';
    }
}

function openAuthModal(tab) {
  document.getElementById("authModal").style.display = "flex";
  switchTab(tab);
}
function closeAuthModal() {
  document.getElementById("authModal").style.display = "none";
}

function switchTab(tab) {
  const loginForm = document.getElementById("loginForm");
  const registerForm = document.getElementById("registerForm");

  if (tab === 'login') {
    loginForm.style.display = 'block';
    registerForm.style.display = 'none';
  } else {
    loginForm.style.display = 'none';
    registerForm.style.display = 'block';
  }
}

function validateRegisterForm() {
  const password = document.getElementById("regPassword").value;
  const confirm = document.getElementById("regConfirm").value;
  const errorDiv = document.getElementById("registerErrorMessage");

  if (password !== confirm) {
    errorDiv.textContent = "❗ Mật khẩu không khớp!";
    return false;
  }

  errorDiv.textContent = "";
  return true;
}

// Form validation
document.addEventListener('DOMContentLoaded', function() {
    // Add form validation for product forms
    const productForms = document.querySelectorAll('.product-form');
    
    productForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const name = form.querySelector('input[name="name"]');
            const price = form.querySelector('input[name="price"]');
            
            let isValid = true;
            let errorMessage = '';
            
            // Validate name
            if (!name.value.trim()) {
                isValid = false;
                errorMessage += 'Tên sản phẩm không được để trống.\n';
            }
            
            // Validate price
            if (!price.value || parseFloat(price.value) <= 0) {
                isValid = false;
                errorMessage += 'Giá sản phẩm phải lớn hơn 0.\n';
            }
            
            if (!isValid) {
                e.preventDefault();
                alert(errorMessage);
            }
        });
    });
    
    // Add confirmation for delete actions
    const deleteLinks = document.querySelectorAll('a[href*="delete"]');
    deleteLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            if (!confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
                e.preventDefault();
            }
        });
    });
    
    // Auto-hide messages after 5 seconds
    const messages = document.querySelectorAll('.success-message, .error-message');
    messages.forEach(message => {
        setTimeout(() => {
            message.style.opacity = '0';
            setTimeout(() => {
                message.style.display = 'none';
            }, 300);
        }, 5000);
    });
});

// Search functionality enhancements
function handleSearch() {
    const searchInput = document.querySelector('input[name="keyword"]');
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                this.form.submit();
            }
        });
    }
}

// Initialize search functionality
document.addEventListener('DOMContentLoaded', handleSearch);

// Add loading animation for forms
function addLoadingToForms() {
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function() {
            const submitBtn = this.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.innerHTML = '⏳ Đang xử lý...';
                submitBtn.disabled = true;
            }
        });
    });
}


document.addEventListener('DOMContentLoaded', addLoadingToForms);