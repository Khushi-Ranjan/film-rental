package com.filmrental.controller;

import com.filmrental.dto.request.RentFilmRequest;
import com.filmrental.dto.request.ReturnFilmRequest;
import com.filmrental.entity.Category;
import com.filmrental.exception.BusinessException;
import com.filmrental.exception.ResourceNotFoundException;
import com.filmrental.repository.CategoryRepository;
import com.filmrental.repository.CustomerRepository;
import com.filmrental.service.ActorService;
import com.filmrental.service.CustomerService;
import com.filmrental.service.FilmService;
import com.filmrental.service.InventoryService;
import com.filmrental.service.RentalService;
import com.filmrental.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebPageController {

    private final FilmService filmService;
    private final RentalService rentalService;
    private final CustomerService customerService;
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final StoreService storeService;
    private final InventoryService inventoryService;
    private final ActorService actorService;

    @ModelAttribute("allCategories")
    public List<Category> allCategories() {
        return categoryRepository.findAll(Sort.by("name"));
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("topFilms", filmService.getTopRentedFilms());
        return "index";
    }

    @GetMapping("/films")
    public String films(
            @PageableDefault(size = 24, sort = "title") Pageable pageable,
            Model model) {
        model.addAttribute("filmsPage", filmService.getFilmsPage(pageable));
        model.addAttribute("pageHeading", "Catalogue");
        model.addAttribute("subtitle", "Browse every title in the collection.");
        return "films";
    }

    @GetMapping("/films/category/{categoryId}")
    public String filmsByCategory(@PathVariable Integer categoryId, Model model) {
        model.addAttribute("films", filmService.getFilmsByCategoryId(categoryId));
        Category cat = categoryRepository.findById(categoryId).orElse(null);
        model.addAttribute("pageHeading", cat != null ? cat.getName() : "Category");
        model.addAttribute("subtitle", "Films in this category.");
        model.addAttribute("categoryId", categoryId);
        return "films-category";
    }

    @GetMapping("/films/{filmId}")
    public String filmDetail(@PathVariable Integer filmId, Model model) {
        model.addAttribute("film", filmService.getFilmById(filmId));
        return "film-detail";
    }

    @GetMapping("/rentals")
    public String rentals(Model model) {
        model.addAttribute("rentals", rentalService.getAllRentals());
        model.addAttribute("rentForm", new RentFilmRequest());
        model.addAttribute("returnForm", new ReturnFilmRequest());
        return "rentals";
    }

    @GetMapping("/rentals/customer/{customerId}")
    public String rentalsByCustomer(@PathVariable Integer customerId, Model model) {
        model.addAttribute("customerId", customerId);
        model.addAttribute("rentals", rentalService.getRentalsByCustomerId(customerId));
        return "rentals-customer";
    }

    @PostMapping("/actions/rent")
    public String rentFilm(
            @Valid @ModelAttribute("rentForm") RentFilmRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("toastError", "Customer ID and film ID are required.");
            return "redirect:/rentals";
        }
        try {
            rentalService.rentFilm(request);
            redirectAttributes.addFlashAttribute("toastSuccess", "Rental recorded. Enjoy the film.");
        } catch (BusinessException | ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("toastError", ex.getMessage());
        }
        return "redirect:/rentals";
    }

    @PostMapping("/actions/return")
    public String returnFilm(
            @Valid @ModelAttribute("returnForm") ReturnFilmRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("toastError", "Rental ID is required.");
            return "redirect:/rentals";
        }
        try {
            rentalService.returnFilm(request);
            redirectAttributes.addFlashAttribute("toastSuccess", "Return processed. Payment recorded.");
        } catch (BusinessException | ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("toastError", ex.getMessage());
        }
        return "redirect:/rentals";
    }

    @GetMapping("/customers")
    public String customers(
            @PageableDefault(size = 36, sort = "lastName") Pageable pageable,
            Model model) {
        model.addAttribute("customersPage", customerRepository.findAll(pageable));
        return "customers";
    }

    @GetMapping("/customers/{customerId}")
    public String customerDetail(@PathVariable Integer customerId, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(customerId));
        model.addAttribute("rentals", customerService.getCustomerRentals(customerId));
        model.addAttribute("payments", customerService.getCustomerPayments(customerId));
        return "customer-detail";
    }

    @GetMapping("/actors")
    public String actorsHub() {
        return "actors";
    }

    @GetMapping("/actors/{actorId}/films")
    public String actorFilms(@PathVariable Integer actorId, Model model) {
        model.addAttribute("films", actorService.getFilmsByActorId(actorId));
        model.addAttribute("subtitle", "Films featuring this actor.");
        model.addAttribute("actorId", actorId);
        return "actors-films";
    }

    @GetMapping("/stores")
    public String storesHub() {
        return "stores";
    }

    @GetMapping("/stores/{storeId}")
    public String storeDetail(@PathVariable Integer storeId, Model model) {
        model.addAttribute("store", storeService.getStoreById(storeId));
        return "store-detail";
    }

    @GetMapping("/stores/{storeId}/inventory")
    public String storeInventory(@PathVariable Integer storeId, Model model) {
        model.addAttribute("storeId", storeId);
        model.addAttribute("items", storeService.getStoreInventory(storeId));
        return "store-inventory";
    }

    @GetMapping("/inventory")
    public String inventoryHub() {
        return "inventory";
    }

    @GetMapping("/inventory/{inventoryId}")
    public String inventoryDetail(@PathVariable Integer inventoryId, Model model) {
        model.addAttribute("inventory", inventoryService.getInventoryById(inventoryId));
        return "inventory-detail";
    }

    @GetMapping("/inventory/store/{storeId}")
    public String inventoryByStore(@PathVariable Integer storeId, Model model) {
        model.addAttribute("storeId", storeId);
        model.addAttribute("inventories", inventoryService.getInventoryByStoreId(storeId));
        return "inventory-store";
    }

    @GetMapping("/inventory/film/{filmId}")
    public String inventoryByFilm(@PathVariable Integer filmId, Model model) {
        model.addAttribute("filmId", filmId);
        model.addAttribute("inventories", inventoryService.getInventoryByFilmId(filmId));
        return "inventory-film";
    }
}

