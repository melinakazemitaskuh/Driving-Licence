document.addEventListener('DOMContentLoaded', function () {

    const licenseModalEl = document.getElementById('licenseModal');
    const licenseModal = licenseModalEl ? new bootstrap.Modal(licenseModalEl) : null;

    const licenseForm = document.getElementById('licenseForm');
    const methodField = document.getElementById('methodField');
    const modalTitle = document.getElementById('modalTitle');

    const nameInput = document.getElementById('name');
    const familyInput = document.getElementById('family');
    const numberInput = document.getElementById('drivingLicenseNumber');
    const certInput = document.getElementById('certificateDate');
    const expInput = document.getElementById('expiryDate');
    const cityInput = document.getElementById('cityIssued');


    // ✅ NEW: Show toast if exists
    const toastEl = document.querySelector(".toast");
    if (toastEl) {
        const toast = new bootstrap.Toast(toastEl, {
            delay: 2500
        });
        toast.show();
    }


    // ✅ NEW: Auto open modal if validation error happened
    // thymeleaf injects this variable only when needed
    if (typeof showModal !== "undefined" && showModal === true) {
        if (licenseModal) {
            licenseModal.show();
        }
    }


    // -------- ADD --------
    window.openAddModal = function () {
        if (!licenseModal) return;

        modalTitle.innerText = "Add New Driving License";
        licenseForm.action = "/licenses";

        licenseForm.reset();

        methodField.disabled = true;
        methodField.value = "";

        licenseModal.show();
    }


    // -------- EDIT --------
    window.openEditModal = function (id, name, family, number, city, certDate, expDate) {
        if (!licenseModal) return;

        modalTitle.innerText = "Edit Driving License";
        licenseForm.action = "/licenses/" + id;

        nameInput.value = name;
        familyInput.value = family;
        numberInput.value = number;
        cityInput.value = city;
        certInput.value = certDate;
        expInput.value = expDate;

        methodField.disabled = false;
        methodField.value = "put";

        licenseModal.show();
    }


    // -------- DELETE --------
    const deleteModal = document.getElementById('deleteModal');
    const deleteForm = document.getElementById('deleteForm');

    if (deleteModal && deleteForm) {
        deleteModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const id = button.getAttribute('data-id');
            deleteForm.action = "/licenses/" + id;
        });
    }

});
