package com.example.drivingLicenceID.controller;

import com.example.drivingLicenceID.dto.DrivingLicenseDto;
import com.example.drivingLicenceID.service.DrivingLicenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/licenses")
@RequiredArgsConstructor
public class DrivingLicenseController {

    private final DrivingLicenseService drivingLicenseService;

    // --- Helper Method to Load Data (DRY) ---
    private void prepareListModel(Model model, int page, int size, String family, String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<DrivingLicenseDto> licensePage;

        if (family != null && name != null && !family.isBlank()) {
            licensePage = drivingLicenseService.findByFamilyAndName(family, name, pageable);
        } else {
            licensePage = drivingLicenseService.findAll(pageable);
        }

        model.addAttribute("licenses", licensePage);
    }

    // --- GET LIST ---
    @GetMapping
    public String getAllLicenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String family,
            @RequestParam(required = false) String name,
            Model model
    ) {

        prepareListModel(model, page, size, family, name);

        if (!model.containsAttribute("license")) {
            model.addAttribute("license", new DrivingLicenseDto());
        }

        return "license/list";
    }

    // --- GET TRASH ---
    @GetMapping("/trash")
    public String getTrash(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<DrivingLicenseDto> deletedPage = drivingLicenseService.findAllSoftDeleted(pageable);

        model.addAttribute("licenses", deletedPage);
        model.addAttribute("isTrash", true);
        return "license/list";
    }

    // --- CREATE (POST) ---
    @PostMapping
    public String createLicense(
            @Valid @ModelAttribute("license") DrivingLicenseDto licenseDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        // ✅ Cross-field validation (BindingResult)
        if (licenseDto.getCertificateDate() != null
                && licenseDto.getExpiryDate() != null
                && licenseDto.getCertificateDate().isAfter(licenseDto.getExpiryDate())) {
            result.rejectValue("certificateDate", "invalidDateOrder",
                    "Certificate date cannot be after expiry date");
        }

        if (result.hasErrors()) {
            prepareListModel(model, page, size, null, null);
            model.addAttribute("showModal", true);
            model.addAttribute("formAction", "/licenses");
            return "license/list";
        }

        drivingLicenseService.save(licenseDto);
        redirectAttributes.addFlashAttribute("successMessage", "Driving license created successfully!");
        return "redirect:/licenses";
    }

    // --- UPDATE (PUT) ---
    @PutMapping("/{id}")
    public String updateLicense(
            @PathVariable Long id,
            @Valid @ModelAttribute("license") DrivingLicenseDto licenseDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page
    ) {

        // ✅ Cross-field validation (BindingResult)
        if (licenseDto.getCertificateDate() != null
                && licenseDto.getExpiryDate() != null
                && licenseDto.getCertificateDate().isAfter(licenseDto.getExpiryDate())) {
            result.rejectValue("certificateDate", "invalidDateOrder",
                    "Certificate date cannot be after expiry date");
        }

        if (result.hasErrors()) {
            prepareListModel(model, page, 10, null, null);
            model.addAttribute("showModal", true);
            model.addAttribute("formAction", "/licenses/" + id);
            licenseDto.setId(id);
            return "license/list";
        }

        licenseDto.setId(id);
        drivingLicenseService.update(licenseDto);

        redirectAttributes.addFlashAttribute("successMessage", "Driving license updated successfully!");
        return "redirect:/licenses";
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public String deleteLicense(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        drivingLicenseService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Driving license deleted (moved to trash).");
        return "redirect:/licenses";
    }

    // --- RESTORE ---
    @PostMapping("/restore/{id}")
    public String restoreLicense(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        drivingLicenseService.restoreSoftDeletedById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Driving license restored successfully!");
        return "redirect:/licenses/trash";
    }
}
